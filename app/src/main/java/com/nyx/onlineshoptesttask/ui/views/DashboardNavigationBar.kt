package com.nyx.onlineshoptesttask.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nyx.catalog_compose.screens.CatalogScreen
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_compose.utils.toStable
import com.nyx.common_compose.views.StubView
import com.nyx.favourites_compose.screens.FavouriteProductsScreen
import com.nyx.onlineshoptesttask.R
import com.nyx.onlineshoptesttask.navigation.NavigationTree
import com.nyx.onlineshoptesttask.navigation.screens.catalog.CatalogScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.favourites.FavouritesScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.product_card.ProductCardScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.profile.ProfileScreenNavigationImpl
import com.nyx.product_card_compose.screens.ProductCardScreen
import com.nyx.profile_compose.screens.ProfileScreen
import com.nyx.common_compose.R as CommonRes

const val catalogNavGraph = "CatalogNavGraph"
const val profileNavGraph = "ProfileNavGraph"
private const val ARGS = "args"

object Constants {
    const val PRODUCT_ID = "productId"
}

sealed class NavItem(val route: String, val titleResId: Int, val iconResId: Int) {
    object Main :
        NavItem(
            route = NavigationTree.Root.Dashboard.Main.name,
            titleResId = R.string.nav_item_main_text,
            iconResId = R.drawable.home_icon
        )

    object Catalog : NavItem(
        route = NavigationTree.Root.Dashboard.Catalog.ProductsCatalog.name,
        titleResId = R.string.nav_item_catalog_text,
        iconResId = R.drawable.catalog_icon
    )

    object Cart :
        NavItem(
            route = NavigationTree.Root.Dashboard.Cart.name,
            titleResId = R.string.nav_item_cart_text,
            iconResId = R.drawable.cart_icon
        )

    object Stocks : NavItem(
        route = NavigationTree.Root.Dashboard.Stocks.name,
        titleResId = R.string.nav_item_stocks_text,
        iconResId = R.drawable.stocks_icon
    )

    object Profile :
        NavItem(
            route = NavigationTree.Root.Dashboard.Profile.UserProfile.name,
            titleResId = R.string.nav_item_profile_text,
            iconResId = CommonRes.drawable.profile_icon
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
            BottomNavigation(
                backgroundColor = Color.White,
                contentColor = colorResource(id = R.color.dark_blue)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconResId),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(screen.titleResId),
                                style = AppTypography.caption1
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        selectedContentColor = colorResource(id = R.color.pink),
                        unselectedContentColor = colorResource(id = R.color.dark_blue),
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


                // TODO ref route
                composable(
                    route = "${NavigationTree.Root.Dashboard.Catalog.ProductCard.name}/{${Constants.PRODUCT_ID}}",
                    arguments = listOf(
                        navArgument(Constants.PRODUCT_ID) {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString(Constants.PRODUCT_ID)
                    requireNotNull(productId) { "productId parameter wasn't found. Please make sure it's set!" }
                    ProductCardScreen(
                        productId = productId,
                        screenNavigation = productCardScreenNavigation
                    )
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
                    /* ProductCardScreen(
                         product = "",
                         screenNavigation = productCardScreenNavigation
                     )*/
                }
            }
        }
    }
}
