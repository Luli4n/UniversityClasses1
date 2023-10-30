package com.example.mini_projekt_1.model

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    val isPurchased: Boolean,
) {

    companion object {
        fun fromContentValues(values: ContentValues): Product {
            return Product(
                id = values.getAsLong("id"),
                name = values.getAsString("name"),
                price = values.getAsDouble("price"),
                quantity = values.getAsInteger("quantity"),
                isPurchased = values.getAsBoolean("isPurchased")
            )
        }
    }
}