package com.example.rickandmortyinkotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyinkotlin.R
import com.example.rickandmortyinkotlin.ui.fragment.ListFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // -------------------- FRAGEMENT -----------------

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container,
            ListFragment.newInstance()).commit()
    }

}