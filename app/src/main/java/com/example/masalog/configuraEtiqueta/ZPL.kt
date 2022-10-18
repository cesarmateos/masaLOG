package com.example.masalog.configuraEtiqueta

import com.example.masalog.BTHandler

class ZPL : Lenguaje{

    override fun cargarFormatoDespacho(
        vertical: Int,
        horizontal: Int,
        sentidoNormal: Boolean,
        barras: Boolean
    ) {
        val multiplicador = 5

        val origenX = intArrayOf(560,580,40,190,190,190,250,190,190,340, 40, 90,270,480,670, 55, 40, 40)
        val origenY = intArrayOf( 40,100,40, 40, 70,100,100,145,185,243,283,335,335,335,335,360,435,470)

        val finalX = origenX.map({coordenada -> (coordenada + horizontal * multiplicador) })
        val finalY = origenY.map({coordenada -> (coordenada + vertical * multiplicador) })

        val scaneable = if(barras){
            "^BY2,2.7,50\n" + "^FO" + finalX[2] + "," + finalY[2] +  "^B2B,90,Y,N^FN1^FS\n"
        }else{
            "^FO" +  finalX[2] + "," + finalY[2] + "^BXN,10,200,,,1,^FN1^FS\n" +
                    "^FO" +  finalX[2] + "," + (180+ vertical * multiplicador) + "^A0,25,25^FN1^FS\n"
        }

        val etiqueta = "^XA\n" +
                "^DFR:PACK.ZPL^FS\n" +
                "^FO" +  finalX[0] + "," + finalY[0] + ZPLLogo + "^FS\n"+
                "^FO" +  finalX[1] + "," + finalY[1] + "^A0N,20,23^FDMonroe Americana^FS\n"+
                scaneable +
                "^FO" + finalX[3] + "," + finalY[3] +  "^A0N,28,28^FN2^FS\n" +
                "^FO" + finalX[4] + "," + finalY[4] +  "^A0N,28,28^FN3^FS\n" +
                "^FO" + finalX[5] + "," + finalY[5] +  "^A0N,28,28^FDPed:^FS\n" +
                "^FO" + finalX[6] + "," + finalY[6] +  "^A0N,28,28^FN4^FS\n" +
                "^FO" + finalX[7] + "," + finalY[7] +  "^A0N,45,45^FN5^FS\n" +
                "^FO" + finalX[8] + "," + finalY[8] +  "^A0N,45,45^FN6^FS\n" +
                "^FO" + finalX[9] + "," + finalY[9] +  "^A0N,30,30^FN7^FS\n" +
                "^FO" + finalX[10] + "," + finalY[10] +  "^A0N,30,30^FN8^FS\n" +
                "^FO" + finalX[11] + "," + finalY[11] +  "^A0N,20,20^FDBulto^FS\n" +
                "^FO" + finalX[12] + "," + finalY[12] +  "^A0N,20,20^FDTurno^FS\n" +
                "^FO" + finalX[13] + "," + finalY[13] +  "^A0N,20,20^FDRadio^FS\n" +
                "^FO" + finalX[14] + "," + finalY[14] +  "^A0N,20,20^FDOrden^FS\n" +
                "^FO" + finalX[15] + "," + finalY[15] +  "^A0N,60,60^FN9^FS\n" +
                "^FO" + finalX[16] + "," + finalY[16] +  "^A0N,30,30^FN10^FS\n" +
                "^FO" + finalX[17] + "," + finalY[17] +  "^A0N,30,30^FN11^FS\n" +
                "^XZ"

        BTHandler.imprimir(etiqueta)
    }

    override fun cargarFormatoPredespacho(vertical: Int, horizontal: Int, margen: Int, barras :Boolean) {
        var modificadorMargen =""
        val multiplicador = 5


        if (margen>0){
            modificadorMargen = "^F020,20^FD.^FS<ETX>\n"
        }

        val origenX = intArrayOf(40, 65,310,390,590, 40, 80,250,440,655, 60, 40, 50, 50,420, 40,595,565)
        val origenY = intArrayOf(40,100, 50, 50, 50,150,210,210,210,210,235,340,375,400,400,445,335,365)

        val finalX = origenX.map({coordenada -> (coordenada + horizontal * multiplicador) })
        val finalY = origenY.map({coordenada -> ((coordenada + margen) + (vertical * multiplicador)) })

        val scaneable = if(barras){
            "^BY2,2.7,50\n" +
                    "^FO"+ ( 620 + horizontal * multiplicador) + "," + ( 310 + margen + (vertical * multiplicador))+"^B2B,110,Y,N^FN10^FS"
        }else{
            "^FO" +  finalX[16] + "," + finalY[16] + "^BXN,12,200,,,1,^FN10^FS\n"+
                    "^FO" +  finalX[17] + "," + finalY[17] + "^A0B,25,25^FN10^FS\n"
        }


        val etiqueta = "^XA\n" +
                "^DFR:TAPADORA.ZPL^FS\n" +
                modificadorMargen +
                "^FO" +  finalX[0] + "," + finalY[0] + ZPLLogo + "^FS\n"+
                "^FO" +  finalX[1] + "," + finalY[1] + "^A0N,20,23^FDMonroe Americana^FS\n"+
                "^FO" +  finalX[2] + "," + finalY[2] + "^A0N,35,35^FDPed:^FS\n"+
                "^FO" +  finalX[3] + "," + finalY[3] + "^A0N,35,35^FN1^FS\n"+
                "^FO" +  finalX[4] + "," + finalY[4] + "^A0N,35,35^FN2^FS\n"+
                "^FO" +  finalX[5] + "," + finalY[5] + "^A0N,30,33^FN3^FS\n"+
                "^FO" +  finalX[6] + "," + finalY[6] + "^A0N,20,20^FDEntrega^FS\n"+
                "^FO" +  finalX[7] + "," + finalY[7] + "^A0N,20,20^FDRadio^FS\n"+
                "^FO" +  finalX[8] + "," + finalY[8] + "^A0N,20,20^FDSalida^FS\n"+
                "^FO" +  finalX[9] + "," + finalY[9] + "^A0N,20,20^FDOrden^FS\n"+
                "^FO" +  finalX[10] + "," + finalY[10] + "^A0N,90,100^FN4^FS\n"+
                "^FO" +  finalX[11]+ "," + finalY[11] + "^A0N,28,33^FN5^FS\n"+
                "^FO" +  finalX[12] + "," + finalY[12]+ "^A0N,25,25^FN6^FS\n"+
                "^FO" +  finalX[13] + "," + finalY[13] + "^A0N,25,25^FN7^FS\n"+
                "^FO" +  finalX[14] + "," + finalY[14]+ "^A0N,25,25^FN8^FS\n"+
                "^FO" +  finalX[15]+ "," + finalY[15]+ "^A0N,45,50^FN9^FS\n"+
                scaneable+
                "^XZ"

        BTHandler.imprimir(etiqueta)
    }

    override fun imprimirDespachoPack() {
        BTHandler.imprimir(ZPLetiquetaPack)
    }

    override fun imprimirDespachoIOMA() {
        BTHandler.imprimir(ZPLetiquetaIOMA)
    }
    override fun imprimirDespachoRefrigerado() {
        BTHandler.imprimir(ZPLetiquetaRefrigerados)
    }

    override fun imprimirPredespacho() {
        BTHandler.imprimir(ZPLetiquetaPredespacho)
    }

    override fun admiteGiro(): Boolean {
        return false
    }

    override fun nombreLenguaje(): String {
        return "ZPL"
    }

    override fun admiteBarrasPack(): Boolean {
        return true
    }

    override fun admiteBarrasTapadora(): Boolean {
        return true
    }
}

const val ZPLetiquetaPack = "^XA\n" +
        "^XFR:PACK.ZPL\n" +
        "^FN1^FD123456789^FS\n" +
        "^FN2^FDBulto Numero^FS\n" +
        "^FN3^FD01/01/1900^FS\n" +
        "^FN4^FD0000^FS^FS\n" +
        "^FN5^FDNombre Producto^FS\n" +
        "^FN6^FDPresentacion Producto^FS\n" +
        "^FN7^FDFal. 0/ 2^FS\n" +
        "^FN8^FD1   (Desc  1) [LOCALIZ]^FS\n" +
        "^FN9^FD0001   30/10  F3 13:20   022^FS\n" +
        "^FN10^FDEtiqueta Ejemplo Pack^FS\n" +
        "^FN11^FD(ID_CLIE)^FS\n" +
        "^XZ"

const val ZPLetiquetaIOMA = "^XA\n" +
        "^XFR:PACK.ZPL\n" +
        "^FN1^FD1234567890^FS\n" +
        "^FN2^FDEtiq. de DESPACHO^FS\n" +
        "^FN3^FD01/01/1900^FS\n" +
        "^FN4^FD0000^FS^FS\n" +
        "^FN5^FDDireccion^FS\n" +
        "^FN6^FDLocalidad^FS\n" +
        "^FN7^FDEtiqueta IOMA^FS\n" +
        "^FN8^FD*^FS\n" +
        "^FN9^FD0001   30/10  F3 13:20   022^FS\n" +
        "^FN10^FDFarmacia^FS\n" +
        "^FN11^FD(INIO)^FS\n" +
        "^XZ"

const val ZPLetiquetaRefrigerados = "^XA\n" +
        "^XFR:PACK.ZPL\n" +
        "^FN1^FD1234567890^FS\n" +
        "^FN2^FDEtiq. de DESPACHO^FS\n" +
        "^FN3^FD01/01/1900^FS\n" +
        "^FN4^FD0000^FS^FS\n" +
        "^FN5^FDEtiqueta Ejemplo^FS\n" +
        "^FN6^FDRefrigerados^FS\n" +
        "^FN7^FD^FS\n" +
        "^FN8^FD***PRODUCTOS A REFRIGERAR ENTRE 2 y 8 GRADOS***^FS\n" +
        "^FN9^FD0001   30/10  F3 13:20   022^FS\n" +
        "^FN10^FDFarmacia^FS\n" +
        "^FN11^FD(ID_CLIENTE)^FS\n" +
        "^XZ"

const val ZPLetiquetaPredespacho = "^XA\n" +
        "^XFR:TAPADORA.ZPL\n" +
        "^FN1^FD125@#^FS\n" +
        "^FN2^FD04/11/2020^FS\n" +
        "^FN3^FDBul: 001 Fmbx.=200692  Doc.=FC 1116-05939381^FS\n" +
        "^FN4^FD01  A5 13:20  22^FS\n" +
        "^FN5^FDFCIA ANTIGUA POZUELO SCS ^FS\n" +
        "^FN6^FDAv. Mitre 2471^FS\n" +
        "^FN7^FDAvellaneda^FS\n" +
        "^FN8^FD(2676901)^FS\n" +
        "^FN9^FDDESP.COPIA 734-D^FS\n" +
        "^FN10^FD123456789^FS\n" +
        "^XZ"

const val ZPLLogo = "^GFA,1450,1450,25,gI03FFC07FF,Y06TFB,V037XFEC,U0gIFE,S03gLF8,R0gOFC,P01gQFE,P0SF4R0BLFC,O0PFBX01KF8,N0NFECgI05IF8,M07MF8gL01IF,L03LFEgP07FE,L0LF8gR03FC,K03KFCgT03F,J01KF8gV03C,J03JF8gX018J0JFC,I03JF,I0JF8,001IFC,003IFM0FC001F8K03FFL07F8M07FE,007FFC003FFC3FF007FFJ03JFJ03IFCK07JF,01IFI03FFCIFC1IF8001KFCI0KFJ03KFC,01FFCI07FF9IFC3IFC003LF001KFCI07KFE,03FF8I07FFBIFE7IFE00MF807KFEI0MF,07FEJ07RF01MFC07LF001MF80FFCJ07RF03MFC0MF803MF81FF8J07RF03MFE1MFC07MFC1FFK07RF07IF1IFE1FFE07FFC0IFE1IFC3FEK0SF07FFC07FFE3FFE01FF80IF80IFC3FCK0IFC1IFC3IF00FF807FFE3FFE00FJ0FF00IFC7FCK0IF81IF81IFI07007FFE3FFEN0600IFC7F8K0IF81IF01IFL07FFC3IFEP0IFC7F8K0IF01IF01IFJ07JFE3KFM06JFC7FL0IF01IF01IF001LFC1KFEJ03LFCFFK01IF01IF01IF00MFC1LF8001MFCFEK01IF01FFE03IF03MFC1LFE007MF8FEK01IF01FFE03FFE07MFC0LFE00NF8FEK01IF01FFE03FFE0NFC07LF01NF8FEK01FFE03FFE03FFE1JF9IFC03LF83JF1IF87EK03FFE03FFE03FFE3IFC1IFC01LFC7IF81IF87EK03FFE03FFE07FFE7IF01IF8007KFC7FFE01IF,7EK03FFE03FFC07FFC7FFE01IF8I0KFCIFC03IF,3EK03FFE07FFC07FFC7FFE01IF8J07IFCIFC03IF,3EK03FFE07FFC07FFCIFE03IF801400IFCIFC03IF,1EK03FFC07FFC07FFCIFE03IF0FFC007FFDIFC07IF,0FK03FFC07FFC07FFCIFE07IF1FFE007FFDIFC0JF,0FK07FFC07FFC0IFCJF1JF1IFC0IF8IFE3IFE,078J07FFC07FFC0IFCOF0NF8NFE,038J07FFC07FF80IF87NF07MF0NFE,01CJ07FFC0IF80IF87JFDIF07MF07MFE,00EJ07FFC0IF80IF83JF9IF03LFE07JFBFFE,006J07FF80IF80IF81JF3IF01LFC03IFE3FFE,001J0IF80IF01IF80IFE3IF007KF001IFE3FFE,I0CI0IF80IF80IF007FFC1IFI0JFCI07FF83FFE,I06X01FFN03FFEJ03FE,,:"