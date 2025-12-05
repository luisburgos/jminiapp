package com.jminiapp.examples.miniuber;

import com.jminiapp.core.engine.JMiniAppRunner;

public class MiniUberAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(MiniUberApp.class)
            .withState(Driver.class)
            .withAdapters(new DriverJSONAdapter())
            .named("MiniUber")
            .run(args);
    }
}

