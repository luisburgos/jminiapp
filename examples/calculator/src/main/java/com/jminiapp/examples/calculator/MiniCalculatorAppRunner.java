package com.jminiapp.examples.calculator;

import com.jminiapp.core.engine.JMiniAppRunner;

public class MiniCalculatorAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(MiniCalculatorApp.class)
            .withState(MiniCalculatorModel.class)
            .withAdapters(new CalculatorJSONAdapter())
            .named("MiniCalculator")
            .run(args);
    }
}
