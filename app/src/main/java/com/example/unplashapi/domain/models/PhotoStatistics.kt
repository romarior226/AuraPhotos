package com.example.unplashapi.domain.models

data class DailyValue(
    val date: String,
    val value: Int
)

data class PhotoStatistics(
    val id: String,
    val downloads: Int,
    val views: Int,
    val changeViews: Int,
    val changeDownloads: Int,
    val valuesViews: List<DailyValue>,
    val valuesDownloads: List<DailyValue>
)
