package com.example.tema1app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tema1app.databinding.ActivityMainBinding
import android.os.Build
import java.util.Calendar
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CALL_PERMISSION = 1
    private var pendingPhoneNumber: String? = null // Número pendiente de llamada, en caso de falta de permisos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón 1 -->  Abrir otra Activity
        binding.phoneButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // Botón 2 --> Abrir navegador Chrome
        binding.navButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://www.youtube.com/watch?v=vJapzH_46a8&pp=ygUUY3Vyc28ga290bGluIGRlc2RlIDA%3D")
                setPackage("com.android.chrome")
            }
            startActivity(intent)
        }

        // Botón 3 --> Crear una alarma que suene en dos minutos
        binding.mapButton.setOnClickListener {

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 2)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)

            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma de prueba")
                putExtra(AlarmClock.EXTRA_HOUR, hour)
                putExtra(AlarmClock.EXTRA_MINUTES, minutes)

            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No se encontró ninguna aplicación de reloj", Toast.LENGTH_SHORT).show()
            }
        }


        // Botón 4 --> Mandar correo con Gmail
        binding.appButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("luisjerezz04@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "APP TEMA 1")
                putExtra(Intent.EXTRA_TEXT, "Prueba completada")
                setPackage("com.google.android.gm")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Gmail no está instalado en este dispositivo", Toast.LENGTH_SHORT).show()
            }
        }

    }




}
