package io.github.kabirnayeem99.awesomeCatApp.data.repositories

import io.github.kabirnayeem99.awesomeCatApp.common.responseSealed.Resource
import io.github.kabirnayeem99.awesomeCatApp.common.utility.DispatcherProvider
import io.github.kabirnayeem99.awesomeCatApp.data.dataSources.remoteDataSource.CatRemoteDataSource
import io.github.kabirnayeem99.awesomeCatApp.domain.repositories.CatRepository
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCatRepository @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource,
    private val dispatcher: DispatcherProvider
) :
    CatRepository {

    // Mutex to make writes to cached values thread-safe.
    private val latestCatsMutex = Mutex()

    // Cache of the latest news got from the network.
    private var latestCatFacts: Resource<List<CatVo>> = Resource.Empty()

    override suspend fun getCatFacts(refresh: Boolean): Resource<List<CatVo>> {
        withContext(dispatcher.io) {
            val itemsRes = remoteDataSource.getCatFacts()

            // Thread-safe write to latestNews
            latestCatsMutex.withLock {
                this@DefaultCatRepository.latestCatFacts = itemsRes
            }
        }
        return latestCatsMutex.withLock { latestCatFacts }
    }
}