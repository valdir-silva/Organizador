package net.alunando.organizador

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_cadastro.*
import net.alunando.organizador.config.ConfiguracaFirebase
import net.alunando.organizador.model.Usuario

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
                    if(task.isSuccessful){
                        toast("Sucesso ao cadastrar usuário!")
                    } else {
                        toast("Erro ao cadastrar usuário!")
                    }
                }
            }
        }
    }

    inline fun Context.toast(message:String){
        Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
    }
}