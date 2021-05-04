package com.example.todolist.di

import com.example.todolist.view.MainActivity
import com.example.todolist.view.TaskListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindFragment(): TaskListFragment

}