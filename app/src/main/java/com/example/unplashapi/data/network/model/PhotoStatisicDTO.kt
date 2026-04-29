package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailPhotoStatisticDTO(
    @SerialName("id") val id: String,
    @SerialName("downloads") val downloads: StatisticSectionDTO,
    @SerialName("views") val views: StatisticSectionDTO,
)

@Serializable
data class StatisticSectionDTO(
    @SerialName("total") val total: Int,
    @SerialName("historical") val historical:HistoricalDataDTO,
)


@Serializable
data class HistoricalDataDTO(
    @SerialName("change") val change: Int,
    @SerialName("values") val values: List<DailyMetric>,
)


@Serializable
data class DailyMetric(
    @SerialName("date") val date: String,
    @SerialName("value") val value: Int
)

