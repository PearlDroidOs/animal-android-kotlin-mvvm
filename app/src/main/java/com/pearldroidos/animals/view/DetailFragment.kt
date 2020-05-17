package com.pearldroidos.animals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.pearldroidos.animals.R
import com.pearldroidos.animals.databinding.FragmentDetailBinding
import com.pearldroidos.animals.model.Animal
import com.pearldroidos.animals.util.getProgressDrawable
import com.pearldroidos.animals.util.loadImage

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    var animal: Animal? = null
    private lateinit var dataBinding:FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Init DataBinding
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        animal?.imageUrl?.let {
            setUpBackgroundColor(it)
        }

        dataBinding.animal = animal
    }


    private fun setUpBackgroundColor(url: String){
        Glide.with(this).asBitmap().load(url).into(object : CustomTarget<Bitmap>(){
            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    dataBinding.animalLayout = Palette.from(resource).generate()
            }

        })
    }

}
