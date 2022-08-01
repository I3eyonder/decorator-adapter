package com.hieupt.adapter.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.adapter.FooterLoadingDecoratorAdapter

/**
 * Created by HieuPT on 8/1/2022.
 */
class LoadingFooterAdapter(delegateAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null) :
    FooterLoadingDecoratorAdapter(delegateAdapter) {

    override val footerViewType: Int
        get() = R.id.view_type_loading

    override fun onCreateLoadingViewHolder(parent: ViewGroup): LoadingViewHolder =
        LayoutInflater.from(parent.context).run {
            LoadingViewHolder(inflate(R.layout.item_loading, parent, false))
        }

    class LoadingViewHolder(itemView: View) :
        FooterLoadingDecoratorAdapter.LoadingViewHolder(itemView) {

        override fun startLoading() {
        }

    }
}