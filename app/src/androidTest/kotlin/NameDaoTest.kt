package com.example.uts

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.Before
import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import kotlin.jvm.Throws
import androidx.lifecycle.LiveData
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Rule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class NameDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var nameDao: NameDao
    private lateinit var nameDatabase: NameDatabase
    private var name1 = Name(1, "Fauzan")
    private var name2 = Name(2, "Afiq")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        nameDatabase = Room.inMemoryDatabaseBuilder(context, NameDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        nameDao = nameDatabase.nameDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        nameDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertName_savesDataCorrectly() = runBlocking {
        nameDao.insert(name1)
        val allNames = nameDao.getAllNames().getOrAwaitValue()
        assertEquals(1, allNames.size)
        assertEquals(name1.name, allNames[0].name)
    }

    @Test
    @Throws(Exception::class)
    fun getAllNames_returnsAllNames() = runBlocking {
        nameDao.insert(name1)
        nameDao.insert(name2)
        val allNames = nameDao.getAllNames().getOrAwaitValue()
        assertEquals(2, allNames.size)
        assertEquals(name1.name, allNames[1].name)
        assertEquals(name2.name, allNames[0].name)
    }

    // Extension function to get LiveData value for testing
    fun <T> LiveData<T>.getOrAwaitValue(): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return data ?: throw IllegalStateException("LiveData value was never set.")
    }
}
