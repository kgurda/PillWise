package com.example.pillwise.feature.login.data.di

import com.example.pillwise.feature.login.data.LoginRepository
import com.example.pillwise.feature.login.data.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginModule {

    @Binds
    internal abstract fun bindLoginRepository(impl: LoginRepositoryImpl) : LoginRepository

}