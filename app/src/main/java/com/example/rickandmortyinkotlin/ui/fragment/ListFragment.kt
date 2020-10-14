package com.example.rickandmortyinkotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyinkotlin.API.API
import com.example.rickandmortyinkotlin.API.Character
import com.example.rickandmortyinkotlin.API.Example
import com.example.rickandmortyinkotlin.R
import com.example.rickandmortyinkotlin.ui.MyAdapter
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListFragment: Fragment() {

    lateinit var service: API
    var BASE_URL = "https://rickandmortyapi.com/api/"
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager

    var characterList = ArrayList<Character>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_character,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.characterRecyclerView)

        layoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = layoutManager

        mAdapter = MyAdapter(characterList)
        recyclerView.adapter = mAdapter

        recyclerView.setHasFixedSize(true)

        // ---------------- API CONNECTION ----------------

        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create(gson)).build()

        service = retrofit.create(API::class.java)

        loadPages(1)

    }

    fun loadPages(page : Int){

        val callCharacterList: Call<Example> = service.getPage(page)

        callCharacterList.enqueue(object : Callback<Example> {
            override fun onFailure(call: Call<Example>, t: Throwable) {
                println("PB")
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

    companion object{
        fun newInstance(): ListFragment {
            val args = Bundle()

            val fragment =
                ListFragment()
            fragment.arguments = args
            return fragment
        }
    }

}