package br.unifor.cct.gestaofinanceira.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.unifor.cct.gestaofinanceira.R
import br.unifor.cct.gestaofinanceira.model.User
import br.unifor.cct.gestaofinanceira.util.Database
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRegisterName: EditText
    private lateinit var mRegisterEmail: EditText
    private lateinit var mRegisterTelefone: EditText
    private lateinit var mRegisterPassword: EditText
    private lateinit var mRegisterPasswordConfirm: EditText
    private lateinit var mRegisterSignUp: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterName = findViewById(R.id.register_name)
        mRegisterEmail = findViewById(R.id.register_email)
        mRegisterTelefone = findViewById(R.id.register_telefone)
        mRegisterPassword = findViewById(R.id.register_senha)
        mRegisterPasswordConfirm = findViewById(R.id.register_confirmar_senha)

        mRegisterSignUp = findViewById(R.id.register_button)
        mRegisterSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.register_button -> {

                isFormFilled()
                if(isFormFilled()) {
                    val name = mRegisterName.text.toString()
                    val email = mRegisterEmail.text.toString()
                    val telefone = mRegisterTelefone.text.toString()
                    val password = mRegisterPassword.text.toString()
                    val passwordConfirmation = mRegisterPasswordConfirm.text.toString()


                    if (password == passwordConfirmation) {
                        val newUser = User (name =  name, email =  email, telefone = telefone, password =  password)



                        GlobalScope.launch {
                            val user = Database
                                .getInstance(applicationContext)
                                .getUserDao()
                                .findByEmail(email)

                            if(user == null) {
                                Database
                                    .getInstance(applicationContext)
                                    .getUserDao()
                                    .insert(newUser)

                                handler.post {
                                    Toast.makeText(
                                        applicationContext,
                                        "Usuário inserido com sucesso!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                }

                            } else {
                                handler.post {
                                    Toast.makeText(
                                        applicationContext,
                                        "Já existe um usuário com o email informado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    } else {
                        formError(mRegisterPassword, R.string.register_mismatch_password_error)
                    }

                }

            }

        }
    }

    private fun isFormFilled(): Boolean {
        var isFormFilled = true

        if (mRegisterName.text.isEmpty()) {
            isFormFilled = formError(mRegisterName, R.string.register_error)
        }
//        if (isFormFilled) {
//            // TODO: Cadastro de usuário
//        }
        if (mRegisterEmail.text.isEmpty()) {
            isFormFilled = formError(mRegisterEmail, R.string.register_error)
        }
        if (mRegisterTelefone.text.isEmpty()) {
            isFormFilled = formError(mRegisterTelefone, R.string.register_error)
        }
        if (mRegisterPassword.text.isEmpty()) {
            isFormFilled = formError(mRegisterPassword, R.string.register_error)
        }
        if (mRegisterPasswordConfirm.text.isEmpty()) {
            isFormFilled = formError(mRegisterPasswordConfirm, R.string.register_error)
        }

        return isFormFilled
    }
    private fun formError(editText: EditText, stringId: Int): Boolean {
        editText.error = resources.getString(stringId)
        return false
    }
}