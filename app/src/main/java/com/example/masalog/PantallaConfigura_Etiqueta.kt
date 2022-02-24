package com.example.masalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.ui.theme.*


val redondeoFlechas = 20.dp
val espaciado = 20.dp


@Composable
fun  PantallaConfiguraEtiqueta() {
    Scaffold(
        topBar = {barraTOP()},
        content = {
            Tabs()
        }
    )
}

@Composable
fun Tabs() {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Despacho", "Predespacho")
    Column {
        TabRow(
            selectedTabIndex = state,
            //unselectedContentColor = MaterialTheme.colors.onBackground,
            contentColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primaryVariant,

        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text= title,
                        style = MaterialTheme.typography.h6) },
                    selected = state == index,
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    unselectedContentColor  = Color.LightGray,
                    onClick = { state = index }
                )
            }
        }
        if(state==0){
            Despacho()
        }else{
            Predespacho()
        }
    }
}

@Composable
fun Despacho(){
    var horizontal : Int by remember { mutableStateOf(0)}
    var vertical : Int by remember { mutableStateOf(0)}
    var sentidoNormal: Boolean by remember { mutableStateOf(true)}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.size(espaciado))
        FlechaArriba(vertical,onClick = { vertical = it })
        Row(modifier= Modifier.padding(5.dp)) {
            FlechaIzquierda(horizontal, onClick = {horizontal = it})
            Spacer(modifier = Modifier.size(5.dp))
            FlechaDerecha(horizontal,onClick = { horizontal = it })
        }
        FlechaAbajo(vertical,onClick = { vertical = it })
        Spacer(modifier = Modifier.size(espaciado))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Column{
                Row(){
                    Text(text="Horizontal : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(horizontal.toString(), fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row(){
                    Text(text="Vertical : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(vertical.toString(), fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.size(espaciado))
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End) {
                Toggle(normal = sentidoNormal, onClick = {sentidoNormal = it})
            }
        }
        Spacer(modifier = Modifier.size(espaciado*3))
        BotonStandard(texto = "Despacho Pack / Gran Volumen",
                    onClick = { cargarFormatoDespacho(vertical,horizontal, sentidoNormal)
                                BTHandler.imprimir(etiquetaPack)}
        )
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Despacho IOMA",
                    onClick = { cargarFormatoDespacho(vertical,horizontal, sentidoNormal)
                                BTHandler.imprimir(etiquetaIOMA) }
        )

        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Despacho Refrigerados",
                    onClick = { cargarFormatoDespacho(vertical,horizontal, sentidoNormal)
                                BTHandler.imprimir(etiquetaRefrigerados) }
        )
    }

}

@Composable
fun Predespacho(){
    var horizontal : Int by remember { mutableStateOf(0)}
    var vertical : Int by remember { mutableStateOf(0)}
    var sentidoNormal: Boolean by remember { mutableStateOf(true)}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.size(espaciado))
        FlechaArriba(vertical,onClick = { vertical = it })
        Row(modifier= Modifier.padding(5.dp)) {
            FlechaIzquierda(horizontal, onClick = {horizontal = it})
            Spacer(modifier = Modifier.size(5.dp))
            FlechaDerecha(horizontal,onClick = { horizontal = it })
        }
        FlechaAbajo(vertical,onClick = { vertical = it })
        Spacer(modifier = Modifier.size(espaciado))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Column{
                Row(){
                    Text(text="Horizontal : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(horizontal.toString(), fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row(){
                    Text(text="Vertical : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(vertical.toString(), fontSize = 18.sp)
                }
            }
        }
        Spacer(modifier = Modifier.size(espaciado*3))
        BotonStandard(texto = "Predespacho Papel Continuo",
            onClick = { cargarFormatoPredespacho(vertical,horizontal, 225)
                BTHandler.imprimir(etiquetaPredespacho)}
        )
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Predespacho Etiqueta Autoadhesiva",
            onClick = { cargarFormatoPredespacho(vertical,horizontal, 0)
                BTHandler.imprimir(etiquetaPredespacho) }
        )

        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Predespacho Emergencia",
            onClick = { cargarFormatoPredespacho(vertical,horizontal, 825)
                BTHandler.imprimir(etiquetaPredespacho) }
        )
    }
}

@Composable
fun FlechaDerecha(variable: Int, onClick: (Int) -> Unit){
    Button(onClick = { onClick(variable+1) },
        shape = RoundedCornerShape(
            topEnd = redondeoFlechas,
            bottomEnd = redondeoFlechas
        ),
        colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier.height(40.dp),
        contentPadding = PaddingValues(1.dp)
        ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "",
            modifier = Modifier.size(50.dp))
    }
}

@Composable
fun FlechaIzquierda(variable: Int, onClick: (Int) -> Unit){
    Button(onClick = { onClick(variable -1) },
        shape = RoundedCornerShape(
            topStart = redondeoFlechas,
            bottomStart = redondeoFlechas,
        ),
        colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier.height(40.dp),
        contentPadding = PaddingValues(1.dp)
        ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "",
            modifier = Modifier.size(50.dp))
    }
}

@Composable
fun FlechaArriba(variable: Int, onClick: (Int) -> Unit){
    Button(onClick = { onClick(variable+1) },
        shape = RoundedCornerShape(
            topStart = redondeoFlechas,
            topEnd = redondeoFlechas
        ),
        colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier.height(35.dp),
        contentPadding = PaddingValues(1.dp)
        ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = "Arriba",
            modifier = Modifier.size(50.dp))
    }
}

@Composable
fun FlechaAbajo(variable: Int, onClick: (Int) -> Unit){
    Button(onClick = { onClick(variable-1) },
        shape = RoundedCornerShape(
            bottomEnd = redondeoFlechas,
            bottomStart = redondeoFlechas
        ),
        colors =ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier.height(35.dp),
        contentPadding = PaddingValues(1.dp)
        ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Abajo",
            modifier = Modifier.size(50.dp))
    }
}

@Composable
fun Toggle(normal:Boolean, onClick: (Boolean) -> Unit){
    if(normal){
        Column(){
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
                Text("Normal", color = Color.White, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(onClick = {onClick(!normal)},
                //colors =ButtonDefaults.buttonColors(backgroundColor = GrisClaro ),
                shape = RoundedCornerShape(
                    bottomEnd = redondeoFlechas,
                    bottomStart = redondeoFlechas
                ),
                border = BorderStroke(1.dp, Naranja),
                modifier = Modifier.width(110.dp).height(27.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                Text(text= "Invertida",color = Naranja)
            }
        }
    }else{
        OutlinedButton(onClick = {onClick(!normal)},
            //colors =ButtonDefaults.buttonColors(backgroundColor = GrisClaro ),
            shape = RoundedCornerShape(
                topStart = redondeoFlechas,
                topEnd = redondeoFlechas
            ),
            border = BorderStroke(1.dp, Naranja),
            modifier = Modifier.width(110.dp).height(27.dp),
            contentPadding = PaddingValues(1.dp)
        ){
            Text(text= "Normal", color = Naranja)
        }
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
            Text(text= "Invertida", color=Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

fun cargarFormatoDespacho(vertical: Int, horizontal: Int, sentidoNormal: Boolean){

    BTHandler.mandarLogo()
    lateinit var posicionesEnX:IntArray
    lateinit var posicionesEnY:IntArray
    lateinit var tamanioTexto:IntArray
    lateinit var orientacion:IntArray

    val multiplicador = 4

    var modificadorPosicionX:Int
    var modificadorPosicionY:Int

    if(sentidoNormal){
        modificadorPosicionX = - vertical * multiplicador
        modificadorPosicionY = - horizontal * multiplicador
        orientacion = intArrayOf(4,1)
    }else{
        modificadorPosicionX = + vertical * multiplicador
        modificadorPosicionY = + vertical * multiplicador
        orientacion = intArrayOf(2,3)
    }

    if (BTHandler.btDevice?.name?.take(3).equals("RP4")) {
        tamanioTexto = intArrayOf(18)
        if (sentidoNormal) {
            posicionesEnX = intArrayOf(30, 30, 10, 45, 80, 80, 15, 65, 125, 165, 230, 270, 328, 328, 328, 328, 345, 422, 460)
            posicionesEnY = intArrayOf(670, 638, 610, 610, 610, 500, 230, 203, 610, 610, 490, 760, 730, 540, 340, 115, 765, 750, 750)
        }else{
            posicionesEnX = intArrayOf(460,460,480,445,410,410,475,425,365,325,260,220,162,162,162,162,145,68,30)
            posicionesEnY = intArrayOf(130,162,185,185,185,290,600,627,185,185,310,40,60,250,450,685,35,50,50)
        }

    } else {
        tamanioTexto = intArrayOf(17)
        if (sentidoNormal) {
            posicionesEnX = intArrayOf(30, 30, 10, 40, 70, 70, 15, 65, 120, 160, 230, 270, 330, 330, 330, 330, 345, 422, 460)
            posicionesEnY = intArrayOf(670, 638, 610, 610, 610, 500, 215, 188, 610, 610, 490, 760, 730, 540, 340, 110, 760, 750, 750)
        } else {
            posicionesEnX = intArrayOf(460, 460, 480, 450, 420, 420, 475, 425, 370, 330, 260, 220, 160, 160, 160, 160, 145, 68, 30)
            posicionesEnY = intArrayOf(130, 162, 185, 185, 185, 290, 585, 612, 185, 185, 310, 40, 70, 260, 470, 680, 40, 50, 50)
        }
    }

    for (posicion in posicionesEnX.indices) {
        posicionesEnX[posicion] = posicionesEnX[posicion] + modificadorPosicionX
    }
    for (posicion in posicionesEnY.indices) {
        posicionesEnY[posicion] = posicionesEnY[posicion] + modificadorPosicionY
    }

    BTHandler.imprimir("<STX><ESC>C<ETX>\n" +
            "<STX><ESC>P<ETX>\n" +
            "<STX>E1;F1;<ETX>\n" +
            "<STX>B11;f" + orientacion[0] + ";o" + posicionesEnX[0] + "," + posicionesEnY[0] + ";c2,0;w2;h90;i1;d0,10;<ETX>\n" +
            "<STX>I11;f" + orientacion[0] + ";o" + posicionesEnX[1] + "," + posicionesEnY[1] + ";c20;h1;w1;b0;<ETX>\n" +
            "<STX>H6;f" + orientacion[1] + ";o" + posicionesEnX[2] + "," + posicionesEnY[2] + ";c26;b0;k11;d0,18;<ETX>\n" +
            "<STX>H8;f" + orientacion[1] + ";o" + posicionesEnX[3] + "," + posicionesEnY[3] + ";c26;k12;d0,10;<ETX>\n" +
            "<STX>H5;f" + orientacion[1] + ";o" + posicionesEnX[4] + "," + posicionesEnY[4] + ";c26;b0;k11;d3,Ped:<ETX>\n" +
            "<STX>H10;f" + orientacion[1] + ";o" + posicionesEnX[5] + "," + posicionesEnY[5] + ";c26;b0;k11;d0,8;<ETX>\n" +
            "<STX>U31;f" + orientacion[1] + ";o" + posicionesEnX[6] + "," + posicionesEnY[6] + ";c2;w1;h1;<ETX>\n" +
            "<STX>H26;f" + orientacion[1] + ";o" + posicionesEnX[7] + "," + posicionesEnY[7] + ";c26;b0;k6;d3,Monroe Americana<ETX>\n" +
            "<STX>H15;f" + orientacion[1] + ";o" + posicionesEnX[8] + "," + posicionesEnY[8] + ";c26;b0;k14;d0,26;<ETX>\n" +
            "<STX>H16;f" + orientacion[1] + ";o" + posicionesEnX[9] + "," + posicionesEnY[9] + ";c26;b0;k14;d0,26;<ETX>\n" +
            "<STX>H25;f" + orientacion[1] + ";o" + posicionesEnX[10] + "," + posicionesEnY[10] + ";c26;b0;k12;d0,15;<ETX>\n" +
            "<STX>H17;f" + orientacion[1] + ";o" + posicionesEnX[11] + "," + posicionesEnY[11] + ";c26;b0;k10;d0,50;<ETX>\n" +
            "<STX>H30;f" + orientacion[1] + ";o" + posicionesEnX[12] + "," + posicionesEnY[12] + ";c26;b0;k8;d3,Bulto<ETX>\n" +
            "<STX>H32;f" + orientacion[1] + ";o" + posicionesEnX[13] + "," + posicionesEnY[13] + ";c26;b0;k8;d3,Turno<ETX>\n" +
            "<STX>H34;f" + orientacion[1] + ";o" + posicionesEnX[14] + "," + posicionesEnY[14] + ";c26;b0;k8;d3,Radio<ETX>\n" +
            "<STX>H36;f" + orientacion[1] + ";o" + posicionesEnX[15] + "," + posicionesEnY[15] + ";c26;b0;k8;d3,Orden<ETX>\n" +
            "<STX>H21;f" + orientacion[1] + ";o" + posicionesEnX[16] + "," + posicionesEnY[16] + ";c26;b0;k" + tamanioTexto[0] + ";d0,28;<ETX>\n" +
            "<STX>H13;f" + orientacion[1] + ";o" + posicionesEnX[17] + "," + posicionesEnY[17] + ";c26;b0;k14;d0,30;<ETX>\n" +
            "<STX>H12;f" + orientacion[1] + ";o" + posicionesEnX[18] + "," + posicionesEnY[18] + ";c26;b0;k10;d0,10;<ETX>\n" +
            "<STX>R<ETX>")

}

fun cargarFormatoPredespacho(vertical: Int, horizontal: Int,margen:Int){

    var modificadorMargen =""

    BTHandler.mandarLogo()
    val multiplicador = 4
    

    if (margen>0){
        modificadorMargen = "<STX>H38;f3;o" + (475+margen + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k6;d3,.<ETX>\n"
    }

    BTHandler.imprimir("<STX><ESC>C<ETX>\n" +
            "<STX><ESC>P<ETX>\n" +
            "<STX>E11;F11;<ETX>\n" +
            modificadorMargen+
            "<STX>U31;f3;o" + (475 + vertical * multiplicador) + "," + (35 + horizontal * multiplicador) + ";c2;w1;h1;<ETX>\n" +
            "<STX>H26;f3;o" + (425 + vertical * multiplicador) + "," + (62 + horizontal * multiplicador) + ";c26;b0;k6;d3,Monroe Americana<ETX>\n" +
            "<STX>H4;f3;o" + (465 + vertical * multiplicador) + "," + (250 + horizontal * multiplicador) + ";c26;b0;k15;d3,Ped:<ETX>\n" +
            "<STX>H10;f3;o" + (465 + vertical * multiplicador) + "," + (345 + horizontal * multiplicador) + ";c26;b0;k15;d0,10;<ETX>\n" +
            "<STX>H8;f3;o" + (465 + vertical * multiplicador) + "," + (570 + horizontal * multiplicador) + ";c26;b0;k12;d0,10;<ETX>n" +
            "<STX>H17;f3;o" + (370 + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k10;d0,50;<ETX>n" +
            "<STX>H27;f3;o" + (290 + vertical * multiplicador) + "," + (55 + horizontal * multiplicador) + ";c26;b0;k7;d3,Entrega<ETX>n" +
            "<STX>H28;f3;o" + (290 + vertical * multiplicador) + "," + (235 + horizontal * multiplicador) + ";c26;b0;k7;d3,Radio<ETX>n" +
            "<STX>H29;f3;o" + (290 + vertical * multiplicador) + "," + (440 + horizontal * multiplicador) + ";c26;b0;k7;d3,Salida<ETX>n" +
            "<STX>H30;f3;o" + (290 + vertical * multiplicador) + "," + (680 + horizontal * multiplicador) + ";c26;b0;k7;d3,Orden<ETX>n" +
            "<STX>H21;f3;o" + (285 + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k29;d0,30;<ETX>n" +
            "<STX>H13;f3;o" + (160 + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k12;d0,26;<ETX>n" +
            "<STX>H15;f3;o" + (120 + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k9;d0,26;<ETX>n" +
            "<STX>H16;f3;o" + (90 + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k9;d0,26;<ETX>n" +
            "<STX>H12;f3;o" + (100 + vertical * multiplicador) + "," + (420 + horizontal * multiplicador) + ";c26;b0;k12;d0,10;<ETX>n" +
            "<STX>H6;f3;o" + (55 + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k18;d0,16;<ETX>n" +
            "<STX>B11;f3;o" + (170 + vertical * multiplicador) + "," + (620 + horizontal * multiplicador) + ";c17,200,0;w11;h11;i1;d0,10;<ETX>n" +
            "<STX>I11;f3;o" + (30 + vertical * multiplicador) + "," + (620 + horizontal * multiplicador) + ";c20;h1;w1;b0<ETX>n" +
            "<STX>H25;f3;o" + (40 + vertical * multiplicador) + "," + (640 + horizontal * multiplicador) + ";c26;b0;k7;d0,30;<ETX>n" +
            "<STX>R<ETX>")
}

fun cambiarPapel(){
    BTHandler.imprimir("A"+
            "IG2" +
            "PM0" +
            "Z")
    //hayRolloPapel = true
}

fun cambiarEtiqueta(){
    BTHandler.imprimir("A"+
            "IG1" +
            "PM1" +
            "Z")
    //hayRolloEtiqueta = true
}

val etiquetaPack = "<STX>R<ETX> \n" +
        "<STX><ESC>E1<CAN><ETX>\n" +
        "<STX><ESC>F6<DEL>Bulto 351874129<ETX>\n" +
        "<STX><ESC>F8<DEL>11/02/2021<ETX>\n" +
        "<STX><ESC>F10<DEL>1<ETX>\n" +
        "<STX><ESC>F11<DEL>09663168<ETX>\n" +
        "<STX><ESC>F12<DEL>( 5113101)<ETX>\n" +
        "<STX><ESC>F13<DEL>FCIA. NOBEL ESPERANZA<ETX>\n" +
        "<STX><ESC>F15<DEL>TAFIROL 500MG                <ETX>\n" +
        "<STX><ESC>F16<DEL>EXHIB. X10X40 BLISTERS<ETX>\n" +
        "<STX><ESC>F17<DEL>2   (Desc  1) [ #E1C16]<ETX>\n" +
        "<STX><ESC>F21<DEL>0001   11/02  A1 14:10   025<ETX>\n" +
        "<STX><ESC>F25<DEL>Fal. 0/ 2<ETX>\n" +
        "<STX><RS>1<ETB><FF><ETX>"

val etiquetaRefrigerados = "<STX>R<ETX> \n" +
        "<STX><ESC>E1<CAN><ETX>\n" +
        "<STX><ESC>F6<DEL>Etiq. de DESPACHO<ETX>\n" +
        "<STX><ESC>F8<DEL>17/03/2021<ETX>\n" +
        "<STX><ESC>F10<DEL>1169<ETX>\n" +
        "<STX><ESC>F11<DEL>0351014150<ETX>\n" +
        "<STX><ESC>F12<DEL>( 1472101)<ETX>\n" +
        "<STX><ESC>F13<DEL>DESCALZI <ETX>\n" +
        "<STX><ESC>F15<DEL>Estrada 2500              <ETX>\n" +
        "<STX><ESC>F16<DEL>Villa Maipu<ETX>\n" +
        "<STX><ESC>F17<DEL>***PRODUCTOS A REFRIGERAR ENTRE 2 y 8 GRADOS<ETX>\n" +
        "<STX><ESC>F21<DEL>0001   17/03  U7 23:40   007<ETX>\n" +
        "<STX><RS>1<ETB><FF><ETX>"

val etiquetaIOMA = "<STX>R<ETX> \n" +
        "<STX><ESC>E1<CAN><ETX>\n" +
        "<STX><ESC>F6<DEL>Etiq. de DESPACHO<ETX>\n" +
        "<STX><ESC>F8<DEL>18/03/2021<ETX>\n" +
        "<STX><ESC>F10<DEL>1871<ETX>\n" +
        "<STX><ESC>F11<DEL>0351020723<ETX>\n" +
        "<STX><ESC>F12<DEL>( INIO)<ETX>\n" +
        "<STX><ESC>F13<DEL>AMARTINO<ETX>\n" +
        "<STX><ESC>F15<DEL>O ROARKE 2408<ETX>\n" +
        "<STX><ESC>F16<DEL>BARADERO<ETX>\n" +
        "<STX><ESC>F17<DEL>*                             <ETX>\n" +
        "<STX><ESC>F21<DEL>0001   18/03  V2 23:58   002<ETX>\n" +
        "<STX><ESC>F25<DEL>BUSCEMI FRAN<ETX>\n" +
        "<STX><RS>1<ETB><FF><ETX>"

val etiquetaPredespacho = "<STX>R<ETX>\n" +
        "<STX><ESC>E11<CAN><ETX>\n" +
        "<STX><ESC>F6<DEL>DESP.COPIA 734-D<ETX>\n" +
        "<STX><ESC>F8<DEL>04/11/2020<ETX>\n" +
        "<STX><ESC>F10<DEL>125@#<ETX>\n" +
        "<STX><ESC>F11<DEL>0408017097<ETX>\n" +
        "<STX><ESC>F12<DEL>(2676901)<ETX>\n" +
        "<STX><ESC>F17<DEL>Bul: 001 Fmbx.=200692  Doc.=FC 1116-05939381<ETX>\n" +
        "<STX><ESC>F15<DEL>Av. Mitre 2471<ETX>\n" +
        "<STX><ESC>F16<DEL>Avellaneda<ETX>\n" +
        "<STX><ESC>F13<DEL>FCIA ANTIGUA POZUELO SCS <ETX>\n" +
        "<STX><ESC>F21<DEL>01  A5 13:20  22<ETX>\n" +
        "<STX><RS>1<ETB><FF><ETX>"