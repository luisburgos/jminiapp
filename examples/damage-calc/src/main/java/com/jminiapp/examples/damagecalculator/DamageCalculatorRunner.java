package com.jminiapp.examples.damagecalculator;

import com.jminiapp.core.engine.JMiniAppRunner;

public class DamageCalculatorRunner {

    public static void main(String[] args) {
        JMiniAppRunner
                .forApp(DamageCalculatorApp.class)
                .withState(DamageState.class)
                .run(args);
    }
}


