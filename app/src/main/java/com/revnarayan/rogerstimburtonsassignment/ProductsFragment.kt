package com.revnarayan.rogerstimburtonsassignment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.revnarayan.rogerstimburtonsassignment.databinding.FragmentProductsBinding
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel
import com.revnarayan.rogerstimburtonsassignment.recyclerview.ProductsAdapter
import com.revnarayan.rogerstimburtonsassignment.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val myViewModel: ProductsViewModel by activityViewModels()
    private var productsList: List<ProductsUiModel>? = listOf()

    @Inject
    lateinit var productsAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.productsPageUiModel.observe(viewLifecycleOwner, Observer {
            productsList = it.productsUiModel
            updateProductList()
        })
        rv_recycler_view.apply {
            setHasFixedSize(true)
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(activity?.applicationContext)
        }


        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                myViewModel.executeSearch(s)
            }
        }
        search_text.addTextChangedListener(listener)
    }

    private fun updateProductList() {
        productsAdapter.apply {
            productItems = productsList
            notifyDataSetChanged()
        }
    }


}