package com.example.mini_projekt_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.mini_projekt_1.models.Product
import com.example.mini_projekt_1.viewmodel.ProductListViewModel

class ProductListActivity : ComponentActivity() {
    private lateinit var viewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

        setContent {
            val products = viewModel.products.collectAsState(initial = emptyList()).value
            Column {
                ProductAddForm { product ->
                    // Dodaj produkt do ViewModel
                    viewModel.addProduct(product)
                }
                Spacer(modifier = Modifier.height(16.dp))
                ProductList(products, viewModel::updateProduct, viewModel::deleteProduct)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductAddForm(onAddProduct: (Product) -> Unit) {
        var productName by remember { mutableStateOf("") }
        var productPrice by remember { mutableStateOf("") }
        var productQuantity by remember { mutableStateOf("") }

        Column {
            TextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Nazwa produktu") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = productPrice,
                onValueChange = {
                    if (it.count { char -> char == '.' } <= 1 && it.all { char -> char.isDigit() || char == '.' }) {
                        productPrice = it
                    }
                },
                label = { Text("Cena produktu") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = productQuantity,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        productQuantity = it
                    }
                },
                label = { Text("Ilość produktu") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                onAddProduct(
                    Product(
                        name = productName,
                        price = productPrice.toDouble(),
                        quantity = productQuantity.toInt(),
                        isPurchased = false
                    )
                )
            }) {
                Text("Dodaj")
            }
        }
    }

    @Composable
    fun ProductList(products: List<Product>, onProductUpdate: (Product) -> Unit, onProductDelete: (Product) -> Unit) {
        LazyColumn {
            items(products) { product ->
                ProductItem(product, onProductUpdate, onProductDelete)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductItem(product: Product, onProductUpdate: (Product) -> Unit, onProductDelete: (Product) -> Unit) {
        var updatedName by remember { mutableStateOf(product.name) }
        var updatedPrice by remember { mutableStateOf(product.price.toString()) }
        var updatedQuantity by remember { mutableStateOf(product.quantity.toString()) }
        var isChecked by remember { mutableStateOf(product.isPurchased) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onProductDelete(product) }) {
                Text("Usuń")
            }

            TextField(
                value = updatedName,
                onValueChange = { updatedName = it
                    onProductUpdate(product.copy(name = updatedName))
                },
                modifier = Modifier.weight(1f)
            )

            TextField(
                value = updatedPrice,
                onValueChange = {
                    if (it.count { char -> char == '.' } <= 1 && it.all { char -> char.isDigit() || char == '.' }) {
                        updatedPrice = it
                        if (updatedPrice.toDoubleOrNull() != null) {
                            onProductUpdate(product.copy(price = updatedPrice.toDouble()))
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                value = updatedQuantity,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        updatedQuantity = it
                        if (updatedQuantity.toIntOrNull() != null) {
                            onProductUpdate(product.copy(quantity = updatedQuantity.toInt()))
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onProductUpdate(product.copy(name = updatedName, price = updatedPrice.toDouble(), quantity = updatedQuantity.toInt(), isPurchased = isChecked))
                }
            )


        }
    }



}

