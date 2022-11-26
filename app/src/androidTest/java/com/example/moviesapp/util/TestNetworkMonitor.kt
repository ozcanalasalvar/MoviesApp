package com.example.moviesapp.util

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TestNetworkMonitor @Inject constructor(): NetworkMonitor {

    private val connectivityFlow = MutableStateFlow(true)

    override val isOnline: Flow<Boolean> = connectivityFlow

    @VisibleForTesting
    override fun setConnected(isConnected: Boolean) {
        connectivityFlow.value = isConnected
    }
}