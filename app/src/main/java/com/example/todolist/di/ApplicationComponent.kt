package com.example.todolist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, AndroidInjectionModule::class, ActivitiesModule::class])
interface ApplicationComponent: AndroidInjector<ProjectApplication> {
    fun applicationContext(): Application

    @Component.Builder
    interface Builder {

        fun appModule(applicationModule: ApplicationModule):  Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: Application)
}