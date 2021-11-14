package com.websarva.android.vulnmemory_aes.route

import com.websarva.android.vulnmemory_aes.html.inputDataAES
import com.websarva.android.vulnmemory_aes.plugins.MySession
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.html.HTML
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun Routing.vulnMemoryAESRoute(){
    route("/vulnMemoryAES"){
        get {
            getSession(this)
        }

        route("/solve"){
            get {
                // get時は受け付けない
                getSession(this)
            }
            post {
                // InputDataAESのformからのデータを受け取る
                val params = call.receiveParameters()
                val encodeSrc = params["encodeSrc"] ?: ""
                val encodeKey = params["encodeKey"] ?: ""
                val encodeIV = params["encodeIV"] ?: ""

                // パラメータがどれか1つでも空ならエラー
                if (encodeSrc.isBlank() || encodeKey.isBlank() || encodeIV.isBlank()){
                    call.respondHtml(block = inputDataAES(error = "encodeSrc or encodeKey or encodeIV were blank."))
                }else{
                    // 複合化開始
                    val respondObj = decrypt(encodeSrc, encodeKey, encodeIV)
                    // 結果をJSONで返す
                    call.respond(respondObj)
                }
            }
        }
    }
}

private suspend fun getSession(pipeline: PipelineContext<Unit, ApplicationCall>){
    with(pipeline){
        val session = call.sessions.get<MySession>()
        if (session == null){
            call.respondText("User is logged.")
        }else{
            // HTMLを表示
            call.respondHtml(block = inputDataAES())
        }
    }
}

private fun decrypt(encodeSrc: String, encodeKey: String, encodeIV: String): ResultResponse{
    val result: ResultResponse = try {
        val key = SecretKeySpec(encodeKey.toByteArray(), "AES")
        val decodeIV = Base64.getDecoder().decode(encodeIV)

        val ips = IvParameterSpec(decodeIV)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, ips)
        val decodeSrc = cipher.doFinal(Base64.getDecoder().decode(encodeSrc))

        ResultResponse(status = "OK", result = String(decodeSrc, StandardCharsets.UTF_8))
    } catch (e: Exception){
        ResultResponse(status = "Failure", result = "Failure decrypt process.")
    }

    return result
}

data class ResultResponse(
    val status: String,
    val result: String
)