package minhdtm.example.exampledatastore.ui.proto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import minhdtm.example.exampledatastore.R
import minhdtm.example.exampledatastore.databinding.FragmentProtoDatastoreBinding
import minhdtm.example.exampledatastore.ext.autoClear
import minhdtm.example.exampledatastore.ext.nonNullEventObserve
import minhdtm.example.exampledatastore.ext.nonNullObserver
import minhdtm.example.exampledatastore.ui.adapter.PersonAdapter

@AndroidEntryPoint
class ProtoDataStoreFragment : Fragment() {

    private var binding: FragmentProtoDatastoreBinding by autoClear()

    private val viewModel: ProtoDataStoreViewModel by viewModels()

    private var adapter: PersonAdapter by autoClear()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_proto_datastore, container, false)
        return binding.apply {
            viewModel = this@ProtoDataStoreFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        observer()

        binding.btSave.setOnClickListener {
            val name = binding.etFullName.text.toString()
            val phone = binding.etPhone.text.toString()

            viewModel.savePerson(name, phone)
        }
    }

    private fun setupAdapter() {
        adapter = PersonAdapter()
        binding.rvPeople.adapter = adapter
    }

    private fun observer() {
        viewModel.run {
            getPeople.nonNullObserver(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            errorMessage.nonNullEventObserve(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            clearDataSuccess.nonNullEventObserve(viewLifecycleOwner) {
                Toast.makeText(
                    requireContext(),
                    R.string.fragment_proto_datastore_success_clear_data,
                    Toast.LENGTH_SHORT
                ).show()
            }

            saveDataSuccess.nonNullEventObserve(viewLifecycleOwner) {
                binding.etPhone.text?.clear()
                binding.etFullName.text?.clear()

                Toast.makeText(
                    requireContext(),
                    R.string.fragment_proto_datastore_success_save_data,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}
