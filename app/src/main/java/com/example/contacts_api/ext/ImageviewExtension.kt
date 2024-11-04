package com.example.contacts_api.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.contacts_api.R

fun ImageView.cargarImagen(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.predeterminada)
        .error(R.drawable.error)
        .into(this)
}
