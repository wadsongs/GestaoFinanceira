package br.unifor.cct.gestaofinanceira.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.unifor.cct.gestaofinanceira.R
import br.unifor.cct.gestaofinanceira.model.Category
import br.unifor.cct.gestaofinanceira.util.Database
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryFormActivity : AppCompatActivity(), View.OnClickListener {

    private var userId = -1
    private var userEmail = ""
    private var categoryId = -1

    private lateinit var mCategoryFormTitle: TextView
    private lateinit var mCategoryFormName: EditText
    private lateinit var mCategoryFormValue: EditText
    private lateinit var mCategoryFormDescription: EditText
    private lateinit var mCategoryFormButton: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_form)

        userId = intent.getIntExtra("user_id", -1)
        categoryId = intent.getIntExtra("category_id", -1)
        userEmail = intent.getStringExtra("user_email").toString()

        mCategoryFormTitle = findViewById(R.id.categoryform_edittext_title)
        if (categoryId == -1) {
            mCategoryFormTitle.text = "Nova Categoria"
        } else {
            GlobalScope.launch {

                val category = Database
                    .getInstance(applicationContext)
                    .getCategoryDao()
                    .find(categoryId)

                handler.post {
                    mCategoryFormTitle.text = "Editar Categoria"
                    mCategoryFormName.text = SpannableStringBuilder(category.name)
                    mCategoryFormValue.text = SpannableStringBuilder(category.value)
                    mCategoryFormButton.text = "Atualizar"
                }
            }
            mCategoryFormTitle.text = "Editar Categoria"
        }

        mCategoryFormName = findViewById(R.id.categoryform_edittext_name)

        mCategoryFormValue = findViewById(R.id.categoryform_edittext_value)

        mCategoryFormDescription = findViewById(R.id.categoryform_edittext_description)

        mCategoryFormButton = findViewById(R.id.categoryform_button)
        mCategoryFormButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.categoryform_button -> {
                var isFormFilled = true

                if(mCategoryFormName.text.isEmpty()) {
                    mCategoryFormName.error = "Campo obrigatório"
                    isFormFilled = false
                }

                if(isFormFilled) {
                    val name = mCategoryFormName.text.toString()
                    val value = mCategoryFormValue.text.toString()
                    val description = mCategoryFormDescription.text.toString()

                    GlobalScope.launch {

                        if(categoryId == -1) {
                            val newCategory = Category(
                                name = name,
                                value ="R$" +value,
                                description = description,
                                userId = userId)

                            Database
                                .getInstance(applicationContext)
                                .getCategoryDao()
                                .insert(newCategory)

                            handler.post {
                                Toast
                                    .makeText(
                                        applicationContext,
                                        "Categoria $name cadastrada com sucesso",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                finish()
                            }
                        } else {
                            val newCategory = Category(
                                id = categoryId,
                                name = name,
                                value ="R$" +value,
                                description = description,
                                userId = userId)

                            Database
                                .getInstance(applicationContext)
                                .getCategoryDao()
                                .update(newCategory)

                            handler.post {
                                Toast
                                    .makeText(
                                        applicationContext,
                                        "Categoria $name atualizada com sucesso",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}