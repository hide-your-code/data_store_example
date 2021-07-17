package minhdtm.example.exampledatastore.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import minhdtm.example.exampledatastore.model.Person

class PersonAdapter : ListAdapter<Person, PersonViewHolder>(TASK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder =
        PersonViewHolder.create(parent)

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val TASK_COMPARATOR = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem == newItem
        }
    }
}
