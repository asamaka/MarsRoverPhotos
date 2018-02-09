package com.udacity.toyapps.marsroverphotos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val nasaApiService by lazy {
        NasaApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // select a random sol day
        val sol: Int = (Math.random() * 1000 + 1).toInt()
        //display the sol day as the activity's title
        title = getString(R.string.sol, sol)
        // make the network call and get all navigation cam photos using the demo key
        nasaApiService.getPhotos(sol, "NAVCAM", "DEMO_KEY")
                .enqueue(object : Callback<Model.Result> {
                    override fun onResponse(call: Call<Model.Result>?, response: Response<Model.Result>?) {
                        // If we get a response from the api server
                        val errorMessage = findViewById<TextView>(R.id.error_message_text)
                        if (response != null && response.isSuccessful) {
                            errorMessage.visibility = View.GONE
                            // display all photos in the response body
                            for ((imgUrl) in response.body()?.photos.orEmpty()) {
                                val imageView = ImageView(this@MainActivity)
                                Glide.with(this@MainActivity).load(imgUrl).into(imageView)
                                (findViewById<LinearLayout>(R.id.main_layout)).addView(imageView)
                            }
                        } else {
                            // display an error message
                            errorMessage.text = getString(R.string.response_not_successful)
                            errorMessage.visibility = View.VISIBLE
                        }
                    }

                    override fun onFailure(call: Call<Model.Result>?, t: Throwable?) {
                        // display an error message
                        val errorMessage = findViewById<TextView>(R.id.error_message_text)
                        errorMessage.text = getString(R.string.api_call_failed)
                        errorMessage.visibility = View.VISIBLE
                    }
                })
    }
}
