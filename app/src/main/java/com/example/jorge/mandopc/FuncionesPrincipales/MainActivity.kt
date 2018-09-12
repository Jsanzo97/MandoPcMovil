package com.example.jorge.mandopc.FuncionesPrincipales

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.jorge.mandopc.BaseDeDatosLocal.IpDbHelper
import com.example.jorge.mandopc.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val INTERVALO = 2000 //2 segundos para salir
    var tiempoPrimerClick: Long = 0

    lateinit var ipDbHelper: IpDbHelper
    var ipPred: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ipDbHelper = IpDbHelper(this)

        ipPred = ipDbHelper.readIp("1")
        if(ipPred != null){
            ip.setText(ipPred)
        }
    }

    fun conectar(v: View) {
        val ip = ip.text.toString()
        if (ip == "") {
            Toast.makeText(this, "Introduzca la ip", Toast.LENGTH_SHORT).show()
        } else {
            if(predeterminada.isChecked){
                if(ip != ipPred){
                    ipDbHelper.deleteIp("1")
                }
                ipDbHelper.insertIp(ip)
            }
            val con = Intent(this, Mando::class.java)
            con.putExtra("ip", ip)
            startActivity(con)
        }
    }

    fun instrucciones(v: View) {
        val ins = Intent(this, Instrucciones::class.java)
        startActivity(ins)
    }

    fun ocultarTeclado(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show()
        }
        tiempoPrimerClick = System.currentTimeMillis()
    }
}