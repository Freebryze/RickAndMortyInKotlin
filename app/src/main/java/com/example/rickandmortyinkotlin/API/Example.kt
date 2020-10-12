package com.example.rickandmortyinkotlin.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Example {
    @SerializedName("info")
    @Expose
    var info: Info? = null

    @SerializedName("results")
    @Expose
    var results: List<Character>? = null
}