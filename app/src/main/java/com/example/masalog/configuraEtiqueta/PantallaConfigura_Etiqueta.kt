package com.example.masalog.configuraEtiqueta

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
import com.example.masalog.BTHandler
import com.example.masalog.BotonStandard
import com.example.masalog.PADDING_HORIZONTAL
import com.example.masalog.ToggleVertical


val redondeoFlechas = 20.dp
val espaciado = 20.dp


@Composable
fun  PantallaConfiguraEtiqueta() {
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
    var barras: Boolean by remember {mutableStateOf(true)}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(PADDING_HORIZONTAL),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row(){
            Column{
                Row(modifier = Modifier.fillMaxWidth()){
                    Text(text="Horizontal : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(horizontal.toString(), fontSize = 18.sp)
                }
                Row(){
                    Text(text="Vertical : ", fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(vertical.toString(), fontSize = 18.sp)
                }
            }
        }
        FlechaArriba(vertical,onClick = { vertical = it })
        Row(modifier= Modifier.padding(5.dp)) {
            FlechaIzquierda(horizontal, onClick = {horizontal = it})
            Spacer(modifier = Modifier.size(5.dp))
            FlechaDerecha(horizontal,onClick = { horizontal = it })
        }
        FlechaAbajo(vertical,onClick = { vertical = it })
        Spacer(modifier = Modifier.size(espaciado /2))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Column(horizontalAlignment = Alignment.Start) {
                ToggleVertical(normal = barras, onClick = {barras = it}, textoA= "C.Barras", textoB="QR")
            }
            Spacer(modifier = Modifier.size(espaciado))
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End) {
                ToggleVertical(normal = sentidoNormal, onClick = {sentidoNormal = it}, textoA= "Normal", textoB="Invertida")
            }
        }
        //BOTONES
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Ejemplo Pack / G.Volumen",
                    onClick = { cargarFormatoDespacho(vertical,horizontal, sentidoNormal,barras)
                                BTHandler.imprimir(etiquetaPack)
                    }
        )
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Ejemplo IOMA",
                    onClick = { cargarFormatoDespacho(vertical,horizontal, sentidoNormal,barras)
                                BTHandler.imprimir(etiquetaIOMA) }
        )

        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Ejemplo Refrigerados",
                    onClick = { cargarFormatoDespacho(vertical,horizontal, sentidoNormal,barras)
                                BTHandler.imprimir(etiquetaRefrigerados) }
        )
    }

}

@Composable
fun Predespacho(){
    var horizontal : Int by remember { mutableStateOf(0)}
    var vertical : Int by remember { mutableStateOf(0)}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(PADDING_HORIZONTAL),
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
        Spacer(modifier = Modifier.size(espaciado *2))
        BotonStandard(texto = "Formulario Papel Continuo",
            onClick = { cargarFormatoPredespacho(vertical,horizontal, 225)
                BTHandler.imprimir(etiquetaPredespacho)
            }
        )
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Formulario Etiqueta",
            onClick = { cargarFormatoPredespacho(vertical,horizontal, 0)
                BTHandler.imprimir(etiquetaPredespacho) }
        )

        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Formulario Emergencia",
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


fun cargarFormatoDespacho(vertical: Int, horizontal: Int, sentidoNormal: Boolean, barras: Boolean){

    BTHandler.mandarLogo()
    lateinit var posicionesEnX:IntArray
    lateinit var posicionesEnY:IntArray
    lateinit var orientacion:IntArray

    var tamanioTexto = 17
    val multiplicador = 4

    var matrizOpcionesX = Array(2){Array(2){IntArray(19)} }
    var matrizOpcionesY = Array(2){Array(2){IntArray(19)} }


    //NORMAL - BARRAS
    matrizOpcionesX[0][0] = intArrayOf(460,460,480,450,420,420,475,425,370,330,260,220,160,160,160,160,145,68,30)
    matrizOpcionesY[0][0] = intArrayOf(130, 162, 185, 185, 185, 290, 585, 612, 185, 185, 310, 40, 70, 260, 470, 680, 40, 50, 50)

    //NORMAL - QR
    matrizOpcionesX[0][1] = intArrayOf(470,450,480,450,420,420,475,425,370,330,260,220,160,160,160,160,145,68,30)
    matrizOpcionesY[0][1] = intArrayOf( 40,  40, 190, 190, 190, 290, 590, 617, 190, 190, 310, 40, 70, 260, 470, 680, 40, 50, 50)

    //INVERTIDA - BARRAS
    matrizOpcionesX[1][0] = intArrayOf(30, 30, 10, 40, 70, 70, 15, 65, 120, 160, 230, 270, 330, 330, 330, 330, 345, 412, 450)
    matrizOpcionesY[1][0] = intArrayOf(670, 638, 610, 610, 610, 500, 215, 188, 610, 610, 490, 760, 730, 540, 340, 110, 760, 750, 750)

    //INVERTIDA - QR
    matrizOpcionesX[1][1] = intArrayOf(20, 80, 10, 40, 70, 70, 15, 65, 120, 160, 230, 270, 330, 330, 330, 330, 345, 412, 450)
    matrizOpcionesY[1][1] = intArrayOf(750, 750, 605, 605, 605, 500, 210, 183, 605, 605, 490, 760, 730, 540, 340, 110, 760, 750, 750)


    var modificadorPosicionX:Int
    var modificadorPosicionY:Int

    var matrizDimension0 = 0
    var matrizDimension1 = 0

    if(sentidoNormal){
        modificadorPosicionX = + vertical * multiplicador
        modificadorPosicionY = + horizontal * multiplicador
        orientacion = intArrayOf(2,3)
    }else{
        matrizDimension0 = 1
        modificadorPosicionX = - vertical * multiplicador
        modificadorPosicionY = - horizontal * multiplicador
        orientacion = intArrayOf(4,1)
    }


    if (BTHandler.btDevice?.name?.take(3).equals("RP4")) {
        tamanioTexto = 18
    }

    if (!barras){
        matrizDimension1 = 1
    }

    posicionesEnX = matrizOpcionesX[matrizDimension0][matrizDimension1]
    posicionesEnY = matrizOpcionesY[matrizDimension0][matrizDimension1]

    for (posicion in posicionesEnX.indices) {
        posicionesEnX[posicion] = posicionesEnX[posicion] + modificadorPosicionX
    }
    for (posicion in posicionesEnY.indices) {
        posicionesEnY[posicion] = posicionesEnY[posicion] + modificadorPosicionY
    }

    var codigoBarras = "<STX>B11;f" + orientacion[0] + ";o" + posicionesEnX[0] + "," + posicionesEnY[0] + ";c2,0;w2;h90;i1;d0,10;<ETX>\n" +
            "<STX>I11;f" + orientacion[0] + ";o" + posicionesEnX[1] + "," + posicionesEnY[1] + ";c20;h1;w1;b0;<ETX>\n"

    if (!barras){
        codigoBarras = "<STX>B11;f" + orientacion[1] + ";o" + posicionesEnX[0] + "," + posicionesEnY[0] + ";c17,0;w10;h10;i1;d0,10;<ETX>\n" +
        "<STX>I11;f" + orientacion[1] + ";o" + posicionesEnX[1] + "," + posicionesEnY[1] + ";c20;h1;w1;b0;<ETX>\n"
    }

    BTHandler.imprimir(
        "<STX><ESC>C<ETX>\n" +
                "<STX><ESC>P<ETX>\n" +
                "<STX>E1;F1;<ETX>\n" +
                codigoBarras +
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
                "<STX>H21;f" + orientacion[1] + ";o" + posicionesEnX[16] + "," + posicionesEnY[16] + ";c26;b0;k" + tamanioTexto + ";d0,28;<ETX>\n" +
                "<STX>H13;f" + orientacion[1] + ";o" + posicionesEnX[17] + "," + posicionesEnY[17] + ";c26;b0;k14;d0,30;<ETX>\n" +
                "<STX>H12;f" + orientacion[1] + ";o" + posicionesEnX[18] + "," + posicionesEnY[18] + ";c26;b0;k10;d0,10;<ETX>\n" +
                "<STX>R<ETX>"
    )

}

fun cargarFormatoPredespacho(vertical: Int, horizontal: Int,margen:Int){

    var modificadorMargen =""

    BTHandler.mandarLogo()
    val multiplicador = 4
    

    if (margen>0){
        modificadorMargen = "<STX>H38;f3;o" + (475+margen + vertical * multiplicador) + "," + (40 + horizontal * multiplicador) + ";c26;b0;k6;d3,.<ETX>\n"
    }

    BTHandler.imprimir(
        "<STX><ESC>C<ETX>\n" +
                "<STX><ESC>P<ETX>\n" +
                "<STX>E11;F11;<ETX>\n" +
                modificadorMargen +
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
                "<STX>R<ETX>"
    )
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