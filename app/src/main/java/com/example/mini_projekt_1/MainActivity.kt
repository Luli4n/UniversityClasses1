package com.example.mini_projekt_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalAppFontSize = compositionLocalOf { 14.sp }
val LocalAppBackgroundColor = compositionLocalOf { Color.White }
class MainActivity : ComponentActivity() {

    private val preferences: SharedPreferences by lazy {
        getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }


    @Composable
    fun MainScreen() {
        val fontSize = rememberSaveable { mutableStateOf(getOption("font_size", "small")) }
        val bgColor = rememberSaveable { mutableStateOf(getOption("background_color", "white")) }

        val finalFontSize = when (fontSize.value) {
            "small" -> 14.sp
            else -> 20.sp
        }

        val finalBgColor = when (bgColor.value) {
            "white" -> Color.White
            else -> Color.Gray
        }


        DisposableEffect(Unit) {
            val prefListener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
                fontSize.value = preferences.getString("font_size", "small") ?: "small"
                bgColor.value = preferences.getString("background_color", "white") ?: "white"
            }

            preferences.registerOnSharedPreferenceChangeListener(prefListener)

            onDispose {
                preferences.unregisterOnSharedPreferenceChangeListener(prefListener)
            }
        }

        CompositionLocalProvider(
            LocalAppFontSize provides finalFontSize,
            LocalAppBackgroundColor provides finalBgColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LocalAppBackgroundColor.current)
                    .padding(16.dp)
            ) {
                Button(onClick = {
                    val intent = Intent(this@MainActivity, ProductListActivity::class.java)
                    startActivity(intent)
                }) {
                    Text("Lista produktów", fontSize = LocalAppFontSize.current)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val intent = Intent(this@MainActivity, OptionsActivity::class.java)
                    startActivity(intent)
                }) {
                    Text("Opcje", fontSize = LocalAppFontSize.current)
                }
            }
        }


    }
    fun getOption(key: String, default: String): String = preferences.getString(key, default) ?: default
}