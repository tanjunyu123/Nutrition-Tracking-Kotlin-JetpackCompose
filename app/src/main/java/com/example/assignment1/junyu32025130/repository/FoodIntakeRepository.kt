import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Room
import com.example.assignment1.junyu32025130.dao.FoodIntakeDao
import com.example.assignment1.junyu32025130.dao.PatientDao
import com.example.assignment1.junyu32025130.database.AppDatabase
import com.example.assignment1.junyu32025130.entity.FoodIntake
import kotlinx.coroutines.flow.Flow

class FoodIntakeRepository {
    private var foodIntakeDao: FoodIntakeDao


    constructor(context : Context){
        foodIntakeDao = AppDatabase.getDatabase(context).FoodIntakeDao()
    }


    suspend fun upsertFoodIntake(foodIntake: FoodIntake) {
        Log.d("saveRepoi", "upsertFoodIntake: ")
        foodIntakeDao.upsertFoodIntake(foodIntake)
    }


    suspend fun deleteFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.deleteFoodIntake(foodIntake)
    }


    fun getFoodIntakeByUserId(userId: Int): Flow<FoodIntake?> {
        return foodIntakeDao.getFoodIntakeByUserId(userId)
    }


}
