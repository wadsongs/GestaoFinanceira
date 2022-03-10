package br.unifor.cct.gestaofinanceira.repository

import androidx.room.*
import br.unifor.cct.gestaofinanceira.model.Task

@Dao
interface TaskDAO {
    @Insert
    fun insert(task: Task)
    @Update
    fun update(task: Task)
    @Delete
    fun delete(task: Task)
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun find(id: Int): Task
    @Query("SELECT * FROM tasks")
    fun findAll():List<Task>
}