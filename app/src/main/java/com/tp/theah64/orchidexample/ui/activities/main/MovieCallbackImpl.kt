package com.tp.theah64.orchidexample.ui.activities.main

import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.tp.theah64.orchidexample.R
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse
import com.tp.theah64.orchidexample.ui.activities.movie.MovieActivity
import com.tp.theah64.orchidexample.ui.adapters.MoviesAdapter
import com.tp.theah64.orchid.ui.activities.base.BaseAppCompatActivity

class MovieCallbackImpl(private val activity: BaseAppCompatActivity) : MoviesAdapter.Callback {

    override fun onMovieClicked(root: View, movie: SearchResponse.Movie) {
        val startIntent = MovieActivity.getStartIntent(activity, movie)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                Pair<View, String>(
                    root.findViewById(R.id.iv_poster),
                    MovieActivity.TRANSITION_NAME_POSTER
                )
            )

            activity.startActivity(
                startIntent,
                options.toBundle()
            )

        } else {
            activity.startActivity(startIntent)
        }
    }

}