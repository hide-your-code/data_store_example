package minhdtm.example.exampledatastore.data.datastore

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import minhdtm.example.exampledatastore.PeopleDataStore
import minhdtm.example.exampledatastore.PersonDataStore
import minhdtm.example.exampledatastore.data.datastore.serializer.PeopleSerializer
import minhdtm.example.exampledatastore.model.Person
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProtoDataStoreHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private fun PersonDataStore.toPerson() = Person(
        name = name,
        phone = phone
    )

    private fun Person.toPersonDataStore() = PersonDataStore.newBuilder()
        .setName(name)
        .setPhone(phone)
        .build()

    private val Context.people: DataStore<PeopleDataStore> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = PeopleSerializer
    )

    //region Migration from Share Preferences to Proto DataStore
    private fun Context.sharedPreferencesMigration(): DataMigration<PeopleDataStore> = SharedPreferencesMigration(
        this,
        PEOPLE_PREFERENCES_NAME
    ) { sharedPrefs: SharedPreferencesView, currentData: PeopleDataStore ->
        // Map your sharedPrefs to your type here
        currentData
    }

    private val Context.peoplePreferences: DataStore<PeopleDataStore> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = PeopleSerializer,
        produceMigrations = { context ->
            listOf(context.sharedPreferencesMigration())
        }
    )
    //endregion

    fun getPeople(): Flow<List<Person>> = context.people.data
        .catch { exception ->
            if (exception is IOException) {
                emit(PeopleDataStore.getDefaultInstance())
            } else {
                throw exception
            }
        }
        .map { people ->
            people.peopleList.map {
                it.toPerson()
            }
        }

    fun setPerson(person: Person): Flow<Boolean> = flow {
        context.people.updateData { people ->
            val mapper = person.toPersonDataStore()
            people.toBuilder()
                .addPeople(mapper)
                .build()
        }
        emit(true)
    }.catch { exception ->
        Timber.e(exception)
        emit(false)
    }

    fun clearData(): Flow<Boolean> = flow {
        context.people.updateData {
            it.toBuilder().clearPeople().build()
        }
        emit(true)
    }.catch { exception ->
        Timber.e(exception)
        emit(false)
    }

    companion object {
        private const val DATA_STORE_FILE_NAME = "people_prefs.pb"
        private const val PEOPLE_PREFERENCES_NAME = "people_preferences_name"
    }
}
