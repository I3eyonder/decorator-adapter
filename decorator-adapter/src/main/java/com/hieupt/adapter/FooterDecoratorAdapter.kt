package com.hieupt.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 7/30/2022.
 */
abstract class FooterDecoratorAdapter(
    delegateAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
) : DecoratorAdapter<RecyclerView.ViewHolder>(delegateAdapter) {

    protected abstract val footerViewType: Int

    protected abstract fun onCreateFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    protected abstract fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            footerViewType -> onCreateFooterViewHolder(parent)
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (getItemViewType(position)) {
            footerViewType -> onBindFooterViewHolder(holder, position)
            else -> super.onBindViewHolder(holder, position)
        }

    override fun calculateItemCount(delegateItemCount: Int): Int = delegateItemCount + FOOTER_COUNT

    override fun getItemViewType(position: Int) = when (position) {
        delegateAdapter.itemCount -> footerViewType
        else -> super.getItemViewType(position)
    }

    companion object {
        const val FOOTER_COUNT = 1
    }
}