package com.example.interviewcontent

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.interviewcontent.databinding.ActivityMainBinding
import com.example.interviewcontent.db.DailyTaskDatabase
import com.example.interviewcontent.fragments.CalanderFragment
import com.example.interviewcontent.repository.CalanderRepository
import com.example.interviewcontent.util.CalanderViewModelProvider
import com.example.interviewcontent.viewmodel.CalanderViewModel
import dagger.hilt.android.AndroidEntryPoint

class CalanderActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var calanderViewModel: CalanderViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val repository = CalanderRepository(DailyTaskDatabase.invoke(this))
        val calenderViewModelFactory = CalanderViewModelProvider(repository)
        calanderViewModel = ViewModelProvider(this,calenderViewModelFactory).get(CalanderViewModel::class.java)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment= CalanderFragment()
        fragmentTransaction.add(R.id.navHostFragment, fragment) // Add fragment to container
        fragmentTransaction.commit()

    }
}