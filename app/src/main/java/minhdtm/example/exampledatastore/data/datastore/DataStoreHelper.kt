package minhdtm.example.exampledatastore.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreHelper @Inject constructor(@ApplicationContext private val context: Context) {

    //region Init Preferences DataStore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

    // If you want migrate from SharePreference to DataStore, using this.
    private val Context.dataStoreMigrate: DataStore<Preferences> by preferencesDataStore(
        name = PREFERENCE_NAME,
        produceMigrations = {
            listOf(SharedPreferencesMigration(it, PREFERENCE_NAME))
        })
    //endregion

    private fun <T> getValue(transform: (preferences: Preferences) -> T): Flow<T> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.d("DataStore: Fail to get value")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            transform.invoke(it)
        }

    private suspend fun setValue(transform: (preference: MutablePreferences) -> Unit) = try {
        context.dataStore.edit {
            transform.invoke(it)
        }
    } catch (ioException: IOException) {
        Timber.d("DataStore: Fail to set value - Exception")
    } catch (exception: Exception) {
        Timber.d("DataStore: Fail to set value - $exception")
    }

    // Read data
    val count = getValue {
        it[KEY_COUNT] ?: 0
    }

    // Write data
    suspend fun setCount(count: Int) = setValue {
        it[KEY_COUNT] = count
    }

    companion object {
        private const val PREFERENCE_NAME = "todo"

        private val KEY_COUNT = intPreferencesKey("EXAMPLE_COUNT")
    }
}