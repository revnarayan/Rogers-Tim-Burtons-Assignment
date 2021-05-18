package com.revnarayan.rogerstimburtonsassignment.model

data class ProductUiModel(
    val name: String?,
    val cost: Double?,
) {
    val costString: String = "$ ${cost?.let { cost.toString() } ?: "-"}"
}

