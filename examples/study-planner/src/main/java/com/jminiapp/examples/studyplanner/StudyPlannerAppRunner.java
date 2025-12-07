package com.jminiapp.examples.studyplanner;

import com.jminiapp.core.engine.JMiniAppRunner;

/**
 * Bootstrap class to configure and run the study planner app.
 */
public class StudyPlannerAppRunner {

    public static void main(String[] args) {
        JMiniAppRunner
                .forApp(StudyPlannerApp.class)
                .withState(StudyTask.class)
                .withAdapters(new StudyPlannerJSONAdapter())
                .named("StudyPlanner")
                .run(args);
    }
}
