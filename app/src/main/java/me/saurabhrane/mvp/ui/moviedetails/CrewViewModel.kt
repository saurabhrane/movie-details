package me.saurabhrane.mvp.ui.moviedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import me.saurabhrane.mvp.data.remote.Crew

class CrewViewModel : ViewModel() {
    val item = ObservableField<Crew>()
}