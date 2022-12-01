package com.ozcanalasalvar.moviesapp.util

import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.TestOnly

/**
 * Utility for reporting app connectivity status
 */
interface NetworkMonitor {
    val isOnline: Flow<Boolean>

    @TestOnly
    fun setConnected(isConnected: Boolean) {

    }
}