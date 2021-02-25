package net.alunando.organizador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import net.alunando.organizador.config.ConfiguracaFirebase

class MainActivity : IntroActivity() {

    val autenticacao = ConfiguracaFirebase.autenticacao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        throw RuntimeException("Test Crash")

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build()
        )
    }

    override fun onStart() {
        super.onStart()
        verificarUsuarioLogado()
    }

    private fun btEntrar(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun btCadastrar(view: View) {
        startActivity(Intent(this, CadastroActivity::class.java))
    }

    private fun verificarUsuarioLogado() {
        if(autenticacao.currentUser != null){
            val intent = Intent(this, Principalctivity::class.java)
            startActivity(intent)
        }
    }
}