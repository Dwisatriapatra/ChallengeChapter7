package com.example.challengechapter7.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentDetailBinding
import com.example.challengechapter7.model.FavoriteFilm
import com.example.challengechapter7.model.GetAllGhibliFilmResponseItem
import com.example.challengechapter7.viewmodel.FavoriteFilmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var detailFragmentBinding: FragmentDetailBinding? = null
    private lateinit var favoriteFilmViewModel: FavoriteFilmViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        detailFragmentBinding = binding

        getAllDetail()
    }

    private fun getAllDetail() {
        favoriteFilmViewModel = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        if (requireArguments().containsKey("FAVORITEGHIBLIFILMDATA")) {
            val detail = arguments?.getParcelable<FavoriteFilm>("FAVORITEGHIBLIFILMDATA")
            detailFragmentBinding!!.detailDeskripsi.text = detail!!.description
            detailFragmentBinding!!.detailDirector.text = detail.director
            detailFragmentBinding!!.detailProducer.text = detail.producer
            detailFragmentBinding!!.detailJudulAsli.text = detail.judulOriginal
            detailFragmentBinding!!.detailJudulInggris.text = detail.judulInggris
            detailFragmentBinding!!.detailJudulRomaji.text = detail.judulRomaji
            detailFragmentBinding!!.detailRating.text = (detail.rating!!.toInt() / 10.0).toString()
            detailFragmentBinding!!.detailTanggalRilis.text = detail.releaseDate
            Glide.with(detailFragmentBinding!!.detailImage.context)
                .load(detail.image)
                .error(R.drawable.ic_launcher_background)
                .into(detailFragmentBinding!!.detailImage)

            detailFragmentBinding!!.detailAddOrRemoveFavorite.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("HAPUS FILM DARI LIST FAVORIT")
                    .setMessage("Anda yakin ingin menghapus film ini dari list favorit?")
                    .setNegativeButton("TIDAK") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA") { _: DialogInterface, _: Int ->
                        favoriteFilmViewModel.deleteFavoriteFilm(detail.id!!)
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil dihapus dari list favorit",
                            Toast.LENGTH_LONG
                        ).show()
                    }.show()
            }

            detailFragmentBinding!!.detailShare.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey Check out this great film: ${detail.judulInggris}!"
                )
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share To:"))
            }


        } else if (requireArguments().containsKey("GHIBLIFILMDATA")) {
            val detail = arguments?.getParcelable<GetAllGhibliFilmResponseItem>("GHIBLIFILMDATA")

            detailFragmentBinding!!.detailDeskripsi.text = detail!!.description
            detailFragmentBinding!!.detailDirector.text = detail.director
            detailFragmentBinding!!.detailProducer.text = detail.producer
            detailFragmentBinding!!.detailJudulAsli.text = detail.original_title
            detailFragmentBinding!!.detailJudulInggris.text = detail.title
            detailFragmentBinding!!.detailJudulRomaji.text = detail.original_title_romanised
            detailFragmentBinding!!.detailRating.text = (detail.rt_score.toInt() / 10.0).toString()
            detailFragmentBinding!!.detailTanggalRilis.text = detail.release_date
            Glide.with(detailFragmentBinding!!.detailImage.context)
                .load(detail.image)
                .error(R.drawable.ic_launcher_background)
                .into(detailFragmentBinding!!.detailImage)

            detailFragmentBinding!!.detailAddOrRemoveFavorite.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Tambah ke favorit")
                    .setMessage("Anda yakin ingin menambahkan film ke list favorit?")
                    .setNegativeButton("TIDAK") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA") { _: DialogInterface, _: Int ->
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        favoriteFilmViewModel.insertNewFavoriteFilm(
                            FavoriteFilm(
                                null,
                                detail.title,
                                detail.original_title,
                                detail.original_title_romanised,
                                detail.director,
                                detail.producer,
                                detail.release_date,
                                detail.rt_score,
                                detail.description,
                                detail.image,
                                detail.id
                            )
                        )
                    }.show()
            }
            detailFragmentBinding!!.detailShare.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey Check out this great film: ${detail.title}!"
                )
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share To:"))
            }
        }
    }
}