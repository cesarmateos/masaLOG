package com.example.masalog

import android.bluetooth.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import java.io.OutputStream
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import androidx.lifecycle.MutableLiveData


private const val TAG = "MainActivity"

object BTHandler {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private var bluetoothManager: BluetoothManager? = null
    private var bluetoothSocket: BluetoothSocket? = null

    var btDevice : BluetoothDevice? = null
    private var dispositivosEmparejados: MutableList<String> = mutableListOf()

    private var existeBT: Boolean = false

    //Variable de estado que accederá la UI
    val estadoBT = MutableLiveData<EstadoDispositivo>()
    val alerta = MutableLiveData(false)

    private var logoCargado: Boolean = false
    private var outputStream: OutputStream? = null

    fun iniciar(activity: MainActivity){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        existeBT = bluetoothAdapter != null
        estadoBT.postValue(EstadoDispositivo.SINHARDWARE)

        if (existeBT){
            estadoBT.postValue(EstadoDispositivo.DESCONECTADO)

            //Habilita el adaptador si está deshabilitado
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                val REQUEST_ENABLE_BT = 1
                activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            bluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager


            //Obtengo la lista de los dispositivos apareados
            pairedDevices = bluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
            pairedDevices.forEach { device ->
                dispositivosEmparejados.add(device.name)
            }
        }else{
            estadoBT.postValue(EstadoDispositivo.SINHARDWARE)
        }
    }

    private fun evaluoConexion() : Boolean{
        val retorno: Boolean = bluetoothSocket?.isConnected ==true

        if (retorno){
            estadoBT.postValue(EstadoDispositivo.CONECTADO)
        } else{
            estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
            logoCargado = false
        }
        return retorno
    }

    private suspend fun connect(device:BluetoothDevice): OutputStream? {
        return withContext(Dispatchers.IO) {
            var outputStream: OutputStream? = null
            if (existeBT && bluetoothAdapter!!.isEnabled) {
                try {
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(
                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                    )
                    bluetoothAdapter!!.cancelDiscovery()
                    bluetoothSocket?.connect()
                    if (bluetoothSocket!!.isConnected) {
                        outputStream = bluetoothSocket!!.outputStream
                    }
                } catch (e: Exception){
                    Log.d(TAG, "connect: ${e.message}")
                    estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
                }
            }
            outputStream
        }
    }

    fun dispositivos(): MutableList<String>{
        return dispositivosEmparejados
    }

    fun nombreDispositivoConectado(): String?{
        if(estadoBT.value == EstadoDispositivo.CONECTADO || estadoBT.value == EstadoDispositivo.CONECTANDO ){
            return btDevice?.name
        }
       return ""
    }

    fun imprimir(datos: String){
        if (existeBT && evaluoConexion()) {
            estadoBT.postValue(EstadoDispositivo.IMPRIMIENDO)
            outputStream?.run {
                write(datos.toByteArray())
                write(byteArrayOf(10))                  // Feed line
                estadoBT.postValue(EstadoDispositivo.CONECTADO)
            }
        }else{
            alerta.postValue(true)
        }
    }

    fun conectar(posicion: Int){
        if(existeBT){
            estadoBT.postValue(EstadoDispositivo.CONECTANDO)

            logoCargado = false
            btDevice = pairedDevices.elementAt(posicion)

            GlobalScope.launch (Dispatchers.Main) {
                if(outputStream == null) {
                    outputStream = connect(btDevice!!)?.also {
                        estadoBT.postValue(EstadoDispositivo.CONECTADO)
                    }
                }
            }

        }
    }

    fun desconectar(){
        if(existeBT) {
            outputStream = null

            try {
                outputStream?.close()
            } catch (e: IOException) {
            }

            try {
                bluetoothSocket?.close()
            } catch (e: IOException) {
            }

            bluetoothSocket = null
            logoCargado = false
            estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
        }
    }

    fun cerrarDialogo(){
        alerta.postValue(false)
    }

    fun mandarLogo(){
        if(!logoCargado) {
            imprimir("<STX><ESC>C<ETX>\n" +
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
                    "<STX>R<ETX>")
            logoCargado = true
        }
    }

    fun listando(){
        estadoBT.postValue(EstadoDispositivo.LISTABT)
    }

}
