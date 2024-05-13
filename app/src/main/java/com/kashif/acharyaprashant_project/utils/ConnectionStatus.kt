package com.kashif.acharyaprashant_project.utils

/**
 * Created by Mohammad Kashif Ansari on 11,May,2024
 */
sealed class ConnectionStatus {

    object Available:ConnectionStatus()
    object Unavailable:ConnectionStatus()
}