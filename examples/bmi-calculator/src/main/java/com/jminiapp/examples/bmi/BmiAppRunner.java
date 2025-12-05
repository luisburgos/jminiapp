package com.jminiapp.examples.bmi;

import com.jminiapp.core.engine.JMiniAppRunner;

/**
 * Runner class for the BMI Calculator application.
 * 
 * This class configures and launches the BMI Calculator app with:
 * - BmiApp as the main application class
 * - BmiState as the data model
 * - JSON adapter for import/export functionality
 */
public class BmiAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(BmiApp.class)
            .withState(BmiState.class)
            .withAdapters(new BmiJSONAdapter())
            //.withResourcesPath("test-data/")  // Custom import/export path
            .named("BmiCalculator")
            .run(args);
    }
}
