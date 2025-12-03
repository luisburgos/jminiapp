package com.jminiapp.examples.calculator;

import com.jminiapp.core.engine.JMiniAppRunner;

public class MiniCalculatorAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner.forApp(MiniCalculatorApp.class).run(args);
    }
}
