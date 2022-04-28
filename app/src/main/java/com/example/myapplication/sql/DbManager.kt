package com.example.myapplication.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.model.Task
import com.example.myapplication.utils.DateUtils

class DbManager(context: Context) {
    private val dbHelp: DbHelp = DbHelp(context)
    private var database: SQLiteDatabase? = null

    fun OpenDb() {
        database = dbHelp.writableDatabase
    }

    fun insert(name: String?,  date: String?, content: String?, done: String?): Long {
        val cv = ContentValues()
        cv.put(Constraint.NAME, name)
        cv.put(Constraint.DATE, date)
        cv.put(Constraint.CONTENT, content)
        cv.put(Constraint.DONE, done)
        return database!!.insert(Constraint.TABLE_NAME, null, cv)
    }

    fun update(task: Task) {
        val cv = ContentValues()
        cv.put(Constraint.NAME, task.name)
        cv.put(Constraint.CONTENT, task.desc)
        cv.put(Constraint.DATE, DateUtils.toString(task.date, DateUtils.DATE_WITH_TIME))
        cv.put(Constraint.DONE, task.done.toString())
        database!!.update(Constraint.TABLE_NAME, cv, "id = ?", Array(1) { task.id.toString() })
    }

    fun updateState(id: Int, state: Boolean) {
        val cv = ContentValues()
        cv.put(Constraint.DONE, state.toString())
        database!!.update(Constraint.TABLE_NAME, cv, "id = ?", Array(1) { id.toString() })
    }

    fun deleteAllById(noteIds: ArrayList<Int>) {
        noteIds.forEach { id ->
            database!!.delete(
                Constraint.TABLE_NAME,
                "id = ?",
                Array(1) { id.toString() })
        }
    }

    val readAll: ArrayList<Task>
        @SuppressLint("Range", "SimpleDateFormat")
        get() {
            val tasks: ArrayList<Task> = ArrayList()
            val cursor = database!!.query(Constraint.TABLE_NAME, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(Constraint.ID))
                val name = cursor.getString(cursor.getColumnIndex(Constraint.NAME))
                val date = DateUtils.toDate(
                        cursor.getString(cursor.getColumnIndex(Constraint.DATE)),
                        DateUtils.DATE_WITH_TIME
                    )
                val desc = cursor.getString(cursor.getColumnIndex(Constraint.CONTENT))
                val done = cursor.getString(cursor.getColumnIndex(Constraint.DONE)) == "true"
                tasks.add(Task(id, name, date, desc, done))
            }
            cursor.close()
            return tasks
        }

    fun closeDb() {
        dbHelp.close()
    }
}