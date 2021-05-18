package com.revnarayan.rogerstimburtonsassignment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import javax.inject.Inject

@AndroidEntryPoint
class SearchItemFragment : Fragment() {
    private val productsViewModel: ProductsViewModel by activityViewModels()
    private var productList: List<ProductUiModels>? = listOf()

    @Inject
    lateinit var productsAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchItemBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productsViewModel.productsPageUiModel.observe(viewLifecycleOwner, Observer {
            productList = it.productUiModels
            updateProductList()
        })
        rv_search_recycler_view.visibility =View.GONE
        rv_search_recycler_view.apply {
            setHasFixedSize(true)
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(activity?.applicationContext)
        }

        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                rv_search_recycler_view.visibility = View.GONE
                progress_circular.visibility = View.VISIBLE
                productsViewModel.executeSearch(s)
            }
        }
        search_text.addTextChangedListener(listener)
    }

    private fun updateProductList() {
        productsAdapter.apply {
            productItems = productList
            notifyDataSetChanged()
        }
    }
    override fun onResume() {
        activity?.getWindow()?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onResume()
    }


}

