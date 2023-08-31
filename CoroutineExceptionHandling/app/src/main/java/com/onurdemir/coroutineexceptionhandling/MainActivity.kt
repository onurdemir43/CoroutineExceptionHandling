package com.onurdemir.coroutineexceptionhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("exception: " + throwable.localizedMessage)
        }

        /*
        lifecycleScope.launch(handler) {
            throw Exception("error")
        }

        lifecycleScope.launch(handler) {
            launch {
                throw Exception("error 2")
            }
        }

         */

        lifecycleScope.launch(handler) {
            supervisorScope {
                launch {
                    throw Exception("error")
                }
                launch {
                    delay(2000)
                    println("this is executed")
                }
            }

        }

        CoroutineScope(Dispatchers.Main + handler).launch {
            launch {
                delay(4000)
                throw Exception("error in coroutine scope")

            }
        }
    }
}