package com.inmar.compose1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.inmar.compose1.LoginScreen
import com.inmar.compose1.menuprocprim.MenuProcPrim
import com.inmar.compose1.subprocesos.SubProcesos

@Composable
fun NavigationHost(navController: NavController){
    NavHost(navController = navController as NavHostController,
        startDestination = "login"){

        composable("login"){
            LoginScreen(navController)
        }
        composable("menuprocprim"){
            MenuProcPrim(navController)
        }

        composable(
            "subprocesos/{idprocprim}",
            arguments = listOf(navArgument("idprocprim"){type = NavType.StringType})
        ){  backStackEntry ->
            SubProcesos(navController,backStackEntry.arguments?.getString("idprocprim"))
        }
    }
}

