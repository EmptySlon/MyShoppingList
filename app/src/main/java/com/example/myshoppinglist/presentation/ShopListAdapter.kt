package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem
import kotlinx.coroutines.NonDisposableHandle.parent

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutId =
            if (viewType == ShopListAdapter.TYPE_ENABLED) R.layout.item_shop_enabled
            else R.layout.item_shop_disabled
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = "${shopItem.name} isActive: ${shopItem.enabled}"
        holder.tvCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

    }

    override fun getItemCount(): Int = shopList.size

    fun getItem(position: Int): ShopItem {
        return shopList[position]
    }


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