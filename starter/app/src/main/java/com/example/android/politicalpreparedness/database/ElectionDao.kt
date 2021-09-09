package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(election: Election): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(elections: List<Election>)

    @Query("SELECT * FROM elections")
    fun getAll(): LiveData<List<Election>>

    @Query("SELECT * FROM elections where isCached = 1")
    fun getCached(): LiveData<List<Election>>

    @Query("SELECT * FROM elections WHERE id=:id")
    fun findById(id: Int): LiveData<Election>

    @Query("DELETE FROM elections where id=:id")
    fun delete(id: Int)

    @Query("DELETE FROM elections")
    fun clearAll()
}