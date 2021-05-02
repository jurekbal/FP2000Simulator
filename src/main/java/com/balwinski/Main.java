package com.balwinski;

import com.balwinski.panel.Configuration;
import com.balwinski.panel.Panel;
import com.balwinski.panel.logictable.Expression;
import com.balwinski.services.LogicTableParser;
import com.balwinski.services.TextFileReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TextFileReader textFileReader = new TextFileReader();
        List<String> logicTableStrings = textFileReader.readLogic();

        LogicTableParser logicTableParser = new LogicTableParser();
        List<Expression> logicTable = logicTableParser.parseToExpressions(logicTableStrings);

        Configuration configuration = new Configuration(logicTable);

        Panel panel = new Panel(configuration);

        boolean[] inputs = panel.getInputs();
        inputs[1] = false;
        inputs[2] = true;
        inputs[4] = true;
        inputs[5] = false;
        inputs[6] = true;
        inputs[7] = false;
        inputs[8] = true;
        panel.boot();

        boolean[] outputs = panel.getOutputs();
        boolean noActiveOutputs = true;
        for (int i = 0; i < outputs.length ; i++) {
            if (outputs[i]) {
                System.out.println("Output " + i + " is Active");
                noActiveOutputs = false;
            }
        }
        if (noActiveOutputs) {
            System.out.println("No Outputs were activated");
        }

    }
}
