package com.tp.orchid.ui.activities.main

import dagger.Module
import dagger.Provides
import java.util.*

@Module
class MainModule {

    @Provides
    fun provideRandom(): Random {
        return Random()
    }
}