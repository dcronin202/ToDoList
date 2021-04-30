package com.example.todolist.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todolist.database.TaskDao
import com.example.todolist.database.TaskDatabase
import com.example.todolist.database.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication() : Application = application

    @Provides
    fun provideTaskDatabase(application: Application): TaskDatabase {
        return Room.databaseBuilder(
            application,
            TaskDatabase::class.java,
            "task_database")
            .build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase) = database.taskDao()

    @Provides
    fun provideTaskRepository(taskDao: TaskDao) = TaskRepository(taskDao)

}