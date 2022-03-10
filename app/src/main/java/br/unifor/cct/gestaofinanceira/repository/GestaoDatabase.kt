package br.unifor.cct.gestaofinanceira.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import br.unifor.cct.gestaofinanceira.model.Category
import br.unifor.cct.gestaofinanceira.model.Task
import br.unifor.cct.gestaofinanceira.model.User

@Database(entities = [User::class, Task::class, Category::class], version = 30)
abstract class GestaoDatabase: RoomDatabase() {

    abstract fun getUserDao():UserDAO

    abstract fun getTaskDao():TaskDAO

    abstract fun getCategoryDao():CategoryDAO



}