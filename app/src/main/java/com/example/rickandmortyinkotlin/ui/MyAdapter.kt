package com.example.rickandmortyinkotlin.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyinkotlin.API.Character
import com.example.rickandmortyinkotlin.R
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class MyAdapter(characterList: ArrayList<Character>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var adapterCharacterList: ArrayList<Character> = characterList

    var pageLoaded = 1
    val NB_CHARACTER_ON_A_PAGE = 20
    val NB_CHARACTER_BEFORE_LOADING_NEXT_PAGE = 5

    //--------------- FAVORIS -------------------

    lateinit var mPrefs: SharedPreferences
    lateinit var prefsEditor: SharedPreferences.Editor
    lateinit var favoriCharacterList : ArrayList<Character>
    val PREFERENCE_FILE_KEY = "saves"


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        val viewHolder = MyViewHolder(v)

        viewHolder.itemView.setOnClickListener {
            println("testPetitClick")
        }

        viewHolder.itemView.setOnLongClickListener {
            println("TestGrosClick")
            true
        }

        //--------------- FAVORIS -------------------

        mPrefs = parent.context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        prefsEditor = mPrefs.edit()

        loadFavoris()


        return viewHolder
    }

    override fun getItemCount(): Int {
        return adapterCharacterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val me: Character = adapterCharacterList.get(position)
        try {
            Picasso.get().load(me.image).resize(200, 200)
                .into(holder.itemView.findViewById<View>(R.id.imageViewCharacterImage) as ImageView)
        } catch (e: Exception) {
            e.message
        }

        (holder.itemView.findViewById<View>(R.id.textViewCharacterName) as TextView).text = me.name

        var textViewStatus =
            holder.itemView.findViewById<View>(R.id.textViewCharacterStatus) as TextView
        textViewStatus.text = me.status

        when (me.status) {
            "Alive" -> textViewStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
            "unknown" -> textViewStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.yellow
                )
            )
            "Dead" -> textViewStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )
            else -> {
                textViewStatus.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.grey
                    )
                )
            }
        }

    }



    fun loadFavoris(){
        val gsonFavori = Gson()
        val savedCharacters = mPrefs.all as Map<String, String?>
        for (json in savedCharacters.values) {
            val savedCharacter = gsonFavori.fromJson(json, Character::class.java)
            favoriCharacterList.add(savedCharacter)
        }
    }
}
