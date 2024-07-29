package com.example.myrecipeapplication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapplication.model.Category
import com.example.myrecipeapplication.services.recipeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categorystate = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categorystate

    init {
        fetchCategories()
    }

    private fun fetchCategories(){
        val recipeCollectionFlow : Flow<List<Category>> = flow {
            while (true){
                val collection = recipeService.getCategories()
                emit(collection.categories)
            }
        }

        viewModelScope.launch {
            recipeCollectionFlow.catch { e ->
                    _categorystate.value = _categorystate.value.copy(
                        loading = false,
                        error = "Error fetching categories ${e.message}"
                    )
                    }.collect{ collections ->
                    _categorystate.value = _categorystate.value.copy(
                        loading = false,
                        categorise = collections,
                        error = null
                    )
                }


            }
        }
    }

    data class RecipeState(
        val loading : Boolean = true,
        val categorise : List<Category> = emptyList(),
        val error: String? = null
    )


