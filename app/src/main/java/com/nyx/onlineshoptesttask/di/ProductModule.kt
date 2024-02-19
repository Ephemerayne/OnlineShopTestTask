package com.nyx.onlineshoptesttask.di

import android.content.Context
import android.content.SharedPreferences
import com.nyx.common_api.constant.Constants
import com.nyx.common_api.repository.product.ProductRepository
import com.nyx.common_api.repository.user.UserRepository
import com.nyx.common_data.local.product.FavouriteProductStorage
import com.nyx.common_data.local.user.UserStorage
import com.nyx.common_data.remote.ApiClient
import com.nyx.common_data.remote.ProductService
import com.nyx.common_data.repository.product.ProductDatabase
import com.nyx.common_data.repository.product.ProductRepositoryImpl
import com.nyx.common_data.repository.product.room.ProductDao
import com.nyx.common_data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
class ProductModule {

    @Provides
    @ViewModelScoped
    fun provideProductRepository(
        dao: ProductDao,
        storage: FavouriteProductStorage,
        service: ProductService,
    ): ProductRepository =
        ProductRepositoryImpl(dao, storage, service)

    @Provides
    @ViewModelScoped
    fun provideUserRepository(storage: UserStorage): UserRepository =
        UserRepositoryImpl(storage)
}

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
}

@Module
@InstallIn(SingletonComponent::class)
class ProductServiceModule {

    @Singleton
    @Provides
    fun provideProductService() = ApiClient.productService
}

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideProductDao(productDatabase: ProductDatabase): ProductDao =
        productDatabase.productDao()
}

@Module
@InstallIn(SingletonComponent::class)
class ProductDatabaseModule {

    @Singleton
    @Provides
    fun provideProductDatabase(@ApplicationContext appContext: Context) =
        ProductDatabase.getInstance(appContext)
}