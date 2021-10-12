package com.entreprisecorp.projectinfo901

import android.content.Context
import android.util.Log
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun Button.setDrawableEnd(@DrawableRes drawableRes: Int?, context: Context) {
    val img = drawableRes?.let { ContextCompat.getDrawable(context, drawableRes) }
    this.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null)
}

fun Button.setColor(@ColorRes color: Int, context: Context) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}