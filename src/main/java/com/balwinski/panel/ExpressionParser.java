package com.balwinski.panel;

import java.util.List;

public class ExpressionParser {

    private final List<String> logicTable;

    public ExpressionParser(List<String> logicTable) {
        this.logicTable = logicTable;
    }

    public Expression parseLine(int i) {

        String[] parts = logicTable.get(i).split(" {2,}");
        int partsNum = parts.length;

        int logicLineNumber, parameter1, parameter2;
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
                throw new InvalidDataException("Unrecognized Line Type: " + logicTable.get(i));
            }
        }

        // DEBUG
        System.out.println(lineType+"|"+operator+"|"+operand);

        return null;
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

            // TODO implement rest of operators

            case "koniec" : {
                return Operator.END;
            }
            case ")kasuj-z" : {
                return Operator.RESET_Z;
            }
            case ")ustaw-z" : {
                return Operator.SET_Z;
            }
            case ")ustaw-p" : {
                return Operator.SET_P;
            }
            case ")kasuj-p" : {
                return Operator.RESET_P;
            }
            default: {
                return null;
            }

        }
        // TODO check "kasuj-x/ustaw-x" matching
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
            default: {
                return null;
            }
        }
        // TODO implementation of exotic operands not_ ...
    }


}
