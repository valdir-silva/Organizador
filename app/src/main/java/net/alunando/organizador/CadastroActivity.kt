package net.alunando.organizador

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import net.alunando.organizador.config.ConfiguracaFirebase
import net.alunando.organizador.databinding.ActivityCadastroBinding
import net.alunando.organizador.model.Usuario

class CadastroActivity : AppCompatActivity() {

    val autenticacao = ConfiguracaFirebase.autenticacao
    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        binding = ActivityCadastroBinding.inflate(layoutInflater)

        val teste = findViewById<Button>(R.id.buttonCadastrar)

        teste.setOnClickListener {
            val nome = findViewById<EditText>(R.id.editNome).text
            val email = findViewById<EditText>(R.id.editEmail).text
            val senha = findViewById<EditText>(R.id.editSenha).text

            if (nome.isNotEmpty()) {
                if (email.isNotEmpty()) {
                    if (senha.isNotEmpty()) {
                        val usuario = Usuario()
                        usuario.nome = nome.toString()
                        usuario.email = email.toString()
                        usuario.senha = senha.toString()
                        cadastrarUsuario(usuario)
                    } else {
                        toast("Preencha a senha!")
                    }
                } else {
                    toast("Preencha o email!")
                }
            } else {
                toast("Preencha o nome!")
            }
        }

    }

    private fun cadastrarUsuario(usuario: Usuario) {
        usuario.email?.let { email ->
            usuario.senha?.let { senha ->
                autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        finish()
                    } else {
                        var excecao: String
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            excecao = "Digite uma senha mais forte!"
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            excecao = "Por favor, digite um email válido"
                        } catch (e: FirebaseAuthUserCollisionException) {
                            excecao = "Esta conta já foi cadastrada"
                        } catch (e: Exception) {
                            excecao = "Erro ao cadastrar usuário: " + e.message
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