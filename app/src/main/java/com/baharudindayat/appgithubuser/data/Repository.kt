package com.baharudindayat.appgithubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.baharudindayat.appgithubuser.data.local.entity.FavoriteUser
import com.baharudindayat.appgithubuser.data.local.room.FavoriteUserDao
import com.baharudindayat.appgithubuser.data.local.room.FavoriteUserDatabase
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository (application: Application){

    private val mFavoriteDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getInstance(application)
        mFavoriteDao = db.FavoriteUserDao()
    }

    fun getFav(): LiveData<List<FavoriteUser>> = mFavoriteDao.getFav()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteDao.addFav(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteDao.removeFav(favoriteUser.id) }
    }
}