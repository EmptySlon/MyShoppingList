package com.example.myshoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    lateinit var binding: ActivityMainBinding

    lateinit var shopListAdapter: ShopListAdapter
    lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var viewModel: MainViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.shopList = it
        }
        binding.buttonAddShopItem
        val buttonAddItem = binding.buttonAddShopItem
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceAddItem()
                launchFragment(fragment)
            }



        }

    }

    private fun setupRecycleView() {
        val rvShopList = binding.rvShopList
        shopListAdapter = ShopListAdapter()
        with(rvShopList) {
            rvShopList.adapter = shopListAdapter
            rvShopList.recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.TYPE_ENABLED,
                ShopListAdapter.VIEW_POOL_MAX
            )
            rvShopList.recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.TYPE_DISABLED,
                ShopListAdapter.VIEW_POOL_MAX
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvShopList)

    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val shopItem = shopListAdapter.getItem(viewHolder.adapterPosition)
                    viewModel.deleteShopItem(shopItem)
                }

            }
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            val shopItemId = it.id
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, shopItemId)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceEditItem(shopItemId)
                launchFragment(fragment)
            }

        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnabled(it)
        }
    }

    private fun isOnePaneMode(): Boolean {
        return  binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


}
