import android.content.Context
import com.example.assignment1.junyu32025130.entity.Patient

fun readCsvFromAssets(context: Context, fileName: String): List<String> {
    return try {
        context.assets.open(fileName).bufferedReader().useLines { it.toList() }
    } catch (e: Exception) {
        emptyList()
    }
}

fun parseCsvToUserData(context: Context, fileName: String): List<Patient> {
    val csvData = readCsvFromAssets(context, fileName)
    println("Raw CSV Data: $csvData")
    return csvData.drop(1).map { line ->
        val fields = line.split(",")
        Patient(
            phoneNumber = fields[0],
            userId = fields[1].toInt(),
            sex = fields[2],
            heifaTotalScoreMale = fields[3].toDouble(),
            heifaTotalScoreFemale = fields[4].toDouble(),
            discretionaryHeifaScoreMale = fields[5].toDouble(),
            discretionaryHeifaScoreFemale = fields[6].toDouble(),
            discretionaryServeSize = fields[7].toDouble(),
            vegetablesHeifaScoreMale = fields[8].toDouble(),
            vegetablesHeifaScoreFemale = fields[9].toDouble(),
            vegetablesWithLegumesAllocatedServeSize = fields[10].toDouble(),
            legumesAllocatedVegetables = fields[11].toDouble(),
            vegetablesVariationsScore = fields[12].toDouble(),
            vegetablesCruciferous = fields[13].toDouble(),
            vegetablesTuberAndBulb = fields[14].toDouble(),
            vegetablesOther = fields[15].toDouble(),
            legumes = fields[16].toDouble(),
            vegetablesGreen = fields[17].toDouble(),
            vegetablesRedAndOrange = fields[18].toDouble(),
            fruitHeifaScoreMale = fields[19].toDouble(),
            fruitHeifaScoreFemale = fields[20].toDouble(),
            fruitServeSize = fields[21].toDouble(),
            fruitVariationsScore = fields[22].toDouble(),
            fruitPome = fields[23].toDouble(),
            fruitTropicalAndSubtropical = fields[24].toDouble(),
            fruitBerry = fields[25].toDouble(),
            fruitStone = fields[26].toDouble(),
            fruitCitrus = fields[27].toDouble(),
            fruitOther = fields[28].toDouble(),
            grainsAndCerealsHeifaScoreMale = fields[29].toDouble(),
            grainsAndCerealsHeifaScoreFemale = fields[30].toDouble(),
            grainsAndCerealsServeSize = fields[31].toDouble(),
            grainsAndCerealsNonWholeGrains = fields[32].toDouble(),
            wholeGrainsHeifaScoreMale = fields[33].toDouble(),
            wholeGrainsHeifaScoreFemale = fields[34].toDouble(),
            wholeGrainsServeSize = fields[35].toDouble(),
            meatAndAlternativesHeifaScoreMale = fields[36].toDouble(),
            meatAndAlternativesHeifaScoreFemale = fields[37].toDouble(),
            meatAndAlternativesWithLegumesAllocatedServeSize = fields[38].toDouble(),
            legumesAllocatedMeatAndAlternatives = fields[39].toDouble(),
            dairyAndAlternativesHeifaScoreMale = fields[40].toDouble(),
            dairyAndAlternativesHeifaScoreFemale = fields[41].toDouble(),
            dairyAndAlternativesServeSize = fields[42].toDouble(),
            sodiumHeifaScoreMale = fields[43].toDouble(),
            sodiumHeifaScoreFemale = fields[44].toDouble(),
            sodiumMgMilligrams = fields[45].toDouble(),
            alcoholHeifaScoreMale = fields[46].toDouble(),
            alcoholHeifaScoreFemale = fields[47].toDouble(),
            alcoholStandardDrinks = fields[48].toDouble(),
            waterHeifaScoreMale = fields[49].toDouble(),
            waterHeifaScoreFemale = fields[50].toDouble(),
            water = fields[51].toDouble(),
            waterTotalMl = fields[52].toDouble(),
            beverageTotalMl = fields[53].toDouble(),
            sugarHeifaScoreMale = fields[54].toDouble(),
            sugarHeifaScoreFemale = fields[55].toDouble(),
            sugar = fields[56].toDouble(),
            saturatedFatHeifaScoreMale = fields[57].toDouble(),
            saturatedFatHeifaScoreFemale = fields[58].toDouble(),
            saturatedFat = fields[59].toDouble(),
            unsaturatedFatHeifaScoreMale = fields[60].toDouble(),
            unsaturatedFatHeifaScoreFemale = fields[61].toDouble(),
            unsaturatedFatServeSize = fields[62].toDouble()
        )
    }
}

//fun getUserCsvData(context: Context, fileName: String) : Patient{
//    val users = parseCsvToUserData(context,fileName)
//
//    val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
//    val userIndex = currentUserPref.getInt("userIndex",-1)
//
//    return users[userIndex]
//}
