package minhdtm.example.exampledatastore.data

import kotlinx.coroutines.flow.Flow
import minhdtm.example.exampledatastore.data.datastore.DataStoreHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataStoreRepository @Inject constructor(private val dataStore: DataStoreHelper) {

    suspend fun setCount(count: Int) {
        dataStore.setCount(count)
    }

    fun getCount(): Flow<Int> = dataStore.count
}
