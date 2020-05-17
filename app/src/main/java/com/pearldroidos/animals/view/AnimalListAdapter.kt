package com.pearldroidos.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.pearldroidos.animals.R
import com.pearldroidos.animals.databinding.ItemAnimalBinding
import com.pearldroidos.animals.model.Animal
import com.pearldroidos.animals.util.getProgressDrawable
import com.pearldroidos.animals.util.loadImage

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {

    class AnimalViewHolder(var view: ItemAnimalBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //DataBinding
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(
            inflater,
            R.layout.item_animal,
            parent,
            false
        )
        //Original way to implement
//        val view = inflater.inflate(
//            R.layout.item_animal,
//            parent,
//            false
//        ) //command + P to look requirement next
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this



        //Original way to implement
//        holder.view.animalName.text = animalList[position].name
//        holder.view.animalImage.loadImage(
//            animalList[position].imageUrl,
//            getProgressDrawable(holder.view.context)
//        )
//        holder.view.animalLayout.setOnClickListener {
//            val action = ListFragmentDirections.actionDetail(animalList[position])
//            Navigation.findNavController(holder.view.root).navigate(action)
//        }
    }

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged() // this function is quite critical because it will recreate whole view again
    }

    override fun onClick(v: View) {
        for(animal in animalList){
            if(v.tag == animal.name){
                val action = ListFragmentDirections.actionDetail(animal)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }

}