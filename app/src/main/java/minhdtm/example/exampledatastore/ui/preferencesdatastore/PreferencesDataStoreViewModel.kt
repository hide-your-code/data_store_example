package minhdtm.example.exampledatastore.ui.preferencesdatastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import minhdtm.example.exampledatastore.data.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class PreferencesDataStoreViewModel @Inject constructor(private val repository: DataStoreRepository) : ViewModel() {

    val count: LiveData<Int> = repository.getCount().asLiveData()

    fun clickSet() {
        viewModelScope.launch {
            count.value?.let {
                val newCount = it.plus(1)
                repository.setCount(newCount)
            }
        }
    }
}
