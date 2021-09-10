package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.data.repository.ElectionsRepository
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class ElectionsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ElectionDatabase.getInstance(application)
    private val repository = ElectionsRepository(database)

    private val _navigateToElection = MutableLiveData<Election?>()
    val navigateToElection: LiveData<Election?>
        get() = _navigateToElection

    val upcomingElections = repository.upcomingElections
    val savedElections = repository.savedElections

    init {
        viewModelScope.launch { repository.refreshElections() }
    }

    fun navigateToElection(election: Election) {
        _navigateToElection.value = election
    }

    fun navigateToElectionDone(){
        _navigateToElection.value = null
    }
}