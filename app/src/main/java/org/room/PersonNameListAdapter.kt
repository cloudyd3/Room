package org.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.room.PersonNameListAdapter.PersonNameHolder


class PersonNameListAdapter : ListAdapter<PersonName, PersonNameHolder>(NAMES_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonNameHolder {
        return PersonNameHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PersonNameHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.first_name + " " +current.last_name)
    }

    class PersonNameHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            nameItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PersonNameHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return PersonNameHolder(view)
            }
        }
    }

    companion object {
        private val NAMES_COMPARATOR = object : DiffUtil.ItemCallback<PersonName>() {
            override fun areItemsTheSame(oldItem: PersonName, newItem: PersonName): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: PersonName, newItem: PersonName): Boolean {
                return oldItem.first_name == newItem.first_name && oldItem.last_name == newItem.last_name
            }
        }
    }
}