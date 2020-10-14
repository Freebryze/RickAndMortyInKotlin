package com.example.rickandmortyinkotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.rickandmortyinkotlin.API.Character
import com.example.rickandmortyinkotlin.R
import com.example.rickandmortyinkotlin.ui.MainActivity
import com.squareup.picasso.Picasso

class DetailCharacterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_character,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //------------------ CREATION DE LA VUE --------------------

        val myCharacter = arguments!!.getSerializable("myCharacter") as Character

        val myCharacterImageView = (view.findViewById<View>(R.id.myCharacterImageView) as ImageView)


        try {
            Picasso.get().load(myCharacter.image).resize(200, 200).into(myCharacterImageView)
        } catch (e: Exception) {
            e.message
        }

        (view.findViewById<View>(R.id.myCharacterNameTextView) as TextView).text = myCharacter.name
        val myCharacterSpeciesTextView = (view.findViewById<View>(R.id.myCharacterStatusTextView) as TextView)
        myCharacterSpeciesTextView.text = myCharacter.status
        (view.findViewById<View>(R.id.myCharacterSpeciesTextView) as TextView).text = myCharacter.species

        when (myCharacter.status) {
            "Alive" -> myCharacterSpeciesTextView.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.green
                )
            )
            "unknown" -> myCharacterSpeciesTextView.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.yellow
                )
            )
            "Dead" -> myCharacterSpeciesTextView.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.red
                )
            )
            else -> {
                myCharacterSpeciesTextView.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.grey
                    )
                )
            }

        }

        //--------------- OUVERTURE D'UN NOUVEAU FRAGMENT --------------

        myCharacterImageView.setOnClickListener{

            val bundle = Bundle()
            bundle.putSerializable("myCharacterImage",myCharacter)
            val fragobj = DetailPictureFragment()
            fragobj.setArguments(bundle)

            val transaction = (it.context as MainActivity)
                .supportFragmentManager.beginTransaction()

            transaction
                /*.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right,android.R.anim.slide_in_left,android.R.anim.slide_out_right)*/
                .replace(R.id.fragment_container, fragobj)
                .addToBackStack("DetailCharacterFragment")
                .commit()

        }

        //----------------- GESTION DU RETOUR PRECEDENT -------------------

        view.setOnClickListener{
            activity?.onBackPressed()
        }

    }

    companion object{
        fun newInstance(): DetailCharacterFragment {
            val args = Bundle()

            val fragment =
                DetailCharacterFragment()
            fragment.arguments = args
            return fragment
        }
    }

}