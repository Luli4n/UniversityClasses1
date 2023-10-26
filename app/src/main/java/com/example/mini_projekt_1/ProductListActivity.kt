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
                ProductList(products)
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
                onValueChange = { productPrice = it },
                label = { Text("Cena produktu") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = productQuantity,
                onValueChange = { productQuantity = it },
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
    fun ProductList(products: List<Product>) {
        LazyColumn {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }

    @Composable
    fun ProductItem(product: Product) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = product.name)
            Text(text = product.price.toString())
            Text(text = product.quantity.toString())
            Checkbox(
                checked = product.isPurchased,
                onCheckedChange = null
            )
        }
    }
}