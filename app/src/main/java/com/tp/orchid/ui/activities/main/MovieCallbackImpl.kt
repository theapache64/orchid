package com.tp.orchid.ui.activities.main

import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.tp.orchid.R
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.ui.activities.movie.MovieActivity
import com.tp.orchid.ui.adapters.MoviesAdapter

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