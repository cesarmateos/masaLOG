package com.example.masalog.configuraEtiqueta

import com.example.masalog.BTHandler

class IPL : Lenguaje() {

    override val multiplicador = 4

    override fun cargarFormatoDespacho(vertical: Int, horizontal: Int, sentidoNormal: Boolean, barras: Boolean) {
        BTHandler.imprimir(LogoIPL)
        lateinit var posicionesEnX:IntArray
        lateinit var posicionesEnY:IntArray
        lateinit var orientacion:IntArray

        var tamanioTexto = 17

        val matrizOpcionesX = Array(2){Array(2){IntArray(19)} }
        val matrizOpcionesY = Array(2){Array(2){IntArray(19)} }


        //NORMAL - BARRAS
        matrizOpcionesX[0][0] = intArrayOf(460,460,480,450,420,420,475,425,370,330,260,220,160,160,160,160,145,68,30)
        matrizOpcionesY[0][0] = intArrayOf(130, 162, 185, 185, 185, 290, 585, 612, 185, 185, 310, 40, 70, 260, 470, 680, 40, 50, 50)

        //NORMAL - QR
        matrizOpcionesX[0][1] = intArrayOf(470,350,480,450,420,420,475,425,370,330,260,220,160,160,160,160,145,68,30)
        matrizOpcionesY[0][1] = intArrayOf( 40,  40, 190, 190, 190, 290, 590, 617, 190, 190, 310, 40, 70, 260, 470, 680, 40, 50, 50)

        //INVERTIDA - BARRAS
        matrizOpcionesX[1][0] = intArrayOf(30, 30, 10, 40, 70, 70, 15, 65, 120, 160, 230, 270, 330, 330, 330, 330, 345, 412, 450)
        matrizOpcionesY[1][0] = intArrayOf(670, 638, 610, 610, 610, 500, 215, 188, 610, 610, 490, 760, 730, 540, 340, 110, 760, 750, 750)

        //INVERTIDA - QR
        matrizOpcionesX[1][1] = intArrayOf(20, 80, 10, 40, 70, 70, 15, 65, 120, 160, 230, 270, 330, 330, 330, 330, 345, 412, 450)
        matrizOpcionesY[1][1] = intArrayOf(750, 650, 605, 605, 605, 500, 210, 183, 605, 605, 490, 760, 730, 540, 340, 110, 760, 750, 750)


        val modificadorPosicionX:Int
        val modificadorPosicionY:Int

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


        if (BTHandler.nombreDispositivoConectado()?.take(3).equals("RP4")) {
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

    override fun cargarFormatoPredespacho(vertical: Int, horizontal: Int, margen: Int, barras :Boolean) {
        var modificadorMargen =""

        BTHandler.imprimir(LogoIPL)

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

    override fun imprimirRefrigerados50mm(modificaX : Int, modificaY: Int, cantidad: Int) {
        BTHandler.imprimir(LogoIPL)
        super.imprimirRefrigerados50mm(modificaX, modificaY, cantidad)
    }

    override fun imprimirRefrigerados65mm(modificaX : Int, modificaY: Int, cantidad: Int) {
        BTHandler.imprimir(LogoIPL)
        super.imprimirRefrigerados65mm(modificaX, modificaY, cantidad)
    }

    override fun admiteGiro(): Boolean {
        return true
    }

    override fun nombreLenguaje(): String {
        return "IPL"
    }

    override fun admiteBarrasPack(): Boolean {
        return true
    }

    override fun admiteBarrasTapadora(): Boolean {
        return false
    }

    override fun etiquetaHeladera50mm(modificaX : Int, modificaY: Int, cantidad: Int): String{

        val baseY =  intArrayOf(360,230,230,25,25 ,370,340,285,285,210,185,150,115, 80,220,185,145,100,85, 40,205,160,145,100, 85, 40,160)
        val baseX =  intArrayOf(35 ,35 ,410,35,420,390,295,50 ,425,435,463,435,435,435, 50, 70, 90,125,75,125,250,300,250,300,250,300,35)

        val posicionesY = baseY.map({ coordenada -> coordenada + modificaY * multiplicador})
        val posicionesX = baseX.map({ coordenada -> coordenada + modificaX * multiplicador})


        return "<STX><ESC>C<ETX>\n" +
                "<STX><ESC>P<ETX>\n" +
                "<STX>E5;F5;<ETX>\n" +
                "<STX>U1;f3;o" + posicionesY[0] +","+ posicionesX[0] + ";c2;w1;h1;<ETX>\n" +
                "<STX>W2;o" +  posicionesY[1] +","+ posicionesX[1] + ";l70;h380;w5;<ETX>\n" +
                "<STX>W3;o" + posicionesY[2] +","+ posicionesX[2] + ";l70;h380;w5;<ETX>\n" +
                "<STX>W4;o" + posicionesY[3] +","+ posicionesX[3] + ";l200;h370;w5;<ETX>\n" +
                "<STX>W5;o" + posicionesY[4] +","+ posicionesX[4] + ";l200;h370;w5;<ETX>\n" +
                "<STX>H6;f3;o" + posicionesY[5] +","+ posicionesX[5] + ";c21;b0;k10;d3,Cadena de Frio<ETX>\n" +
                "<STX>H7;f3;o" + posicionesY[6] +","+ posicionesX[6] + ";c26;b0;k10;d3,Mantener entre 2 y 8 grados C<ETX>\n" +
                "<STX>H8;f3;o" + posicionesY[7] +","+ posicionesX[7] + ";c21;b0;k12;d3,Fecha:<ETX>\n" +
                "<STX>H9;f3;o" + posicionesY[8] +","+ posicionesX[8] + ";c21;b0;k12;d3,Hora:<ETX>\n" +
                "<STX>H10;f3;o" + posicionesY[9] +","+ posicionesX[9] + ";c26;b0;k10;d3,- Mantener lejos de<ETX>\n" +
                "<STX>H11;f3;o" + posicionesY[10] +","+ posicionesX[10] + ";c26;b0;k10;d3,fuentes de calor<ETX>\n" +
                "<STX>H12;f3;o" + posicionesY[11] +","+ posicionesX[11] + ";c26;b0;k10;d3,- No Congelar<ETX>\n" +
                "<STX>H13;f3;o" + posicionesY[12] +","+ posicionesX[12] + ";c26;b0;k10;d3,- Fragil<ETX>\n" +
                "<STX>H14;f3;o" + posicionesY[13] +","+ posicionesX[13] + ";c26;b0;k10;d3,- No abrir<ETX>\n" +
                "<STX>H15;f3;o" + posicionesY[14] +","+ posicionesX[14] + ";c26;b0;k12;d3,Validez<ETX>\n" +
                "<STX>H16;f3;o" + posicionesY[15] +","+ posicionesX[15] + ";c26;b0;k6;d3,(en horas)<ETX>\n" +
                "<STX>H17;f3;o" + posicionesY[16] +","+ posicionesX[16] + ";c26;b0;k12;d3,6<ETX>\n" +
                "<STX>W18;o"+ posicionesY[17] +","+ posicionesX[17] + ";l50;h50;w5;<ETX>\n" +
                "<STX>H19;f3;o"+ posicionesY[18] +","+ posicionesX[18] + ";c26;b0;k12;d3,12<ETX>\n" +
                "<STX>W20;o"+ posicionesY[19] +","+ posicionesX[19] + ";l50;h50;w5;<ETX>\n" +
                "<STX>H21;f3;o"+ posicionesY[20] +","+ posicionesX[20] + ";c26;b0;k12;d3,24<ETX>\n" +
                "<STX>W22;o"+ posicionesY[21] +","+ posicionesX[21] + ";l50;h50;w5;<ETX>\n" +
                "<STX>H23;f3;o"+ posicionesY[22] +","+ posicionesX[22] + ";c26;b0;k12;d3,48<ETX>\n" +
                "<STX>W24;o"+ posicionesY[23] + "," + posicionesX[23] + ";l50;h50;w5;<ETX>\n" +
                "<STX>H25;f3;o"+ posicionesY[24] + "," + posicionesX[24] + ";c26;b0;k12;d3,72<ETX>\n" +
                "<STX>W26;o"+ posicionesY[25] + "," + posicionesX[25] + ";l50;h50;w5;<ETX>\n" +
                "<STX>W27;o"+posicionesY[26] + "," + posicionesX[26] + ";l65;h170;w5;<ETX>\n" +
                "<STX>R<ETX>\n" +
                "<STX><ESC>E5<CAN><ETX>\n" +
                "<STX><RS>" + cantidad.toString()+"<ETB><FF><ETX>"
    }

    override fun etiquetaHeladera65mm(modificaX: Int, modificaY: Int, cantidad: Int): String {
        return ""
    }

    override val ejemploFarmabox: String
        get() = "<STX>R<ETX>\n" +
                "<STX><ESC>E11<CAN><ETX>\n" +
                "<STX><ESC>F6<DEL>DESP.COPIA NNN-D<ETX>\n" +
                "<STX><ESC>F8<DEL>Fecha_Hoy<ETX>\n" +
                "<STX><ESC>F10<DEL>Pedido<ETX>\n" +
                "<STX><ESC>F11<DEL>4444444444<ETX>\n" +
                "<STX><ESC>F12<DEL>(ID_CLIE)<ETX>\n" +
                "<STX><ESC>F17<DEL>Bul: NUM Fmbx.=NUMERO  Doc.=NUMERO FACTURA<ETX>\n" +
                "<STX><ESC>F15<DEL>Direccion Farmacia<ETX>\n" +
                "<STX><ESC>F16<DEL>Localidad<ETX>\n" +
                "<STX><ESC>F13<DEL>ETIQUETA EJEMPLO PREDESPACHO<ETX>\n" +
                "<STX><ESC>F21<DEL>01  A5 13:20  22<ETX>\n" +
                "<STX><RS>1<ETB><FF><ETX>"

    override val ejemploPack: String
        get() = "<STX>R<ETX> \n" +
                "<STX><ESC>E1<CAN><ETX>\n" +
                "<STX><ESC>F6<DEL>Bulto Numero<ETX>\n" +
                "<STX><ESC>F8<DEL>Fecha_Hoy<ETX>\n" +
                "<STX><ESC>F10<DEL>1<ETX>\n" +
                "<STX><ESC>F11<DEL>66666666<ETX>\n" +
                "<STX><ESC>F12<DEL>(ID_CLIE)<ETX>\n" +
                "<STX><ESC>F13<DEL>ETIQUETA EJEMPLO PACK<ETX>\n" +
                "<STX><ESC>F15<DEL>NOMBRE PRODUCTO               <ETX>\n" +
                "<STX><ESC>F16<DEL>PRESENTACION PRODUCTO<ETX>\n" +
                "<STX><ESC>F17<DEL>2   (Desc  1) [LOCALIZ]<ETX>\n" +
                "<STX><ESC>F21<DEL>0001   11/02  A1 14:10   025<ETX>\n" +
                "<STX><ESC>F25<DEL>Fal. 0/ 2<ETX>\n" +
                "<STX><RS>1<ETB><FF><ETX>"

    override val ejemploIOMA: String
        get() = "<STX>R<ETX> \n" +
                "<STX><ESC>E1<CAN><ETX>\n" +
                "<STX><ESC>F6<DEL>Etiq. de DESPACHO<ETX>\n" +
                "<STX><ESC>F8<DEL>Fecha_Hoy<ETX>\n" +
                "<STX><ESC>F10<DEL>0000<ETX>\n" +
                "<STX><ESC>F11<DEL>5555555555<ETX>\n" +
                "<STX><ESC>F12<DEL>( INIO)<ETX>\n" +
                "<STX><ESC>F13<DEL>FARMACIA<ETX>\n" +
                "<STX><ESC>F15<DEL>Direccion  <ETX>\n" +
                "<STX><ESC>F16<DEL>Localidad<ETX>\n" +
                "<STX><ESC>F17<DEL>*                             <ETX>\n" +
                "<STX><ESC>F21<DEL>0001   18/03  V2 23:58   002<ETX>\n" +
                "<STX><ESC>F25<DEL>ETIQUETA IOMA<ETX>\n" +
                "<STX><RS>1<ETB><FF><ETX>"

    override val ejemploRefrigerados: String
        get() = "<STX>R<ETX> \n" +
                "<STX><ESC>E1<CAN><ETX>\n" +
                "<STX><ESC>F6<DEL>Etiq. de DESPACHO<ETX>\n" +
                "<STX><ESC>F8<DEL>Fecha_Hoy<ETX>\n" +
                "<STX><ESC>F10<DEL>0000<ETX>\n" +
                "<STX><ESC>F11<DEL>9999999999<ETX>\n" +
                "<STX><ESC>F12<DEL>(ID_CLIE)<ETX>\n" +
                "<STX><ESC>F13<DEL>FARMACIA <ETX>\n" +
                "<STX><ESC>F15<DEL>Etiqueta Ejemplo<ETX>\n" +
                "<STX><ESC>F16<DEL>Refrigerados<ETX>\n" +
                "<STX><ESC>F17<DEL>***PRODUCTOS A REFRIGERAR ENTRE 2 y 8 GRADOS<ETX>\n" +
                "<STX><ESC>F21<DEL>0001   17/03  U7 23:40   007<ETX>\n" +
                "<STX><RS>1<ETB><FF><ETX>"
}

const val LogoIPL = "<STX><ESC>C<ETX>\n" +
        "<STX><ESC>P<ETX>\n" +
        "<STX>G2,Masa;x174;y50;<ETX>\n" +
        "<STX>u0,@@@@`\u007F@@@;<ETX>\n" +
        "<STX>u1,@@@@|\u007FG@@;<ETX>\n" +
        "<STX>u2,@@@@\u007F\u007FO@@;<ETX>\n" +
        "<STX>u3,@@@`\u007F\u007F\u007F@@;<ETX>\n" +
        "<STX>u4,@@@p\u007F\u007F\u007FA@;<ETX>\n" +
        "<STX>u5,@@@x\u007F\u007F\u007FC@;<ETX>\n" +
        "<STX>u6,@@@|\u007FGxG@;<ETX>\n" +
        "<STX>u7,@@@~\u007F@@O@;<ETX>\n" +
        "<STX>u8,@@@\u007FO@@\\@;<ETX>\n" +
        "<STX>u9,@@`\u007FG@@X@;<ETX>\n" +
        "<STX>u10,@@p\u007FC@@p@;<ETX>\n" +
        "<STX>u11,@@p\u007FA@@`A;<ETX>\n" +
        "<STX>u12,@@x\u007F@@@@C;<ETX>\n" +
        "<STX>u13,@@|_@@@@B;<ETX>\n" +
        "<STX>u14,@@|O@@@@@;<ETX>\n" +
        "<STX>u15,@@~G@@@@@;<ETX>\n" +
        "<STX>u16,@@~G@@@@@;<ETX>\n" +
        "<STX>u17,@@\u007FC@@@@@;<ETX>\n" +
        "<STX>u18,@@\u007FC@@@@@;<ETX>\n" +
        "<STX>u19,@`\u007FA@@@@@;<ETX>\n" +
        "<STX>u20,@`\u007F@@@@@@;<ETX>\n" +
        "<STX>u21,@`\u007F@@@@@@;<ETX>\n" +
        "<STX>u22,@p\u007F@@@@@@;<ETX>\n" +
        "<STX>u23,@p_@@@@@@;<ETX>\n" +
        "<STX>u24,@x_@@@@@@;<ETX>\n" +
        "<STX>u25,@xO@@@@\u007FA;<ETX>\n" +
        "<STX>u26,@xO@@@\u007F\u007FA;<ETX>\n" +
        "<STX>u27,@|O@@\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u28,@|G@\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u29,@|G~\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u30,@~g\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u31,@~c\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u32,@~c\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u33,@~c\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u34,@\u007Fa\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u35,@\u007Fa\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u36,@\u007Fa\u007F\u007F\u007F\u007FG@;<ETX>\n" +
        "<STX>u37,@\u007Fa\u007F\u007F\u007FG@@;<ETX>\n" +
        "<STX>u38,`\u007F`\u007F\u007FG@@@;<ETX>\n" +
        "<STX>u39,`\u007F`\u007F_@@@@;<ETX>\n" +
        "<STX>u40,`\u007F@xG@@@@;<ETX>\n" +
        "<STX>u41,`\u007F@|C@@@@;<ETX>\n" +
        "<STX>u42,p_@~C@@\u007FA;<ETX>\n" +
        "<STX>u43,p_@~C@\u007F\u007FA;<ETX>\n" +
        "<STX>u44,p_@\u007FG\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u45,p_@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u46,p_@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u47,pO`\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u48,xO`\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u49,xO`\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u50,xO@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u51,xO@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u52,xO@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u53,xO@~\u007F\u007F\u007FO@;<ETX>\n" +
        "<STX>u54,xG@|\u007F\u007FO@@;<ETX>\n" +
        "<STX>u55,|G@x\u007FO@@@;<ETX>\n" +
        "<STX>u56,|G@p_@@@@;<ETX>\n" +
        "<STX>u57,|G@xG@@@@;<ETX>\n" +
        "<STX>u58,|G@|G@@@@;<ETX>\n" +
        "<STX>u59,|G@~C@@~A;<ETX>\n" +
        "<STX>u60,|G@\u007FC@~\u007FA;<ETX>\n" +
        "<STX>u61,|C@\u007FG~\u007F\u007FA;<ETX>\n" +
        "<STX>u62,|C@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u63,~C`\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u64,~C`\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u65,~C`\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u66,~C@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u67,~C@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u68,~C@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u69,~C@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u70,~C@~\u007F\u007F\u007F\u007F@;<ETX>\n" +
        "<STX>u71,~C@|\u007F\u007F\u007FA@;<ETX>\n" +
        "<STX>u72,~A@x\u007F\u007FA@@;<ETX>\n" +
        "<STX>u73,~A@`\u007FAP@@;<ETX>\n" +
        "<STX>u74,~A@@@@\u007FG@;<ETX>\n" +
        "<STX>u75,~A@@@`\u007FO@;<ETX>\n" +
        "<STX>u76,\u007FA@@@p\u007F_@;<ETX>\n" +
        "<STX>u77,\u007FA@@Bx\u007F\u007F@;<ETX>\n" +
        "<STX>u78,\u007FA@@G|\u007F\u007FA;<ETX>\n" +
        "<STX>u79,\u007FA@pG~\u007F\u007FA;<ETX>\n" +
        "<STX>u80,\u007FA@xG~\u007F\u007FA;<ETX>\n" +
        "<STX>u81,\u007FA@xG~\u007F\u007FA;<ETX>\n" +
        "<STX>u82,\u007FA@|G\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u83,\u007FA@~O\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u84,\u007FA@~O\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u85,\u007FA@~O\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u86,\u007FA@\u007FO\u007Fw\u007FA;<ETX>\n" +
        "<STX>u87,\u007FA@\u007Fo\u007FA\u007FA;<ETX>\n" +
        "<STX>u88,\u007FA@\u007Fg\u007F@\u007F@;<ETX>\n" +
        "<STX>u89,\u007FA@\u007Fc\u007F@\u007F@;<ETX>\n" +
        "<STX>u90,\u007FA@\u007Fa_`_@;<ETX>\n" +
        "<STX>u91,\u007F@@\u007Fa_`O@;<ETX>\n" +
        "<STX>u92,\u007F@`\u007Fa_pG@;<ETX>\n" +
        "<STX>u93,\u007F@`\u007Fa_~\u007FA;<ETX>\n" +
        "<STX>u94,\u007F@`\u007Fa\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u95,\u007F@@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u96,\u007F@@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u97,\u007F@@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u98,\u007F@@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u99,\u007F@@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u100,\u007F@@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u101,\u007F@@~\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u102,\u007F@@~\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u103,\u007F@@~\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u104,\u007F@@|\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u105,\u007F@@x\u007F\u007F_@@;<ETX>\n" +
        "<STX>u106,\u007F@@p\u007F_@@@;<ETX>\n" +
        "<STX>u107,\u007F@@@_@P@@;<ETX>\n" +
        "<STX>u108,\u007F@@@@@pA@;<ETX>\n" +
        "<STX>u109,\u007F@@@@@pG@;<ETX>\n" +
        "<STX>u110,\u007F@@@|ApO@;<ETX>\n" +
        "<STX>u111,\u007F@@`\u007FGxO@;<ETX>\n" +
        "<STX>u112,\u007F@@p\u007FOx_@;<ETX>\n" +
        "<STX>u113,\u007F@@x\u007F_x\u007F@;<ETX>\n" +
        "<STX>u114,~@@|\u007F\u007Fx\u007F@;<ETX>\n" +
        "<STX>u115,~@@|\u007F\u007Fx\u007F@;<ETX>\n" +
        "<STX>u116,~@@~\u007F\u007Fy\u007FA;<ETX>\n" +
        "<STX>u117,~@@~\u007F\u007Fy\u007FA;<ETX>\n" +
        "<STX>u118,~@@\u007F\u007F\u007Fa\u007FA;<ETX>\n" +
        "<STX>u119,~A@\u007F\u007F\u007Fc\u007FA;<ETX>\n" +
        "<STX>u120,~A@\u007F\u007F\u007FC\u007FA;<ETX>\n" +
        "<STX>u121,~A@\u007F\u007F\u007FC\u007FA;<ETX>\n" +
        "<STX>u122,~A@\u007Fq\u007FC\u007FA;<ETX>\n" +
        "<STX>u123,~A`\u007Fq\u007FG\u007FA;<ETX>\n" +
        "<STX>u124,~A`\u007Fq\u007FG\u007FA;<ETX>\n" +
        "<STX>u125,~A`\u007Fq\u007FG\u007FA;<ETX>\n" +
        "<STX>u126,|A@\u007Fa\u007Fg\u007FA;<ETX>\n" +
        "<STX>u127,|A@\u007Fa\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u128,|A@\u007Fc\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u129,|A@\u007Fg\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u130,|A@\u007FO\u007F\u007F\u007F@;<ETX>\n" +
        "<STX>u131,|A@~G\u007F\u007F\u007F@;<ETX>\n" +
        "<STX>u132,|A@~G\u007F\u007F\u007F@;<ETX>\n" +
        "<STX>u133,|A@|G~\u007F_@;<ETX>\n" +
        "<STX>u134,xA@|G~\u007FO@;<ETX>\n" +
        "<STX>u135,xC@xG|\u007FG@;<ETX>\n" +
        "<STX>u136,xC@pCx\u007FC@;<ETX>\n" +
        "<STX>u137,xC@`C`\u007FA@;<ETX>\n" +
        "<STX>u138,xC@@C@@@@;<ETX>\n" +
        "<STX>u139,xC@@@@x@@;<ETX>\n" +
        "<STX>u140,pC@@@@\u007FG@;<ETX>\n" +
        "<STX>u141,pC@@@`\u007FO@;<ETX>\n" +
        "<STX>u142,pC@@@x\u007F_@;<ETX>\n" +
        "<STX>u143,pC@@Bx\u007F\u007F@;<ETX>\n" +
        "<STX>u144,pG@@G|\u007F\u007FA;<ETX>\n" +
        "<STX>u145,`G@pG~\u007F\u007FA;<ETX>\n" +
        "<STX>u146,`G@xG~\u007F\u007FA;<ETX>\n" +
        "<STX>u147,`G@xG~\u007F\u007FA;<ETX>\n" +
        "<STX>u148,`G@|G\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u149,@G@~O\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u150,@O@~O\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u151,@O@~O\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u152,@O@\u007FO\u007Fc\u007FA;<ETX>\n" +
        "<STX>u153,@N@\u007Fo\u007FA\u007FA;<ETX>\n" +
        "<STX>u154,@N@\u007Fc\u007F@\u007F@;<ETX>\n" +
        "<STX>u155,@N@\u007Fc_@\u007F@;<ETX>\n" +
        "<STX>u156,@\\@\u007Fa_`_@;<ETX>\n" +
        "<STX>u157,@\\@\u007Fa_`O@;<ETX>\n" +
        "<STX>u158,@\\`\u007Fa_pG@;<ETX>\n" +
        "<STX>u159,@\\`\u007Fa_~\u007FA;<ETX>\n" +
        "<STX>u160,@x`\u007Fc\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u161,@x@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u162,@p@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u163,@p@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u164,@p@\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u165,@`A\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u166,@`A\u007F\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u167,@`A~\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u168,@@A~\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u169,@@C|\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u170,@@B|\u007F\u007F\u007F\u007FA;<ETX>\n" +
        "<STX>u171,@@Fx\u007F\u007F_@@;<ETX>\n" +
        "<STX>u172,@@Dp\u007F_@@@;<ETX>\n" +
        "<STX>u173,@@H@^@@@@;<ETX>\n" +
        "<STX>R<ETX>"