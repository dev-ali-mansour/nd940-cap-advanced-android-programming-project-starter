package com.example.android.politicalpreparedness.network.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.android.politicalpreparedness.BR

class Address /*(
    val line1: String,
    val line2: String? = null,
    val city: String,
    val state: String,
    val zip: String
) */ : BaseObservable() {
    @get:Bindable
    var line1: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.line1)
        }

    @get:Bindable
    var line2: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.line2)
        }

    @get:Bindable
    var city: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.city)
        }

    @get:Bindable
    var state: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.line1)
        }

    @get:Bindable
    var zip: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.zip)
        }

    fun toFormattedString(): String {
        var output = line1.plus("\n")
        /*if (!line2.isNullOrEmpty()) output = output.plus(line2).plus("\n")
        output = output.plus("$city, $state $zip")*/
        return output
    }
}