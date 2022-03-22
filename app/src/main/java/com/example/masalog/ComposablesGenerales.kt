package com.example.masalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.ui.theme.GrisClaro
import com.example.masalog.ui.theme.Naranja

import kotlin.math.min
import kotlin.math.max


val PADDING_HORIZONTAL = 20.dp

@Composable
fun BotonStandard(texto: String, onClick: () -> Unit){
    Button(onClick =  onClick,
        modifier = Modifier.fillMaxWidth()){
        Text(text= texto,fontSize = 18.sp)
    }
}

@Composable
fun flechita(intLimitado: IntLimitado, intMostrable: Int, rotacion: Float, onClick: (Int) -> Unit, operacion: (IntLimitado)-> Unit){
    Button(onClick = { operacion(intLimitado)
                     onClick(intLimitado.valor)
                     },
        shape = RoundedCornerShape(50),
        colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier.height(23.dp).width(35.dp),
        contentPadding = PaddingValues(1.dp)
    ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Abajo",
            modifier = Modifier.size(20.dp).rotate(rotacion))
    }
}

@Composable
fun ToggleHorizontal(estadoA:Boolean, onClick: (Boolean) -> Unit, textoA: String, textoB:String){
    if(estadoA){
        //Clickeado
        Row(){
            //Clickeado
            OutlinedButton(onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja ),
                shape = RoundedCornerShape(
                    topStart = redondeoFlechas,
                    bottomStart = redondeoFlechas
                ),
                border = BorderStroke(1.dp, Naranja),
                modifier = Modifier.width(90.dp).height(23.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(textoA, color = Color.White, fontWeight = FontWeight.Bold)
            }
            //No Clickeado
            OutlinedButton(onClick = {onClick(!estadoA)},
                shape = RoundedCornerShape(
                    topEnd = redondeoFlechas,
                    bottomEnd = redondeoFlechas
                ),
                border = BorderStroke(1.dp, GrisClaro),
                modifier = Modifier.width(90.dp).height(23.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(text= textoB,color = GrisClaro)
            }
        }
    }else{
        //No Clickeado
        OutlinedButton(onClick = {onClick(!estadoA)},
            shape = RoundedCornerShape(
                topStart = redondeoFlechas,
                bottomStart = redondeoFlechas
            ),
            border = BorderStroke(1.dp, GrisClaro),
            modifier = Modifier.width(90.dp).height(23.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoA, color = GrisClaro)
        }
        //Clickeado
        OutlinedButton(onClick = {},
            colors = ButtonDefaults.buttonColors(backgroundColor = Naranja ),
            shape = RoundedCornerShape(
                bottomEnd = redondeoFlechas,
                topEnd = redondeoFlechas
            ),
            border = BorderStroke(1.dp, Naranja),
            modifier = Modifier.width(90.dp).height(23.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoB, color= Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ToggleVertical(normal:Boolean, onClick: (Boolean) -> Unit, textoA: String, textoB:String){
    if(normal){
        Column(){
            //Clickeado
            OutlinedButton(onClick = {},
                colors =ButtonDefaults.buttonColors(backgroundColor = Naranja ),
                shape = RoundedCornerShape(
                    topStart = redondeoFlechas,
                    topEnd = redondeoFlechas
                ),
                border = BorderStroke(1.dp, Naranja),
                modifier = Modifier.width(110.dp).height(27.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(textoA, color = Color.White, fontWeight = FontWeight.Bold)
            }
            //No Clickeado
            OutlinedButton(onClick = {onClick(!normal)},
                shape = RoundedCornerShape(
                    bottomEnd = redondeoFlechas,
                    bottomStart = redondeoFlechas
                ),
                border = BorderStroke(1.dp, GrisClaro),
                modifier = Modifier.width(110.dp).height(27.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(text= textoB,color = GrisClaro)
            }
        }
    }else{
        //No Clickeado
        OutlinedButton(onClick = {onClick(!normal)},
            shape = RoundedCornerShape(
                topStart = redondeoFlechas,
                topEnd = redondeoFlechas
            ),
            border = BorderStroke(1.dp, GrisClaro),
            modifier = Modifier.width(110.dp).height(27.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoA, color = GrisClaro)
        }
        //Clickeado
        OutlinedButton(onClick = {},
            colors =ButtonDefaults.buttonColors(backgroundColor = Naranja ),
            shape = RoundedCornerShape(
                bottomEnd = redondeoFlechas,
                bottomStart = redondeoFlechas
            ),
            border = BorderStroke(1.dp, Naranja),
            modifier = Modifier.width(110.dp).height(27.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoB, color=Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

class IntLimitado(ValorIncial:Int,LimiteInferior:Int, LimiteSuperior: Int){
    var valor:Int = ValorIncial
    val limiteSuperior = LimiteSuperior
    val limiteInferior = LimiteInferior

    fun sumar(){
        this.valor = min(this.valor+1,limiteSuperior)
    }

    fun nuevoValor(nuevoValor:Int){
        this.valor = nuevoValor.coerceIn(limiteInferior, limiteSuperior)
    }

    fun restar(){
        this.valor = max(this.valor-1,limiteInferior)
    }
}