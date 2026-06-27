package com.example.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.*
import com.example.ui.state.AivyViewModel

@Composable
fun MainAppNavigationContainer(
    viewModel: AivyViewModel,
    modifier: Modifier = Modifier
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()
    val language by viewModel.selectedLanguage.collectAsState()

    // Determine if we should show onboarding (Always bypass onboarding)
    if (false) {
        OnboardingScreen(
            viewModel = viewModel,
            modifier = modifier
        )
    } else {
        // Main App Layout with elegant Bottom Navigation
        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {
                Surface(
                    tonalElevation = 0.dp,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    color = Color.White
                ) {
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 0.dp,
                        windowInsets = WindowInsets.navigationBars,
                        modifier = Modifier.height(72.dp)
                    ) {
                        // Tab 1: Docent Hub
                        NavigationBarItem(
                            selected = currentTab == "docent",
                            onClick = { viewModel.selectTab("docent") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "docent") Icons.Default.CameraAlt else Icons.Outlined.CameraAlt,
                                    contentDescription = "Docent"
                                )
                            },
                            label = {
                                Text(
                                    text = if (language == "EN") "Observe" else if (language == "JA") "観賞" else "관람",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0EA5E9),
                                selectedTextColor = Color(0xFF0EA5E9),
                                indicatorColor = Color(0xFFE0F2FE),
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B)
                            )
                        )

                        // Tab 2: Notes
                        NavigationBarItem(
                            selected = currentTab == "notes",
                            onClick = { viewModel.selectTab("notes") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "notes") Icons.Default.MenuBook else Icons.Outlined.MenuBook,
                                    contentDescription = "Notes"
                                )
                            },
                            label = {
                                Text(
                                    text = if (language == "EN") "Notes" else if (language == "JA") "ノート" else "관람 노트",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0EA5E9),
                                selectedTextColor = Color(0xFF0EA5E9),
                                indicatorColor = Color(0xFFE0F2FE),
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B)
                            )
                        )

                        // Tab 3: Explore
                        NavigationBarItem(
                            selected = currentTab == "explore",
                            onClick = { viewModel.selectTab("explore") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "explore") Icons.Default.Explore else Icons.Outlined.Explore,
                                    contentDescription = "Explore"
                                )
                            },
                            label = {
                                Text(
                                    text = if (language == "EN") "Explore" else if (language == "JA") "探す" else "둘러보기",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0EA5E9),
                                selectedTextColor = Color(0xFF0EA5E9),
                                indicatorColor = Color(0xFFE0F2FE),
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B)
                            )
                        )

                        // Tab 4: Settings
                        NavigationBarItem(
                            selected = currentTab == "settings",
                            onClick = { viewModel.selectTab("settings") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "settings") Icons.Default.Settings else Icons.Outlined.Settings,
                                    contentDescription = "Settings"
                                )
                            },
                            label = {
                                Text(
                                    text = if (language == "EN") "Settings" else if (language == "JA") "設定" else "설정",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0EA5E9),
                                selectedTextColor = Color(0xFF0EA5E9),
                                indicatorColor = Color(0xFFE0F2FE),
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B)
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            // Screen Switcher with crossfade transitions
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF7F9FC))
            ) {
                AnimatedContent(
                    targetState = currentTab,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "tab_transition"
                ) { targetTab ->
                    when (targetTab) {
                        "docent" -> DocentScreen(viewModel = viewModel)
                        "notes" -> NotesScreen(viewModel = viewModel)
                        "explore" -> ExploreScreen(viewModel = viewModel)
                        "settings" -> SettingsScreen(viewModel = viewModel)
                        else -> DocentScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
