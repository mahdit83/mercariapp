package com.mercari.device.model

class ConnectionModel(var type: Int, var isConnected: Boolean) {
    companion object {

        val WIFI_DATA = 1
        val MOBILE_DATA = 2
    }
}
