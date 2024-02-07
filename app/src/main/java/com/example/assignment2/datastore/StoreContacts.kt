package com.example.assignment2.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreContacts(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Contacts")

        val CONTACT_NAME_KEY = stringPreferencesKey("contact_name")
        val CONTACT_NUM_KEY = stringPreferencesKey("contact_num")
    }

    val getName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[CONTACT_NAME_KEY] ?: ""
        }

    val getNum: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[CONTACT_NUM_KEY] ?: ""
        }

    suspend fun saveInfo(name: String, number: String) {
        context.dataStore.edit { preferences ->
            preferences[CONTACT_NAME_KEY] = name
            preferences[CONTACT_NUM_KEY] = number
        }
    }
}