package com.jminiapp.examples.daycare;

import com.jminiapp.core.engine.JMiniAppRunner;

public class DaycareAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(DaycareApp.class)
            .withState(DaycareState.class)
            .withAdapters(new DaycareJSONAdapter())
            // We force a specific file for easy persistence checking
            // In a real scenario, this matches the App name or config
            .named("DaycareData") 
            .run(args);
    }
}