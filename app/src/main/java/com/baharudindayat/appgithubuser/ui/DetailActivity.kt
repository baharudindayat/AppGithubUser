@file:Suppress("DEPRECATION")

package com.baharudindayat.appgithubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.baharudindayat.appgithubuser.R
import com.baharudindayat.appgithubuser.data.local.entity.FavoriteUser
import com.baharudindayat.appgithubuser.data.remote.response.DetailUser
import com.baharudindayat.appgithubuser.databinding.ActivityDetailBinding
import com.baharudindayat.appgithubuser.ui.adapter.SectionsPagerAdapter
import com.baharudindayat.appgithubuser.ui.viewmodel.DetailViewModel
import com.baharudindayat.appgithubuser.ui.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        val tabs = binding.tabs

        val id = intent.getIntExtra("ID", 0)
        val username = intent.getStringExtra("USERNAME").toString()
        val avatar = intent.getStringExtra("AVATAR").toString()

        var isChecked = false
        val detailFabFavorite = binding.detailFabFavorite

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel.getDetailUser(username)

        detailViewModel.items.observe(this){ Detail ->
            setDetailData(Detail)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    isChecked = if (count>0) {
                        detailFabFavorite.setImageDrawable(ContextCompat.getDrawable(detailFabFavorite.context,R.drawable.baseline_favorite_24))
                        true
                    } else {
                        detailFabFavorite.setImageDrawable(ContextCompat.getDrawable(detailFabFavorite.context,R.drawable.baseline_favorite_border_24))
                        false
                    }
                }
            }
        }
        binding.detailFabFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                val data = FavoriteUser(id,username,avatar)
                detailViewModel.insert(data)
//                detailViewModel.addFavorite(id, username, avatar)
                detailFabFavorite.setImageDrawable(ContextCompat.getDrawable(detailFabFavorite.context,R.drawable.baseline_favorite_24))
            } else {
                val data = FavoriteUser(id,username,avatar)
                detailViewModel.delete(data)
//                detailViewModel.removeFavorite(id)
                detailFabFavorite.setImageDrawable(ContextCompat.getDrawable(detailFabFavorite.context,R.drawable.baseline_favorite_border_24))
            }
        }
    }

    private fun setDetailData(Detail: DetailUser){
        with(binding) {
            tvUsername.text = Detail.login
            tvName.text = Detail.name
            tvLocation.text = Detail.location
            tvFollowing.text = Detail.following.toString()
            tvFollower.text = Detail.followers.toString()
            Glide.with(this@DetailActivity)
                .load(Detail.avatarUrl)
                .circleCrop()
                .into(ivImageDetail)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val USERNAME = "extra_user"
        const val ID = "extra_id"
        const val AVATAR = "extra_avatar"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}