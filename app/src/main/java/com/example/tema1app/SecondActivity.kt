package com.example.tema1app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.tema1app.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val sharedPrefs by lazy { getSharedPreferences("com.example.tema1app.PREFS", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numeroGuardado = sharedPrefs.getString("numero_telefono_guardado", "")
        binding.textViewSavedNumber.text = numeroGuardado ?: "No hay número guardado"

        binding.buttonCall.setOnClickListener {
            val numero = binding.editTextPhone.text.toString()
            if (numero.isNotBlank()) {
                guardarNumeroTelefono(numero)
                realizarLlamada(numero)
            } else if (!numeroGuardado.isNullOrBlank()) {
                // Si el EditText está vacío, usar el número guardado en las preferencias
                realizarLlamada(numeroGuardado)
            } else {
                Toast.makeText(this, "Por favor, introduce un número válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun guardarNumeroTelefono(numero: String) {
        sharedPrefs.edit().putString("numero_telefono_guardado", numero).apply()
        binding.textViewSavedNumber.text = numero
        Toast.makeText(this, "Número guardado correctamente", Toast.LENGTH_SHORT).show()
    }

    private fun realizarLlamada(numero: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$numero"))
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
        } else {
            startActivity(intent)
        }
    }


}
