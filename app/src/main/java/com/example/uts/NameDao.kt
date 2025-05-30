package com.example.uts

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NameDao {
    @Insert // Anotasi untuk operasi insert
    suspend fun insert(name: Name)

    @Delete
    suspend fun delete(name: Name)

    @Query("SELECT * FROM Name ORDER BY id DESC") // Query untuk mengambil semua data
    fun getAllNames(): LiveData<List<Name>>
}

