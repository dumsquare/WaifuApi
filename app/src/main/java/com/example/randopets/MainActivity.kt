package com.example.randopets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


class MainActivity : AppCompatActivity() {
    var waifuImageURL = ""
    var waifuArtist = ""
    var waifuSource = ""
    lateinit var artistNameText: TextView
    lateinit var urlText: TextView
    lateinit var  button : Button
    lateinit var imageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)
        urlText = findViewById(R.id.sourceText)
        artistNameText = findViewById(R.id.artistNameText)


        getWaifuImageURL()
        getNextWaifu(button, imageView)
        Log.d("waifuImageURL", "waifu image URL set")
    }

    private fun getWaifuImageURL() {
        val client = AsyncHttpClient()



        client["https://nekos.best/api/v2/waifu", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Waifu", "response successful$json")
                var resultsJSON = json.jsonObject.getJSONArray("results").getJSONObject(0)
                waifuImageURL = resultsJSON.getString("url")
                waifuArtist = resultsJSON.getString("artist_name")
                waifuSource = resultsJSON.getString("source_url")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Waifu Error", errorResponse)
            }
        }]
    }


    private fun getNextWaifu(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            getWaifuImageURL()

            Glide.with(this)
                .load(waifuImageURL)
                .fitCenter()
                .into(imageView)

            artistNameText.text = waifuArtist
            urlText.text = waifuSource


        }
    }
}