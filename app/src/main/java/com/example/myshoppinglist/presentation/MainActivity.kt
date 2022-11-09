package com.example.myshoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    lateinit var adapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycleView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            adapter.shopList = it

        }

    }

    private fun setupRecycleView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
        rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.TYPE_ENABLED, ShopListAdapter.VIEW_POOL_MAX)
        rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.TYPE_DISABLED, ShopListAdapter.VIEW_POOL_MAX)
    }
}