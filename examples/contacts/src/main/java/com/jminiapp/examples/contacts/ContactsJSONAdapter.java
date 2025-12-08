package com.jminiapp.examples.contacts;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for ContactsState objects.
 *
 * <p>This adapter enables the Contacts app to import and export contact state
 * to/from JSON files. It leverages the framework's JSONAdapter interface which
 * provides default implementations for serialization using Gson.</p>
 *
 * <p>Example JSON format:</p>
 * <pre>
 * [
 *   {
 *     "name": "John Doe",
 *     "phoneNumber": "123-456-7890"
 *   }
 * ]
 * </pre>
 */

public class ContactsJSONAdapter implements JSONAdapter<ContactsState> {
    @Override
    public Class<ContactsState> getstateClass() {
        return ContactsState.class;
    }
}