package minhdtm.example.exampledatastore.data

import kotlinx.coroutines.flow.Flow
import minhdtm.example.exampledatastore.data.datastore.ProtoDataStoreHelper
import minhdtm.example.exampledatastore.model.Person
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProtoDataStoreRepository @Inject constructor(private val proto: ProtoDataStoreHelper) {

    fun setPerson(person: Person) : Flow<Boolean> = proto.setPerson(person)

    fun getPeople(): Flow<List<Person>> = proto.getPeople()

    fun clearData() = proto.clearData()
}
