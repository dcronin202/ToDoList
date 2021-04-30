package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.utils.subscribeOnBackground

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var instance: TaskDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TaskDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database")
                        .fallbackToDestructiveMigration()
                        //.addCallback(roomCallback)
                        .build()
            return instance!!
        }

//        private val roomCallback = object : Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                populateDatabase(instance!!)
//            }
//        }
//
//        private fun populateDatabase(db: TaskDatabase) {
//            val taskDao = db.taskDao()
//            subscribeOnBackground {
//                taskDao.insertTask(TaskEntity(1, 0, "Task 1"))
//                taskDao.insertTask(TaskEntity(2, 0, "Task 2"))
//                taskDao.insertTask(TaskEntity(3, 0, "Task 3"))
//            }
//        }
    }

}