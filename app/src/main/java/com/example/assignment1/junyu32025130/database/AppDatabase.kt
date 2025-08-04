package com.example.assignment1.junyu32025130.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.assignment1.junyu32025130.dao.FoodIntakeDao
import com.example.assignment1.junyu32025130.dao.NutriCoachTipsDao
import com.example.assignment1.junyu32025130.dao.PatientDao
import com.example.assignment1.junyu32025130.entity.Patient
import com.example.assignment1.junyu32025130.entity.FoodIntake
import com.example.assignment1.junyu32025130.entity.NutriCoachTips


@Database(entities = [Patient::class, FoodIntake::class, NutriCoachTips::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PatientDao(): PatientDao
    abstract fun FoodIntakeDao(): FoodIntakeDao
    abstract fun NutriCoachTipsDao(): NutriCoachTipsDao

    companion object {
        @Volatile
        private var Instance : AppDatabase? = null

        fun getDatabase(context : Context) : AppDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,AppDatabase::class.java,"app_database")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3,MIGRATION_3_4)
                    .build().also { Instance = it }

            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the new column with a default value or null allowed
                database.execSQL("ALTER TABLE patients ADD COLUMN password TEXT")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table for NutriCoachTips
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS `nutri_coach_tips` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `prompt` TEXT NOT NULL,
                `response` TEXT NOT NULL,
                `timestamp` INTEGER NOT NULL
            )
        """.trimIndent())
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. Create new table with userId column and foreign key
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS `nutri_coach_tips_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `userId` INTEGER NOT NULL,
                `prompt` TEXT NOT NULL,
                `response` TEXT NOT NULL,
                `timestamp` INTEGER NOT NULL,
                FOREIGN KEY(`userId`) REFERENCES `patients`(`userId`) ON DELETE CASCADE
            )
        """.trimIndent())


                // 3. Drop old table
                database.execSQL("DROP TABLE nutri_coach_tips")

                // 4. Rename new table to original name
                database.execSQL("ALTER TABLE nutri_coach_tips_new RENAME TO nutri_coach_tips")
            }
        }
    }


}
