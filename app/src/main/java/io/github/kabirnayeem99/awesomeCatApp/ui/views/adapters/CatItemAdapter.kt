package io.github.kabirnayeem99.awesomeCatApp.ui.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.kabirnayeem99.awesomeCatApp.databinding.ListItemCatBinding
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo

class CatItemAdapter : RecyclerView.Adapter<CatItemAdapter.CatViewHolder>() {

    fun submitCatFactList(facts: List<CatVo>) = differ.submitList(facts)


    class CatViewHolder(private val binding: ListItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: CatVo) {
            binding.cat = cat
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CatVo>() {
        override fun areItemsTheSame(
            oldItem: CatVo,
            newItem: CatVo
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CatVo,
            newItem: CatVo
        ): Boolean {
            return oldItem.text == newItem.text && oldItem.updatedAt == newItem.updatedAt
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemCatBinding = ListItemCatBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(listItemCatBinding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = differ.currentList[position]
        holder.bind(cat)
    }

    override fun getItemCount(): Int = differ.currentList.size
}