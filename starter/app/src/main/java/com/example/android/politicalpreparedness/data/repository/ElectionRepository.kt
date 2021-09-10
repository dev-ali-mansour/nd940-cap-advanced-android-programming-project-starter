package com.example.android.politicalpreparedness.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(private val database: ElectionDatabase) {

    val upcomingElections: LiveData<List<Election>> = database.electionDao.getAll()
    val voterInfo = MutableLiveData<VoterInfoResponse>()


    fun getElection(id: Int) = database.electionDao.findById(id)

    suspend fun getVoterInfo(electionId: Int, address: String) {
        try {
            withContext(Dispatchers.IO) {
                val response = CivicsApi.retrofitService.getVoterInfoAsync(electionId, address)
                voterInfo.postValue(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insertElection(election: Election) {
        Log.i("election", election.isSaved.toString())
        withContext(Dispatchers.IO) {
            database.electionDao.insert(election)
        }
    }

    suspend fun refreshElections() {
        try {
            withContext(Dispatchers.IO) {
                val electionResponse = CivicsApi.retrofitService.getElectionsAsync()
                database.electionDao.insertList(electionResponse.elections)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val savedElections: LiveData<List<Election>> = database.electionDao.getCached()
}