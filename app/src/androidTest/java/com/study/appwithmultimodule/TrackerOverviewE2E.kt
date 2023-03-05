package com.study.appwithmultimodule

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.common.truth.Truth.assertThat
import com.study.appwithmultimodule.navigation.Route
import com.study.appwithmultimodule.repository.TrackerRepositoryFake
import com.study.appwithmultimodule.ui.theme.AppWithMultiModuleTheme
import com.study.core.domain.model.ActivityLevel
import com.study.core.domain.model.Gender
import com.study.core.domain.model.GoalType
import com.study.core.domain.model.UserInfo
import com.study.core.domain.preferences.Preferences
import com.study.core.domain.use_case.FilterOutDigitsUseCase
import com.study.tracker_domain.model.TrackableFood
import com.study.tracker_domain.use_case.*
import com.study.tracker_presentation.search.SearchScreen
import com.study.tracker_presentation.search.SearchViewModel
import com.study.tracker_presentation.tracker_overview.TrackerOverViewViewModel
import com.study.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt

@HiltAndroidTest
class TrackerOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repositoryFake: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var preferences: Preferences
    private lateinit var trackerOverViewViewModel: TrackerOverViewViewModel
    private lateinit var searchViewViewModel: SearchViewModel

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalCoilApi::class)
    @Before
    fun setup() {
        preferences = mockk()
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 1,
            weight = 1f,
            height = 1,
            activityLevel = ActivityLevel.High,
            goalType = GoalType.GainWeight,
            carbRatio = 1f,
            proteinRatio = 1f,
            fatRatio = 1f
        )
        repositoryFake = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            trackFoodUseCase = TrackFoodUseCase(repositoryFake),
            searchFoodUseCase = SearchFoodUseCase(repositoryFake),
            getFoodsForDate = GetFoodForDateUseCase(repositoryFake),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(repositoryFake),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
        )
        trackerOverViewViewModel = TrackerOverViewViewModel(
            preferences = preferences,
            trackerUseCases = trackerUseCases
        )
        searchViewViewModel = SearchViewModel(
            trackerUseCases = trackerUseCases,
            filterOutDigitsUseCase = FilterOutDigitsUseCase()
        )

        composeRule.setContent {
            AppWithMultiModuleTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.TRACKER_OVERVIEW
                    ) {
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, day, month, year ->
                                navController.navigate(
                                    Route.SEARCH + "/$mealName" + "/$day" + "/$month" + "/$year"
                                )
                            },
                            viewModel = trackerOverViewViewModel
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },

                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                onPopBackStack = { navController.popBackStack() },
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                viewModel = searchViewViewModel
                            )
                        }
                    }
                }
            }

        }

    }

    @Test
    fun addBreakfast_appearsUnderBreakfast_nutrientsProperlyCalculated() {
        repositoryFake.searchResults = listOf(
            TrackableFood(
                name = "apple",
                imageUrl = null,
                caloriesPer100g = 150,
                carbsPer100g = 50,
                proteinsPer100g = 5,
                fatPer100g = 1
            )
        )
        val addedAmount = 150
        val expectedCalories = (1.5f * 150).roundToInt()
        val expectedCarbs = (1.5f * 50).roundToInt()
        val expectedProtein = (1.5f * 5).roundToInt()
        val expectedFat = (1.5f * 1).roundToInt()

        composeRule.onNodeWithText("Add Breakfast")
            .assertDoesNotExist()

        composeRule.onNodeWithContentDescription("Breakfast")
            .performClick()

        composeRule.onNodeWithText("Add Breakfast")
            .assertIsDisplayed()

        composeRule.onNodeWithText("Add Breakfast")
            .performClick()

        assertThat(navController.currentDestination?.route?.startsWith(Route.SEARCH)).isTrue()

        composeRule.onNodeWithTag("search_textField")
            .performTextInput("apple")

        composeRule.onNodeWithContentDescription("Search...")
            .performClick()
        composeRule
            .onNodeWithText("Carbs")
            .performClick()
        composeRule.onNodeWithContentDescription("Amount")
            .performTextInput(addedAmount.toString())
        composeRule
            .onNodeWithContentDescription("Track")
            .performClick()

        assertThat(
            navController.currentDestination?.route?.startsWith(Route.TRACKER_OVERVIEW)
        )

        composeRule
            .onAllNodesWithText(expectedCarbs.toString())
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(expectedProtein.toString())
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(expectedFat.toString())
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(expectedCalories.toString())
            .onFirst()
            .assertIsDisplayed()

    }

}