package com.example.digimenu

import com.example.digimenu.models.response.Response
import com.example.digimenu.models.response.ResponseDeserializer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MenuAPIClient {
    var BASE_URL: String = "https://api.jsonbin.io/"
    val getClient: MenuAPIInterface
        get() {

            val gson = GsonBuilder()
                .registerTypeAdapter(
                    Response::class.java,
                    ResponseDeserializer()
                )
                .create()
            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(MenuAPIInterface::class.java)

        }

}