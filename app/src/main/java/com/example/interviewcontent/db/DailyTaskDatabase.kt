package com.example.interviewcontent.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.interviewcontent.models.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class DailyTaskDatabase : RoomDatabase() {

    public abstract fun getCalenderTaskDao():DailyTasksDao

    companion object{

        @Volatile
        private var instance:DailyTaskDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = Companion.instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance =it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DailyTaskDatabase::class.java,
                "dailytask_db.db"
            ).build()
    }
}