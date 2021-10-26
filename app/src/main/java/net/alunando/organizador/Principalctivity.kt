package net.alunando.organizador

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.clans.fab.FloatingActionButton

class Principalctivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principalctivity)
        setSupportActionBar(findViewById(R.id.toolbar))

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        findViewById<FloatingActionButton>(R.id.menu_receita).setOnClickListener {
            adicionarReceita()
        }
        findViewById<FloatingActionButton>(R.id.menu_despesa).setOnClickListener {
            adicionarDespesa()
        }
    }

    private fun adicionarDespesa() {
        startActivity(Intent(this, DespesasActivity::class.java))
    }

    private fun adicionarReceita() {
        startActivity(Intent(this, ReceitasActivity::class.java))
    }
}