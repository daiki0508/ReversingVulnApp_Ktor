package com.websarva.android.vulnmemory_aes

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.websarva.android.vulnmemory_aes.plugins.configureRouting
import com.websarva.android.vulnmemory_aes.plugins.configureSecurity
import com.websarva.android.vulnmemory_aes.plugins.configureSerialization
import io.ktor.application.*

fun Application.module(){
    configureSecurity(environment)
    configureSerialization()
    configureRouting()
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)) {
    }.start(wait = true)
}
