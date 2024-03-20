package com.thoriq.githubsubmit2.data.remote.retrofit

import com.thoriq.githubsubmit2.data.remote.response.DetailUserResponse
import com.thoriq.githubsubmit2.data.remote.response.GithubResponse
import com.thoriq.githubsubmit2.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getListUsers(
        @Query("q") id: String
    ): GithubResponse

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
}