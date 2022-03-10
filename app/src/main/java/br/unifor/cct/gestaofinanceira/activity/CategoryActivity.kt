package br.unifor.cct.gestaofinanceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unifor.cct.gestaofinanceira.R
import br.unifor.cct.gestaofinanceira.adapter.CategoryAdapter
import br.unifor.cct.gestaofinanceira.model.Category
import br.unifor.cct.gestaofinanceira.util.CategoryListener
import br.unifor.cct.gestaofinanceira.util.Database
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity(), View.OnClickListener, CategoryListener {

    private var userId = -1
    private var userEmail = ""

    private lateinit var mCategoryList: RecyclerView
    private lateinit var mCategoryAdd: FloatingActionButton
    private lateinit var categories:List<Category>

    private val handler = Handler(Looper.getMainLooper())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        userId = intent.getIntExtra("user_id", -1)
        userEmail = intent.getStringExtra("user_email").toString()

        mCategoryList = findViewById(R.id.category_recyclerview_category)
        mCategoryList.layoutManager = LinearLayoutManager(applicationContext)

        mCategoryAdd = findViewById(R.id.category_floatbutton_add)
        mCategoryAdd.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            categories = Database
                .getInstance(applicationContext)
                .getCategoryDao()
                .findAll()
            handler.post {
                val adapter = CategoryAdapter(categories)
                adapter.setOnCategoryListener(this@CategoryActivity)

                mCategoryList.adapter = adapter
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.category_floatbutton_add -> {
                val it = Intent(applicationContext, CategoryFormActivity::class.java)
                it.putExtra("user_id", userId)
                it.putExtra("user_email", userEmail)
                startActivity(it)
            }
        }
    }

    override fun onCategoryItemClick(view: View, position: Int) {
        val it = Intent(applicationContext, CategoryFormActivity::class.java)
        it.putExtra("user_id", userId)
        it.putExtra("user_email", userEmail)
        it.putExtra("category_id", categories[position].id)
        startActivity(it)
    }

    override fun onCategoryItemLongClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }
}