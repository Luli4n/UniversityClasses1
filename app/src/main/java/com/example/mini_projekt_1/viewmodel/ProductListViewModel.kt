package com.example.mini_projekt_1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mini_projekt_1.database.AppDatabase
import com.example.mini_projekt_1.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductListViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "products"
    ).build()

    val products: Flow<List<Product>> = db.productDao().getAllProducts()

    fun addProduct(product: Product) = viewModelScope.launch {
        db.productDao().insertProduct(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        db.productDao().updateProduct(product)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        db.productDao().deleteProduct(product)
    }
}