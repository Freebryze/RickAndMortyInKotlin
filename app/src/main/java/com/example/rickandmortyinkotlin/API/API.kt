package com.example.rickandmortyinkotlin.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("character")
    fun getPage(@Query("page")pageId : Int) : Call<Example>
}