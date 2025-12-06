package com.jminiapp.examples;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for NotetakingState objects.
 *
 * <p>This adapter enables the Note taking app to import and export note state
 * to/from JSON files. It leverages the framework's JSONAdapter interface which
 * provides default implementations for serialization using Gson.</p>
 *
 * <p>Example JSON format:</p>
 * <pre>
 * [
 *   {
 *     "title": "Meeting Notes",
 *     "content": "Discuss project timeline and milestones."
 *   }
 * ]
 * </pre>
 */
public class NotetakingJSONAdapter implements JSONAdapter<NotetakingState> {
    @Override
    public Class<NotetakingState> getstateClass() {
        return NotetakingState.class;
    }
}