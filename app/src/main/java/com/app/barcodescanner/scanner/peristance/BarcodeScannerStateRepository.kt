package com.app.barcodescanner.scanner.peristance

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.barcodescanner.scanner.presentation.model.BarcodeScannerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore("barcode_scanner_state")

@Singleton
class BarcodeScannerStateRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val APP_STATE_KEY = stringPreferencesKey("barcode_scanner_state")
    }

    suspend fun saveAppState(appState: BarcodeScannerState) {
        try {
            val serializable = appState.toSerializable()
            val json = Json.encodeToString(BarcodeScannerStoreState.serializer(), serializable)

            dataStore.edit { preferences ->
                preferences[APP_STATE_KEY] = json
            }
        } catch (e: Exception) {
            // Handle serialization error
            e.printStackTrace()
        }
    }

    fun getAppState(): Flow<BarcodeScannerState?> {
        return dataStore.data.map { preferences ->
            preferences[APP_STATE_KEY]?.let { json ->
                try {
                    Json.decodeFromString(BarcodeScannerStoreState.serializer(), json).toAppState()
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}