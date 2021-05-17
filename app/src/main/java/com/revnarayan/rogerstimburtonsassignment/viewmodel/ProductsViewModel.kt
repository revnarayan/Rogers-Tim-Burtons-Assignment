package com.revnarayan.rogerstimburtonsassignment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel
import com.revnarayan.rogerstimburtonsassignment.repository.ProductsRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

@ActivityScoped
class ProductsViewModel @ViewModelInject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {

    private val _productsPageUiModel = MutableLiveData<ProductsPageUiModel>()
    val productsPageUiModel: LiveData<ProductsPageUiModel>
        get() = _productsPageUiModel
    var unfilteredProductsList: List<ProductsUiModel>? = null

    init {
        getProductContent()
    }

    private fun getProductContent() = liveData(Dispatchers.IO) {
        emit(
            withContext(Dispatchers.IO) {
                productsRepository.getProductsPageUiModel()
            })
    }.observeForever { result ->
        unfilteredProductsList = result?.productsUiModel
        _productsPageUiModel.postValue(result)
    }

    fun executeSearch(searchTerm: CharSequence?) {
        val totalProductsList = _productsPageUiModel.value?.productsUiModel
        val filteredList = searchTerm?.takeIf { it.isNotEmpty() }?.let { totalProductsList?.filter { it.name?.startsWith(searchTerm, ignoreCase = true) ?: false } } ?: unfilteredProductsList
         _productsPageUiModel.postValue(ProductsPageUiModel(filteredList))
    }
}