package com.example.uts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.databinding.ItemNameBinding

class NameAdapter(
    private var names: List<Name>,
    private val viewModel: NameViewModel
) : RecyclerView.Adapter<NameAdapter.NameViewHolder>() {

    inner class NameViewHolder(val binding: ItemNameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: Name) {
            binding.name = name
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val binding = ItemNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(names[position])
    }

    override fun getItemCount(): Int = names.size

    fun updateList(newList: List<Name>) {
        names = newList
        notifyDataSetChanged()
    }
}