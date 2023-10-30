package com.example.mini_projekt_1.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.example.mini_projekt_1.database.AppDatabase
import com.example.mini_projekt_1.model.Product
import kotlinx.coroutines.runBlocking

class ProductContentProvider : ContentProvider() {

    companion object {
        const val CODE_PRODUCTS = 1

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI("com.example.mini_projekt_1.provider", "products", CODE_PRODUCTS)
        }
    }

    private lateinit var database: AppDatabase

    override fun onCreate(): Boolean {
        database = Room.databaseBuilder(
            context!!,
            AppDatabase::class.java, "products"
        ).build()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        when (MATCHER.match(uri)) {
            CODE_PRODUCTS -> {
                return database.productDao().getAllProductsCursor()
            }
            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_PRODUCTS -> "vnd.android.cursor.dir/vnd.com.example.mini_projekt_1.provider.products"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (MATCHER.match(uri) == CODE_PRODUCTS) {
            val id = runBlocking {
                    database.productDao().insertProduct(Product.fromContentValues(values!!))
                }
            context?.contentResolver?.notifyChange(uri, null)
            return ContentUris.withAppendedId(uri, id)
        }
        throw IllegalArgumentException("Unknown URI: $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        if (MATCHER.match(uri) == CODE_PRODUCTS) {
            val count: Int = runBlocking {
                database.productDao().deleteAll()
            }
            context?.contentResolver?.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Unknown URI: $uri")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        if (MATCHER.match(uri) == CODE_PRODUCTS) {
            val product = Product.fromContentValues(values!!)
            val count: Int = runBlocking {
                database.productDao().updateProduct(product)
            }
            context?.contentResolver?.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Unknown URI: $uri")
    }

}