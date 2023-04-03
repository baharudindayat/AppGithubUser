package com.baharudindayat.appgithubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.baharudindayat.appgithubuser.data.local.entity.FavoriteUser
import com.baharudindayat.appgithubuser.data.local.room.FavoriteUserDao
import com.baharudindayat.appgithubuser.data.local.room.FavoriteUserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favUserDAO: FavoriteUserDao?
    private var favUserDb: FavoriteUserDatabase? = FavoriteUserDatabase.getInstance(application)

    init {
        favUserDAO = favUserDb?.FavoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? = favUserDAO?.getFav()
}
