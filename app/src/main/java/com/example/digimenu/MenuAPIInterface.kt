package com.example.digimenu

import com.example.digimenu.models.response.Response
import retrofit2.Call
import retrofit2.http.GET

interface MenuAPIInterface {
    @GET("b/5f985915076e516c36fc1e72/1")
    fun getCurrentMenuData(): Call<Response>
}