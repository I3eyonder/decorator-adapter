package com.hieupt.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 7/30/2022.
 */
abstract class DecoratorAdapter<VH : RecyclerView.ViewHolder>(
    delegateAdapter: RecyclerView.Adapter<VH>? = null
) : RecyclerView.Adapter<VH>() {

    private var _delegateAdapter: RecyclerView.Adapter<VH>? = null

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            onDelegateAdapterChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            onDelegateAdapterItemRangeChanged(positionStart, itemCount, null)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            onDelegateAdapterItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            onDelegateAdapterItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            onDelegateAdapterItemRangeMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            onDelegateAdapterItemRangeRemoved(positionStart, itemCount)
        }
    }

    val delegateAdapter: RecyclerView.Adapter<VH>
        get() = _delegateAdapter!!

    init {
        delegateAdapter?.let { setDelegateAdapter(it) }
    }

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

    open fun getParentAdapterPosition(delegatePosition: Int): Int = delegatePosition

    final override fun getItemCount(): Int = calculateItemCount(delegateAdapter.itemCount)

    @SuppressLint("NotifyDataSetChanged")
    open fun onDelegateAdapterChanged() {
        notifyDataSetChanged()
    }

    open fun onDelegateAdapterItemRangeChanged(
        delegatePositionStart: Int,
        itemCount: Int,
        payload: Any?
    ) {
        notifyItemRangeChanged(getParentAdapterPosition(delegatePositionStart), itemCount, payload)
    }

    open fun onDelegateAdapterItemRangeInserted(delegatePositionStart: Int, itemCount: Int) {
        notifyItemRangeInserted(getParentAdapterPosition(delegatePositionStart), itemCount)
    }

    open fun onDelegateAdapterItemRangeMoved(delegateFromPosition: Int, delegateToPosition: Int) {
        notifyItemMoved(
            getParentAdapterPosition(delegateFromPosition),
            getParentAdapterPosition(delegateToPosition)
        )
    }

    open fun onDelegateAdapterItemRangeRemoved(delegatePositionStart: Int, itemCount: Int) {
        notifyItemRangeRemoved(getParentAdapterPosition(delegatePositionStart), itemCount)
    }

    fun setDelegateAdapter(delegateAdapter: RecyclerView.Adapter<VH>) {
        detachDelegateAdapter()
        _delegateAdapter = delegateAdapter.also {
            try {
                it.registerAdapterDataObserver(adapterDataObserver)
            } catch (e: Exception) {
            }
        }
    }

    fun detachDelegateAdapter() {
        try {
            _delegateAdapter?.unregisterAdapterDataObserver(adapterDataObserver)
        } catch (e: Exception) {
        }
        _delegateAdapter = null
    }
}