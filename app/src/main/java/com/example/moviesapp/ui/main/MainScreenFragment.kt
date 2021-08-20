package com.example.moviesapp.ui.main

import android.transition.TransitionInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.data.model.list.MovieModel
import com.example.moviesapp.databinding.FragmentMainScreenBinding
import com.example.moviesapp.ui.base.BaseFragment
import com.example.moviesapp.ui.main.adapter.MovieClickListener
import com.example.moviesapp.ui.main.adapter.list.MovieListAdapter
import com.example.moviesapp.ui.main.adapter.slider.SliderAdapter
import com.example.moviesapp.utils.EndlessRecyclerViewScrollListener
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding, MainScreenViewModel>(),
    MainScreenListener, AppBarLayout.OnOffsetChangedListener, MovieClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val sliderAdapter: SliderAdapter by lazy { SliderAdapter(arrayListOf()) }

    private val listAdapter: MovieListAdapter by lazy { MovieListAdapter(arrayListOf()) }

    override fun getViewModel(): MainScreenViewModel =
        ViewModelProvider(this, viewModelFactory).get(MainScreenViewModel::class.java)

    override fun getLayoutResId(): Int = R.layout.fragment_main_screen

    override fun onInject() {
        super.onInject()
        getApplicationComponent().inject(this)
    }

    override fun init() {
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_bottom)

        mViewModel.listener = this

        binding.tabDots.setupWithViewPager(binding.slider, true)
        binding.slider.adapter = sliderAdapter
        binding.slider.startAutoScroll()


        binding.recyclerviewMovie.adapter = listAdapter
        binding.recyclerviewMovie.addOnScrollListener(scrollListener)

        postponeEnterTransition()
        binding.recyclerviewMovie.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        mViewModel.getMovies()

        binding.swipeContainer.setOnRefreshListener {
            mViewModel.getMovies()
        }


        listAdapter.setOnMovieClickListener(this)
        sliderAdapter.setOnMovieClickListener(this)

    }


    override fun onResume() {
        super.onResume()
        binding.appBar.addOnOffsetChangedListener(this)
        binding.slider.startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        binding.appBar.removeOnOffsetChangedListener(this)
        binding.slider.cancelAutoScroll()
    }

    override fun onDestroy() {
        super.onDestroy()
        //To avoid out of memory
        binding.slider.cancelAutoScroll()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        (binding.appBar.height + verticalOffset == ViewCompat.getMinimumHeight(binding.appBar)).also {
            binding.swipeContainer.isEnabled = it
        }
    }


    override fun updateSliderContent(movies: List<MovieModel>) {
        sliderAdapter.notifyDataChanges(movies)
    }

    override fun updateListContent(movies: List<MovieModel>) {
        listAdapter.notifyDataChanges(movies)
    }

    override fun insertListContent(movies: List<MovieModel>) {
        listAdapter.notifyItemInsert(movies)
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.swipeContainer.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeContainer.isRefreshing = false
    }

    override fun resetScrollSate() {
        scrollListener.resetState()
    }

    private val scrollListener by lazy {
        object :
            EndlessRecyclerViewScrollListener(binding.recyclerviewMovie.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if (mViewModel.loadMore) {
                    mViewModel.getUpComingVideos(page + 1, false)
                }
            }
        }
    }


    override fun onMovieClicked(movie: MovieModel, imageView: ImageView, textViewTitle: TextView) {
        val action = MainScreenFragmentDirections.actionMainScreenFragmentToDetailFragment(
            selectedMovie = movie.id,
            movieUrl = movie.backdrop_path
        )
        val extras = FragmentNavigatorExtras(
            imageView to imageView.transitionName,
            textViewTitle to textViewTitle.transitionName
        )

        Navigation.findNavController(this.requireView()).navigate(action, extras)
    }


}