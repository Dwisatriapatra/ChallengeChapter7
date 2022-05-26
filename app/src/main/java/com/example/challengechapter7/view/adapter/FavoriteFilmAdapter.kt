package com.example.challengechapter7.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.ItemAdapterGhibliFilmBinding
import com.example.challengechapter7.model.FavoriteFilm

class FavoriteFilmAdapter(private val onClick: (FavoriteFilm) -> Unit) :
    RecyclerView.Adapter<FavoriteFilmAdapter.ViewHolder>() {

    private var listFavoriteGhibliFilm: List<FavoriteFilm>? = null
    fun setDataFavoriteGhibliFilms(list: List<FavoriteFilm>) {
        this.listFavoriteGhibliFilm = list
    }

    inner class ViewHolder(val binding: ItemAdapterGhibliFilmBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteFilmAdapter.ViewHolder {
        val binding = ItemAdapterGhibliFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteFilmAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(listFavoriteGhibliFilm!![position]) {
                binding.ghibliFilmsTitle.text = "Title : $judulRomaji / " +
                        "$judulInggris ($judulOriginal)"
                binding.ghibliFilmsProducer.text = "Producer : $producer"
                binding.ghibliFilmsDirector.text = "Director : $director"
                binding.ghibliFilmsReleaseData.text = "Release date : $releaseDate"
                binding.ghibliFilmRating.text = "${rating!!.toInt() / 10.0}"
                Glide.with(binding.ghibliFilmsImage.context)
                    .load(image)
                    .override(100, 150)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.ghibliFilmsImage)
            }
        }
        holder.binding.seeDetailButton.setOnClickListener {
            onClick(listFavoriteGhibliFilm!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (listFavoriteGhibliFilm.isNullOrEmpty()) {
            0
        } else {
            listFavoriteGhibliFilm!!.size
        }
    }
}