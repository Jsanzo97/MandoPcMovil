package com.example.jorge.mandopc.utilities

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class TimberLogger : Logger() {
    override fun display(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> Timber.d(msg)
            Level.INFO -> Timber.i(msg)
            Level.WARNING -> Timber.w(msg)
            Level.ERROR -> Timber.e(msg)
            Level.NONE -> Timber.v(msg)
        }
    }
}