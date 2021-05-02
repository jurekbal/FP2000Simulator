package com.balwinski.panel;

import com.balwinski.panel.logictable.*;
import com.balwinski.panel.logictable.Timer;

import java.util.*;

public class Panel {

    private final Set<Operator> openingOperators = new HashSet<>(Arrays.asList(
            Operator.OPEN_BRACKET, Operator.NOT_OPEN_BRACKET, Operator.END));
    private final Set<Operator> continueOperators = new HashSet<>(Arrays.asList(
            Operator.AND, Operator.AND_NOT, Operator.AND_OPEN_BRACKET, Operator.AND_NOT_OPEN_BRACKET,
            Operator.OR, Operator.OR_NOT, Operator.OR_OPEN_BRACKET, Operator.OR_NOT_OPEN_BRACKET
    ));
    private final Set<Operator> resultSettingOperators = new HashSet<>(Arrays.asList(
            Operator.RESULT, Operator.SET_P, Operator.RESET_P, Operator.SET_Z, Operator.RESET_Z
    ));

    private final Set<Operator> openingOperatorTemp = new HashSet<>(Arrays.asList(
            Operator.OPEN_BRACKET));
    private final Set<Operator> closingOperatorTemp = new HashSet<>(Arrays.asList(
            Operator.RESULT));

    private final Configuration configuration;
    private List<Expression> logicTable;

    private final boolean[] inputs = new boolean[301];
    private final boolean[] outputs = new boolean[1000];
    private final Map<Integer, Timer> timers = new HashMap<>();
    private final Map<Integer, Boolean> flags = new HashMap<>();

    public boolean[] getInputs() {
        return inputs;
    }

    public boolean[] getOutputs() {
        return outputs;
    }

    private int currentLine;
    private int statementLevel;

    public Panel(Configuration configuration) {
        this.configuration = configuration;
    }

    public void boot() {
        logicTable = configuration.getLogicTable();
        try {
            runTable();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private void runTable() throws InvalidDataException {

        Expression expression;
        Operator operator;
        boolean statementResult;
        statementLevel = 0;
        currentLine = 0;

        while (currentLine < logicTable.size()) {

            // Process one statement each iteration
            // Statement begins with "(" or "not (" at level 0 and ends with one of result setting operators
            expression = logicTable.get(currentLine);
            operator = expression.getOperator();

            switch (operator) {
                case OPEN_BRACKET: {
                    statementResult = processStatement();
                    break;
                }
                case NOT_OPEN_BRACKET: {
                    statementResult = !processStatement();
                    break;
                }
                case END: {
                    return;
                }
                default: {
                    throw new InvalidDataException("Unexpected Operator " + operator +
                            " at line " + expression.getLine() + "(" + currentLine + ") - expecting begin of statement or END");
                }
            }

            // write statement result
            expression = logicTable.get(currentLine);
            operator = expression.getOperator(); // should be result operator - checked by processStatement()

            if (resultSettingOperators.contains(operator)) {
                setStatementResult(statementResult, expression);
                currentLine++;
            } else {
                throw new InvalidDataException("Unsupported result operator: " + operator + " at line " +
                        expression.getLine() + " (" + currentLine + ")");
            }
        }
    }


    private boolean processStatement() throws InvalidDataException {
        Expression expression = logicTable.get(currentLine);
        Operator operator;
        Operand operand = expression.getOperand();
        if(++statementLevel > 7) {
            throw new StatementLevelTooDeepException("Exceeded at line " + expression.getLine() +
                    " (" + currentLine + ")");
        };

        boolean currentValue = getValue(expression);


        while(++currentLine < logicTable.size()) {
            expression = logicTable.get(currentLine);
            operator = expression.getOperator();

            switch (operator) {
                case RESULT: {
                    if(--statementLevel != 0) {
                        throw new UnexpectedOperatorException("Operator " + operator +
                                " is not allowed at statement level " + statementLevel + ". Check brackets at line "
                                + expression.getLine() + " (" + currentLine + ")");
                    }
                    return currentValue;
                }
                // TODO implement rest of closing operators
                case AND: {
                    currentValue = getValue(expression) && currentValue;
                    break;
                }
                case AND_NOT: {
                    currentValue = !getValue(expression) && currentValue;
                    break;
                }
                case AND_OPEN_BRACKET: {
                    // make sure JVM will not ignore to call processStatement when currentValue is false!
                    // here: it has to be first operand in expression! (or maybe better use local variable before)
                    // It's a bad design I suppose...
                    currentValue = processStatement() && currentValue;
                    break;
                }
                case AND_NOT_OPEN_BRACKET: {
                    currentValue = !processStatement() && currentValue;
                    break;
                }
                case OR: {
                    currentValue = getValue(expression) || currentValue;
                    break;
                }
                case OR_NOT: {
                    currentValue = !getValue(expression) || currentValue;
                    break;
                }
                case CLOSING_BRACKET: {
                    if(--statementLevel < 1) {
                        throw new UnexpectedOperatorException("Operator " + operator +
                                " is not allowed at statement level " + statementLevel + ". Check brackets at line "
                                + expression.getLine() + " (" + currentLine + ")");
                    }
                    return currentValue;
                }

                default: {
                    throw new UnexpectedOperatorException("Unsupported operator at line " + expression.getLine()
                            + "(" + currentLine + ")");
                }


            }

        };

        return currentValue;
    }

    private void setStatementResult(boolean statementResult, Expression expression) throws InvalidDataException {
        switch (expression.getOperand()) {
            case OUTPUT: {
                outputs[expression.getParameter1()] = statementResult;
                break;
            }
            case FLAG: {
                flags.put(expression.getParameter1(), statementResult);
                break;
            }
            // TODO implement TIMER, SET_P?, SET_Z?
            default: {
                throw new InvalidDataException("Unsupported or unexpected operand " + expression.getOperand() +
                        " at line " + expression.getLine() + " (" + currentLine + ")");
            }
        }
    }

    private boolean getValue(Expression expression) {
        Operand operand = expression.getOperand();
        boolean currentValue;
        switch (operand) {
            case INPUT: {
                currentValue = inputs[expression.getParameter1()];
                break;
            }
            case FLAG: {
                currentValue = flags.getOrDefault(expression.getParameter1(), false);
                break;
            }
            // TODO rest cases here
            default: {
                throw new InvalidDataException("Unexpected Operand " + operand +
                        " at line " + expression.getLine() + " (" + currentLine + ")");
            }
        }

        return currentValue;
    }


}
