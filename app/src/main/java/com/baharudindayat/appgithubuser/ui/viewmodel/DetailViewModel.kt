package com.baharudindayat.appgithubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baharudindayat.appgithubuser.data.local.entity.FavoriteUser
import com.baharudindayat.appgithubuser.data.local.room.FavoriteUserDao
import com.baharudindayat.appgithubuser.data.local.room.FavoriteUserDatabase
import com.baharudindayat.appgithubuser.data.remote.response.DetailUser
import com.baharudindayat.appgithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.baharudindayat.appgithubuser.data.Repository

class DetailViewModel (application: Application): AndroidViewModel(application) {

    private var favUserDAO: FavoriteUserDao?
    private var favUserDb: FavoriteUserDatabase? = FavoriteUserDatabase.getInstance(application)
    private val mFavoriteUser:  Repository = Repository(application)


    private val _github = MutableLiveData<DetailUser>()
    val items: LiveData<DetailUser> = _github

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        favUserDAO = favUserDb?.FavoriteUserDao()
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailProfile(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: Response<DetailUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _github.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addFavorite(id: Int, username: String, avatar: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(id,username,avatar)
            favUserDAO?.addFav(user)
        }
    }

    suspend fun checkUser(id: Int) = favUserDAO?.checkUser(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favUserDAO?.removeFav(id)
        }
    }

    init {
        favUserDAO = favUserDb?.FavoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? = favUserDAO?.getFav()



    fun insert(favoriteUser: FavoriteUser){
        mFavoriteUser.insert(favoriteUser)
    }

    fun get(){
        mFavoriteUser.getFav()
    }

    fun delete(favoriteUser: FavoriteUser){
        mFavoriteUser.delete(favoriteUser)
    }

    private val _favorite = MutableLiveData<List<FavoriteUser>>()
    val favorite: LiveData<List<FavoriteUser>> = _favorite




    companion object{
        const val TAG = "DetailViewModel"
    }

}