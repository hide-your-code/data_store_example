package minhdtm.example.exampledatastore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import minhdtm.example.exampledatastore.R
import minhdtm.example.exampledatastore.databinding.ItemPersonBinding
import minhdtm.example.exampledatastore.ext.executeAfter
import minhdtm.example.exampledatastore.model.Person

class PersonViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {

    internal fun bind(item: Person) {
        binding.executeAfter {
            person = item
        }
    }

    companion object {
        fun create(parent: ViewGroup): PersonViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
            val binding = ItemPersonBinding.bind(view)
            return PersonViewHolder(binding)
        }
    }
}
