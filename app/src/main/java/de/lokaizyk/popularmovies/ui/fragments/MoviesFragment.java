package de.lokaizyk.popularmovies.ui.fragments;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import de.lokaizyk.popularmovies.R;
import de.lokaizyk.popularmovies.databinding.FragmentMoviesBinding;
import de.lokaizyk.popularmovies.logic.MoviesProvider;
import de.lokaizyk.popularmovies.logic.model.MovieModel;
import de.lokaizyk.popularmovies.ui.activities.MovieDetailsActivity;
import de.lokaizyk.popularmovies.ui.activities.SettingsActivity;
import de.lokaizyk.popularmovies.ui.adapter.BaseBindingAdapter;
import de.lokaizyk.popularmovies.ui.adapter.MovieAdapter;
import de.lokaizyk.popularmovies.util.PrefHelper;

/**
 * Created by lars on 12.09.16.
 */
public class MoviesFragment extends BaseBindingFragment<FragmentMoviesBinding> implements BaseBindingAdapter.OnItemClickListener<MovieModel> {

    private static final String TAG = MoviesFragment.class.getSimpleName();

    public ObservableBoolean isLoading = new ObservableBoolean(false);

    public ObservableArrayList<MovieModel> movies = new ObservableArrayList<>();

    private MoviesProvider.RequestListener<List<MovieModel>> moviesListener = new MoviesProvider.RequestListener<List<MovieModel>>() {
        @Override
        public void onSuccess(List<MovieModel> data) {
            updateMovies(data);
        }

        @Override
        public void onError(String cause) {
            Log.e(TAG, cause);
            if (isAdded()) {
                isLoading.set(false);
                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().movieGrid.setAdapter(new MovieAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movies;
    }

    @Override
    protected void onBindingInitialized() {
        getBinding().setMoviesFragment(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshMovies();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                return true;
            case R.id.action_settings:
                SettingsActivity.start(getContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshMovies() {
        if (!isLoading.get()) {
            isLoading.set(true);
            MoviesProvider.loadMovies(PrefHelper.getSortingSettings(getContext()), moviesListener);
        }
    }

    @Override
    public void onItemSelected(int position, MovieModel item) {
        MovieDetailsActivity.start(getContext(), item.getMovieId());
    }

    private void updateMovies(List<MovieModel> newMovies) {
        if (isAdded()) {
            isLoading.set(false);
            if (newMovies != null) {
                movies.clear();
                movies.addAll(newMovies);
            }
        }
    }
}
