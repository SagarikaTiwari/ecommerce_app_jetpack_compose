package com.sagarikatiwari.ecommerceapp.di

import android.content.Context
import androidx.room.Room
import com.sagarikatiwari.ecommerceapp.data.repositories.ProductRepository
import com.sagarikatiwari.ecommerceapp.data.remote.ApiClient
import com.sagarikatiwari.ecommerceapp.data.repositories.ProductRepositoryImpl
import com.sagarikatiwari.ecommerceapp.data.remote.ProductService

import com.sagarikatiwari.ecommerceapp.data.repositories.WishlistRepository
import com.sagarikatiwari.ecommerceapp.data.repositories.WishlistDatabaseRepositoryImpl
import com.sagarikatiwari.ecommerceapp.data.database.AppDatabase
import com.sagarikatiwari.ecommerceapp.data.database.dao.WishListDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesProductService(): ProductService = ApiClient.getService()

    @Provides
    fun providedsProductRepositoryAPI(
        service: ProductService
    ): ProductRepositoryImpl = ProductRepositoryImpl(service)

    @Provides
    fun providesProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository = productRepositoryImpl

    @Provides
    fun providesWishlistRepository(
        databaseRepository: WishlistDatabaseRepositoryImpl
    ): WishlistRepository = databaseRepository

    @Provides
    fun providesWishlistDatabaseRepository(databaseDAO: WishListDAO): WishlistDatabaseRepositoryImpl {
        return WishlistDatabaseRepositoryImpl(databaseDAO)
    }

    @Provides
    fun providesWishListDAO(
        @ApplicationContext context: Context
    ): WishListDAO {
        val db = Room.databaseBuilder(
            context, AppDatabase::class.java,
            "ecommerce-database"
        ).build()
        return db.wishListDao()
    }


    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}