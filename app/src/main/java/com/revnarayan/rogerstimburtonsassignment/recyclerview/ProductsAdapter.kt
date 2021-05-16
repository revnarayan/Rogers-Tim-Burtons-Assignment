package com.revnarayan.rogerstimburtonsassignment.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revnarayan.rogerstimburtonsassignment.R
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel
import kotlinx.android.synthetic.main.products_list_row_item.view.*

class ProductsAdapter(private val productList: List<ProductsUiModel>?) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.products_list_row_item, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductsViewHolder, position: Int) {
     productList?.run {
         val itemPosition = this[position]
         holder.name.text = itemPosition.name
         holder.cost.text = itemPosition.cost.toString()
     }
    }

    override fun getItemCount(): Int = productList?.size ?: 0

    inner class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = itemView.tv_name
        val cost = itemView.tv_cost
    }
}