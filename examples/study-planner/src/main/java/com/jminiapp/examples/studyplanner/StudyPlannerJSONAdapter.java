package com.jminiapp.examples.studyplanner;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for StudyTask objects
 */
public class StudyPlannerJSONAdapter implements JSONAdapter {

    @Override
    public Class getstateClass() {
        return StudyTask.class;
    }
}
