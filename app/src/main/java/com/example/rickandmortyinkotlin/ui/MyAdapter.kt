package com.example.rickandmortyinkotlin.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyinkotlin.API.Character
import com.example.rickandmortyinkotlin.R
import com.example.rickandmortyinkotlin.ui.fragment.DetailCharacterFragment
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
    var favoriCharacterList = ArrayList<Character>()
    val PREFERENCE_FILE_KEY = "saves"


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        val viewHolder = MyViewHolder(v)


        //--------------- FAVORIS -------------------

        mPrefs = parent.context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        prefsEditor = mPrefs.edit()

        loadSetFavoris(adapterCharacterList)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return adapterCharacterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        //----------------- CREATION DE LA VUE ET DES LABELS ---------------------

        val me: Character = adapterCharacterList.get(position)
        try {
            Picasso.get().load(me.image).resize(200, 200)
                .into(holder.itemView.findViewById<View>(R.id.imageViewCharacterImage) as ImageView)
        } catch (e: Exception) {
            e.message
        }

        (holder.itemView.findViewById<View>(R.id.textViewCharacterName) as TextView).text = me.name

        var textViewStatus = holder.itemView.findViewById<View>(R.id.textViewCharacterStatus) as TextView
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

        val isFavoriteView: ImageView = holder.itemView.findViewById(R.id.isFavorite)
        val isNotFavoriteView: ImageView = holder.itemView.findViewById(R.id.isNotFavorite)

        if (me.isFavorite) {
            isFavoriteView.visibility = View.VISIBLE
            isNotFavoriteView.visibility = View.INVISIBLE
        } else {
            isFavoriteView.visibility = View.INVISIBLE
            isNotFavoriteView.visibility = View.VISIBLE
        }

        // ---------------------- FAVORIS ET OUVERTURE DU FRAGMENT -----------------------

        holder.itemView.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable("myCharacter",me)
            val fragobj = DetailCharacterFragment()
            fragobj.setArguments(bundle)

            val transaction = (it.context as MainActivity)
                .supportFragmentManager.beginTransaction()

            transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right,android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .add(R.id.fragment_container, fragobj)
                .addToBackStack("ListFragment")
                .commit()
        }

        holder.itemView.setOnLongClickListener {

            val savedCharacters = mPrefs.all as Map<String, String>
            val gson = Gson()

            if (savedCharacters.containsKey("${me.id}")) {
                prefsEditor.remove("${me.id}")
                me.isFavorite = false
                println(me.name)

            } else {
                me.isFavorite = true
                val json = gson.toJson(me)
                prefsEditor.putString("${me.id}", json)
            }
            prefsEditor.commit()

            if (me.isFavorite) {
                isFavoriteView.visibility = View.VISIBLE
                isNotFavoriteView.visibility = View.INVISIBLE
            } else {
                isFavoriteView.visibility = View.INVISIBLE
                isNotFavoriteView.visibility = View.VISIBLE
            }
            true
        }

    }



    fun loadSetFavoris(mCharacterListToCompare :ArrayList<Character>){
        val gsonFavori = Gson()
        val savedCharacters = mPrefs.all as Map<String, String?>
        for (json in savedCharacters.values) {
            val savedCharacter = gsonFavori.fromJson(json, Character::class.java)
            favoriCharacterList.add(savedCharacter)
        }
        for (favori in favoriCharacterList) {
            for (mCharac in mCharacterListToCompare) {
                if (favori.id == mCharac.id){
                    mCharac.isFavorite = true
                }
            }
        }
    }
}
