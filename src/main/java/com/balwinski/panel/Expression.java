package com.balwinski.panel;

public class Expression {
    private int line;
    private Operator operator;
    private Operand operand;
    private int parameter1;
    private int parameter2;

    public Expression(int line, Operator operator, Operand operand, int parameter1, int parameter2) {
        this.line = line;
        this.operator = operator;
        this.operand = operand;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Operand getOperand() {
        return operand;
    }

    public void setOperand(Operand operand) {
        this.operand = operand;
    }

    public int getParameter1() {
        return parameter1;
    }

    public void setParameter1(int parameter1) {
        this.parameter1 = parameter1;
    }

    public int getParameter2() {
        return parameter2;
    }

    public void setParameter2(int parameter2) {
        this.parameter2 = parameter2;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "line=" + line +
                ", operator=" + operator +
                ", operand=" + operand +
                ", parameter1=" + parameter1 +
                ", parameter2=" + parameter2 +
                '}';
    }
}


