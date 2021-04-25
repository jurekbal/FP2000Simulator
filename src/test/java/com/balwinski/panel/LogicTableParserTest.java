package com.balwinski.panel;

import com.balwinski.panel.logictable.Expression;
import com.balwinski.services.LogicTableParser;
import com.balwinski.services.TextFileReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LogicTableParserTest {

    private static final int NUM_OF_HEADER_LINES = 1;

    @Test
    void resultMustContainsSameNumberOfExpressions() {
        // TODO prepare appropriate test data and assertions (or better: sensible test cases)
        TextFileReader textFileReader = new TextFileReader();
        List<String> logicTableStrings = textFileReader.readLogic();
        LogicTableParser logicTableParser = new LogicTableParser();

        List<Expression> logicTable = logicTableParser.parseToExpressions(logicTableStrings);

        assertThat(logicTable.size()).isEqualTo(logicTableStrings.size() - NUM_OF_HEADER_LINES);
    }

}