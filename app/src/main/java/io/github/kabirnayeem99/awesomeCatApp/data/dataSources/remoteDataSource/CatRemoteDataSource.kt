package io.github.kabirnayeem99.awesomeCatApp.data.dataSources.remoteDataSource

import io.github.kabirnayeem99.awesomeCatApp.common.responseSealed.Resource
import io.github.kabirnayeem99.awesomeCatApp.data.dto.cats.CatsApiResponseDto
import io.github.kabirnayeem99.awesomeCatApp.data.dto.cats.mappers.toCatVo
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(private val api: CatFactsApi) {
    suspend fun getCatFacts(): Resource<List<CatVo>> {
        return try {
            val response = api.getCatFacts()
            if (response.isSuccessful) {
                val catFacts: List<CatVo> = response.body()?.map {
                    it.toCatVo()
                } ?: emptyList()
                Resource.Success(catFacts)
            } else {
                Resource.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Could not get cat facts.")
        }
    }

}

interface CatFactsApi {
    @GET("facts")
    suspend fun getCatFacts(): Response<CatsApiResponseDto>
}