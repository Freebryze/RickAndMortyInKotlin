package com.example.rickandmortyinkotlin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.rickandmortyinkotlin.API.Character
import com.example.rickandmortyinkotlin.R
import com.squareup.picasso.Picasso

class DetailPictureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myCharacterPicture = arguments!!.getSerializable("myCharacterImage") as Character

        val myCharacterZoomedImageView = (view.findViewById<View>(R.id.myCharacterZoomedImageView) as ImageView)

        try {
            Picasso.get().load(myCharacterPicture.image).resize(200, 200).into(myCharacterZoomedImageView)
        } catch (e: Exception) {
            e.message
        }

        view.setOnClickListener{
            activity?.onBackPressed()
        }


    }

}