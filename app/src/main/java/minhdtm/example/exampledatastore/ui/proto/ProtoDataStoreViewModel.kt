package minhdtm.example.exampledatastore.ui.proto

import android.content.Context
import android.telephony.PhoneNumberUtils
import androidx.annotation.StringRes
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import minhdtm.example.exampledatastore.R
import minhdtm.example.exampledatastore.data.ProtoDataStoreRepository
import minhdtm.example.exampledatastore.util.Event
import minhdtm.example.exampledatastore.model.Person
import javax.inject.Inject

@HiltViewModel
class ProtoDataStoreViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // Don't care about that. This is not have memory leak.
    private val protoRepository: ProtoDataStoreRepository
) : ViewModel() {

    val getPeople = protoRepository.getPeople().asLiveData()

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _saveDataSuccess = MutableLiveData<Event<Any>>()
    val saveDataSuccess: LiveData<Event<Any>> = _saveDataSuccess

    private val _clearDataSuccess = MutableLiveData<Event<Any>>()
    val clearDataSuccess: LiveData<Event<Any>> = _clearDataSuccess

    fun savePerson(name: String, phone: String) {
        viewModelScope.launch {
            if (validate(name, phone)) {
                val person = Person(name, phone)

                protoRepository.setPerson(person).collect {
                    if (it) {
                        _saveDataSuccess.value = Event(Any())
                    } else {
                        onError(R.string.fragment_proto_datastore_error_save_data)
                    }
                }
            }
        }
    }

    fun clearData() {
        viewModelScope.launch {
            protoRepository.clearData().collect {
                if (it) {
                    _clearDataSuccess.value = Event(Any())
                } else {
                    onError(R.string.fragment_proto_datastore_error_clear_data)
                }
            }
        }
    }

    private fun validate(name: String, phone: String): Boolean = when {
        name == "null" || phone == "null" || name.isBlank() || phone.isBlank() -> {
            onError(R.string.fragment_proto_datastore_error_name_or_number_null)
            false
        }
        phone.length < 10 || !PhoneNumberUtils.isGlobalPhoneNumber(phone) -> {
            onError(R.string.fragment_proto_datastore_error_number_must_have_10_digits)
            false
        }
        else -> {
            true
        }
    }

    private fun onError(@StringRes id: Int) {
        _errorMessage.value = Event(context.getString(id))
    }
}
