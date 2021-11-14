package com.websarva.android.vulnmemory_aes.route

import com.websarva.android.vulnmemory_aes.plugins.MySession
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

fun Routing.authBasic(){
    authenticate {
        // /loginで認証flowに入る
        get("/vulnMemoryAES/login"){
            val user = call.principal<MySession>() ?: error("No Principal")
            // session情報を登録
            call.sessions.set("SESSION", MySession(user.name))

            // リダイレクト
            call.respondRedirect("/vulnMemoryAES", permanent = false)
        }
    }
}