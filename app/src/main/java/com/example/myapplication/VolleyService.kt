package com.example.myapplication

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.reflect.Method

object VolleyService {

    var testurl2 : String? = null
    val testUrl = "https://api.dev-dtrm.site/canieat-api/get-test"

    var sttest : String? = null

    fun testVolley(context: Context, success: (Boolean) -> Unit) {

        val myJson = JSONObject()
        val requestBody = myJson.toString()
        /* myJson에 아무 데이터도 put 하지 않았기 때문에 requestBody는 "{}" 이다 */

        val testRequest = object : StringRequest(Method.GET, testurl2 , Response.Listener { response ->
            println("서버 Response 수신: $response")
            val utf8String = String(response.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8) //한글 깨짐현상 해결
            sttest = utf8String
            success(true)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "서버 Response 가져오기 실패: $error")
            success(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
            /* getBodyContextType에서는 요청에 포함할 데이터 형식을 지정한다.
             * getBody에서는 요청에 JSON이나 String이 아닌 ByteArray가 필요하므로, 타입을 변경한다. */
        }

        Volley.newRequestQueue(context).add(testRequest)
    }
}