package com.hieupt.adapter.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.adapter.sample.databinding.ItemNumberBinding

/**
 * Created by HieuPT on 8/1/2022.
 */
class ItemAdapter : ListAdapter<Int, RecyclerView.ViewHolder>(ItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        LayoutInflater.from(parent.context).run {
            ItemViewHolder(ItemNumberBinding.inflate(this, parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(getItem(position))
        }
    }

    class ItemViewHolder(private val viewBinding: ItemNumberBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(number: Int) {
            viewBinding.root.text = number.toString()
        }
    }
}