package br.edu.ifspcjo.fic.pdma.calculadoraimc

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Home()
        }
    }
}

@Composable
fun Home(){
    var weightInput by remember { mutableStateOf("") }
    var heightInput by remember { mutableStateOf("") }
    var imcResult by remember { mutableStateOf<Double?>(null) }
    var condition by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Text(
            text = "Calculadora IMC",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = weightInput,
            onValueChange = {
                if(it.matches(Regex("^\\d*([.,])?\\d*\$"))){
                    weightInput = it
                } else if(it.isEmpty()){
                    weightInput = ""
                }
            },
            label = {
                Text("Peso (Kg)")
            },
            placeholder = {
                Text("Ex: 70.5")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = heightInput,
            onValueChange = {
                if(it.matches(Regex("^\\d*([.,])?\\d*\$"))){
                    heightInput = it
                } else if(it.isEmpty()){
                    heightInput = ""
                }
            },
            label = {
                Text("Altura (m)")
            },
            placeholder = {
                Text("Ex: 1.8")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val weightString = weightInput.replace(',', '.')
                val heightString = heightInput.replace(',', '.')

                val weight = weightString.toDoubleOrNull()
                val height = heightString.toDoubleOrNull()

                if(weight == null || weight <= 0){
                    Toast.makeText(context, "Por favor, insira um peso válido", Toast.LENGTH_SHORT).show()
                    imcResult = null
                    condition = ""
                    return@Button
                }
                if(height == null || height <= 0){
                    Toast.makeText(context, "Por favor, insira uma altura válido", Toast.LENGTH_SHORT).show()
                    imcResult = null
                    condition = ""
                    return@Button
                }
                // calcular
                val imcValue = weight / (height * height)
                imcResult = imcValue
                condition = getCondition(imcValue)
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular IMC")
        }

        imcResult?.let {
            val df = DecimalFormat("#.##")
            Text(
                text = "Seu IMC: ${df.format(it)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Condição: $condition",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun getCondition(imc: Double): String {
        return when{
            imc < 18.5 -> "Abaixo do peso"
            imc <= 24.9 -> "Peso normal"
            imc <= 29.9 -> "Acima do peso"
            else -> "Obeso"
        }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}