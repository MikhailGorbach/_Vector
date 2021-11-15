package com.example.vector.di

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.local.MarkDao
import com.example.vector.domain.local.UserDao
import com.example.vector.domain.repositories.MarkRepository
import com.example.vector.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        DataBase::class.java,
        "user_table"
    ).build()

    @Provides
    @Singleton
    fun provideMarkDao(dataBase: DataBase) = dataBase.markDao()

    @Provides
    @Singleton
    fun provideUserDao(dataBase: DataBase) = dataBase.userDao()

    @Provides
    @Singleton
    fun provideMarkRepository(markDao: MarkDao) = MarkRepository(markDao)

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao) = UserRepository(userDao)
}