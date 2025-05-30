package com.example.uts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NameViewModel(application: Application) : AndroidViewModel(application) {
    // Mendapatkan instance DAO dari database
    private val dao = NameDatabase.getDatabase(application).nameDao()

    // LiveData untuk menyimpan list nama
    val allNames: LiveData<List<Name>> = dao.getAllNames()

    // MutableLiveData untuk input nama
    val inputName = MutableLiveData<String>()

    // MutableLiveData untuk status tombol simpan
    private val _isSaveButtonEnabled = MutableLiveData<Boolean>(false)
    val isSaveButtonEnabled: LiveData<Boolean> = _isSaveButtonEnabled

    // MutableLiveData untuk pesan sukses
    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    // Fungsi untuk validasi input
    fun validateInput() {
        val name = inputName.value?.trim() ?: ""
        _isSaveButtonEnabled.value = name.isNotEmpty()
    }

    // Fungsi untuk menyimpan nama baru
    fun saveName() {
        val name = inputName.value?.trim() ?: return
        if (name.isEmpty()) {
            _successMessage.value = "Nama tidak boleh kosong"
            return
        }
        
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    dao.insert(Name(name = name))
                }
                _successMessage.value = "Nama berhasil disimpan"
                inputName.value = ""
                _isSaveButtonEnabled.value = false
            } catch (e: Exception) {
                _successMessage.value = "Error: ${e.message}"
            }
        }
    }

    // Fungsi untuk menghapus nama
    fun deleteName(name: Name) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    dao.delete(name)
                }
                _successMessage.value = "Nama berhasil dihapus"
            } catch (e: Exception) {
                _successMessage.value = "Error: ${e.message}"
            }
        }
    }
}