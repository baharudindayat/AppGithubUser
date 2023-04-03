package com.baharudindayat.appgithubuser.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.baharudindayat.appgithubuser.R
import com.baharudindayat.appgithubuser.data.local.entity.FavoriteUser
import com.baharudindayat.appgithubuser.data.local.room.FavoriteDiffCallback
import com.baharudindayat.appgithubuser.ui.DetailActivity
import com.bumptech.glide.Glide


class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listFavorites = ArrayList<FavoriteUser>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val users = listFavorites[position]
        viewHolder.tvItem.text = users.login
        Glide.with(viewHolder.itemView.context)
            .load(users.avatar_url)
            .circleCrop()
            .into(viewHolder.ivProfile)

        viewHolder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listFavorites[viewHolder.adapterPosition])
        }

        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intent.putExtra("USERNAME", listFavorites[viewHolder.adapterPosition].login)
            intent.putExtra("ID",listFavorites[viewHolder.adapterPosition].id)
            intent.putExtra("AVATAR",listFavorites[viewHolder.adapterPosition].avatar_url)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listFavorites.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
    }

    fun setListFavorite(listFavorites: ArrayList<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites,listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)

    }


    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }
}