package com.nyx.common_data.repository.product

import com.nyx.common_api.common.ProgressState
import com.nyx.common_api.models.ProductEntity
import com.nyx.common_api.repository.product.ProductRepository
import com.nyx.common_data.local.product.FavouriteProductStorage
import com.nyx.common_data.remote.ProductService
import com.nyx.common_data.repository.product.room.ProductDao
import com.nyx.common_data.repository.product.room.mappers.toProductEntity
import com.nyx.common_data.repository.product.room.mappers.toRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val favouriteProductStorage: FavouriteProductStorage,
    private val service: ProductService,
) : ProductRepository {

    private suspend fun getAllProducts(): Flow<ProgressState<List<ProductEntity>>> = flow {
        emit(ProgressState.Progress)
        getRemoteData()

        dao.getProducts().collect { products ->
            if (products.isEmpty()) {
                emit(ProgressState.Failure)
            } else {
                emit(ProgressState.Success((products.map { it.toProductEntity() })))
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getRemoteData() {
        try {
            val remoteProducts = service.getProducts()

            remoteProducts.body()?.items?.let { products ->
                dao.insertProducts(products.map { it.toRoomEntity() })
            }
        } catch (e: Exception) {
            println("debug: $e")
        }
    }

    override suspend fun getProducts(): Flow<ProgressState<List<ProductEntity>>> = combine(
        flow = getAllProducts(),
        flow2 = favouriteProductStorage.getFavouriteProductIds()
    ) { state, favourites ->
        when (state) {
            is ProgressState.Success -> {
                val updatedProducts = state.value.map { product ->
                    product.copy(isFavourite = favourites.contains(product.id))
                }
                ProgressState.Success(updatedProducts)
            }

            else -> state
        }
    }

    override suspend fun getFavourite(): Flow<List<ProductEntity>> =
        combine(
            flow = getAllProducts(),
            flow2 = favouriteProductStorage.getFavouriteProductIds()
        ) { state, favourites ->
            when (state) {
                is ProgressState.Success -> {
                    state.value
                        .filter { favourites.contains(it.id) }
                        .map { it.copy(isFavourite = true) }
                }

                else -> listOf()
            }
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
    ) { state, favourites ->
        when (state) {
            is ProgressState.Success -> {
                state.value
                    .map { it.copy(isFavourite = favourites.contains(it.id)) }
                    .firstOrNull { it.id == id }
            }

            else -> null
        }
    }
}