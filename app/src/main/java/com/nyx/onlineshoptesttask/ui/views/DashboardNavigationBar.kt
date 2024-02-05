package com.nyx.onlineshoptesttask.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.nyx.catalog_compose.CatalogScreen
import com.nyx.common.utils.toStable
import com.nyx.dashboard_compose.views.StubView
import com.nyx.favourites_compose.screens.FavouriteProductsScreen
import com.nyx.onlineshoptesttask.navigation.NavigationTree
import com.nyx.onlineshoptesttask.navigation.screens.catalog.CatalogScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.favourites.FavouritesScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.product_card.ProductCardScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.profile.ProfileScreenNavigationImpl
import com.nyx.product_card_compose.screens.ProductCardScreen
import com.nyx.profile_compose.screens.ProfileScreen

const val catalogNavGraph = "CatalogNavGraph"
const val profileNavGraph = "ProfileNavGraph"

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
    object Main :
        NavItem(
            route = NavigationTree.Root.Dashboard.Main.Home.name,
            title = "Главная",
            icon = Icons.Filled.Home
        )

    object Catalog : NavItem(
        route = NavigationTree.Root.Dashboard.Catalog.ProductsCatalog.name,
        title = "Каталог",
        icon = Icons.Filled.List
    )

    object Cart :
        NavItem(
            route = NavigationTree.Root.Dashboard.Cart.name,
            title = "Корзина",
            icon = Icons.Filled.ShoppingCart
        )

    object Stocks : NavItem(
        route = NavigationTree.Root.Dashboard.Stocks.name,
        title = "Акции",
        icon = Icons.Filled.Star
    )

    object Profile :
        NavItem(
            route = NavigationTree.Root.Dashboard.Profile.UserProfile.name,
            title = "Профиль",
            icon = Icons.Filled.Face
        )
}

@Composable
fun DashboardNavigationBar() {
    val items = remember {
        listOf(
            NavItem.Main,
            NavItem.Catalog,
            NavItem.Cart,
            NavItem.Stocks,
            NavItem.Profile,
        ).toStable()
    }

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavItem.Catalog.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            val catalogScreenNavigation = CatalogScreenNavigationImpl(navController)
            val productCardScreenNavigation = ProductCardScreenNavigationImpl(navController)
            val profileScreenNavigation = ProfileScreenNavigationImpl(navController)
            val favouritesScreenNavigation = FavouritesScreenNavigationImpl(navController)

            composable(NavItem.Main.route) { StubView(pageName = "main") }

            navigation(
                startDestination = catalogNavGraph,
                route = NavItem.Catalog.route,
            ) {

                composable(catalogNavGraph) {
                    CatalogScreen(screenNavigation = catalogScreenNavigation)
                }

                composable(NavigationTree.Root.Dashboard.Catalog.ProductCard.name) {
                    ProductCardScreen(screenNavigation = productCardScreenNavigation)
                }
            }

            composable(NavItem.Cart.route) { StubView(pageName = "cart") }
            composable(NavItem.Stocks.route) { StubView(pageName = "stocks") }

            navigation(
                startDestination = profileNavGraph,
                route = NavItem.Profile.route
            ) {
                composable(profileNavGraph) {
                    ProfileScreen(screenNavigation = profileScreenNavigation)
                }

                composable(NavigationTree.Root.Dashboard.Profile.Favourite.name) {
                    FavouriteProductsScreen(screenNavigation = favouritesScreenNavigation)
                }

                composable(NavigationTree.Root.Dashboard.Profile.ProductCard.name) {
                    ProductCardScreen(screenNavigation = productCardScreenNavigation)
                }
            }
        }
    }
}
