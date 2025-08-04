package com.example.assignment1.junyu32025130.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment1.junyu32025130.entity.FoodIntake
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodIntakeDao {

    @Insert
    suspend fun insertFoodIntake(foodIntake: FoodIntake)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFoodIntake(foodIntake: FoodIntake)

    @Delete
    suspend fun deleteFoodIntake(foodIntake: FoodIntake)

    @Query("SELECT * FROM food_intake WHERE id = :userId")
    fun getFoodIntakeByUserId(userId: Int): Flow<FoodIntake?>


}
