package com.example.debtgo.ui.navigation

import PaymentViewModel
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.MoneyOff
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.debtgo.data.model.Course
import com.example.debtgo.ui.screen.LoginScreen
import com.example.debtgo.ui.screen.PlanSelectionScreen
import com.example.debtgo.ui.screen.HomeScreen
import com.example.debtgo.data.model.PlanType
import com.example.debtgo.data.model.Service
import com.example.debtgo.data.model.User
import com.example.debtgo.ui.messages.ChatScreen
import com.example.debtgo.ui.messages.MessagesScreen
import com.example.debtgo.ui.screen.CheckoutScreen
import com.example.debtgo.ui.screen.ProfileScreen
import com.example.debtgo.ui.screen.ReviewsScreen
import com.example.debtgo.ui.screen.WorkspaceScreen
import com.example.debtgo.ui.screen.AddDebtScreen
import com.example.debtgo.ui.screen.AddPaymentConfigurationScreen
import com.example.debtgo.ui.screen.ConsultantApplicationScreen
import com.example.debtgo.ui.screen.CourseDetailScreen
import com.example.debtgo.ui.screen.CourseLessonScreen
import com.example.debtgo.ui.screen.EditServiceListScreen
import com.example.debtgo.ui.screen.EditServiceScreen
import com.example.debtgo.ui.screen.EducationScreen
import com.example.debtgo.ui.screen.ManagePaymentsScreen
import com.example.debtgo.ui.screen.NotificationPreferencesScreen
import com.example.debtgo.ui.screen.OpportunityApplyScreen
import com.example.debtgo.ui.screen.PostNewServiceScreen
import com.example.debtgo.ui.screen.RegisterScreen
import com.example.debtgo.viewmodel.analysis.EducationViewModel
import com.example.debtgo.viewmodel.analysis.ServiceViewModel

// Definición de rutas (sealed class)
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object PlanSelection : Screen("planSelection")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Workspace : Screen("workspace")
    object Reviews : Screen("reviews")
    object Messages : Screen("messages")
    object Checkout : Screen("checkout/{planType}/{planPrice}/{planFeatures}")
    object ManagePayments : Screen("managePayments")
    object AddDebt : Screen("addDebt")
    object AddPaymentConfiguration : Screen("addPaymentConfiguration")
    object Education : Screen("education")
    object NotificationPreferencesScreen : Screen("notification_preferences")
    object PostNewService : Screen("post_new_service")

    // Nuevas rutas para la funcionalidad de educación
    object CourseDetail : Screen("course_detail/{title}") {
        fun createRoute(title: String) = "course_detail/$title"
    }
    object CourseLesson : Screen("course_lesson/{title}/{lessonNumber}") {
        fun createRoute(title: String, lessonNumber: Int) = "course_lesson/$title/$lessonNumber"
    }
    object CourseResume : Screen("course_resume/{title}") {
        fun createRoute(title: String) = "course_resume/$title"
    }
    object OpportunityApply : Screen("opportunity_apply/{title}") {
        fun createRoute(title: String) = "opportunity_apply/$title"
    }
    object ConsultantApplication : Screen("consultant_application")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val navController = rememberNavController()
    val paymentViewModel: PaymentViewModel = viewModel()
    val serviceViewModel: ServiceViewModel = viewModel()


    fun completeCourse(title: String) {
        Log.d("CourseCompletion", "Course $title marked as completed")
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        // Pantalla de Registro

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = { name, email, role, password ->
                    Log.d("RegisterScreen", "Registration data: $name, $email, $role, $password")
                    navController.navigate(Screen.PlanSelection.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToPlanSelection = {
                    navController.navigate(Screen.PlanSelection.route)
                }
            )
        }


        composable(
            route = Screen.CourseLesson.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("lessonNumber") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val lessonNumber = backStackEntry.arguments?.getInt("lessonNumber") ?: 1
            CourseLessonScreen(
                title = title,
                lessonNumber = lessonNumber,
                navController = navController,
                onCourseCompleted = { completeCourse(title) }
            )
        }

        // Pantalla de Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    // Autenticación exitosa
                    navController.navigate(Screen.PlanSelection.route) {
                        popUpTo(Screen.Login.route) // Elimina login del back stack
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Login.route) // Elimina login del back stack
                    }
                },
                onNavigateToPlanSelection = {
                    navController.navigate(Screen.PlanSelection.route)
                }
            )
        }

        // Selección de Planes
        composable(Screen.PlanSelection.route) {
            PlanSelectionScreen(
                onBasicPlanSelected = {
                    navController.navigate(
                        "checkout/Basic/S%2F80%20%2Fmonth/" + listOf(
                            "Basic debt management tools",
                            "Income and expense tracking",
                            "Budget creation tools",
                            "Access to standard financial education materials"
                        ).joinToString("%7C") // URL encode the features
                    )
                },
                onPremiumPlanSelected = {
                    navController.navigate(
                        "checkout/Premium/S%2F150%20%2Fmonth/" + listOf(
                            "All Basic Plan features",
                            "Personalized financial consulting",
                            "Priority customer support"
                        ).joinToString("%7C")
                    )
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Checkout Screen
        composable(
            route = Screen.Checkout.route,
            arguments = listOf(
                navArgument("planType") { type = NavType.StringType },
                navArgument("planPrice") { type = NavType.StringType },
                navArgument("planFeatures") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val planType = backStackEntry.arguments?.getString("planType") ?: ""
            val planPrice = backStackEntry.arguments?.getString("planPrice") ?: ""
            val features = backStackEntry.arguments?.getString("planFeatures")?.split("%7C") ?: emptyList()
            CheckoutScreen(
                navController = navController,
                selectedPlan = planType,
                planPrice = planPrice,
                planFeatures = features,
                onBackClick = { navController.popBackStack() }
            )
        }


        // Pantalla Principal (Home)
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onLogout = {
                    // Vuelve al Login y limpia el stack
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                },
                totalDebt = 10000.0,
                monthlyPayment = 300.0, // Pasa los datos del ViewModel
                progress = 0.5f,
                interestRate = 0.07,
                estimatedPayoffDate = "2025-12-31",
                remainingBalance = 5000.0,
                isBasicPlan = false
            )
        }

        composable(Screen.NotificationPreferencesScreen.route) { navBackStackEntry ->

            NotificationPreferencesScreen(
                navController = navController,
                onSavePreferences = { paymentReminders, actionConfirmations, accountAlerts ->
                }
            )
        }

        composable(Screen.Education.route) {
            EducationScreen(navController)
        }

        composable(
            route = Screen.CourseDetail.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val courses = remember {
                listOf(
                    Course("Debt Reduction: Master Your Finances", 65, Icons.Outlined.MoneyOff, true, "Learn effective strategies to get out of debt and build a strong financial future."),
                    Course("Basic Investments: Make Your Money Grow", 30, Icons.Outlined.TrendingUp, false, "Discover the fundamental principles of investing and how to start generating returns."),
                    Course("Smart Financial Planning", 0, Icons.Outlined.CalendarMonth, false, "Create a personalized financial plan that helps you achieve your short-, medium-, and long-term goals.")
                )
            }
            val course = courses.find { it.title == title }
            CourseDetailScreen(
                title = title,
                description = course?.description ?: "No description available",
                isCompleted = course?.progress == 100,
                navController = navController
            )
        }

        // Nueva ruta para lección de curso
        composable(
            route = Screen.CourseLesson.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("lessonNumber") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val lessonNumber = backStackEntry.arguments?.getInt("lessonNumber") ?: 1
            CourseLessonScreen(
                title = title,
                lessonNumber = lessonNumber,
                navController = navController,
                onCourseCompleted = { completeCourse(title) }
            )
        }

        // Nueva ruta para continuar curso
        composable(
            route = Screen.CourseResume.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val viewModel: EducationViewModel = viewModel()

            val lastViewedLesson by remember {
                derivedStateOf { viewModel.getLastLessonForCourse(title) }
            }

            CourseLessonScreen(
                title = title,
                lessonNumber = lastViewedLesson,
                navController = navController,
                onCourseCompleted = {
                    viewModel.completeCourse(title)
                }
            )
        }

        // Nueva ruta para aplicar a oportunidad
        composable(
            route = Screen.OpportunityApply.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            OpportunityApplyScreen(
                title = title,
                navController = navController
            )
        }

        // Nueva ruta para aplicación de consultor
        composable(Screen.ConsultantApplication.route) {
            ConsultantApplicationScreen(navController = navController)
        }

        composable(Screen.ManagePayments.route) {
            ManagePaymentsScreen(navController = navController,
                viewModel = paymentViewModel)
        }

        composable(Screen.AddPaymentConfiguration.route) {
            AddPaymentConfigurationScreen(navController = navController,
                viewModel = paymentViewModel)
        }

        composable(Screen.AddDebt.route) {
            AddDebtScreen(navController = navController)
        }

        // Perfil de Usuario
        composable(Screen.Profile.route) {
            ProfileScreen(
                user = User(
                    name = "Estrellita",
                    email = "jusjusa@gmail.com",
                    plan = PlanType.PREMIUM
                ),
                navController = navController,
                onDeleteAccount = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                },
                onNavigateToWorkspace = {
                    navController.navigate(Screen.Workspace.route)
                }
            )
        }

        // Workspace Screen
        composable(Screen.Workspace.route) {
            // Pass the navController here
            WorkspaceScreen(user = User(
                name = "Estrellita",
                email = "jusjusa@gmail.com",
                plan = PlanType.PREMIUM
            ), navController = navController)
        }

        composable("post_new_service") {
            PostNewServiceScreen(navController)
        }

        composable("edit_service_list") {
            val services = listOf(
                Service(1, "Financial Consulting", "Personalized financial help", listOf("$100", "$180"), false),
                Service(2, "Credit Counseling", "Bank Loan Advice", listOf("$120"), true)
            )

            EditServiceListScreen(
                navController = navController,
                services = services,
                viewModel = serviceViewModel
            )
        }

        composable("edit_service") {
            EditServiceScreen(navController, serviceViewModel)
        }

        composable(Screen.Reviews.route) {
            ReviewsScreen(navController = navController)
        }

        composable(Screen.Messages.route) {
            MessagesScreen(navController)
        }

        composable(
            route = "chat/{contactId}",
            arguments = listOf(navArgument("contactId") {type = NavType.StringType})
        ) {
            backStackEntry ->
            ChatScreen(
                navController = navController,
                contactId = backStackEntry.arguments?.getString("contactId")
            )
        }
    }
}




