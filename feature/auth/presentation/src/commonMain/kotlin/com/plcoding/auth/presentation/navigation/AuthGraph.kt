package com.plcoding.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.auth.presentation.register.RegisterRoot
import com.plcoding.auth.presentation.register_success.RegisterSuccessRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Register
    ) {
        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = {
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(it))
                }
            )
        }
        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessRoot()
        }
    }
}