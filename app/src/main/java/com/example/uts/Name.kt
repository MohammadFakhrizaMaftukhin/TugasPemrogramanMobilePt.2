package com.example.uts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity // Menandakan bahwa class ini adalah entity untuk database
data class Name(
    @PrimaryKey(autoGenerate = true) // Menandakan primary key yang auto increment
    val id: Int = 0,
    val name: String // Field untuk menyimpan nama
)