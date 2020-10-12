package com.example.rickandmortyinkotlin.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Origin {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}