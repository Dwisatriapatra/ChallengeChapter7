package com.example.challengechapter7.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentDetailBinding
import com.example.challengechapter7.model.GetAllGhibliFilmResponseItem

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var detailFragmentBinding: FragmentDetailBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        detailFragmentBinding = binding

        getAllDetail()
    }

    private fun getAllDetail() {
        val detail = arguments?.getParcelable<GetAllGhibliFilmResponseItem>("GHIBLIFILMDATA")

        detailFragmentBinding!!.detailDeskripsi.text = detail!!.description
        detailFragmentBinding!!.detailDirector.text = detail.director
        detailFragmentBinding!!.detailProducer.text = detail.producer
        detailFragmentBinding!!.detailJudulAsli.text = detail.original_title
        detailFragmentBinding!!.detailJudulInggris.text = detail.title
        detailFragmentBinding!!.detailJudulRomaji.text = detail.original_title_romanised
        detailFragmentBinding!!.detailRating.text = (detail.rt_score.toInt()/10.0).toString()
        detailFragmentBinding!!.detailTanggalRilis.text = detail.release_date
        Glide.with(detailFragmentBinding!!.detailImage.context)
            .load(detail.image)
            .error(R.drawable.ic_launcher_background)
            .into(detailFragmentBinding!!.detailImage)
    }
}