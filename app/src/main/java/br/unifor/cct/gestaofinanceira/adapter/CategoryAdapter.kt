package br.unifor.cct.gestaofinanceira.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.unifor.cct.gestaofinanceira.R
import br.unifor.cct.gestaofinanceira.model.Category
import br.unifor.cct.gestaofinanceira.util.CategoryListener

class CategoryAdapter(val categories:List<Category>):
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var listener:CategoryListener? = null

    class CategoryViewHolder(item: View, listener: CategoryListener?):RecyclerView.ViewHolder(item) {
        val mName: TextView = item.findViewById(R.id.item_category_textview_name)
        val mValue: TextView = item.findViewById(R.id.item_category_textview_value)

        init {
            item.setOnClickListener {
                listener?.onCategoryItemClick(it, adapterPosition)
            }
            item.setOnLongClickListener {
                listener?.onCategoryItemLongClick(it, adapterPosition)
                true

            }
        }
    }

    fun setOnCategoryListener(listener: CategoryListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)

        return CategoryViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.mName.text = categories[position].name
        holder.mValue.text = categories[position].value

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}