package com.jminiapp.examples.damagecalculator;

import com.jminiapp.core.JMiniAppRunner;

public class DamageCalculatorRunner {

    public static void main(String[] args) {
        JMiniAppRunner
                .forApp(DamageCalculatorApp.class)
                .withState(DamageState.class)
                .withAdapters(new DamageJSONAdapter())
                .run(args);
    }
}
