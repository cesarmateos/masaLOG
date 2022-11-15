package com.example.masalog.configuracion

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.masalog.configuraEtiqueta.Lenguaje
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class Configuracion(private val context: Context) {

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("configuracion")
        val LENGUAJE_KEY = booleanPreferencesKey("lenguaje_seleccionado")
    }

    val getLenguaje: Flow<Boolean?> = context.dataStoree.data
        .map { preferences -> preferences[LENGUAJE_KEY] ?: true }

    suspend fun cambiarLenguaje(nuevo: Boolean) {
        context.dataStoree.edit { preferences ->
            preferences[LENGUAJE_KEY] = nuevo
        }
    }

}


