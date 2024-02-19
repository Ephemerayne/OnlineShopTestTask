package com.nyx.common_data.repository.product

import com.nyx.common_api.models.ProductEntity
import com.nyx.common_api.repository.product.ProductRepository
import com.nyx.common_data.local.product.FavouriteProductStorage
import com.nyx.common_data.remote.ProductService
import com.nyx.common_data.repository.product.room.ProductDao
import com.nyx.common_data.repository.product.room.mappers.toProductEntity
import com.nyx.common_data.repository.product.room.mappers.toRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val favouriteProductStorage: FavouriteProductStorage,
    private val service: ProductService,
) : ProductRepository {

    private suspend fun getAllProducts(): Flow<List<ProductEntity>> = coroutineScope {
        launch(Dispatchers.IO) {
            try {
                val remoteProducts = service.getProducts()

                remoteProducts.body()?.items?.let { products ->
                    dao.insertProducts(products.map { it.toRoomEntity() })
                }
            } catch (e: Exception) {
                println("debug: $e")
            }
        }
        val cachedProducts = dao.getProducts().map { products ->
            products.map { it.toProductEntity() }
        }

        return@coroutineScope cachedProducts
    }

    override suspend fun getProducts(): Flow<List<ProductEntity>> = combine(
        flow = getAllProducts(),
        flow2 = favouriteProductStorage.getFavouriteProductIds()
    ) { products, favourites ->
        products
            .map { it.copy(isFavourite = favourites.contains(it.id)) }
    }

    override suspend fun getFavourite(): Flow<List<ProductEntity>> =
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

    override suspend fun getProduct(id: String): Flow<ProductEntity?> = combine(
        flow = getAllProducts(),
        flow2 = favouriteProductStorage.getFavouriteProductIds()
    ) { products, favourites ->
        products
            .map { it.copy(isFavourite = favourites.contains(it.id)) }
            .firstOrNull { it.id == id }
    }
}