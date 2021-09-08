package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VoterInfoViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
            return VoterInfoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}