package com.example.masalog.etiquetaRefrigerados

import org.junit.Assert.assertEquals
import org.junit.Test

internal class PantallaEtiquetaRefrigeradosKtTest{
    val etiquetaSinModificaciones = "<STX><ESC>C<ETX>\n" +
            "<STX><ESC>P<ETX>\n" +
            "<STX>E5;F5;<ETX>\n" +
            "<STX>U1;f3;o360,35;c2;w1;h1;<ETX>\n" +
            "<STX>W2;o230,35;l70;h380;w5;<ETX>\n" +
            "<STX>W3;o230,410;l70;h380;w5;<ETX>\n" +
            "<STX>W4;o25,35;l200;h370;w5;<ETX>\n" +
            "<STX>W5;o25,420;l200;h370;w5;<ETX>\n" +
            "<STX>H6;f3;o370,390;c21;b0;k10;d3,Cadena de Frio<ETX>\n" +
            "<STX>H7;f3;o340,295;c26;b0;k10;d3,Mantener entre 2 y 8 grados C<ETX>\n" +
            "<STX>H8;f3;o285,50;c21;b0;k12;d3,Fecha:<ETX>\n" +
            "<STX>H9;f3;o285,425;c21;b0;k12;d3,Hora:<ETX>\n" +
            "<STX>H10;f3;o210,435;c26;b0;k10;d3,- Mantener lejos de<ETX>\n" +
            "<STX>H11;f3;o185,463;c26;b0;k10;d3,fuentes de calor<ETX>\n" +
            "<STX>H12;f3;o150,435;c26;b0;k10;d3,- No Congelar<ETX>\n" +
            "<STX>H13;f3;o115,435;c26;b0;k10;d3,- Fragil<ETX>\n" +
            "<STX>H14;f3;o80,435;c26;b0;k10;d3,- No abrir<ETX>\n" +
            "<STX>H15;f3;o220,50;c26;b0;k12;d3,Validez<ETX>\n" +
            "<STX>H16;f3;o185,70;c26;b0;k6;d3,(en horas)<ETX>\n" +
            "<STX>H17;f3;o145,90;c26;b0;k12;d3,6<ETX>\n" +
            "<STX>W18;o100,125;l50;h50;w5;<ETX>\n" +
            "<STX>H19;f3;o85,75;c26;b0;k12;d3,12<ETX>\n" +
            "<STX>W20;o40,125;l50;h50;w5;<ETX>\n" +
            "<STX>H21;f3;o205,250;c26;b0;k12;d3,24<ETX>\n" +
            "<STX>W22;o160,300;l50;h50;w5;<ETX>\n" +
            "<STX>H23;f3;o145,250;c26;b0;k12;d3,48<ETX>\n" +
            "<STX>W24;o100,300;l50;h50;w5;<ETX>\n" +
            "<STX>H25;f3;o85,250;c26;b0;k12;d3,72<ETX>\n" +
            "<STX>W26;o40,300;l50;h50;w5;<ETX>\n" +
            "<STX>W27;o160,35;l65;h170;w5;<ETX>\n" +
            "<STX>R<ETX>\n" +
            "<STX><ESC>E5<CAN><ETX>\n" +
            "<STX><RS>1<ETB><FF><ETX>"

    val etiquetaModificada = "<STX><ESC>C<ETX>\n" +
            "<STX><ESC>P<ETX>\n" +
            "<STX>E5;F5;<ETX>\n" +
            "<STX>U1;f3;o368,15;c2;w1;h1;<ETX>\n" +
            "<STX>W2;o238,15;l70;h380;w5;<ETX>\n" +
            "<STX>W3;o238,390;l70;h380;w5;<ETX>\n" +
            "<STX>W4;o33,15;l200;h370;w5;<ETX>\n" +
            "<STX>W5;o33,400;l200;h370;w5;<ETX>\n" +
            "<STX>H6;f3;o378,370;c21;b0;k10;d3,Cadena de Frio<ETX>\n" +
            "<STX>H7;f3;o348,275;c26;b0;k10;d3,Mantener entre 2 y 8 grados C<ETX>\n" +
            "<STX>H8;f3;o293,30;c21;b0;k12;d3,Fecha:<ETX>\n" +
            "<STX>H9;f3;o293,405;c21;b0;k12;d3,Hora:<ETX>\n" +
            "<STX>H10;f3;o218,415;c26;b0;k10;d3,- Mantener lejos de<ETX>\n" +
            "<STX>H11;f3;o193,443;c26;b0;k10;d3,fuentes de calor<ETX>\n" +
            "<STX>H12;f3;o158,415;c26;b0;k10;d3,- No Congelar<ETX>\n" +
            "<STX>H13;f3;o123,415;c26;b0;k10;d3,- Fragil<ETX>\n" +
            "<STX>H14;f3;o88,415;c26;b0;k10;d3,- No abrir<ETX>\n" +
            "<STX>H15;f3;o228,30;c26;b0;k12;d3,Validez<ETX>\n" +
            "<STX>H16;f3;o193,50;c26;b0;k6;d3,(en horas)<ETX>\n" +
            "<STX>H17;f3;o153,70;c26;b0;k12;d3,6<ETX>\n" +
            "<STX>W18;o108,105;l50;h50;w5;<ETX>\n" +
            "<STX>H19;f3;o93,55;c26;b0;k12;d3,12<ETX>\n" +
            "<STX>W20;o48,105;l50;h50;w5;<ETX>\n" +
            "<STX>H21;f3;o213,230;c26;b0;k12;d3,24<ETX>\n" +
            "<STX>W22;o168,280;l50;h50;w5;<ETX>\n" +
            "<STX>H23;f3;o153,230;c26;b0;k12;d3,48<ETX>\n" +
            "<STX>W24;o108,280;l50;h50;w5;<ETX>\n" +
            "<STX>H25;f3;o93,230;c26;b0;k12;d3,72<ETX>\n" +
            "<STX>W26;o48,280;l50;h50;w5;<ETX>\n" +
            "<STX>W27;o168,15;l65;h170;w5;<ETX>\n" +
            "<STX>R<ETX>\n" +
            "<STX><ESC>E5<CAN><ETX>\n" +
            "<STX><RS>10<ETB><FF><ETX>"

    @Test
    fun etiqueta0x0x1(){
        assertEquals(etiquetaSinModificaciones,etiquetaHeladera(0,0,1))
    }

    @Test
    fun etiquetam5x2x10(){
        assertEquals(etiquetaModificada,etiquetaHeladera(-5,2,10))
    }

}