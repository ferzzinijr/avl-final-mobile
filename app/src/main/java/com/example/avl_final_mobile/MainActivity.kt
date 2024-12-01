package com.example.avl_final_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.avl_final_mobile.controller.AutenticacaoController
import com.example.avl_final_mobile.databinding.ActivityMainBinding
import com.example.avl_final_mobile.view.CadastrarActivity
import com.example.avl_final_mobile.view.CardapioActivity
import com.example.avl_final_mobile.view.EsqueceuSenhaActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ctrl: AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val senha = binding.txtSenha.text.toString()

            if (senha.isEmpty()) {
                Toast.makeText(this, "Campo senha obrigatório", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ctrl = AutenticacaoController()
            ctrl.login(email,senha){ sucesso,erro ->
                if (sucesso) {
                    val it = Intent(this, CardapioActivity:: class.java)
                    startActivity(it)
                } else {
                    Toast.makeText(this, "ERRO: $erro", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.txtCadastrar.setOnClickListener {
            val it = Intent(this, CadastrarActivity:: class.java)
            startActivity(it)
        }

        binding.txtEsqueceuSenha.setOnClickListener {
            val it = Intent(this, EsqueceuSenhaActivity:: class.java)
            startActivity(it)
        }
    }
}