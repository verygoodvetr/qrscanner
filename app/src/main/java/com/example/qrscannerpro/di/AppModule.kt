package com.example.qrscannerpro.di

import android.content.Context
import androidx.room.Room
import com.example.qrscannerpro.data.local.AppDatabase
import com.example.qrscannerpro.data.local.ScanDao
import com.example.qrscannerpro.data.repository.ScanRepository
import com.example.qrscannerpro.data.repository.ScanRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "scans.db").build()

    @Provides
    fun provideScanDao(db: AppDatabase): ScanDao = db.scanDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindScanRepo(impl: ScanRepositoryImpl): ScanRepository
}
