package com.revnarayan.rogerstimburtonsassignment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revnarayan.rogerstimburtonsassignment.databinding.ProductsListRowItemBinding
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel

class ProductsAdapter(private val productItems: List<ProductsUiModel>?) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductsViewHolder {
        val binding = ProductsListRowItemBinding.inflate(LayoutInflater.from(parent.context))
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductsViewHolder, position: Int) {
        productItems?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = productItems?.size ?: 0

    inner class ProductsViewHolder(private val binding: ProductsListRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productsItem: ProductsUiModel) {
            binding.productsItem = productsItem
            binding.executePendingBindings()
        }
    }
}