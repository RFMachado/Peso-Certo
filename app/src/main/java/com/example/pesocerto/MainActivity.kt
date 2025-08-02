package com.example.pesocerto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pesocerto.ui.theme.PesoCertoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PesoCertoTheme {
                PreverPesoScreen()
            }
        }
    }
}

@Composable
fun PreverPesoScreen() {
    var peso1 by remember { mutableStateOf("") }
    var peso7 by remember { mutableStateOf("") }
    var peso14 by remember { mutableStateOf("") }
    var peso21 by remember { mutableStateOf("") }
    var peso28 by remember { mutableStateOf("") }
    var peso35 by remember { mutableStateOf("") }
    var peso42 by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Icon(
            painter = painterResource(id = R.mipmap.frango), // seu PNG
            contentDescription = null,
//            tint = Color(0xFF007BFF),
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "Previsão de Peso Médio\n(42 dias)",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        @Composable
        fun InputPeso(
            label1: String,
            value1: String,
            label2: String,
            value2: String,
            imeAction1: ImeAction = ImeAction.Next,
            imeAction2: ImeAction = ImeAction.Next,
            onValueChange1: (String) -> Unit,
            onValueChange2: (String) -> Unit
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaço entre os campos
            ) {
                OutlinedTextField(
                    value = value1,
                    onValueChange = onValueChange1,
                    label = { Text("🐥 $label1") },
                    placeholder = { Text("ex: 1.320") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = imeAction1
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        cursorColor = Color.Gray
                    ),
                    modifier = Modifier
                        .weight(1f) // Divide espaço igualmente
                        .padding(vertical = 6.dp)
                )

                OutlinedTextField(
                    value = value2,
                    onValueChange = onValueChange2,
                    label = { Text("🐥 $label2") },
                    placeholder = { Text("ex: 1.320") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = imeAction2
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        cursorColor = Color.Gray
                    ),
                    modifier = Modifier
                        .weight(1f) // Divide espaço igualmente
                        .padding(vertical = 6.dp)
                )
            }

        }

        InputPeso(
            label1 = "Peso 1 dia", value1 = peso1,
            label2 = "Peso 7 dias", value2 = peso7,
            onValueChange1 = { peso1 = it },
            onValueChange2 = { peso7 = it }
        )
        InputPeso(
            label1 = "Peso 14 dia", value1 = peso14,
            label2 = "Peso 21 dias", value2 = peso21,
            onValueChange1 = { peso14 = it },
            onValueChange2 = { peso21 = it }
        )

        InputPeso(
            label1 = "Peso 28 dia", value1 = peso28,
            label2 = "Peso 35 dias", value2 = peso35,
            onValueChange1 = { peso28 = it },
            onValueChange2 = { peso35 = it },
            imeAction2 = ImeAction.Done
        )

        Button(
            onClick = {
                val p1 = peso1.toFloatOrNull() ?: 0f
                val p7 = peso7.toFloatOrNull() ?: 0f
                val p14 = peso14.toFloatOrNull() ?: 0f
                val p21 = peso21.toFloatOrNull() ?: 0f
                val p28 = peso28.toFloatOrNull() ?: 0f
                val p35 = peso35.toFloatOrNull() ?: 0f
                val estimado = 0.305f + (p1 * 1.84f) + (p7 * 1.29f) + (p14 * 0.87f) + (p21 * 0.49f) +
                        (p28 * 0.38f) + (p35 * 0.27f)
                peso42 = "%.3f".format(estimado)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007BFF)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Prever Peso Final", color = Color.White)
        }

        peso42?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .background(color = Color(0xFFE6F2FF))
            ) {
                Text(
                    text = "📈 Peso estimado aos 42 dias: $it kg",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PesoCertoTheme {
        PreverPesoScreen()
    }
}