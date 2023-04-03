package com.baharudindayat.appgithubuser.data.remote.retrofit

import com.baharudindayat.appgithubuser.data.remote.response.DetailUser
import com.baharudindayat.appgithubuser.data.remote.response.FollowersItem
import com.baharudindayat.appgithubuser.data.remote.response.FollowingItem
import com.baharudindayat.appgithubuser.data.remote.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService{
    @GET("search/users")
    fun getUsername(
        @Query("q") page: String

    ): Call<GithubResponse>

    @GET("users/{USERNAME}")
    fun getDetailProfile(
        @Path("USERNAME") id:String

    ): Call<DetailUser>

    @GET("users/{USERNAME}/following")
    fun getFollowing(
        @Path("USERNAME") id:String

    ): Call<List<FollowingItem>>

    @GET("users/{USERNAME}/followers")
    fun getFollowers(
        @Path("USERNAME") id:String

    ): Call<List<FollowersItem>>
}