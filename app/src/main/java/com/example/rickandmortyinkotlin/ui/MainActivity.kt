package com.example.rickandmortyinkotlin.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyinkotlin.API.API
import com.example.rickandmortyinkotlin.API.Character
import com.example.rickandmortyinkotlin.API.Example
import com.example.rickandmortyinkotlin.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var service: API
    var BASE_URL = "https://rickandmortyapi.com/api/"
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager


    var characterList = ArrayList<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.characterRecyclerView)

        recyclerView.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        mAdapter = MyAdapter(characterList)
        recyclerView.adapter = mAdapter


        // ---------------- API CONNECTION ----------------

        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()

        service = retrofit.create(API::class.java)

        loadPages(1)

    }

    fun loadPages(page : Int){

        val callCharacterList: Call<Example> = service.getPage(page)

        callCharacterList.enqueue(object : Callback<Example>{
            override fun onFailure(call: Call<Example>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                response.body()?.results?.let { characterList.addAll(it) }
                mAdapter.notifyDataSetChanged()
                if(response.body()?.info?.next != null){
                    loadPages(page+1)
                }
            }
        })
    }

}