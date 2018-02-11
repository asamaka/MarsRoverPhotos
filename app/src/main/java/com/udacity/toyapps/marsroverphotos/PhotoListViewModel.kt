package com.udacity.toyapps.marsroverphotos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotoListViewModel() : ViewModel() {

    val photos: LiveData<List<Photo>?> by lazy {
        val sol: Int = (Math.random() * 1000 + 1).toInt()
        loadPhotos(sol)
    }

    fun loadPhotos(sol: Int): LiveData<List<Photo>?> {
        val photos = MutableLiveData<List<Photo>?>()
        NasaApiService.create().getPhotos(sol, "NAVCAM", "DEMO_KEY")
                .enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                        photos.value = response?.body()?.photos
                    }

                    override fun onFailure(call: Call<Result>?, t: Throwable?) {
                        photos.value = null
                    }
                })
        return photos
    }


}