package br.unifor.cct.gestaofinanceira.util

import android.view.View


interface CategoryListener {

    fun onCategoryItemClick(view:View, position:Int)

    fun onCategoryItemLongClick(view: View, position: Int)
}