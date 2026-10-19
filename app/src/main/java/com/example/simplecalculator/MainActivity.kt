package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.SimplecalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimplecalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var angka1 by remember { mutableStateOf("") }
    var angka2 by remember { mutableStateOf("") }
    var hasil by remember { mutableStateOf("0") }
    var pesanError by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kalkulator Sederhana",
            fontSize = 22.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = angka1,
            onValueChange = { angka1 = it },
            label = { Text("Masukkan Bilangan Pertama") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = angka2,
            onValueChange = { angka2 = it },
            label = { Text("Masukkan Bilangan Kedua") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    val res = hitung(angka1, angka2, '+') { pesanError = it }
                    if (res != null) hasil = res
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("+")
            }
            Button(
                onClick = {
                    val res = hitung(angka1, angka2, '-') { pesanError = it }
                    if (res != null) hasil = res
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("-")
            }
            Button(
                onClick = {
                    val res = hitung(angka1, angka2, '*') { pesanError = it }
                    if (res != null) hasil = res
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("×")
            }
            Button(
                onClick = {
                    val res = hitung(angka1, angka2, '/') { pesanError = it }
                    if (res != null) hasil = res
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("÷")
            }
        }

        if (pesanError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = pesanError, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Hasil Perhitungan:", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = hasil,
                fontSize = 28.sp,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

fun hitung(str1: String, str2: String, operator: Char, onError: (String) -> Unit): String? {
    if (str1.isEmpty() || str2.isEmpty()) {
        onError("Silakan masukkan kedua bilangan terlebih dahulu!")
        return null
    }

    val angka1 = str1.toDoubleOrNull()
    val angka2 = str2.toDoubleOrNull()

    if (angka1 == null || angka2 == null) {
        onError("Format angka tidak valid!")
        return null
    }

    onError("")
    val hasil = when (operator) {
        '+' -> angka1 + angka2
        '-' -> angka1 - angka2
        '*' -> angka1 * angka2
        '/' -> {
            if (angka2 == 0.0) {
                onError("Kesalahan: Tidak dapat membagi dengan nol!")
                return null
            }
            angka1 / angka2
        }
        else -> 0.0
    }

    return if (hasil % 1.0 == 0.0) {
        hasil.toInt().toString()
    } else {
        hasil.toString()
    }
}