package com.revnarayan.rogerstimburtonsassignment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModels
import com.revnarayan.rogerstimburtonsassignment.repository.ProductsRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ActivityScoped
class ProductsViewModel @ViewModelInject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {

    private val _productsPageUiModel = MutableLiveData<ProductsPageUiModel>()
    val productsPageUiModel: LiveData<ProductsPageUiModel>
        get() = _productsPageUiModel
    var unfilteredProductList: List<ProductUiModels>? = null

    init {
        getProductContent()
    }

     fun getProductContent() = liveData(Dispatchers.IO) {
        emit(
            withContext(Dispatchers.IO) {
                productsRepository.getProductsPageUiModel()
            })
    }.observeForever { result ->
        unfilteredProductList = result?.productUiModels
        _productsPageUiModel.postValue(result)
    }

    fun executeSearch(searchTerm: CharSequence?) {
        val totalProductsList = _productsPageUiModel.value?.productUiModels
        val filteredList = searchTerm?.takeIf { it.isNotEmpty() }?.let {
            totalProductsList?.filter {
                it.name?.startsWith(searchTerm, ignoreCase = true) ?: false
            }
        } ?: unfilteredProductList
        _productsPageUiModel.postValue(ProductsPageUiModel(filteredList))
    }
}