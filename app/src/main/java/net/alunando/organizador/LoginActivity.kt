package net.alunando.organizador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import net.alunando.organizador.config.ConfiguracaFirebase
import net.alunando.organizador.databinding.ActivityLoginBinding
import net.alunando.organizador.model.Usuario

class LoginActivity : AppCompatActivity() {

    val autenticacao = ConfiguracaFirebase.autenticacao
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val teste = findViewById<Button>(R.id.buttonAcessar)

        teste.setOnClickListener {

            val email = findViewById<EditText>(R.id.editEmailLogin).text
            val senha = findViewById<EditText>(R.id.editSenhaLogin).text

            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {
                    val usuario = Usuario()
                    usuario.email = email.toString()
                    usuario.senha = senha.toString()
                    acessar(usuario)
                } else {
                    toast("Preencha a senha!")
                }
            } else {
                toast("Preencha o email!")
            }
        }
    }

    private fun acessar(usuario: Usuario) {
        usuario.email?.let { email ->
            usuario.senha?.let { senha ->
                autenticacao.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Principalctivity::class.java)
                            startActivity(intent)
                        } else {
                            var excecao: String
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                excecao = "Email ou senha incorretos"
                            } catch (e: Exception) {
                                excecao = "Erro ao acessar: " + e.message
                                e.printStackTrace()
                            }
                            toast(excecao)
                        }
                    }
            }
        }
    }

    inline fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}