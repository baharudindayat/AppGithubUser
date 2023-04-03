package com.baharudindayat.appgithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.baharudindayat.appgithubuser.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao{
    @Insert
    fun addFav(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite")
    fun getFav(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun removeFav(id: Int): Int

}