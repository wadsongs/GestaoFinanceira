package br.unifor.cct.gestaofinanceira.util

import android.content.Context
import androidx.room.Room
import br.unifor.cct.gestaofinanceira.repository.GestaoDatabase

object Database {

    private var instance: GestaoDatabase? = null

    fun getInstance(context:Context): GestaoDatabase {

        if (instance == null) {
            instance = Room.databaseBuilder(
                context, GestaoDatabase::class.java, "gestao.db"
            )
                //.allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        return instance!!

    }
}