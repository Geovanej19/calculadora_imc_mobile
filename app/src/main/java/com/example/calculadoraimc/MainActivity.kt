package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable

fun IMCScreen(modifier: Modifier = Modifier) {

    var altura by remember {
        mutableStateOf("")
    }

    var peso by remember {
        mutableStateOf("")
    }

    var imc by remember {
        mutableStateOf(0.0)
    }

    var categoria by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier.fillMaxWidth())
//              -- header --
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(color = colorResource(id = R.color.cor_app)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(70.dp)
                            .padding(16.dp),
                        painter = painterResource(R.drawable.bmi),
                        contentDescription = "IMC"
                    )

                    Text(
                        text = "Calculadora IMC",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
//                -- Formulário --
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .height(300.dp)
                            .offset(y = (-30).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F6F6)
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = "Seus Dados",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.cor_app),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(20.dp)
                        )

                        OutlinedTextField(
                            value = altura,
                            onValueChange = { altura = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    text = "Altura"
                                )
                            },

                            shape = RoundedCornerShape(
                                14.dp,
                                14.dp,
                                14.dp,
                                14.dp
                            ),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.cor_app),
                                unfocusedBorderColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        OutlinedTextField(
                            value = peso,
                            onValueChange = {peso = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    text = "Peso"
                                )
                            },
                            shape = RoundedCornerShape(
                                14.dp,
                                14.dp,
                                14.dp,
                                14.dp
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.cor_app),
                                unfocusedBorderColor = Color.Gray
                            )
                        )



                        Button(
                            onClick = {
                                val alturaM = altura.replace(",", ".").toDouble()
                                val pesoKg = peso.replace(",", ".").toDouble()

                                if (alturaM > 0 && pesoKg > 0) {
                                    imc = pesoKg / (alturaM * alturaM)
                                    categoria = when {
                                        imc < 18.5 -> "Abaixo do peso"
                                        imc < 25.0 -> "Peso ideal"
                                        imc < 30.0 -> "Levemente acima do peso"
                                        imc < 35.0 -> "Obesidade grau I"
                                        imc < 40.0 -> "Obesidade grau II"
                                        else -> "Obesidade grau III"
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.cor_app),
                                contentColor = Color.Blue
                            ),

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 50.dp, vertical = 20.dp)
                                .height(50.dp),

                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Text(
                                text = "Calcular",
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                    }

                    if (categoria.isNotEmpty()) {

                        val corCategoria = when(categoria) {
                            "Peso ideal" -> colorResource(id = R.color.cor_app2)
                            "Levemente acima do peso" -> colorResource(id = R.color.cor_app3)

                            "Abaixo do peso",
                            "Obesidade grau I",
                            "Obesidade grau II",
                            "Obesidade grau III" -> Color.Red

                            else -> Color.Gray
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .height(80.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = corCategoria
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "%.1f".format(imc),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = categoria,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                }
            }
        }
//        -- Card Finalizado --
    }
}