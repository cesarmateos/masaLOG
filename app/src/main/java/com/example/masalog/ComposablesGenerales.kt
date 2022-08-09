package com.example.masalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.configuraEtiqueta.*

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
fun BotonColor(texto: String, onClick: () -> Unit, colors: ButtonColors){
    Button(onClick =  onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = colors){
        Text(text= texto,fontSize = 18.sp)
    }
}

@Composable
fun Flechita(intLimitado: IntLimitado, intMostrable: Int, rotacion: Float, onClick: (Int) -> Unit, operacion: (IntLimitado)-> Unit){
    Button(onClick = { operacion(intLimitado)
                     onClick(intLimitado.valor)
                     },
        shape = RoundedCornerShape(50),
        colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier
            .height(23.dp)
            .width(35.dp),
        contentPadding = PaddingValues(1.dp)
    ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Modificar",
            modifier = Modifier
                .size(20.dp)
                .rotate(rotacion))
    }
}

@Composable
fun BotonFlechaHorizontal(variable: Int, rotacion: Float, onClick: (Int) -> Unit, operacion: (Int) -> Int){
    Button(onClick = { onClick(operacion(variable)) },
        shape = RoundedCornerShape(
            topStart = redondeoFlechas,
            bottomStart = redondeoFlechas,
        ),
        colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier
            .height(40.dp)
            .rotate(rotacion),
        contentPadding = PaddingValues(1.dp)
    ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "Modificar",
            modifier = Modifier.size(50.dp))
    }
}

@Composable
fun BotonFlechaVertical(variable: Int, rotacion: Float, onClick: (Int) -> Unit, operacion: (Int) -> Int) {
    Button(
        onClick = { onClick(operacion(variable)) },
        shape = RoundedCornerShape(
            bottomEnd = redondeoFlechas,
            bottomStart = redondeoFlechas
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier
            .height(35.dp)
            .rotate(rotacion),
        contentPadding = PaddingValues(1.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Modificar",
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun BotoneraDireccion(ejeX : Int, ejeY: Int, onClickX: (Int) -> Unit, onClickY: (Int) -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        BotonFlechaVertical(ejeY,180f,onClick = onClickY, operacion = {valor:Int -> valor + 1})
        Row(modifier= Modifier.padding(5.dp)) {
            BotonFlechaHorizontal(ejeX, 0f,onClick = onClickX, operacion = {valor:Int -> valor - 1})
            Spacer(modifier = Modifier.size(5.dp))
            BotonFlechaHorizontal(ejeX, 180f,onClick = onClickX, operacion = {valor:Int -> valor + 1})
        }
        BotonFlechaVertical(ejeY,0f,onClick = onClickY, operacion = {valor:Int -> valor - 1})
    }
}

@Composable
fun BotoneraDireccionConIndicadores(ejeX : Int, ejeY: Int, onClickX: (Int) -> Unit, onClickY: (Int) -> Unit){
    Column {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text="Horizontal : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
            Text(ejeX.toString(), fontSize = 18.sp)
        }
        Row{
            Text(text="Vertical : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
            Text(ejeY.toString(), fontSize = 18.sp)
        }
        BotoneraDireccion(ejeX = ejeX, ejeY = ejeY, onClickX = onClickX, onClickY = onClickY )
    }
}

@Composable
fun ToggleHorizontal(estadoA:Boolean, onClick: (Boolean) -> Unit, textoA: String, textoB:String){
    if(estadoA){
        //Clickeado
        Row{
            //Clickeado
            OutlinedButton(onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary ),
                shape = RoundedCornerShape(
                    topStart = redondeoFlechas,
                    bottomStart = redondeoFlechas
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                modifier = Modifier
                    .width(90.dp)
                    .height(23.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(textoA, color = MaterialTheme.colors.onPrimary, fontWeight = FontWeight.Bold)
            }
            //No Clickeado
            OutlinedButton(onClick = {onClick(!estadoA)},
                colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background ),
                shape = RoundedCornerShape(
                    topEnd = redondeoFlechas,
                    bottomEnd = redondeoFlechas
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                modifier = Modifier
                    .width(90.dp)
                    .height(23.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(text= textoB,color = MaterialTheme.colors.secondaryVariant)
            }
        }
    }else{
        //No Clickeado
        OutlinedButton(onClick = {onClick(!estadoA)},
            colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background ),
            shape = RoundedCornerShape(
                topStart = redondeoFlechas,
                bottomStart = redondeoFlechas
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
            modifier = Modifier
                .width(90.dp)
                .height(23.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoA, color = MaterialTheme.colors.secondaryVariant)
        }
        //Clickeado
        OutlinedButton(onClick = {},
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary ),
            shape = RoundedCornerShape(
                bottomEnd = redondeoFlechas,
                topEnd = redondeoFlechas
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            modifier = Modifier
                .width(90.dp)
                .height(23.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoB, color= MaterialTheme.colors.onPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ToggleVertical(normal:Boolean, onClick: (Boolean) -> Unit, textoA: String, textoB:String){
    if(normal){
        Column{
            //Clickeado
            OutlinedButton(onClick = {},
                colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary ),
                shape = RoundedCornerShape(
                    topStart = redondeoFlechas,
                    topEnd = redondeoFlechas
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                modifier = Modifier
                    .width(110.dp)
                    .height(27.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(textoA, color = MaterialTheme.colors.onPrimary , fontWeight = FontWeight.Bold)
            }
            //No Clickeado
            OutlinedButton(onClick = {onClick(!normal)},
                colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background ),
                shape = RoundedCornerShape(
                    bottomEnd = redondeoFlechas,
                    bottomStart = redondeoFlechas
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                modifier = Modifier
                    .width(110.dp)
                    .height(27.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(text= textoB,color = MaterialTheme.colors.secondaryVariant)
            }
        }
    }else{
        //No Clickeado
        OutlinedButton(onClick = {onClick(!normal)},
            colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background ),
            shape = RoundedCornerShape(
                topStart = redondeoFlechas,
                topEnd = redondeoFlechas
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
            modifier = Modifier
                .width(110.dp)
                .height(27.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoA, color = MaterialTheme.colors.secondaryVariant)
        }
        //Clickeado
        OutlinedButton(onClick = {},
            colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary ),
            shape = RoundedCornerShape(
                bottomEnd = redondeoFlechas,
                bottomStart = redondeoFlechas
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            modifier = Modifier
                .width(110.dp)
                .height(27.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= textoB, color=MaterialTheme.colors.onPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TituloSeccion(texto:String){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier
            .background(MaterialTheme.colors.surface)
            .padding(10.dp)
            .fillMaxWidth()
    ){
        Text(text= texto,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun EstructuraTituloCuerpo(textoTitulo: String,
                           cuerpo: @Composable () -> Unit){
    Column{
        TituloSeccion(texto = textoTitulo)
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(PADDING_HORIZONTAL)){
            cuerpo()
        }
    }
}

@Composable
fun EsctructuraTituloCuerpoBoton(textoTitulo: String,
                                 textoBoton:String,
                                 onClick: () -> Unit,
                                 cuerpo: @Composable () -> Unit){
    EstructuraTituloCuerpo(textoTitulo){
        Column{
            Row(modifier= Modifier
                .fillMaxWidth()
                .weight(1.0f)){
                cuerpo()
            }
            Row(modifier= Modifier
                .fillMaxWidth()) {
                BotonStandard(texto = textoBoton, onClick)
            }
        }
    }
}


@Composable
fun CajaTextoGris(text:String,modifier: Modifier){
    Text(text= text,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputTexto(onClick: (texto: String) -> Unit,keyboardType: KeyboardType, largo: Dp){

    var ingresoBarras by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(10))
            .background(color = MaterialTheme.colors.background)
            .size(largo, 30.dp)
            .padding(horizontal = 5.dp)
            .focusTarget(),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(value = ingresoBarras,
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground),
            onValueChange = { ingresoBarras = it },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            keyboardActions = KeyboardActions(onDone = {
                if(ingresoBarras != ""){
                    if(ingresoBarras.toInt() != 0 ) {
                        onClick(ingresoBarras)
                    }
                }
                ingresoBarras = ""
            }),
            enabled = true,
            singleLine = true,
            modifier = Modifier
                .onKeyEvent {
                    if (it.key.keyCode == Key.Enter.keyCode) {
                        if (ingresoBarras != "\n" && ingresoBarras != "0\n") {
                            onClick(ingresoBarras.dropLast(1))
                        }
                        ingresoBarras = ""
                    }
                    false
                }
                .focusRequester(focusRequester)
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

class IntLimitado(ValorIncial:Int,LimiteInferior:Int, LimiteSuperior: Int){
    var valor:Int = ValorIncial
    private val limiteSuperior = LimiteSuperior
    private val limiteInferior = LimiteInferior

    fun sumar(unidades:Int){
        this.valor = min(this.valor+unidades,limiteSuperior)
    }

    fun restar(unidades:Int){
        this.valor = max(this.valor-unidades,limiteInferior)
    }
}

