package com.example.jorge.mandopc.Utilities

import java.io.PrintWriter
import java.net.Socket

/* VARIABLES ESTATICAS */

var ip = ""

/* FUNCIONES ESTATICAS */

fun enviar(s: String, ip: String) {
    Thread(Runnable {
        val socket = Socket(ip, 7070)
        val pw = PrintWriter(socket.getOutputStream(), true)
        pw.println(s)
    }).start()
}