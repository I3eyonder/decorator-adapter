package com.hieupt.adapter.sample

import androidx.recyclerview.widget.DiffUtil

object ItemDiffCallback : DiffUtil.ItemCallback<Int>() {

    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
}