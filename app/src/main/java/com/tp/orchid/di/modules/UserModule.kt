package com.tp.orchid.di.modules

import android.content.SharedPreferences
import com.google.gson.Gson
import com.tp.orchid.data.remote.login.LogInResponse
import dagger.Module
import dagger.Provides

@Module(includes = [PreferenceModule::class, GsonModule::class])
class UserModule {

    @Provides
    fun provideUser(sharedPreferences: SharedPreferences, gson: Gson): LogInResponse.User? {

        // getting json user string from pref
        val userJson: String? = sharedPreferences.getString(LogInResponse.User.KEY, null)
        var user: LogInResponse.User? = null;
        if (userJson != null) {
            // converting JSON to Model
            user = gson.fromJson(userJson, LogInResponse.User::class.java)
        }
        return user
    }
}

