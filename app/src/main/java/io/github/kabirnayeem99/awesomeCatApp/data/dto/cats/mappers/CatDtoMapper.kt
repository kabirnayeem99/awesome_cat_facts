package io.github.kabirnayeem99.awesomeCatApp.data.dto.cats.mappers

import io.github.kabirnayeem99.awesomeCatApp.data.dto.cats.CatsApiResponseDtoItem
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo

fun CatsApiResponseDtoItem.toCatVo(): CatVo {
    return CatVo(_id, text, updatedAt)
}