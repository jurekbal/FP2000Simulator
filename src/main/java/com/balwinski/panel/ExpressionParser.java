package com.balwinski.panel;

import java.util.List;

public class ExpressionParser {
    private static final int LOGIC_TABLE_LINE_LENGTH = 44;

    private final List<String> logicTable;

    public ExpressionParser(List<String> logicTable) {
        this.logicTable = logicTable;
    }

    public Expression parseLine(int i) {
//        if (logicTable.get(i).length() != LOGIC_TABLE_LINE_LENGTH) {
//            throw new InvalidDataException("Incorrect logic table data line length:{" + logicTable.get(i)
//                    + "} should be " + LOGIC_TABLE_LINE_LENGTH + " but is " + logicTable.get(i).length());
//        }

//         if line.length < 3 then... error
        String[] parts = logicTable.get(i).split(" {2,}");
        int partsNum = parts.length;
        LineType lineType;
        switch (partsNum) {
            case 3 : {
                lineType = LineType.THREE_PARTS;
                break;
            }
            case 4 : {
                lineType = LineType.STANDARD;
                break;
            }
            case 5 : {
                lineType = LineType.TIMER;
                break;
            }
            default: {
                throw new InvalidDataException("Unrecognized Line Type: " + logicTable.get(i));
            }
        }
        // DEBUG
        System.out.println(lineType);

//        int line = Integer.parseInt(parts[0]);
//        Operator operator = parseOperator(parts[1]);
//        Operand operand;
//        int parameter1;
//        if (parts.length == 3) {
//            operand = Operand.EMPTY;
//            parameter1 = Integer.parseInt(parts[2]);
//        } else if (parts.length == 4) {
//            operand = parseOperand(parts[2]);
//            parameter1 = Integer.parseInt(parts[3]);
//        }
        return null;
    }


}
