package com.balwinski.panel;

import com.balwinski.services.TextFileReader;
import org.junit.jupiter.api.Test;

import java.util.List;

class ExpressionParserTest {

    @Test
    void mustThrowExceptionOnIncorrectLieType() {
        // TODO prepare apropriate test data
        TextFileReader textFileReader = new TextFileReader();
        List<String> lines = textFileReader.readLogic();
        ExpressionParser expressionParser = new ExpressionParser(lines);

        for (int i = 1; i < lines.size(); i++) {
            System.out.print(lines.get(i) + " ");
            expressionParser.parseLine(i);
        }
    }

}