package com.hieupt.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 7/30/2022.
 */
abstract class DecoratorAdapter<VH : RecyclerView.ViewHolder>(
    private var _delegateAdapter: RecyclerView.Adapter<VH>? = null
) : RecyclerView.Adapter<VH>() {

    open fun calculateItemCount(delegateItemCount: Int): Int = delegateItemCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return delegateAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        delegateAdapter.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return delegateAdapter.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return delegateAdapter.getItemId(position)
    }

    override fun onViewRecycled(holder: VH) {
        delegateAdapter.onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        delegateAdapter.onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        delegateAdapter.onViewAttachedToWindow(holder)
        super.onViewAttachedToWindow(holder)
    }

    open fun getDelegateAdapterPosition(basePosition: Int): Int = basePosition

    final override fun getItemCount(): Int = calculateItemCount(delegateAdapter.itemCount)

    fun setDelegateAdapter(delegateAdapter: RecyclerView.Adapter<VH>) {
        _delegateAdapter = delegateAdapter
    }

    open val delegateAdapter: RecyclerView.Adapter<VH>
        get() = _delegateAdapter!!
}