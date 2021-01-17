package com.udacity.asteroidradar.data

import com.squareup.moshi.Json
import com.udacity.asteroidradar.domain.PictureOfDayDomain

data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)


fun PictureOfDay.asDomainModel(): PictureOfDayDomain {
    return PictureOfDayDomain(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}
