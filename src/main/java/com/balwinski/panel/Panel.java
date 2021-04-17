package com.balwinski.panel;

import java.util.HashMap;
import java.util.Map;

public class Panel {
    private final boolean[] inputs = new boolean[301];
    private final boolean[] outputs = new boolean[999];

    private final boolean[] flags = new boolean[250];
    private Map<Integer, Timer> timers = new HashMap<>();
}
