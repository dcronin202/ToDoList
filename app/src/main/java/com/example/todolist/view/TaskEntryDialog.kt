package com.example.todolist.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.todolist.R
import com.example.todolist.databinding.TaskInputDialogBinding
import com.example.todolist.viewmodel.MainActivityViewModel
import java.lang.IllegalStateException

class TaskEntryDialog : DialogFragment() {

    private lateinit var dialogViewModel: MainActivityViewModel
    private lateinit var binding: TaskInputDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.task_input_dialog,
            null, false)

        return activity?.let { activity ->
            // Get shared instance of ViewModel from the MainActivity
            val mainActivity = activity as? MainActivity
                ?: throw IllegalStateException("Dialog should belong to the main activity")
            dialogViewModel = ViewModelProviders.of(mainActivity).get(MainActivityViewModel::class.java)

            // Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(activity)

            builder.setView(binding.root)
                .setTitle("New task")
                .setPositiveButton("Add") { dialogInterface, id ->
                    //
                }
                .setNegativeButton("Cancel") { dialogInterface, id ->
                    dialogInterface.cancel()
                }

            // Create AlertDialog object and return it
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

}