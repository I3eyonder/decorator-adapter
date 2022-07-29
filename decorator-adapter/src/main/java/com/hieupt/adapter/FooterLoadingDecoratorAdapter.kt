package com.hieupt.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 7/30/2022.
 */
abstract class FooterLoadingDecoratorAdapter(
    delegateAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) : FooterDecoratorAdapter(delegateAdapter) {

    var isLoading = false
        set(value) {
            field = value
            if (value) {
                notifyItemInserted(delegateAdapter.itemCount)
            } else {
                notifyItemRemoved(delegateAdapter.itemCount)
            }
        }

    abstract fun onCreateLoadingViewHolder(parent: ViewGroup): LoadingViewHolder

    final override fun onCreateFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        onCreateLoadingViewHolder(parent)

    final override fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadingViewHolder) {
            holder.startLoading()
        }
    }

    final override fun calculateItemCount(delegateItemCount: Int): Int {
        return if (isLoading) {
            super.calculateItemCount(delegateItemCount)
        } else {
            delegateItemCount
        }
    }

    final override fun getItemViewType(position: Int): Int {
        return if (isLoading) {
            super.getItemViewType(position)
        } else {
            delegateAdapter.getItemViewType(position)
        }
    }

    abstract class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun startLoading()
    }
}