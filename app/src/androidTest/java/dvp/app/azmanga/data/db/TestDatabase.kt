package dvp.app.azmanga.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import dvp.app.azmanga.data.database.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class TestDatabase {
    lateinit var db: AppDatabase

    @Before
    fun initDB() {
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDB() {
        db.close()
    }
}