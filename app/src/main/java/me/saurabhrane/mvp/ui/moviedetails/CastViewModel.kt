package me.saurabhrane.mvp.ui.moviedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import me.saurabhrane.mvp.data.remote.Cast

class CastViewModel : ViewModel() {
    val item = ObservableField<Cast>()
}