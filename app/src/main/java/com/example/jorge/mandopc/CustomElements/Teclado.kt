package com.example.jorge.mandopc.CustomElements

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import com.example.jorge.mandopc.Utilities.enviar
import com.example.jorge.mandopc.Utilities.ip

class Teclado : android.support.v7.widget.AppCompatEditText {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        return TecladoInputConnection(super.onCreateInputConnection(outAttrs),
                true)
    }

    private inner class TecladoInputConnection(target: InputConnection, mutable: Boolean) : InputConnectionWrapper(target, mutable) {

        override fun sendKeyEvent(event: KeyEvent): Boolean {
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                enviar("Borrar", ip)
            } else if(event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL){
                enviar("Enter", ip)
            }
            return super.sendKeyEvent(event)
        }

        override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
            // In latest Android, deleteSurroundingText(1, 0) will be called for backspace
            return if (beforeLength == 1 && afterLength == 0) {
                // backspace
                sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
            } else super.deleteSurroundingText(beforeLength, afterLength)

        }
    }


}