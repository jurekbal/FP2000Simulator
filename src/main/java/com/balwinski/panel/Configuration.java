package com.balwinski.panel;

import com.balwinski.panel.logictable.Expression;

import java.util.ArrayList;
import java.util.List;

/*
Configuration class contains panel configuration witch consists of:
1. Parsed logicTable

in future implementations:
2. Inputs detailed data (semi-parsed or fully-parsed)
3. Outputs detailed data (semi-parsed or fully-parsed)
4. Other configuration settings
 */
public class Configuration {

    private final List<Expression> logicTable;

    public Configuration(List<Expression> logicTable) {
        this.logicTable = logicTable;
    }

    public List<Expression> getLogicTable() {
        return logicTable;
    }
}
