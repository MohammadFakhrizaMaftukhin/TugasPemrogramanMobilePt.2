package com.example.uts

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao // Menandakan bahwa interface ini adalah DAO
interface NameDao {
    @Insert // Anotasi untuk operasi insert
    fun insert(name: Name)

    @Delete
    fun delete(name: Name)

    @Query("SELECT * FROM Name ORDER BY id DESC") // Query untuk mengambil semua data
    fun getAllNames(): LiveData<List<Name>>
}

