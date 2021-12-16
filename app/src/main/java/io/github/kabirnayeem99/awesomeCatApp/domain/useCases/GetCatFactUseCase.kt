package io.github.kabirnayeem99.awesomeCatApp.domain.useCases

import io.github.kabirnayeem99.awesomeCatApp.common.responseSealed.Resource
import io.github.kabirnayeem99.awesomeCatApp.domain.repositories.CatRepository
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo
import javax.inject.Inject

class GetCatFactUseCase @Inject constructor(private val catRepo: CatRepository) {

    var shouldRefresh: Boolean = false

    suspend fun configure(shouldRefresh: Boolean = false) {
        this.shouldRefresh = shouldRefresh
    }

    suspend fun getCatFacts(): Resource<List<CatVo>> {
        return catRepo.getCatFacts(shouldRefresh)
    }
}