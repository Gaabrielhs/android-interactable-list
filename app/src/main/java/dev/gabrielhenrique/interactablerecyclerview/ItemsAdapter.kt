package dev.gabrielhenrique.interactablerecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.gabrielhenrique.interactablerecyclerview.databinding.ItemLayoutBinding

data class Item(val id: String, val title: String, val checked: Boolean = false)

class ItemsAdapter(val itemClicked: (Item) -> Unit) : ListAdapter<Item, ItemsAdapter.ItemViewHolder>(diffUtil) {

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.title.text = item.title
            binding.checkIcon.isVisible = item.checked
            binding.card.setOnClickListener { itemClicked(item) }
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}