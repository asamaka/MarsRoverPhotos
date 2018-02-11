package com.udacity.toyapps.marsroverphotos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val err = findViewById<TextView>(R.id.error_message_text)
        val layout = findViewById<LinearLayout>(R.id.main_layout)
        val viewModel = ViewModelProviders.of(this).get(PhotoListViewModel::class.java)
        viewModel.photos.observe(this, Observer<List<Photo>?> { response ->
            when {
                response == null -> err.text = getString(R.string.api_call_failed)
                response.isEmpty() -> err.text = getString(R.string.empty_response)
                else -> {
                    err.visibility = View.GONE
                    title = response.first().earth_date
                    for ((imgUrl) in response) {
                        val imageView = ImageView(this@MainActivity)
                        Glide.with(this@MainActivity).load(imgUrl).into(imageView)
                        layout.addView(imageView)
                    }
                }

            }
        })
    }
}
