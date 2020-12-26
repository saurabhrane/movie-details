package me.saurabhrane.mvp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.saurabhrane.mvp.R
import me.saurabhrane.mvp.base.BaseActivity
import me.saurabhrane.mvp.data.local.Query
import me.saurabhrane.mvp.databinding.ActivityMainBinding
import me.saurabhrane.mvp.extensions.hide
import me.saurabhrane.mvp.extensions.replaceFragment
import me.saurabhrane.mvp.extensions.show
import me.saurabhrane.mvp.ui.movieslist.MoviesListFragment

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        if (savedInstanceState == null) {
            replaceFragment(
                MoviesListFragment.newInstance(),
                R.id.container
            )
        }
        mViewBinding.lifecycleOwner = this
        mViewModel.showSearchBox.observe(this, {
            if (it) {
                mViewBinding.searchBar.show()
            } else {
                mViewBinding.searchBar.hide()
            }
        })
        setUpAutoCompleteView()
        setUpSearch()
        initObservers()
    }

    /**
     * Subscribe to Search Box Observer
     */
    private fun initObservers() {
        mViewModel.queriesLiveData.observe(this, {
            refreshAdapter(it)
        })
    }

    /**
     * Refresh AutoComplete after a query is added
     */
    private fun refreshAdapter(queries: List<Query>?) {
        adapter.clear()
        queries?.forEach {
            adapter.add(it.query)
        }
        adapter.notifyDataSetChanged()
    }

    /**
     * Sets up Search bar present at the top
     */
    private fun setUpSearch() {
        mViewBinding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                mViewModel.searchQuery.value = textView.text.toString()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        mViewBinding.btClear.setOnClickListener {
            mViewBinding.etSearch.setText("")
            mViewModel.searchQuery.value = ""
        }
    }

    /**
     * Hides soft keyboard
     */
    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Sets up auto complete view with the search box
     */
    private fun setUpAutoCompleteView() {
        val arrayList = arrayListOf<String>()
        mViewModel.queriesLiveData.value?.let { queries ->
            queries.forEach {
                arrayList.add(it.query)
            }
        } ?: emptyList<String>()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            arrayList
        )
        mViewBinding.etSearch.setAdapter(adapter)
        mViewBinding.etSearch.setOnClickListener {
            (it as AutoCompleteTextView).showDropDown()
        }
        mViewBinding.etSearch.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                (v as AutoCompleteTextView).showDropDown()
            }
        }
    }

    /**
     * Return Current Activity Binding
     */
    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    /**
     * Returns Current Activity ViewModel
     */
    override val mViewModel: MainActivityViewModel
        get() = ViewModelProvider(this).get(MainActivityViewModel::class.java)
}