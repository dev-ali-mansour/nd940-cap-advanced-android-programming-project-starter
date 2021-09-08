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

    @Query("SELECT * FROM election_table")
    fun getAll(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table where isCached = 1")
    fun getCached(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id=:id")
    fun findById(id: Int): LiveData<Election>

    @Query("DELETE FROM election_table where id=:id")
    fun delete(id: Int)

    @Query("DELETE FROM election_table")
    fun clearAll(id: Int)
}