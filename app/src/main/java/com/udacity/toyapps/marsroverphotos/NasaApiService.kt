package com.udacity.toyapps.marsroverphotos

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("photos")
    fun getPhotos(@Query("sol") sol: Int,
                  @Query("camera") camera: String,
                  @Query("api_key") api_key: String):
            Call<Result>

    companion object {
        fun create(): NasaApiService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/")
                    .build()

            return retrofit.create(NasaApiService::class.java)
        }
    }
}