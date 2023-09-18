package com.rangga.submission.data.retrofit
import com.rangga.submission.data.response.DetailAccountResponse
import com.rangga.submission.data.response.FollowersResponse
import com.rangga.submission.data.response.ListAccountResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") username: String): Call<ListAccountResponse>
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailAccountResponse>
    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<FollowersResponse>>
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowersResponse>>
}