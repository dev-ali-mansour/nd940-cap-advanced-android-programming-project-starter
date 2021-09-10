package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.data.repository.ElectionsRepository
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class VoterInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ElectionDatabase.getInstance(application)
    private val electionsRepository = ElectionsRepository(database)
    var url = MutableLiveData<String>()
    val voterInfo = electionsRepository.voterInfo

    private val _electionId = MutableLiveData<Int>()
    val election = _electionId.switchMap { id ->
        liveData {
            emitSource(electionsRepository.getElection(id))
        }
    }

    private val _voterInfoAvailable = MutableLiveData<Boolean>()
    val voterInfoAvailable: LiveData<Boolean>
        get() = _voterInfoAvailable

    fun getElection(electionId: Int) {
        _electionId.value = electionId
    }

    fun saveElection(election: Election) {
        election.isSaved = !election.isSaved
        viewModelScope.launch {
            electionsRepository.insertElection(election)
        }
    }

    fun getVoterInfo(election: Election) {
        if (election.division.state.isNotEmpty()) {
            val address = "${election.division.country} - ${election.division.state}"
            viewModelScope.launch {
                electionsRepository.getVoterInfo(election.id, address)
            }
            _voterInfoAvailable.value = true
        } else _voterInfoAvailable.value = false
    }

    fun setUrl(url: String) {
        this.url.value = url
    }
}