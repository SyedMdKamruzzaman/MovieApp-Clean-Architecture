package com.mahmoud.mohammed.movieapp.presentation.ui.popmovies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.mohammed.movieapp.R
import com.mahmoud.mohammed.movieapp.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

fun newMovieListFragment() = MovieListFragment()
val MOVIE_LIST_FRAGMENT_TAG = MovieListFragment::class.java.name

class MovieListFragment : BaseFragment() {

    private val viewModel: PopularMoviesViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getPopularMovies()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: PopularMoviesViewState) {
        progressBar.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.movies?.let { popularMoviesAdapter.addMovies(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = popular_movies_progress
        popularMoviesAdapter = PopularMoviesAdapter(context, { movie, view ->

          //     navigateToMovieDetailsScreen(movie)

        })
        recyclerView = popular_movies_recyclerview
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = popularMoviesAdapter
    }






}
