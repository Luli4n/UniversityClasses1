package com.example.mini_projekt_1.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mini_projekt_1.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product")
    fun getAllProductsCursor(): Cursor

    @Insert
    suspend fun insertProduct(product: Product) : Long

    @Update
    suspend fun updateProduct(product: Product): Int

    @Delete
    suspend fun deleteProduct(product: Product): Int

    @Query("DELETE FROM product")
    suspend fun deleteAll(): Int
}