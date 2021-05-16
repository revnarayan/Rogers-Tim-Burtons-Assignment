package com.revnarayan.rogerstimburtonsassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.revnarayan.rogerstimburtonsassignment.databinding.FragmentProductsBinding
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel
import com.revnarayan.rogerstimburtonsassignment.recyclerview.ProductsAdapter
import com.revnarayan.rogerstimburtonsassignment.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_products.*


class ProductsFragment : Fragment() {

    private val myViewModel: ProductsViewModel by activityViewModels()
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
            setProductListAdapter(it.productsUiModel)
        })
    }
    private fun setProductListAdapter(productList: List<ProductsUiModel>?){
        rv_recycler_view.apply {
            setHasFixedSize(true)
            adapter = ProductsAdapter(productList)
            layoutManager = LinearLayoutManager(activity?.applicationContext)
        }
    }
}