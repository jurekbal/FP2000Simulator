package com.balwinski.panel;

import java.util.HashMap;
import java.util.Map;

public class Panel {
    private final boolean[] inputs = new boolean[301];
    private final boolean[] outputs = new boolean[1000];

    private final Map<Integer, Timer> timers = new HashMap<>();
}
