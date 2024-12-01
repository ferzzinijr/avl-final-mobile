package com.example.avl_final_mobile.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.avl_final_mobile.controller.AutenticacaoController
import com.example.avl_final_mobile.controller.UsuarioController
import com.example.avl_final_mobile.databinding.ActivityCadastrarBinding
import com.example.avl_final_mobile.model.Usuario
import java.time.LocalDate

class CadastrarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarBinding
    private lateinit var ctrl: AutenticacaoController
    private lateinit var ctrlUsuario: UsuarioController

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ctrl = AutenticacaoController()
        ctrlUsuario = UsuarioController()

        binding.btnSalvar.setOnClickListener {
            val nome = binding.txtNome.text.toString()
            val email = binding.txtEmail.text.toString()
            val senha = binding.txtSenha.text.toString()
            val confirmarSenha = binding.txtConfirmaSenha.text.toString()

            if (nome.isEmpty()) {
                Toast.makeText(this, "Campo nome obrigatório", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha.isEmpty()) {
                Toast.makeText(this, "Campo senha obrigatório", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Campo confirme a senha obrigatório", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmarSenha){
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ctrl.criarUsuario(nome, email, senha) {sucesso, erro ->
                if (sucesso) {
                    val u = Usuario()
                    u.nome = nome
                    u.email = email
                    u.data = LocalDate.now().toString()

                    ctrlUsuario.adicionar(u)

                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "ERRO: $erro", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}