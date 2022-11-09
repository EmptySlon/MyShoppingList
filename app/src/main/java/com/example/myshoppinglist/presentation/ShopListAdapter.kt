package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = if (viewType == ShopListAdapter.TYPE_ENABLED) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false)
        }
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = "${shopItem.name} isActive: ${shopItem.enabled}"
        holder.tvCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener {
            true
        }
    }

    override fun getItemCount(): Int = shopList.size


    override fun getItemViewType(position: Int): Int {
        val typeItemShop =
            if (shopList[position].enabled) ShopListAdapter.TYPE_ENABLED
            else ShopListAdapter.TYPE_DISABLED

        return typeItemShop

    }


    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

    }

    companion object {
        const val TYPE_ENABLED = 1
        const val TYPE_DISABLED = 0

        const val VIEW_POOL_MAX = 5
    }


}