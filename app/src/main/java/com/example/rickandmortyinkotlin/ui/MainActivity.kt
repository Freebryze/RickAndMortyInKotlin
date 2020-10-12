package com.example.rickandmortyinkotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyinkotlin.API.API
import com.example.rickandmortyinkotlin.API.Character
import com.example.rickandmortyinkotlin.API.Example
import com.example.rickandmortyinkotlin.R
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var service: API
    var BASE_URL = "https://rickandmortyapi.com/api/"

    var characterList = ArrayList<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ---------------- API CONNECTION ----------------

        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()

        service = retrofit.create(API::class.java)

        val callCharacterList: Call<Example> = service.getPage(1)


        callCharacterList.enqueue(object : Callback<Example>{
            override fun onFailure(call: Call<Example>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                response.body()?.results?.let { characterList.addAll(it) }
            }

        })

    }
}