package com.example.uts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Mendeklarasikan binding untuk menghubungkan layout XML dengan kode Kotlin
    private lateinit var binding: ActivityMainBinding
    
    // Membuat instance ViewModel menggunakan delegate by viewModels
    private val viewModel: NameViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    // Mendeklarasikan adapter untuk RecyclerView
    private lateinit var adapter: NameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menginisialisasi data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Mengatur lifecycle owner untuk data binding
        binding.lifecycleOwner = this
        // Menghubungkan ViewModel dengan layout
        binding.viewModel = viewModel

        // Initialize adapter with empty list and ViewModel
        adapter = NameAdapter(listOf(), viewModel)
        // Mengatur layout manager untuk RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        // Mengatur adapter untuk RecyclerView
        binding.recyclerView.adapter = adapter

        // Observe changes in the names list
        viewModel.allNames.observe(this) { names ->
            adapter.updateList(names)
        }

        // Mengobservasi pesan sukses dari ViewModel
        viewModel.successMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}