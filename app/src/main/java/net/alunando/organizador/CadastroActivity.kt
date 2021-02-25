package net.alunando.organizador

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_cadastro.*

import net.alunando.organizador.config.ConfiguracaFirebase
import net.alunando.organizador.model.Usuario
import java.lang.Exception


class CadastroActivity : AppCompatActivity() {

    val autenticacao = ConfiguracaFirebase.autenticacao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        buttonCadastrar.setOnClickListener(View.OnClickListener {
            val nome = editNome.text
            val email = editEmail.text
            val senha = editSenha.text

            if (!nome.isEmpty()) {
                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {
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
        })

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