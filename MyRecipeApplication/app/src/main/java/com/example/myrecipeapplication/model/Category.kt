package com.example.myrecipeapplication.model

data class Category(val idCategory: String,
                    val strCategory: String,
                    val strCategoryThumb: String,
                    val strCategoryDescription: String)

data class CategoryCollection(val categories : List<Category>)