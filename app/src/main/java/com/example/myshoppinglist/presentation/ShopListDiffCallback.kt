package com.example.myshoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.myshoppinglist.domain.ShopItem

class ShopListDiffCallback(
    val oldList: List<ShopItem>,
    val newList: List<ShopItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return newItem == oldItem
    }
}
