package com.zacky.fundamentalsubmission.data.remote.retrofit

import com.zacky.fundamentalsubmission.data.remote.response.DetailUserResponse
import com.zacky.fundamentalsubmission.data.remote.response.GithubResponse
import com.zacky.fundamentalsubmission.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getGithubUser(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>


}