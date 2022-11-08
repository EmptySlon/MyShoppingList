package com.example.myshoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    lateinit var llListShopItem: LinearLayout

    private var count = 0

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llListShopItem = findViewById(R.id.ll_shop_list)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            llListShopItem.removeAllViews()
            for (shopItem in it) {
                val layoutId = if (shopItem.enabled) R.layout.item_shop_enabled
                else R.layout.item_shop_disabled

                val view = LayoutInflater.from(this).inflate(layoutId,llListShopItem,false )
                val tvName = view.findViewById<TextView>(R.id.tv_name)
                val tvCount = view.findViewById<TextView>(R.id.tv_count)
                tvName.text = shopItem.name
                tvCount.text = shopItem.count.toString()

                view.setOnLongClickListener {
                    viewModel.changeEnabled(shopItem)
                    true
                }

                llListShopItem.addView(view)

            }


        }


    }
}