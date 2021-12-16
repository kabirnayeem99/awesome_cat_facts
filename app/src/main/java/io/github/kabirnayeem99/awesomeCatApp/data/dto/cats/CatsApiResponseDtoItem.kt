package io.github.kabirnayeem99.awesomeCatApp.data.dto.cats

data class CatsApiResponseDtoItem(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val deleted: Boolean,
    val source: String,
    val status: StatusDto,
    val text: String,
    val type: String,
    val updatedAt: String,
    val used: Boolean,
    val user: String
)