package com.hieupt.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 7/30/2022.
 */
abstract class HeaderDecoratorAdapter(
    delegateAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
) : DecoratorAdapter<RecyclerView.ViewHolder>(delegateAdapter) {

    protected abstract val headerViewType: Int

    protected abstract fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    protected abstract fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            headerViewType -> onCreateHeaderViewHolder(parent)
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (getItemViewType(position)) {
            headerViewType -> onBindHeaderViewHolder(holder, position)
            else -> super.onBindViewHolder(holder, getDelegateAdapterPosition(position))
        }

    override fun calculateItemCount(delegateItemCount: Int): Int = delegateItemCount + HEADER_COUNT

    override fun getItemViewType(position: Int): Int = when (position) {
        HEADER_POSITION -> headerViewType
        else -> super.getItemViewType(getDelegateAdapterPosition(position))
    }

    override fun getDelegateAdapterPosition(basePosition: Int): Int =
        (basePosition - HEADER_COUNT).coerceAtLeast(0)

    override fun getParentAdapterPosition(delegatePosition: Int): Int =
        delegatePosition + HEADER_COUNT

    companion object {
        const val HEADER_POSITION = 0
        const val HEADER_COUNT = 1
    }
}