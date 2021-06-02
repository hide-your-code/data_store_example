package minhdtm.example.exampledatastore.ui.preferencesdatastore

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

@AndroidEntryPoint
class PreferencesDataStoreFragment : Fragment() {

    private lateinit var binding: FragmentPreferencesDatastoreBinding

    private val viewModel: PreferencesDataStoreViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preferences_datastore, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}
