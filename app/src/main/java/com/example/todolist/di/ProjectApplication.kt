package com.example.todolist.di

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ProjectApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .appModule(ApplicationModule(this))
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}