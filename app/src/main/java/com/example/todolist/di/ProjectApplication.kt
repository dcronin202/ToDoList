package com.example.todolist.di

import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ProjectApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .appModule(ApplicationModule(this))
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = activityInjector
}