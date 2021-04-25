package com.balwinski.services;

import com.balwinski.panel.logictable.*;

import java.util.ArrayList;
import java.util.List;

public class LogicTableParser {

    public List<Expression> parseToExpressions(List<String> logicTableStrings) {
        List<Expression> parsedTable = new ArrayList<>();
        for (int i = 1; i < logicTableStrings.size(); i++) {
            parsedTable.add(parseLine(logicTableStrings.get(i)));
        }
        return parsedTable;
    }

    private Expression parseLine(String line) {

        String[] parts = line.split(" {2,}");
        int partsNum = parts.length;

        int logicLineNumber, parameter1 = 0, parameter2 = 0;
        Operator operator;
        Operand operand = Operand.EMPTY;
        LineType lineType;
        switch (partsNum) {
            case 2 :
            case 3 : {
                lineType = LineType.THREE_PARTS;
                logicLineNumber = Integer.parseInt(parts[0]);
                operator = parseOperator(parts[1]);
                break;
            }
            case 4 : {
                lineType = LineType.STANDARD;
                logicLineNumber = Integer.parseInt(parts[0]);
                operator = parseOperator(parts[1]);
                operand = parseOperand(parts[2]);
                parameter1 = Integer.parseInt(parts[3]);
                break;
            }
            case 5 : {
                lineType = LineType.TIMER;
                logicLineNumber = Integer.parseInt(parts[0]);
                operator = parseOperator(parts[1]);
                operand = parseOperand(parts[2]);
                parameter1 = Integer.parseInt(parts[3]);
                parameter2 = Integer.parseInt(parts[4]);
                break;
            }
            default: {
                throw new InvalidDataException("Unrecognized Line Type: " + line);
            }
        }

        Expression expression = new Expression(logicLineNumber, operator, operand, parameter1, parameter2);

        // DEBUG
//        System.out.println(lineType+"|"+operator+"|"+operand);
//        System.out.println(expression.toString());

        return expression;
    }

    private Operator parseOperator(String part) {
        switch (part) {
            case "(" : {
                return Operator.OPEN_BRACKET;
            }
            case ") =" : {
                return Operator.RESULT;
            }
            case "lub" : {
                return Operator.OR;
            }
            case "i" : {
                return Operator.AND;
            }
            case "lub (" : {
                return Operator.OR_OPEN_BRACKET;
            }
            case "i (" : {
                return Operator.AND_OPEN_BRACKET;
            }
            case ")" : {
                return Operator.CLOSING_BRACKET;
            }
            case "i nie" : {
                return  Operator.AND_NOT;
            }
            case "i nie (" : {
                return  Operator.AND_NOT_OPEN_BRACKET;
            }
            case "lub nie" : {
                return Operator.OR_NOT;
            }
            case "lub nie (" : {
                return Operator.OR_NOT_OPEN_BRACKET;
            }
            case "nie (" : {
                return Operator.NOT_OPEN_BRACKET;
            }
            case "koniec" : {
                return Operator.END;
            }
            case ") kasuj-z" : {
                return Operator.RESET_Z;
            }
            case ") ustaw-z" : {
                return Operator.SET_Z;
            }
            case ") ustaw-p" : {
                return Operator.SET_P;
            }
            case ") kasuj-p" : {
                return Operator.RESET_P;
            }
            default: {
                // In printed logic table EMPTY do not occurs - should be treated as error?
                return Operator.EMPTY;
            }
        }
    }

    private Operand parseOperand(String part) {
        switch (part) {
            case "Wejście" : {
                return Operand.INPUT;
            }
            case "Wyjście" : {
                return Operand.OUTPUT;
            }
            case "Znacznik" : {
                return Operand.FLAG;
            }
            case "Zegar" : {
                return Operand.TIMER;
            }
            case "nie Wej" : {
                return Operand.NOT_INPUT;
            }
            case "nie Wyj" : {
                return Operand.NOT_OUTPUT;
            }
            case "nie Znacz" : {
                return Operand.NOT_FLAG;
            }
            case "nie Zegar" : {
                return Operand.NOT_TIMER;
            }
            default: {
                return Operand.ERROR;
            }
        }
    }
}
