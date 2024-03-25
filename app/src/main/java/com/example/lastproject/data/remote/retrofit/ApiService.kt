package com.example.lastproject.data.remote.retrofit

import com.example.lastproject.data.remote.response.ItemsItem
import com.example.lastproject.data.remote.response.ResponseDetail
import com.example.lastproject.data.remote.response.ResponseGitHub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<ResponseGitHub>

    @GET("users/{username}")
    fun getDetails(
        @Path("username") username: String
    ): Call<ResponseDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<List<ItemsItem>>
}