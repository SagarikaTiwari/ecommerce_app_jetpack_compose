package com.sagarikatiwari.ecommerceapp.di

import android.content.Context
import androidx.room.Room
import com.sagarikatiwari.ecommerceapp.repository.CartRepository
import com.sagarikatiwari.ecommerceapp.cart.data.CartRepositorySharedPreferences
import com.sagarikatiwari.ecommerceapp.repository.ProductRepository
import com.sagarikatiwari.ecommerceapp.shared.data.api.ApiClient
import com.sagarikatiwari.ecommerceapp.shared.data.api.ProductRepositoryAPI
import com.sagarikatiwari.ecommerceapp.shared.data.api.ProductService

import com.sagarikatiwari.ecommerceapp.repository.WishlistRepository
import com.sagarikatiwari.ecommerceapp.wishlist.data.WishlistDatabaseRepository
import com.sagarikatiwari.ecommerceapp.wishlist.data.database.AppDatabase
import com.sagarikatiwari.ecommerceapp.wishlist.data.database.WishListDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesProductService(): ProductService = ApiClient.getService()

    @Provides
    fun providedsProductRepositoryAPI(
        service: ProductService
    ): ProductRepositoryAPI = ProductRepositoryAPI(service)

    @Provides
    fun providesProductRepository(
        productRepositoryAPI: ProductRepositoryAPI
    ): ProductRepository = productRepositoryAPI

    @Provides
    fun providesWishlistRepository(
        databaseRepository: WishlistDatabaseRepository
    ): WishlistRepository = databaseRepository

    @Provides
    fun providesWishlistDatabaseRepository(databaseDAO: WishListDAO): WishlistDatabaseRepository {
        return WishlistDatabaseRepository(databaseDAO)
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
    @Singleton
    fun providesCartRepository(@ApplicationContext context: Context): CartRepository {
        return CartRepositorySharedPreferences(context)
    }


    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}