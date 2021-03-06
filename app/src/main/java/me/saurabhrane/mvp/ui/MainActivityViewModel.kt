package me.saurabhrane.mvp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.saurabhrane.mvp.data.local.Query
import me.saurabhrane.mvp.data.local.RecentSearchDao
import me.saurabhrane.mvp.utils.SingleLiveEvent

class MainActivityViewModel @ViewModelInject constructor(
    private val dao: RecentSearchDao
) : ViewModel() {

    val showSearchBox = MutableLiveData(false)
    val searchQuery = SingleLiveEvent<String>()

    private val _queriesLiveData = MutableLiveData<List<Query>>()

    val queriesLiveData: LiveData<List<Query>>
        get() = _queriesLiveData

    /**
     * This methods fetches all the recent search queries by the user
     */
    fun getRecentQueries() {
        viewModelScope.launch {
            dao.getAllSearchQueries().collect {
                _queriesLiveData.value = it
            }
        }
    }

    /**
     * This method inserts the Query into the DB
     * @param query is the string which consist of movie name
     */
    fun insertQuery(query: String) {
        viewModelScope.launch {
            dao.insert(
                Query(
                    query = query
                )
            )
        }
    }

    /**
     * Deletes additional queries if count is greater than 5
     */
    fun deleteQueries() {
        viewModelScope.launch {
            dao.deleteAdditionalQueries()
        }
    }
}