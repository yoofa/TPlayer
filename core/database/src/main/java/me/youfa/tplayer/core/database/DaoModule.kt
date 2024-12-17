package me.youfa.tplayer.core.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.youfa.tplayer.core.database.dao.DirectoryDao
import me.youfa.tplayer.core.database.dao.MediumDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideDirectoryDao(db: MediaDatabase): DirectoryDao = db.directoryDao()

    @Provides
    fun provideMediumDao(db: MediaDatabase): MediumDao = db.mediumDao()

}