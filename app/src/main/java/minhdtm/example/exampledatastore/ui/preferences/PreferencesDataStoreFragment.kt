package minhdtm.example.exampledatastore.ui.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import minhdtm.example.exampledatastore.R
import minhdtm.example.exampledatastore.databinding.FragmentPreferencesDatastoreBinding
import minhdtm.example.exampledatastore.ext.autoClear

@AndroidEntryPoint
class PreferencesDataStoreFragment : Fragment() {

    private var binding: FragmentPreferencesDatastoreBinding by autoClear()

    private val viewModel: PreferencesDataStoreViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preferences_datastore, container, false)

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@PreferencesDataStoreFragment.viewModel
        }.root
    }
}
