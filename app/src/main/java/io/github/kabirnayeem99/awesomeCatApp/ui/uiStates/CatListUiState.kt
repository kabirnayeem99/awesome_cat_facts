package io.github.kabirnayeem99.awesomeCatApp.ui.uiStates

import io.github.kabirnayeem99.awesomeCatApp.domain.viewObjects.CatVo

data class CatListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val catFactList: List<CatVo> = emptyList(),
)
