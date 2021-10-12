package com.entreprisecorp.projectinfo901

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.entreprisecorp.middlewareinfo901.ComSocket
import com.entreprisecorp.middlewareinfo901.model.Com

class MainActivity : AppCompatActivity() {

    var username : String? = null

    lateinit var middleware: Com
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        middleware = ComSocket.Builder("http://10.7.151.185:3000", this).build()
    }
}