package com.example.todolist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    private lateinit var binding: ActivityMainBinding


    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // DAGGER
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
    }

    //**  MENU  **//

    // INFLATE CUSTOM MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // MENU DROPDOWN OPTIONS
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear_tasks -> {
                // Do something here
                true
            }
            R.id.menu_sort_tasks -> {
                // Do something here
                true
            }
            R.id.menu_reorder_tasks -> {
                // Do something here
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}