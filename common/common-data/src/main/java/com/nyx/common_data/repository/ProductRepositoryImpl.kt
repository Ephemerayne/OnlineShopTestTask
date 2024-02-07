package com.nyx.common_data.repository

import com.nyx.common_api.models.ProductEntity
import com.nyx.common_api.repository.ProductRepository
import com.nyx.common_data.local.FavouriteProductStorage
import com.nyx.common_data.remote.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val favouriteProductStorage: FavouriteProductStorage,
) : ProductRepository {

    private val service = ApiClient.productService

    private fun getAllProducts(): Flow<List<ProductEntity>> = flow {
        val products = service.getProducts()
        products.body()?.let { emit(it.items) }
    }

    override fun getProducts(): Flow<List<ProductEntity>> = combine(
        flow = getAllProducts(),
        flow2 = favouriteProductStorage.getFavouriteProductIds()
    ) { products, favourites ->
        products
            .map { it.copy(isFavourite = favourites.contains(it.id)) }
    }

    override fun getFavourite(): Flow<List<ProductEntity>> =
        combine(
            flow = getAllProducts(),
            flow2 = favouriteProductStorage.getFavouriteProductIds()
        ) { products, favourites ->
            products
                .filter { favourites.contains(it.id) }
                .map { it.copy(isFavourite = true) }
        }

    override fun addFavourite(id: String) {
        favouriteProductStorage.putValue(id)
    }

    override fun deleteFavourite(id: String) {
        favouriteProductStorage.deleteValue(id)
    }

    override fun getProduct(id: String): Flow<ProductEntity?> = combine(
        flow = getAllProducts(),
        flow2 = favouriteProductStorage.getFavouriteProductIds()
    ) { products, favourites ->
        products
            .map { it.copy(isFavourite = favourites.contains(it.id)) }
            .firstOrNull { it.id == id }
    }
}