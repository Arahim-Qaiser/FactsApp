package com.example.funfacts.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.funfacts.data.entities.CustomFactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomFactDao {

    @Insert
    suspend fun insertFact(fact: CustomFactEntity)

    @Query("SELECT * FROM custom_facts ORDER BY id DESC")
    fun getAllFacts(): Flow<List<CustomFactEntity>>

    @Delete
    suspend fun deleteFact(fact: CustomFactEntity)

    @Update
    suspend fun updateFact(fact: CustomFactEntity)

    @Query("DELETE FROM custom_facts WHERE text = :text")
    suspend fun deleteFactByText(text: String)

}
