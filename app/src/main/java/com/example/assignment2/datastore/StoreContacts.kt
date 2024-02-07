package com.example.assignment2.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class MyContacts(val name: String, val num: String)
class StoreContacts(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Contacts")

        val CONTACT_KEY = stringPreferencesKey("data")
    }

//    val getName: Flow<String?> = context.dataStore.data
//        .map { preferences ->
//            preferences[CONTACT_NAME_KEY] ?: ""
//        }
//
//    val getNum: Flow<String?> = context.dataStore.data
//        .map { preferences ->
//            preferences[CONTACT_NUM_KEY] ?: ""
//        }

    fun getAllContacts(): Flow<List<MyContacts>> {
        return context.dataStore.data.map { preferences ->
            val savedData = preferences[CONTACT_KEY]
            savedData?.split(",")?.mapNotNull { entry ->
                val (name, num) = entry.split(" ")
                MyContacts(name, num)
            }?: emptyList()
        }
    }

    suspend fun saveInfo(data: MyContacts) {
        context.dataStore.edit { preferences ->
            val existingData = preferences[CONTACT_KEY]?.split(",") ?: emptyList()
            val updatedData = existingData + "${data.name} ${data.num}"
            preferences[CONTACT_KEY] = updatedData.joinToString(",")
        }
    }
}