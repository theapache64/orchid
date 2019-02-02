package com.tp.theah64.orchidexample.di.modules

import com.tp.theah64.orchid.di.modules.BaseNetworkModule
import com.tp.theah64.orchidexample.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [BaseNetworkModule::class])
class NetworkModule {

    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }


}