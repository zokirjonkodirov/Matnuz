package com.intentsoft.matnuz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.intentsoft.matnuz.databinding.DictionaryItemLayoutBinding
import com.intentsoft.matnuz.models.DictionaryItem

class DictionaryAdapter: RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {

    inner class DictionaryViewHolder(val binding: DictionaryItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<DictionaryItem>() {

        override fun areItemsTheSame(oldItem: DictionaryItem, newItem: DictionaryItem): Boolean {
            return oldItem.latin == newItem.latin
        }

        override fun areContentsTheSame(oldItem: DictionaryItem, newItem: DictionaryItem): Boolean {
            return oldItem.latin == newItem.latin
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder(
            DictionaryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val word = differ.currentList[position]

        holder.binding.apply {
            tvDictionaryItem.text = word.latin
        }
    }
}