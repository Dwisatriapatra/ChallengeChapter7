package com.example.challengechapter7.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.ItemAdapterGhibliFilmBinding
import com.example.challengechapter7.model.GetAllGhibliFilmResponseItem

class GhibliFilmsAdapter(
    private val onClick: (GetAllGhibliFilmResponseItem) -> Unit
) : RecyclerView.Adapter<GhibliFilmsAdapter.ViewHolder>() {
    // initializing list dan make function to set value of that list
    private var listGhibliFilms : List<GetAllGhibliFilmResponseItem>? = null
    fun setDataGhibliFilms(list : List<GetAllGhibliFilmResponseItem>){
        this.listGhibliFilms = list
    }

    //view holder with viewBinding
    inner class ViewHolder(val binding: ItemAdapterGhibliFilmBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GhibliFilmsAdapter.ViewHolder {
        val binding = ItemAdapterGhibliFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //set all text in view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GhibliFilmsAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(listGhibliFilms!![position]) {
                binding.ghibliFilmsTitle.text = "Title : $original_title_romanised / " +
                        "$title ($original_title)"
                binding.ghibliFilmsProducer.text = "Producer : $producer"
                binding.ghibliFilmsDirector.text = "Director : $director"
                binding.ghibliFilmsReleaseData.text = "Release date : $release_date"
                binding.ghibliFilmRating.text = "${rt_score.toInt()/10.0}"
                Glide.with(binding.ghibliFilmsImage.context)
                    .load(image)
                    .error(R.drawable.ic_launcher_background)
                    .override(1000, 150)
                    .into(binding.ghibliFilmsImage)
            }
        }
        holder.binding.seeDetailButton.setOnClickListener {
            onClick(listGhibliFilms!![position])
        }

    }

    override fun getItemCount(): Int {
        return if(listGhibliFilms.isNullOrEmpty()){
            0
        }else{
            listGhibliFilms!!.size
        }
    }
}