package com.baharudindayat.appgithubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudindayat.appgithubuser.data.local.entity.FavoriteUser
import com.baharudindayat.appgithubuser.data.remote.response.ItemsItem
import com.baharudindayat.appgithubuser.databinding.ActivityFavoriteBinding
import com.baharudindayat.appgithubuser.ui.adapter.FavoriteAdapter
import com.baharudindayat.appgithubuser.ui.adapter.MainAdapter
import com.baharudindayat.appgithubuser.ui.viewmodel.DetailViewModel
import com.baharudindayat.appgithubuser.ui.viewmodel.FavoriteViewModel
import com.baharudindayat.appgithubuser.ui.viewmodel.ViewModelFactory


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var adapter: FavoriteAdapter
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = arrayListOf<ItemsItem>()
        mainAdapter = MainAdapter(items)
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()


        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvItem.addItemDecoration(itemDecoration)

        favoriteViewModel.getFavoriteUser()?.observe(this){ listFav ->
            adapter.setListFavorite(listFav as ArrayList<FavoriteUser>)
            binding.rvItem.adapter = adapter
        }


    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoriteUser()

        favoriteViewModel.getFavoriteUser()?.observe(this){ listFav ->
            adapter.setListFavorite(listFav as ArrayList<FavoriteUser>)
            binding.rvItem.adapter = adapter
        }
    }

}