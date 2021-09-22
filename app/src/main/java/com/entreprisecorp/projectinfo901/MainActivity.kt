package com.entreprisecorp.projectinfo901

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.entreprisecorp.middlewareinfo901.ComSocket
import com.entreprisecorp.middlewareinfo901.model.Com

class MainActivity : AppCompatActivity() {

    lateinit var middleware: Com
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        middleware = ComSocket()
    }
}