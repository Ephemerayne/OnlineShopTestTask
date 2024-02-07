package com.nyx.onlineshoptesttask.navigation

open class BaseScreen {
    val name = this::class.qualifiedName!!
    fun <T> createRoute(route: String, args: T) = "${route}/$args"
}

object NavigationTree {
    sealed class Root : BaseScreen() {
        object Registration : Root()

        sealed class Dashboard : BaseScreen() {

            object Main : Dashboard()

            sealed class Catalog : BaseScreen() {
                object ProductsCatalog : Catalog()
                object ProductCard : Catalog()
            }

            object Cart : BaseScreen()
            object Stocks : BaseScreen()

            sealed class Profile : BaseScreen() {
                object UserProfile : Profile()
                object Favourite : Profile()
                object ProductCard : Profile()
            }
        }
    }
}