package me.saurabhrane.mvp.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.saurabhrane.mvp.R
import me.saurabhrane.mvp.data.local.Movie
import me.saurabhrane.mvp.data.repository.Resource
import me.saurabhrane.mvp.databinding.MoviesListFragmentBinding
import me.saurabhrane.mvp.extensions.hide
import me.saurabhrane.mvp.extensions.replaceFragment
import me.saurabhrane.mvp.extensions.show
import me.saurabhrane.mvp.ui.MainActivityViewModel
import me.saurabhrane.mvp.ui.moviedetails.MovieDetailsFragment
import me.saurabhrane.mvp.utils.Constants

/**
 * This Fragments consists of Movie List along with the details
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    private val viewModel: MoviesListViewModel by lazy {
        ViewModelProvider(this).get(MoviesListViewModel::class.java)
    }

    private val cachedVm: MainActivityViewModel by lazy {
        activity?.run {
            ViewModelProvider(this).get(MainActivityViewModel::class.java)
        } ?: throw IllegalArgumentException("Activity Not Found")
    }

    private lateinit var binding: MoviesListFragmentBinding

    private val adapter: MovieAdapter by lazy {
        MovieAdapter { movie: Movie, imageView: ImageView ->
            cachedVm.insertQuery(movie.originalTitle)
            cachedVm.getRecentQueries()
            cachedVm.deleteQueries()
            replaceFragment(requireActivity() as AppCompatActivity,
                MovieDetailsFragment.newInstance().apply {
                    arguments = Bundle().apply {
                        putParcelable(Constants.MOVIE, movie)
                    }
                }, R.id.container, "MovieDetailsFragment", imageView
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initObservers()
        if (savedInstanceState == null) {
            viewModel.getNowPlaying()
        }
    }

    private fun initRecyclerView() {
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rvMovies.adapter = adapter
    }

    private fun initObservers() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    showLoading(true)
                }
                Resource.Status.SUCCESS -> {
                    showLoading(false)
                    if (!resource.data.isNullOrEmpty()) {
                        adapter.submitList(resource.data.toMutableList())
                    }
                }
                Resource.Status.ERROR -> {
                    showLoading(false)
                    resource?.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        cachedVm.searchQuery.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                viewModel.getFilteredMovies(it)
            } else {
                viewModel.setEmptyFilteredList()
            }
        })

        viewModel.filteredMoviesLiveData.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            } ?: viewModel.getNowPlaying()
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        cachedVm.showSearchBox.value = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovies.adapter = null
    }
}