package br.edu.ifspcjo.fic.pdma.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "email")

object UserPreferences {

    private val EMAIL_KEY = stringPreferencesKey("email_key")

    suspend fun saveEmail(context: Context, email: String){
        context.dataStore.edit {
            preferences -> preferences[EMAIL_KEY] = email
        }
    }

    fun getEmail(context: Context): Flow<String>{
        return context.dataStore.data.map {
            preferences -> preferences[EMAIL_KEY] ?: ""
        }
    }
}