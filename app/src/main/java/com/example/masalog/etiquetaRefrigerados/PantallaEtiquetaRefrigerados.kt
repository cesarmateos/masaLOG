package com.example.masalog.etiquetaRefrigerados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.*
import com.example.masalog.R

@Composable
fun PantallaEtiquetaHeladeras() {
    var ejeX : Int by remember { mutableStateOf(0) }
    var ejeY : Int by remember { mutableStateOf(0) }
    val cantidadLimitada : IntLimitado by remember { mutableStateOf(IntLimitado(1,1,200)) }
    var cantidad: Int by remember { mutableStateOf(cantidadLimitada.valor) }

    EsctructuraTituloCuerpoBoton(textoTitulo= stringResource(R.string.etiqueta_heladeras),"Imprimir Etiquetas",{imprimirEtiquetasHeladera(ejeX,ejeY,cantidad)}){
        Column{
            BotoneraDireccionConIndicadores(ejeX,ejeY,{ejeX= it}, {ejeY= it})
            Spacer(Modifier.size(10.dp))
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Cantidad:",style = TextStyle(fontWeight = FontWeight.Bold),fontSize =18.sp)
                Spacer(Modifier.size(10.dp))
                Text(cantidad.toString().padStart(3, '0'),fontSize =18.sp)
                Spacer(Modifier.size(20.dp))
                Flechita(
                    intLimitado = cantidadLimitada,
                    intMostrable = cantidad,
                    rotacion = 0f,
                    onClick = { cantidad = it },
                    operacion = { a: IntLimitado -> a.restar(1) })
                Spacer(modifier = Modifier.size(10.dp))
                Flechita(
                    intLimitado = cantidadLimitada,
                    intMostrable = cantidad,
                    rotacion = 180f,
                    onClick = { cantidad = it },
                    operacion = { a: IntLimitado -> a.sumar(1) })
            }
            Spacer(Modifier.size(10.dp))
            Row(modifier = Modifier.fillMaxWidth().background(Color.Yellow).padding(vertical=8.dp),
                horizontalArrangement = Arrangement.Center)
            {
                Text(text="USAR SOLO EN IMPRESORAS FIJAS",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize =18.sp,color= Color.Red,
                    textAlign = TextAlign.Center)
            }

        }
    }
}

fun imprimirEtiquetasHeladera(modificaX : Int, modificaY: Int, cantidad: Int){
    BTHandler.mandarLogo()
    BTHandler.imprimir(etiquetaHeladera(modificaX, modificaY, cantidad))
}

fun etiquetaHeladera(modificaX : Int, modificaY: Int, cantidad: Int): String{
    val posicionesY =  intArrayOf(360,230,230,25,25 ,370,340,285,285,210,185,150,115, 80,220,185,145,100,85, 40,205,160,145,100, 85, 40,160)
    val posicionesX =  intArrayOf(35 ,35 ,410,35,420,390,295,50 ,425,435,463,435,435,435, 50, 70, 90,125,75,125,250,300,250,300,250,300,35)
    val multiplicador = 4
    return "<STX><ESC>C<ETX>\n" +
            "<STX><ESC>P<ETX>\n" +
            "<STX>E5;F5;<ETX>\n" +
            "<STX>U1;f3;o"+(posicionesY[0]+ modificaY * multiplicador)+","+(posicionesX[0]+ modificaX * multiplicador)+";c2;w1;h1;<ETX>\n" +
            "<STX>W2;o"+(posicionesY[1]+ modificaY * multiplicador)+","+(posicionesX[1]+ modificaX * multiplicador)+";l70;h380;w5;<ETX>\n" +
            "<STX>W3;o"+(posicionesY[2]+ modificaY * multiplicador)+","+(posicionesX[2]+ modificaX * multiplicador)+";l70;h380;w5;<ETX>\n" +
            "<STX>W4;o"+(posicionesY[3]+ modificaY * multiplicador)+","+(posicionesX[3]+ modificaX * multiplicador)+";l200;h370;w5;<ETX>\n" +
            "<STX>W5;o"+(posicionesY[4]+ modificaY * multiplicador)+","+(posicionesX[4]+ modificaX * multiplicador)+";l200;h370;w5;<ETX>\n" +
            "<STX>H6;f3;o"+(posicionesY[5]+ modificaY * multiplicador)+","+(posicionesX[5]+ modificaX * multiplicador)+";c21;b0;k10;d3,Cadena de Frio<ETX>\n" +
            "<STX>H7;f3;o"+(posicionesY[6]+ modificaY * multiplicador)+","+(posicionesX[6]+ modificaX * multiplicador)+";c26;b0;k10;d3,Mantener entre 2 y 8 grados C<ETX>\n" +
            "<STX>H8;f3;o"+(posicionesY[7]+ modificaY * multiplicador)+","+(posicionesX[7]+ modificaX * multiplicador)+";c21;b0;k12;d3,Fecha:<ETX>\n" +
            "<STX>H9;f3;o"+(posicionesY[8]+ modificaY * multiplicador)+","+(posicionesX[8]+ modificaX * multiplicador)+";c21;b0;k12;d3,Hora:<ETX>\n" +
            "<STX>H10;f3;o"+(posicionesY[9]+ modificaY * multiplicador)+","+(posicionesX[9]+ modificaX * multiplicador)+";c26;b0;k10;d3,- Mantener lejos de<ETX>\n" +
            "<STX>H11;f3;o"+(posicionesY[10]+ modificaY * multiplicador)+","+(posicionesX[10]+ modificaX * multiplicador)+";c26;b0;k10;d3,fuentes de calor<ETX>\n" +
            "<STX>H12;f3;o"+(posicionesY[11]+ modificaY * multiplicador)+","+(posicionesX[11]+ modificaX * multiplicador)+";c26;b0;k10;d3,- No Congelar<ETX>\n" +
            "<STX>H13;f3;o"+(posicionesY[12]+ modificaY * multiplicador)+","+(posicionesX[12]+ modificaX * multiplicador)+";c26;b0;k10;d3,- Fragil<ETX>\n" +
            "<STX>H14;f3;o"+(posicionesY[13]+ modificaY * multiplicador)+","+(posicionesX[13]+ modificaX * multiplicador)+";c26;b0;k10;d3,- No abrir<ETX>\n" +
            "<STX>H15;f3;o"+(posicionesY[14]+ modificaY * multiplicador)+","+(posicionesX[14]+ modificaX * multiplicador)+";c26;b0;k12;d3,Validez<ETX>\n" +
            "<STX>H16;f3;o"+(posicionesY[15]+ modificaY * multiplicador)+","+(posicionesX[15]+ modificaX * multiplicador)+";c26;b0;k6;d3,(en horas)<ETX>\n" +
            "<STX>H17;f3;o"+(posicionesY[16]+ modificaY * multiplicador)+","+(posicionesX[16]+ modificaX * multiplicador)+";c26;b0;k12;d3,6<ETX>\n" +
            "<STX>W18;o"+(posicionesY[17]+ modificaY * multiplicador)+","+(posicionesX[17]+ modificaX * multiplicador)+";l50;h50;w5;<ETX>\n" +
            "<STX>H19;f3;o"+(posicionesY[18]+ modificaY * multiplicador)+","+(posicionesX[18]+ modificaX * multiplicador)+";c26;b0;k12;d3,12<ETX>\n" +
            "<STX>W20;o"+(posicionesY[19]+ modificaY * multiplicador)+","+(posicionesX[19]+ modificaX * multiplicador)+";l50;h50;w5;<ETX>\n" +
            "<STX>H21;f3;o"+(posicionesY[20]+ modificaY * multiplicador)+","+(posicionesX[20]+ modificaX * multiplicador)+";c26;b0;k12;d3,24<ETX>\n" +
            "<STX>W22;o"+(posicionesY[21]+ modificaY * multiplicador)+","+(posicionesX[21]+ modificaX * multiplicador)+";l50;h50;w5;<ETX>\n" +
            "<STX>H23;f3;o"+(posicionesY[22]+ modificaY * multiplicador)+","+(posicionesX[22]+ modificaX * multiplicador)+";c26;b0;k12;d3,48<ETX>\n" +
            "<STX>W24;o"+(posicionesY[23]+ modificaY * multiplicador)+","+(posicionesX[23]+ modificaX * multiplicador)+";l50;h50;w5;<ETX>\n" +
            "<STX>H25;f3;o"+(posicionesY[24]+ modificaY * multiplicador)+","+(posicionesX[24]+ modificaX * multiplicador)+";c26;b0;k12;d3,72<ETX>\n" +
            "<STX>W26;o"+(posicionesY[25]+ modificaY * multiplicador)+","+(posicionesX[25]+ modificaX * multiplicador)+";l50;h50;w5;<ETX>\n" +
            "<STX>W27;o"+(posicionesY[26]+ modificaY * multiplicador)+","+(posicionesX[26]+ modificaX * multiplicador)+";l65;h170;w5;<ETX>\n" +
            "<STX>R<ETX>\n" +
            "<STX><ESC>E5<CAN><ETX>\n" +
            "<STX><RS>" + cantidad.toString()+"<ETB><FF><ETX>"
}