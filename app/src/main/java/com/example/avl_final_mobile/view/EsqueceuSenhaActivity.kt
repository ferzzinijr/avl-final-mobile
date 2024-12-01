package com.example.avl_final_mobile.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avl_final_mobile.R
import com.example.avl_final_mobile.controller.AutenticacaoController
import com.example.avl_final_mobile.databinding.ActivityEsqueceuSenhaBinding

class EsqueceuSenhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEsqueceuSenhaBinding
    private lateinit var ctrl: AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ctrl = AutenticacaoController()

        binding.btnEnviar.setOnClickListener {
            val email = binding.txtEmail.text.toString()

            ctrl.esqueceuSenha(email) {sucesso, erro ->
                if (sucesso) {
                    Toast.makeText(this, "Email enviado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "ERRO: $erro", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}