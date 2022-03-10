package br.unifor.cct.gestaofinanceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import br.unifor.cct.gestaofinanceira.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var userId = -1
    private var userEmail = ""
    private lateinit var mCategoriesButton: Button
    private lateinit var mDespesaButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent.getIntExtra("user_id", -1)
        userEmail = intent.getStringExtra("user_email").toString()

        mCategoriesButton = findViewById(R.id.main_button_categories)
        mCategoriesButton.setOnClickListener(this)

        mDespesaButton = findViewById(R.id.main_button_despesa)
        mDespesaButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?. id) {
            R.id.main_button_categories -> {
                val it = Intent(applicationContext, CategoryActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email", userEmail)
                startActivity(it)
            }
            R.id.main_button_despesa -> {
                val it = Intent(applicationContext, CategoryActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email", userEmail)
                startActivity(it)
            }
        }
    }
}