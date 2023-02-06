package com.example.masalog

import android.bluetooth.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import java.io.OutputStream
import java.util.*
import java.io.IOException
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*


private const val TAG = "MainActivity"

object BTHandler {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothSocket: BluetoothSocket

    private var btDevice : BluetoothDevice? = null
    private var dispositivosEmparejados: MutableList<itemListaBluetooth> = mutableListOf()

    private var existeBT: Boolean = false

    //Variable de estado que accederá la UI
    val estadoBT = MutableLiveData<EstadoDispositivo>()
    val alerta = MutableLiveData(false)

    private var outputStream: OutputStream? = null

    fun iniciar(activity: MainActivity){
        bluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        existeBT = bluetoothAdapter != null
        estadoBT.postValue(EstadoDispositivo.SINHARDWARE)

        if (existeBT){
            estadoBT.postValue(EstadoDispositivo.DESCONECTADO)

            //Habilita el adaptador si está deshabilitado
            if (!(bluetoothAdapter?.isEnabled)!!) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                val REQUEST_ENABLE_BT = 1
                startActivityForResult(activity,enableBtIntent, REQUEST_ENABLE_BT,null)
                //activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
        }
    }

    //Algún día sacar esto de acá y ponerlo el la UI
    fun obtenerListaDispositivos(){
        //Carga la lista de dispositivos si no está vacía
        if (dispositivosEmparejados.isEmpty()){
            pairedDevices = bluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
            pairedDevices.forEachIndexed  { index, device ->
                if(device.bluetoothClass.deviceClass == 1664){

                    dispositivosEmparejados.add(itemListaBluetooth(device.name,index))
                }

            }
            dispositivosEmparejados.sortBy { it.nombre }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun conectar(posicion: Int){
        if(existeBT){
            estadoBT.postValue(EstadoDispositivo.CONECTANDO)

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

    private suspend fun connect(device:BluetoothDevice): OutputStream? {
        return withContext(Dispatchers.IO) {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            var outputStream: OutputStream? = null
            if (existeBT && bluetoothAdapter!!.isEnabled) {
                try {
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                    bluetoothAdapter!!.cancelDiscovery()
                    bluetoothSocket.connect()
                    if (bluetoothSocket.isConnected) {
                        outputStream = bluetoothSocket.outputStream
                    }
                } catch (e: Exception){
                    Log.d(TAG, "connect: ${e.message}")
                    estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
                }
            }
            outputStream
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
                bluetoothSocket.close()
                estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
            } catch (e: IOException) {
            }
            //bluetoothSocket = null
        }
    }

    private fun evaluoConexion() : Boolean{

        val retorno: Boolean =  bluetoothSocket.isConnected

        if (retorno){
            estadoBT.postValue(EstadoDispositivo.CONECTADO)
        } else{
            estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
        }
        return retorno
    }

    fun dispositivos(): MutableList<itemListaBluetooth>{
        return dispositivosEmparejados
    }

    fun nombreDispositivoConectado(): String?{
        if(estadoBT.value == EstadoDispositivo.CONECTADO || estadoBT.value == EstadoDispositivo.CONECTANDO ){
            return btDevice?.name
        }
       return ""
    }

    fun imprimir(datos: String){
        if (existeBT && estadoBT.value == EstadoDispositivo.CONECTADO && evaluoConexion()) {
            estadoBT.postValue(EstadoDispositivo.IMPRIMIENDO)
            try{
                outputStream?.run {
                    write(datos.toByteArray())
                    write(byteArrayOf(10))                  // Feed line
                    estadoBT.postValue(EstadoDispositivo.CONECTADO)
                }
            }catch(e: Exception){
                desconectar()
            }
        }else{
            alerta.postValue(true)
        }
    }

    fun cerrarDialogo(){
        alerta.postValue(false)
    }


    fun listando(){
        obtenerListaDispositivos()
        estadoBT.postValue(EstadoDispositivo.LISTABT)
    }

}

data class itemListaBluetooth(val nombre: String, val posicion: Int)