package io.github.kabirnayeem99.awesomeCatApp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.kabirnayeem99.awesomeCatApp.common.responseSealed.Resource
import io.github.kabirnayeem99.awesomeCatApp.domain.useCases.GetCatFactUseCase
import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo
import io.github.kabirnayeem99.awesomeCatApp.ui.uiStates.CatListUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel
@Inject constructor(private val getCatFactUseCase: GetCatFactUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(CatListUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchCatFacts: Job? = null

    fun getCatFacts(shouldRefresh: Boolean = false) {
        fetchCatFacts?.cancel()
        fetchCatFacts = viewModelScope.launch {
            getCatFactUseCase.configure(shouldRefresh)
            val factsRes: Resource<List<CatVo>> = getCatFactUseCase.getCatFacts()
            _uiState.update { it.copy(isLoading = true) }
            when (factsRes) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "",
                            catFactList = factsRes.data ?: emptyList()
                        )
                    }
                }

                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = "",
                            catFactList = emptyList()
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = factsRes.message ?: "Something went wrog.",
                            catFactList = emptyList()
                        )
                    }
                }
                is Resource.Empty -> {
                    CatListUiState()
                }
            }
        }
    }


    fun refreshFactList() {
        getCatFacts(true)
    }
}