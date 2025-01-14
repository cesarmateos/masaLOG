package com.example.masalog

import android.Manifest.permission.BLUETOOTH
import android.Manifest.permission.BLUETOOTH_ADVERTISE
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.app.Activity.RESULT_OK
import android.bluetooth.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import java.io.OutputStream
import java.util.*
import java.io.IOException
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*


private const val TAG = "MainActivity"

object BTHandler {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothSocket: BluetoothSocket
    private lateinit var mainActivity: MainActivity

    private var btDevice : BluetoothDevice? = null
    private var dispositivosEmparejados: MutableList<itemListaBluetooth> = mutableListOf()


    //Variable de estado que accederá la UI
    val estadoBT = MutableLiveData(EstadoDispositivo.DESCONECTADO)
    val alerta = MutableLiveData(false)

    private var outputStream: OutputStream? = null

    fun iniciar(activity: MainActivity){
        lateinit var takeResultLauncher :ActivityResultLauncher<Intent>
        mainActivity = activity
        bluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
/*
        if (!(bluetoothAdapter.isEnabled)) {
            estadoBT.postValue(EstadoDispositivo.BTAPAGADO)
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(mainActivity,enableBtIntent, 2,null)
        }
 */

        //Verifico y pido permisos
        val takePermission = mainActivity.registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it)
            {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                takeResultLauncher.launch(intent)
            }
        }
        takeResultLauncher = mainActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback{
                result->
                if(result.resultCode == RESULT_OK){
                    estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
                }

            })

        pedirPermisos()

        if (!(bluetoothAdapter.isEnabled)) {
            estadoBT.postValue(EstadoDispositivo.BTAPAGADO)
            takePermission.launch(android.Manifest.permission.BLUETOOTH_CONNECT)
        }

    }

    fun pedirPermisos(){
        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mainActivity, arrayOf(BLUETOOTH_SCAN, BLUETOOTH_CONNECT, BLUETOOTH,
                    BLUETOOTH_ADVERTISE), 1)
            return
        }
    }

    fun obtenerListaDispositivos(){
        dispositivosEmparejados.clear()

        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            pairedDevices = bluetoothAdapter.bondedDevices as Set<BluetoothDevice>
            pairedDevices.forEachIndexed  { index, device ->
                if(device.bluetoothClass.deviceClass == 1664){

                    dispositivosEmparejados.add(itemListaBluetooth(device.name,index))
                }

            }
        }


        dispositivosEmparejados.sortBy { it.nombre }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun conectar(posicion: Int){
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

    private suspend fun connect(device:BluetoothDevice): OutputStream? {
        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return withContext(Dispatchers.IO) {
                val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                var outputStream: OutputStream? = null

                if (bluetoothAdapter.isEnabled) {
                    try {
                        bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                        bluetoothAdapter!!.cancelDiscovery()
                        bluetoothSocket.connect()
                        if (bluetoothSocket.isConnected) {
                            outputStream = bluetoothSocket.outputStream
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "connect: ${e.message}")
                        estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
                    }
                }
                outputStream
            }
        }
        return null
    }

    fun desconectar(){

        outputStream = null

        try {
            outputStream?.close()
        } catch (e: IOException) {
            //No atajo la excepción
        }

        try {
            bluetoothSocket.close()
            estadoBT.postValue(EstadoDispositivo.DESCONECTADO)
        } catch (e: IOException) {
            //No atajo la excepción
        }
            //bluetoothSocket = null
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

        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (estadoBT.value == EstadoDispositivo.CONECTADO || estadoBT.value == EstadoDispositivo.CONECTANDO) {
                return btDevice?.name
            }
        }
       return ""
    }

    fun imprimir(datos: String){

        if (estadoBT.value == EstadoDispositivo.CONECTADO && evaluoConexion()) {
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
