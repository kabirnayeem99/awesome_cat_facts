package io.github.kabirnayeem99.awesomeCatApp.domain.repositories

import io.github.kabirnayeem99.awesomeCatApp.common.responseSealed.Resource
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo

interface CatRepository {
    suspend fun getCatFacts(refresh: Boolean = false): Resource<List<CatVo>>
}