package com.example.tema1app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tema1app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CALL_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón 1: Abrir otra Activity
        binding.phoneButton.setOnClickListener {
            makePhoneCall("777777777")
        }

        // Botón 2: Abrir navegador Chrome
        binding.navButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.youtube.com/watch?v=vJapzH_46a8&pp=ygUUY3Vyc28ga290bGluIGRlc2RlIDA%3D")
            intent.setPackage("com.android.chrome")
            startActivity(intent)
        }

        // Botón 3: Abrir Google Maps
        binding.mapButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:0,0?q=Google+Maps")
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        // Botón 4: Abrir la aplicación de la calculadora
        binding.appButton.setOnClickListener {
            val intent = Intent(android.provider.Settings.ACTION_SETTINGS)
            startActivity(intent)
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tiene el permiso, se solicita
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
        } else {
            // Si ya se tiene el permiso, se realiza la llamada
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CALL_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, se puede hacer la llamada
                    makePhoneCall("777777777")
                } else {
                    // Permiso denegado, manejar el caso (por ejemplo, mostrar un mensaje al usuario)
                }
            }
        }
    }
}
