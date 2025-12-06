package com.jminiapp.examples.bmi;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for BmiState objects.
 *
 * <p>This adapter enables the Bmi app to import and export bmi state
 * to/from JSON files. It leverages the framework's JSONAdapter interface which
 * provides default implementations for serialization using Gson.</p>
 *
 * <p>Example JSON format:</p>
 * <pre>
 * [
 *   {
 *     "value": 42
 *   }
 * ]
 * </pre>
 */
public class BmiJSONAdapter implements JSONAdapter<BmiState> {

    @Override
    public Class<BmiState> getstateClass() {
        return BmiState.class;
    }
}
