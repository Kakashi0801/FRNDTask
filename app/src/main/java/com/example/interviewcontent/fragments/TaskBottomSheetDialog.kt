package com.example.interviewcontent.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.lifecycle.ViewModelProvider
import com.example.interviewcontent.CalanderActivity
import com.example.interviewcontent.viewmodel.CalanderViewModel
import com.example.interviewcontent.databinding.BottomsheetTaskBinding



class TaskBottomSheetDialog(
    context: Context,
    private val onTaskAdded: (String) -> Unit
) : BottomSheetDialog(context) {

    private lateinit var binding: BottomsheetTaskBinding
    private lateinit var taskViewModel: CalanderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomsheetTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(context as CalanderActivity)[CalanderViewModel::class.java]

        binding.btnDone.setOnClickListener {
            val task = binding.etTask.text.toString()
            if (task.isNotEmpty()) {
                taskViewModel.addDailyTask("2024-8-12",task)
                onTaskAdded(task)
                dismiss()
            }
        }
    }
}
