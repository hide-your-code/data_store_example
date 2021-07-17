package minhdtm.example.exampledatastore.data.datastore.serializer

import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import minhdtm.example.exampledatastore.PersonDataStore
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream

object PersonSerializer : Serializer<PersonDataStore> {

    override val defaultValue: PersonDataStore = PersonDataStore.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): PersonDataStore = try {
        PersonDataStore.parseFrom(input)
    } catch (ex: InvalidProtocolBufferException) {
        Timber.d("Wrong read proto!")
        defaultValue
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: PersonDataStore, output: OutputStream) {
        try {
            t.writeTo(output)
        } catch (ex: Exception) {
            Timber.d("Cannot write proto!")
        }
    }
}
