package com.example.masalog

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController



@Composable
fun PantallaInicio(navController: NavHostController ) {

    EstructuraTituloCuerpo(textoTitulo = "Seleccione una tarea") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            BotonStandard(stringResource(R.string.config_impresora)) { navController.navigate(Pantallas.Impresoras.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard(stringResource(R.string.config_etiquetas)) { navController.navigate((Pantallas.Etiquetas.name)) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard(stringResource(R.string.etiqueta_heladeras)) { navController.navigate(Pantallas.EtiquetaHeladeras.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard(stringResource(R.string.etiquetado_archivo)) { navController.navigate(Pantallas.EtiquetadoPlantaInicio.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard(stringResource(R.string.control_archivo)){ navController.navigate((Pantallas.ControladorProductos.name)) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard("Lenguaje"){ navController.navigate((Pantallas.Configuracion.name))}
        }
    }
}

const val ZPL = "^XA\n" +
        "^FO40,40^GFA,1450,1450,25,gI03FFC07FF,Y06TFB,V037XFEC,U0gIFE,S03gLF8,R0gOFC,P01gQFE,P0SF4R0BLFC,O0PFBX01KF8,N0NFECgI05IF8,M07MF8gL01IF,L03LFEgP07FE,L0LF8gR03FC,K03KFCgT03F,J01KF8gV03C,J03JF8gX018J0JFC,I03JF,I0JF8,001IFC,003IFM0FC001F8K03FFL07F8M07FE,007FFC003FFC3FF007FFJ03JFJ03IFCK07JF,01IFI03FFCIFC1IF8001KFCI0KFJ03KFC,01FFCI07FF9IFC3IFC003LF001KFCI07KFE,03FF8I07FFBIFE7IFE00MF807KFEI0MF,07FEJ07RF01MFC07LF001MF80FFCJ07RF03MFC0MF803MF81FF8J07RF03MFE1MFC07MFC1FFK07RF07IF1IFE1FFE07FFC0IFE1IFC3FEK0SF07FFC07FFE3FFE01FF80IF80IFC3FCK0IFC1IFC3IF00FF807FFE3FFE00FJ0FF00IFC7FCK0IF81IF81IFI07007FFE3FFEN0600IFC7F8K0IF81IF01IFL07FFC3IFEP0IFC7F8K0IF01IF01IFJ07JFE3KFM06JFC7FL0IF01IF01IF001LFC1KFEJ03LFCFFK01IF01IF01IF00MFC1LF8001MFCFEK01IF01FFE03IF03MFC1LFE007MF8FEK01IF01FFE03FFE07MFC0LFE00NF8FEK01IF01FFE03FFE0NFC07LF01NF8FEK01FFE03FFE03FFE1JF9IFC03LF83JF1IF87EK03FFE03FFE03FFE3IFC1IFC01LFC7IF81IF87EK03FFE03FFE07FFE7IF01IF8007KFC7FFE01IF,7EK03FFE03FFC07FFC7FFE01IF8I0KFCIFC03IF,3EK03FFE07FFC07FFC7FFE01IF8J07IFCIFC03IF,3EK03FFE07FFC07FFCIFE03IF801400IFCIFC03IF,1EK03FFC07FFC07FFCIFE03IF0FFC007FFDIFC07IF,0FK03FFC07FFC07FFCIFE07IF1FFE007FFDIFC0JF,0FK07FFC07FFC0IFCJF1JF1IFC0IF8IFE3IFE,078J07FFC07FFC0IFCOF0NF8NFE,038J07FFC07FF80IF87NF07MF0NFE,01CJ07FFC0IF80IF87JFDIF07MF07MFE,00EJ07FFC0IF80IF83JF9IF03LFE07JFBFFE,006J07FF80IF80IF81JF3IF01LFC03IFE3FFE,001J0IF80IF01IF80IFE3IF007KF001IFE3FFE,I0CI0IF80IF80IF007FFC1IFI0JFCI07FF83FFE,I06X01FFN03FFEJ03FE,,:^FS\n" +
        "^FO65,100^A0N,20,23^FDMonroe Americana^FS\n" +
        "^FO310,50^A0N,35,35^FDPed:^FS\n" +
        "^FO390,50^A0N,35,35^FD125@#^FS\n" +
        "^FO590,50^A0N,35,35^FD04/11/2020^FS\n" +
        "^FO40,150^A0N,30,33^FDBul: 001 Fmbx.=200692  Doc.=FC 1116-05939381^FS\n" +
        "^FO80,210^A0N,20,20^FDEntrega^FS\n" +
        "^FO240,210^A0N,20,20^FDRadio^FS\n" +
        "^FO440,210^A0N,20,20^FDSalida^FS\n" +
        "^FO640,210^A0N,20,20^FDOrden^FS\n" +
        "^FO60,235^A0N,90,100^FD01  A5 13:20  22^FS\n" +
        "^FO40,340^A0N,28,33^FDFCIA ANTIGUA POZUELO SCS^FS\n" +
        "^FO50,375^A0N,25,25^FDAv. Mitre 2471^FS\n" +
        "^FO50,400^A0N,25,25^FDAvellaneda^FS\n" +
        "^FO420,400^A0N,25,25^FD(2676901)^FS\n" +
        "^FO40,445^A0N,45,50^FDDESP.COPIA 734-D^FS\n" +
        "^FO600, 340^BXN,12,200,,,1,^FD147896325^FS\n" +
        "^FO570, 365^A0B,25,25^FD147896325 ^FS\n" +
        "^XZ"
