package br.unifor.cct.gestaofinanceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.room.Database
import br.unifor.cct.gestaofinanceira.R
import br.unifor.cct.gestaofinanceira.util.Database.getInstance

//private val DELAY_TIME = 3000L

class SplashScreenActivity : AppCompatActivity() {

    private val DELAY_TIME = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        br.unifor.cct.gestaofinanceira.util.Database.getInstance(this)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                val it = Intent(SplashScreenActivity@this, LoginActivity::class.java)
                startActivity(it)
                finish()
            },DELAY_TIME
        )
    }
}