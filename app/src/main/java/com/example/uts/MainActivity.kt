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

        adapter = NameAdapter(listOf(), viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.allNames.observe(this) { names ->
            adapter.updateList(names)
        }

        viewModel.successMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}