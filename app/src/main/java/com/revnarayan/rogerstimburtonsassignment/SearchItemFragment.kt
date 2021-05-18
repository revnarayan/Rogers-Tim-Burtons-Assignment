package com.revnarayan.rogerstimburtonsassignment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.revnarayan.rogerstimburtonsassignment.databinding.FragmentSearchItemBinding
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModels
import com.revnarayan.rogerstimburtonsassignment.recyclerview.ProductsAdapter
import com.revnarayan.rogerstimburtonsassignment.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.fragment_search_item.*
import kotlinx.android.synthetic.main.fragment_search_item.search_text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchItemFragment : Fragment() {
    private val productsViewModel: ProductsViewModel by activityViewModels()
//    private var productList: List<ProductUiModels>? = listOf()

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsViewModel.productsPageUiModel.observe(viewLifecycleOwner) {
            updateProductList(it.productUiModels)
        }

        rv_search_recycler_view.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(activity?.applicationContext)
        }

        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                progress_circular.visibility = View.VISIBLE
                productsViewModel.executeSearch(s)
            }
        }
        search_text.addTextChangedListener(listener)
    }

    private fun updateProductList(productList: List<ProductUiModels>?) {
        productsAdapter.apply {
            productItems = productList
            notifyDataSetChanged()
        }
        progress_circular.visibility = View.GONE
    }

}

