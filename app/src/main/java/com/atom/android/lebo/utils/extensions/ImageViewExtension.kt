package com.atom.android.lebo.utils.extensions

import android.net.Uri
import android.widget.ImageView
import com.atom.android.lebo.R
import com.atom.android.lebo.utils.constants.Constant
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

fun ImageView.loadImage(uri: Uri) {
    Picasso
        .get()
        .load(uri)
        .placeholder(R.drawable.image_loading_default)
        .error(R.drawable.image_loading_default)
        .into(this)
}

fun CircleImageView.loadImage(uri: Uri) {
    Picasso
        .get()
        .load(uri)
        .resize(Constant.SIZE_DEFAULT_IMAGE, Constant.SIZE_DEFAULT_IMAGE)
        .centerCrop()
        .placeholder(R.drawable.image_user_default)
        .error(R.drawable.image_user_default)
        .into(this)
}
