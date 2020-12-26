package me.saurabhrane.mvp.ui.movieslist

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import me.saurabhrane.mvp.data.local.Movie

/**
 * This ViewModel is used for data binding of value in item_movie layout
 */
class MovieRowViewModel : ViewModel(){
    val item = ObservableField<Movie>()

}