package com.tobibur.aidl_server.di

import android.app.Application
import androidx.room.Room
import com.tobibur.aidl_server.data.MenuItemDao
import com.tobibur.aidl_server.data.MenuItemDatabase
import com.tobibur.aidl_server.data.MenuItemRepositoryImpl
import com.tobibur.aidl_server.domain.repository.MenuItemRepository
import com.tobibur.aidl_server.domain.usecase.AddMenuItemUseCase
import com.tobibur.aidl_server.domain.usecase.DeleteMenuItemUseCase
import com.tobibur.aidl_server.domain.usecase.GetMenuItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MenuItemDatabase =
        Room.databaseBuilder(app, MenuItemDatabase::class.java, "menu_item_db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideMenuItemDao(db: MenuItemDatabase): MenuItemDao = db.menuItemDao()

    @Provides
    fun provideMenuItemRepository(dao: MenuItemDao): MenuItemRepository =
        MenuItemRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providesGetMenuItemUseCase(repository: MenuItemRepository): GetMenuItemsUseCase {
        return GetMenuItemsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesAddMenuItemUseCase(repository: MenuItemRepository): AddMenuItemUseCase {
        return AddMenuItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDeleteMenuItemUseCase(repository: MenuItemRepository): DeleteMenuItemUseCase {
        return DeleteMenuItemUseCase(repository)
    }
}