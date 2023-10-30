package com.example.mini_projekt_1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mini_projekt_1.dao.ProductDao
import com.example.mini_projekt_1.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}