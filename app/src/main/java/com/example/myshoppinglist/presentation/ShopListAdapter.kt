package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ItemShopDisabledBinding
import com.example.myshoppinglist.databinding.ItemShopEnabledBinding
import com.example.myshoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(shopList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutId = if (viewType == TYPE_ENABLED) R.layout.item_shop_enabled
        else R.layout.item_shop_disabled
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), layoutId, parent, false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        val binding = holder.binding
        val textItem = holder.binding.root.context.getString(
            R.string.text_shop_item, shopItem.name, shopItem.enabled.toString()
        )
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        when (binding) {
            is ItemShopEnabledBinding -> {
                binding.tvName.text = textItem
                binding.tvCount.text = shopItem.count.toString()
            }
            is ItemShopDisabledBinding -> {
                binding.tvName.text = textItem
                binding.tvCount.text = shopItem.count.toString()
            }
        }
    }

    override fun getItemCount(): Int = shopList.size

    fun getItem(position: Int): ShopItem {
        return shopList[position]
    }

    override fun getItemViewType(position: Int): Int {
        val typeItemShop =
            if (shopList[position].enabled) TYPE_ENABLED
            else TYPE_DISABLED
        return typeItemShop
    }


    class ShopItemViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val TYPE_ENABLED = 1
        const val TYPE_DISABLED = 0
        const val VIEW_POOL_MAX = 5
    }


}