package com.nyx.onlineshoptesttask.navigation

open class BaseScreen {
    val name = this::class.qualifiedName!!
}

object NavigationTree {
    sealed class Root : BaseScreen() {
        object Registration : Root()

        sealed class Dashboard : BaseScreen() {

            sealed class Main : Dashboard() {
                object Home : Main()
            }

            sealed class Catalog : BaseScreen() {
                object ProductsCatalog : Catalog()
                object ProductCard : Catalog()
            }

            object Cart : BaseScreen()
            object Stocks : BaseScreen()


            sealed class Profile : BaseScreen() {
                object UserProfile : Profile()
                object Favourite : Profile()
            }
        }
    }
}