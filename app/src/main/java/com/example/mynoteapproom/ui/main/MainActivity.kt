package com.example.mynoteapproom.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynoteapproom.R
import com.example.mynoteapproom.databinding.ActivityMainBinding
import com.example.mynoteapproom.helper.ViewModelFactory
import com.example.mynoteapproom.ui.insert.NoteAddUpdateActivity

class MainActivity : AppCompatActivity() {
    
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: NoteAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    
        val mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllNotes().observe(this, { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        })
        
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = NoteAdapter()
        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter
    
        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }
    
    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}