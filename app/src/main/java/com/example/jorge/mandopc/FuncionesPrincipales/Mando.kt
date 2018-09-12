package com.example.jorge.mandopc.FuncionesPrincipales

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mando.*
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.EditorInfo
import com.example.jorge.mandopc.R
import com.example.jorge.mandopc.Utilities.enviar
import com.example.jorge.mandopc.Utilities.ip


class Mando : AppCompatActivity() {
    private var inicioClick: Long = 0L
    private var finClick: Long = 0

    private var origenX = 0
    private var origenY = 0
    private var despX = 0
    private var despY = 0

    private var letra = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mando)

        ip = intent.getStringExtra("ip")

        when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> ocultarTeclado()
            Surface.ROTATION_90 -> teclado()
            Surface.ROTATION_180 -> ocultarTeclado()
            Surface.ROTATION_270 -> teclado()
        }
    }

    fun ocultarTeclado(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0)
    }

    fun ocultarTeclado(v: View){
        ocultarTeclado()
    }

    private fun mostrar(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
    }

    fun mostrar(v: View){
        mostrar()
    }

    fun teclado(){
        teclado2.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                enviar("Enter", ip)
                true
            } else {
                false
            }
        }

        teclado2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                letra = s.toString()
                if(letra != "")
                    enviar("Tec $letra", ip)
                teclado2.text.clear()
            }
        })

        mostrar()
    }

    fun mover (v: View){
        v.setOnTouchListener { _, motionEvent ->
            if(motionEvent.actionMasked == MotionEvent.ACTION_DOWN){
                origenX = motionEvent.x.toInt()
                origenY = motionEvent.y.toInt()
                inicioClick = System.currentTimeMillis()

            }else if(motionEvent.actionMasked == MotionEvent.ACTION_MOVE){
                despX = motionEvent.x.toInt() - origenX
                despY = motionEvent.y.toInt() - origenY
                enviar("Mov $despX,$despY", ip)

            }else if(motionEvent.actionMasked == MotionEvent.ACTION_UP){
                finClick = System.currentTimeMillis()
                enviar("Mov restart", ip)

                if(finClick - inicioClick < 100) {
                    if(motionEvent.x.toInt() > 315 && motionEvent.y.toInt() > 545){
                        enviar("ClickarD", ip)
                    }else {
                        enviar("ClickarI", ip)
                    }
                }
            }
            true
        }
    }

    fun subir(v: View){
        enviar("SubirVol", ip)
    }

    fun bajar(v: View){
        enviar("BajarVol", ip)
    }

    fun subirBrillo(v: View){
        enviar("SubirBrillo", ip)
    }

    fun bajarBrillo(v: View){
        enviar("BajarBrillo", ip)
    }


}
