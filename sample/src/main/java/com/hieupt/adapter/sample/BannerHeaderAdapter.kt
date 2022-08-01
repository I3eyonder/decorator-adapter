package com.hieupt.adapter.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.adapter.HeaderDecoratorAdapter

/**
 * Created by HieuPT on 8/1/2022.
 */
class BannerHeaderAdapter(delegateAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null) :
    HeaderDecoratorAdapter(delegateAdapter) {

    override val headerViewType: Int
        get() = R.id.view_type_banner

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        LayoutInflater.from(parent.context).run {
            BannerViewHolder(inflate(R.layout.item_banner, parent, false))
        }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}