package minhdtm.example.exampledatastore.data.datastore.serializer

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import minhdtm.example.exampledatastore.PeopleDataStore
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream

object PeopleSerializer : Serializer<PeopleDataStore> {

    override val defaultValue: PeopleDataStore = PeopleDataStore.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): PeopleDataStore = try {
        PeopleDataStore.parseFrom(input)
    } catch (ex: InvalidProtocolBufferException) {
        Timber.d("Wrong read proto!")
        defaultValue
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: PeopleDataStore, output: OutputStream) {
        try {
            t.writeTo(output)
        } catch (ex: Exception) {
            Timber.d("Cannot write proto!")
        }
    }
}
