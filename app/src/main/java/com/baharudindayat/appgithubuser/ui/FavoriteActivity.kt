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
        val favorite = arrayListOf<FavoriteUser>()
        mainAdapter = MainAdapter(items)
        adapter = FavoriteAdapter()
        mainAdapter.notifyDataSetChanged()


        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvItem.addItemDecoration(itemDecoration)

//        detailViewModel.favorite.observe(this){listFav ->
//            if (listFav != null){
//                adapter.setListFavorite(listFav as ArrayList<FavoriteUser>)
//            }
//            binding.rvItem.adapter = adapter
//        }


//        favoriteViewModel.getFavoriteUser()?.observe(this){ listFav ->
//            if (listFav != null){
//                adapter.setListFavorite(listFav as ArrayList<FavoriteUser>)
//            }
//            binding.rvItem.adapter = adapter
//        }

        favoriteViewModel.getFavoriteUser()?.observe(this){ users : List<FavoriteUser>? ->
//            if (users != null){
//                adapter.setListFavorite(users as ArrayList<FavoriteUser>)
//            }
            users?.map {
                val item = ItemsItem(id = it.id, login = it.login, avatarUrl = it.avatar_url)
                items.add(item)
            }
            binding.rvItem.adapter = mainAdapter
        }


    }

    override fun onResume() {
        super.onResume()
        detailViewModel.get()
        favoriteViewModel.getFavoriteUser()

        favoriteViewModel.getFavoriteUser()?.observe(this){ users : List<FavoriteUser>? ->
//            if (users != null){
//                adapter.setListFavorite(users as ArrayList<FavoriteUser>)
//            }
            users?.map {
                val items = arrayListOf<ItemsItem>()
                val item = ItemsItem(id = it.id, login = it.login, avatarUrl = it.avatar_url)
                items.add(item)
                mainAdapter.setListFavorite(users as ArrayList<FavoriteUser>)
            }
            binding.rvItem.adapter = mainAdapter
        }

//        detailViewModel.favorite.observe(this){listFav ->
//            if (listFav != null){
//                adapter.setListFavorite(listFav as ArrayList<FavoriteUser>)
//            }
//            binding.rvItem.adapter = adapter
//        }
    }

}