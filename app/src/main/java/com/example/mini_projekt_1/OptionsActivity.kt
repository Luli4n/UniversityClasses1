package com.example.mini_projekt_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class OptionsActivity : ComponentActivity() {
    private val preferences: SharedPreferences by lazy {
        getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OptionsScreen()
        }
    }

    @Composable
    fun OptionsScreen() {
        var fontSize by remember { mutableStateOf(getOption("font_size", "small")) }
        var bgColor by remember { mutableStateOf(getOption("background_color", "white")) }

        val finalFontSize = when (fontSize) {
            "small" -> 14.sp
            else -> 20.sp
        }

        val finalBgColor = when (bgColor) {
            "white" -> Color.White
            else -> Color.Gray
        }

        CompositionLocalProvider(
            LocalAppFontSize provides finalFontSize,
            LocalAppBackgroundColor provides finalBgColor,

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LocalAppBackgroundColor.current)
                    .padding(16.dp)
            ) {
                Text("Opcje wyglądu", fontSize = LocalAppFontSize.current, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                FontSizeOption(fontSize) { newFont ->
                    fontSize = newFont
                    saveOption("font_size", newFont)
                }

                Spacer(modifier = Modifier.height(16.dp))

                BackgroundColorOption(bgColor) { newColor ->
                    bgColor = newColor
                    saveOption("background_color", newColor)
                }
            }
        }
    }

    @Composable
    fun FontSizeOption(current: String, onSelected: (String) -> Unit) {
        Row {
            RadioButtonOption("Mała", current == "small") {
                onSelected("small")
            }

            Spacer(modifier = Modifier.width(16.dp))

            RadioButtonOption("Duża", current == "large") {
                onSelected("large")
            }
        }
    }

    @Composable
    fun BackgroundColorOption(current: String, onSelected: (String) -> Unit) {
        Row {
            RadioButtonOption("Biały", current == "white") {
                onSelected("white")
            }

            Spacer(modifier = Modifier.width(16.dp))

            RadioButtonOption("Szary", current == "gray") {
                onSelected("gray")
            }
        }
    }

    @Composable
    fun RadioButtonOption(text: String, isSelected: Boolean, onSelected: () -> Unit) {
        Row(
            Modifier
                .selectable(
                    selected = isSelected,
                    onClick = onSelected
                )
                .padding(8.dp)
        ) {
            RadioButton(
                selected = isSelected,
                onClick = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = LocalAppFontSize.current, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }

    fun saveOption(key: String, value: String) {
        with(preferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getOption(key: String, default: String): String = preferences.getString(key, default) ?: default
}