package br.unifor.cct.gestaofinanceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.unifor.cct.gestaofinanceira.R
import br.unifor.cct.gestaofinanceira.util.Database
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mEmailLogin: EditText
    private lateinit var mPasswordLogin: EditText
    private lateinit var mRegisterLogin: TextView
    private lateinit var mSignInButton: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmailLogin = findViewById(R.id.login_email)
        mPasswordLogin = findViewById(R.id.login_password)

        mRegisterLogin = findViewById(R.id.login_register)
        mRegisterLogin.setOnClickListener(this)

        mSignInButton = findViewById(R.id.login_button)
        mSignInButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.login_register -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)
            }

            R.id.login_button -> {

                var isFormFilled = true

                if (mEmailLogin.text.isEmpty()) {
                    isFormFilled = formError(mEmailLogin, R.string.login_form_error)
                }
                if (mPasswordLogin.text.isEmpty()) {
                    isFormFilled = formError(mPasswordLogin, R.string.login_form_error)
                }

                if (isFormFilled) {
                    val email = mEmailLogin.text.toString()
                    val password = mPasswordLogin.text.toString()

                    GlobalScope.launch {
                        val user = Database
                            .getInstance(applicationContext)
                            .getUserDao()
                            .findByEmail(email)

                        if (user != null && user.password == password) {

                            handler.post {
                                val it = Intent(applicationContext, MainActivity::class.java)
                                startActivity(it)
                                it.putExtra("user_id", user.id)
                                //it.putExtra("user_email", user.email)
                                finish()
                            }
                        } else {

                            handler.post {
                                Toast.makeText(
                                    applicationContext,
                                    "Usuário ou senha inválidos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun formError(editText: EditText, stringId: Int): Boolean {
        editText.error = resources.getString(stringId)
        return false

    }

}