package io.github.kabirnayeem99.awesomeCatApp.data.repositories

import io.github.kabirnayeem99.awesomeCatApp.common.responseSealed.Resource
import io.github.kabirnayeem99.awesomeCatApp.data.dataSources.remoteDataSource.CatRemoteDataSource
import io.github.kabirnayeem99.awesomeCatApp.domain.repositories.CatRepository
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class DefaultCatRepository @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource,
    private val externalScope: CoroutineScope
) :
    CatRepository {

    // Mutex to make writes to cached values thread-safe.
    private val latestCatsMutex = Mutex()

    // Cache of the latest news got from the network.
    private var latestCatFacts: Resource<List<CatVo>> = Resource.Empty()

    override suspend fun getCatFacts(refresh: Boolean): Resource<List<CatVo>> {

        return if (refresh || latestCatFacts.data == null) {
            externalScope.async {
                remoteDataSource.getCatFacts().also { catVoListResource ->
                    // Thread-safe write to latestNews
                    latestCatsMutex.withLock {
                        this@DefaultCatRepository.latestCatFacts = catVoListResource
                    }
                }
            }.await()
        } else {
            latestCatsMutex.withLock { latestCatFacts }
        }
    }
}