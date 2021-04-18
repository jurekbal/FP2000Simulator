package com.balwinski.panel;

import java.util.List;
import java.util.Map;

public class LogicProcessor {

    private final List<String> logicTable;
    private final boolean[] inputs;
    private final boolean[] outputs = new boolean[1000];

    private final boolean[] flags = new boolean[251];
    private final Map<Integer, Timer> timers;

    private final ExpressionParser expressionParser;

    private boolean currentValue;

    public LogicProcessor(List<String> logicTable, boolean[] inputs, Map<Integer, Timer> timers) {
        this.logicTable = logicTable;
        this.inputs = inputs;
        this.timers = timers;
        this.expressionParser = new ExpressionParser(logicTable);
    }

    public void runCycle() {

        for (int i = 1; i < logicTable.size(); i++) {
            Expression expr = expressionParser.parseLine(i);
        }

    }



}
