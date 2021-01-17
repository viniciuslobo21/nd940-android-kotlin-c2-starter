package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.domain.AsteroidDomain
import com.udacity.asteroidradar.domain.PictureOfDayDomain
import com.udacity.asteroidradar.main.AsteroidAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("contentDescription")
fun bindAsteroidContentDescription(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.contentDescription =
            context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<AsteroidDomain>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    data?.let { adapter.submitList(it) }
    recyclerView.scrollToPosition(0)
}

@BindingAdapter("imageOfDay")
fun bindAsteroidContentDescription(imageView: ImageView, pictureOfDay: PictureOfDayDomain?) {
    val context = imageView.context
    if (pictureOfDay != null && pictureOfDay.mediaType == "image") {
        Picasso
            .with(imageView.context).load(pictureOfDay.url)
            .error(R.drawable.ic_baseline_error_outline)
            .into(imageView)
        imageView.contentDescription = pictureOfDay.title
    } else {
        imageView.setImageResource(R.drawable.ic_baseline_error_outline)
        imageView.contentDescription = context.getString(R.string.unavailable_image_error)

    }
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}