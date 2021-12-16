package io.github.kabirnayeem99.awesomeCatApp.ui.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.github.kabirnayeem99.awesomeCatApp.R
import io.github.kabirnayeem99.awesomeCatApp.databinding.ActivityMainBinding
import io.github.kabirnayeem99.awesomeCatApp.ui.uiStates.CatListUiState
import io.github.kabirnayeem99.awesomeCatApp.ui.viewmodels.CatViewModel
import io.github.kabirnayeem99.awesomeCatApp.ui.views.adapters.CatItemAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val catViewModel: CatViewModel by viewModels()
    private val catItemAdapter: CatItemAdapter by lazy {
        CatItemAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViews()
        subscribeQuery()
    }

    private fun setUpViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.apply {
            setContentView(root)
            viewModel = catViewModel
            lifecycleOwner = this@MainActivity
            rvCatFacts.adapter = catItemAdapter
        }
    }

    private fun subscribeQuery() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catViewModel.apply {
                    getCatFacts()
                    uiState.collect { catUiState -> handleUiState(catUiState) }
                }
            }
        }
    }

    private fun handleUiState(catUiState: CatListUiState) {
        catUiState.apply {
            catItemAdapter.submitCatFactList(facts = catFactList)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}