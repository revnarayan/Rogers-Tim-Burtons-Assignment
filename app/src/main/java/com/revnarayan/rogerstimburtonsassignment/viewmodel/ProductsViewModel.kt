package com.revnarayan.rogerstimburtonsassignment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModel
import com.revnarayan.rogerstimburtonsassignment.repository.ProductsRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*

@ActivityScoped
class ProductsViewModel @ViewModelInject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {
    private val _filteredProductUiModels = MutableLiveData<List<ProductUiModel>>()
    val filteredProductUiModels = _filteredProductUiModels

    private val _unfilteredProductUiModels: MutableLiveData<List<ProductUiModel>> =
        MutableLiveData()
    val unfilteredProductUiModels: LiveData<List<ProductUiModel>>
        get() = _unfilteredProductUiModels

    init {
        fetchProducts()
    }

    private fun fetchProducts() = liveData(Dispatchers.IO) {
        emit(
            withContext(Dispatchers.IO) {
                productsRepository.getProducts()
            })
    }.observeForever { result ->
        _unfilteredProductUiModels.postValue(result)
    }

    fun filterProducts(searchTerm: CharSequence?) {
        viewModelScope.launch {
            delay(50)
            val totalProductsList = _unfilteredProductUiModels.value
            val filteredList = searchTerm?.takeIf { it.isNotEmpty() }?.let {
                totalProductsList?.filter {
                    it.name?.startsWith(searchTerm, ignoreCase = true) ?: false
                }
            } ?: listOf()
            _filteredProductUiModels.postValue(filteredList)
        }
    }
}