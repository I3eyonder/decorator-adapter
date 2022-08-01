package com.hieupt.adapter.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.adapter.sample.databinding.ActivityMainBinding
import com.hieupt.supporter.paging.LoadMoreState
import com.hieupt.supporter.paging.PagingSupporter
import com.hieupt.supporter.paging.setupPagingSupporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by HieuPT on 8/1/2022.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private lateinit var itemAdapter: ItemAdapter

    private val bannerHeaderAdapter by lazy {
        BannerHeaderAdapter()
    }

    private val loadingFooterAdapter by lazy {
        LoadingFooterAdapter()
    }

    private var refreshCount = 0

    private var pagingSupporter: PagingSupporter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        initActions()
        initData()
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val items = getItems(40, refreshCount)
            withContext(Dispatchers.Main) {
                itemAdapter.submitList(items) {
                    viewBinding.srlItems.isRefreshing = false

                }
            }
        }
    }

    private fun getItems(take: Int = 20, root: Int = 1): List<Int> = buildList {
        repeat(take) {
            add(root + it)
        }
    }

    private fun initActions() {
        viewBinding.apply {
            srlItems.setOnRefreshListener {
                refreshCount++
                pagingSupporter?.loadMoreState?.hasMoreData = true
                initData()
            }
            btnAddLoading.setOnClickListener { v ->
                rvItems.adapter?.let { it ->
                    v.isEnabled = false
                    rvItems.adapter = loadingFooterAdapter.apply {
                        setDelegateAdapter(it)
                    }
                }
            }
            btnAddHeader.setOnClickListener { v ->
                rvItems.adapter?.let { it ->
                    v.isEnabled = false
                    rvItems.adapter = bannerHeaderAdapter.apply {
                        setDelegateAdapter(it)
                    }
                }
            }
            btnRemoves.setOnClickListener {
                btnAddLoading.isEnabled = true
                btnAddHeader.isEnabled = true
                rvItems.adapter = itemAdapter
            }
        }
    }

    private fun initViews() {
        viewBinding.apply {
            rvItems.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ItemAdapter().also {
                    itemAdapter = it
                }
                pagingSupporter = setupPagingSupporter {
                    setLoadMoreCallback { _, loadMoreState ->
                        loadMore(loadMoreState)
                    }
                    setOnLoadingChangedListener {
                        try {
                            loadingFooterAdapter.isLoading = it
                        } catch (e: Exception) {
                        }
                    }
                }.install()
            }
        }
    }

    private fun loadMore(loadMoreState: LoadMoreState) {
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadMoreState.isLoading = true
            }
            delay(1000)
            val oldItems = itemAdapter.currentList
            val newItems = oldItems + getItems(root = oldItems.size + refreshCount)
            itemAdapter.submitList(newItems) {
                runOnUiThread {
                    loadMoreState.isLoading = false
                    if (newItems.size > 150) {
                        loadMoreState.hasMoreData = false
                    }
                }
            }
        }
    }
}