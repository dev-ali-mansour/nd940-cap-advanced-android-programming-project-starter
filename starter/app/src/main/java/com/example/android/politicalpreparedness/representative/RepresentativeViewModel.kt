package com.example.android.politicalpreparedness.representative

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel : ViewModel(), Observable {

    val line1 = MutableLiveData<String>()
    val line2 = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MutableLiveData<String>()
    val zip = MutableLiveData<String>()

    private val _representatives = MutableLiveData<List<Representative>>()
    val representative: LiveData<List<Representative>>
        get() = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    init {
        _address.value = Address(/*"", "", "", "", ""*/)
    }

    fun getRepresentatives() {
        address.value?.let {
            viewModelScope.launch {
                runCatching {
                    val (offices, officials) = CivicsApi.retrofitService
                        .getRepresentativesAsync(it.toFormattedString())
                    _representatives.postValue(offices.flatMap { office ->
                        office.getRepresentatives(officials)
                    })
                }.onFailure { it.printStackTrace() }
            }
        }
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    fun setAddress(address: Address) {
        _address.value = address
    }

    fun setAddress(line1: String, line2: String?, city: String, state: String, zip: String) {
        _address.value = Address(/*line1, line2, city, state, zip*/)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}
