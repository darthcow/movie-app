package com.project.movieapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

//todo add progress bar while loading
fun ImageView.loadUrl(url: String?) = Glide.with(this).load(url).centerCrop().into(this)
