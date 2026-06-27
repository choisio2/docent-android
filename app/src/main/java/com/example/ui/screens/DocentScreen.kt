package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Artwork
import com.example.data.ArtworkRepository
import com.example.ui.components.ArtworkVisualizer
import com.example.ui.state.AivyViewModel
import com.example.ui.state.ChatMessage
import kotlinx.coroutines.launch

@Composable
fun DocentScreen(
    viewModel: AivyViewModel,
    modifier: Modifier = Modifier
) {
    val selectedArtworkId by viewModel.selectedArtworkId.collectAsState()
    val isQuietMode by viewModel.isQuietMode.collectAsState()
    val language by viewModel.selectedLanguage.collectAsState()
    val accessMode by viewModel.accessibilityMode.collectAsState()

    val currentArtwork = ArtworkRepository.getArtworkById(selectedArtworkId) ?: ArtworkRepository.artworks[0]

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)) // Editorial Slate-blue background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header: Editorial App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F9FC))
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "AIVY ",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF1A1C1E),
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = "Docent",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF94A3B8), // slate-400
                            letterSpacing = (-0.5).sp
                        )
                    }
                }

                // Small indicator of open-ear audio status with dropdown - Editorial white capsule
                var isDropdownExpanded by remember { mutableStateOf(false) }

                Box {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.clickable { isDropdownExpanded = true }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF0EA5E9))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "OPEN-EAR LINKED",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0EA5E9),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select Artwork",
                                tint = Color(0xFF0EA5E9),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        ArtworkRepository.artworks.forEach { artwork ->
                            val title = when (language) {
                                "EN" -> artwork.titleEn
                                "JA" -> artwork.titleJa
                                else -> artwork.titleKo
                            }
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (artwork.id == selectedArtworkId) Color(0xFF0EA5E9) else Color(0xFF1A1C1E)
                                    )
                                },
                                onClick = {
                                    viewModel.selectArtwork(artwork.id)
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Main Content Area with elegant Transition between Audio View and Chatbot View
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AnimatedContent(
                    targetState = isQuietMode,
                    transitionSpec = {
                        slideInVertically { h -> h } + fadeIn() togetherWith
                                slideOutVertically { h -> h } + fadeOut()
                    },
                    label = "mode_transition"
                ) { quiet ->
                    if (quiet) {
                        // Interactive 2-Way Chatbot View
                        ChatbotView(viewModel, currentArtwork, language, accessMode)
                    } else {
                        // Standard Screenless Audio/Docent View
                        AudioGuideView(viewModel, currentArtwork, language, accessMode)
                    }
                }
            }
        }
    }
}

@Composable
fun AudioGuideView(
    viewModel: AivyViewModel,
    artwork: Artwork,
    lang: String,
    accessMode: String
) {
    ModernAudioGuideView(viewModel, artwork, lang, accessMode)
}

@Composable
fun OldAudioGuideView(
    viewModel: AivyViewModel,
    artwork: Artwork,
    lang: String,
    accessMode: String
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.playbackProgress.collectAsState()
    val isDetailed by viewModel.isDetailedNarrative.collectAsState()
    val speed by viewModel.playbackSpeed.collectAsState()
    val subtitle by viewModel.currentSubtitle.collectAsState()
    val outputDevice by viewModel.outputDevice.collectAsState()
    val bookmarkedIds = viewModel.bookmarkedArtworkIds

    val title = when (lang) {
        "EN" -> artwork.titleEn
        "JA" -> artwork.titleJa
        else -> artwork.titleKo
    }
    val artist = when (lang) {
        "EN" -> artwork.artistEn
        "JA" -> artwork.artistJa
        else -> artwork.artistKo
    }
    val museum = when (lang) {
        "EN" -> artwork.museumEn
        "JA" -> artwork.museumJa
        else -> artwork.museumKo
    }

    val isBookmarked = bookmarkedIds.contains(artwork.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Art Viewfinder Scanner Card (Simulated Camera Screen)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
                .padding(vertical = 12.dp)
                .clip(RoundedCornerShape(32.dp))
                .border(4.dp, Color.White, RoundedCornerShape(32.dp))
                .background(Color.Black)
        ) {
            // Renders the customized Canvas artwork
            ArtworkVisualizer(
                artworkId = artwork.id,
                modifier = Modifier.fillMaxSize(),
                animate = isPlaying
            )

            // Dynamic Scanning Line overlay simulating camera visual recognition
            val infiniteTransition = rememberInfiniteTransition(label = "scanner")
            val scanY by infiniteTransition.animateFloat(
                initialValue = 0.05f,
                targetValue = 0.95f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "scan_line"
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                val h = size.height
                val w = size.width
                val lineY = h * scanY
                // Neon-like scanner line
                drawLine(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF38BDF8).copy(alpha = 0f),
                            Color(0xFF38BDF8).copy(alpha = 0.6f),
                            Color(0xFF38BDF8).copy(alpha = 0f)
                        )
                    ),
                    start = Offset(0f, lineY),
                    end = Offset(w, lineY),
                    strokeWidth = 4f
                )
            }

            // Quick Floating Badges on top of camera
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Top Left: Recognized state pill (matching design HTML status)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val dotAlpha by infiniteTransition.animateFloat(
                        initialValue = 0.4f,
                        targetValue = 1.0f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot_alpha"
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .graphicsLayer { alpha = dotAlpha }
                            .background(Color(0xFF4ADE80), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "RECOGNIZED",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }

                // Bottom Left: Floating toggle button for Quiet Chat Mode
                Button(
                    onClick = { viewModel.setQuietMode(true) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.9f),
                        contentColor = Color(0xFF001D36)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Chatbot Mode",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (lang) {
                                "EN" -> "Ask AIVY (Chat)"
                                "JA" -> "AIに質問する"
                                else -> "조용히 보기 (챗봇 질문)"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Bottom Artwork Meta Overlay (from design HTML)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)),
                            startY = 0f
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .padding(top = 40.dp) // ensure spacing for title readability
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomEnd).fillMaxWidth()
                ) {
                    // Offset elements from the floating Chat button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = title,
                                fontFamily = FontFamily.Serif,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = "$artist · ${artwork.year}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.8f),
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.padding(top = 2.dp),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }

        // Editorial Transcript Area (AIVY Speaking Captions card)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header of transcript area
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AIVY Insights",
                            tint = Color(0xFF0061A4),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isPlaying) "AIVY INSIGHTS" else "AIVY STANDBY",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0061A4),
                            letterSpacing = 1.5.sp
                        )
                    }

                    // Elegant Audio Wave visualizer
                    Box(
                        modifier = Modifier.size(width = 32.dp, height = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AudioWaveform(isPlaying = isPlaying)
                    }
                }

                // Transcript content text
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = if (subtitle.isBlank()) {
                                when (lang) {
                                    "EN" -> "\"Tap Play to hear details. Or ask a chatbot question!\""
                                    "JA" -> "\"再生を押すと音声解説が流れます。質問も可能です。\""
                                    else -> "\"재생 버튼을 누르면 골전도 맞춤형 오디오 해설이 출력됩니다.\""
                                }
                            } else "\"$subtitle\"",
                            fontFamily = FontFamily.Serif,
                            fontSize = if (accessMode == "SlowEasy") 18.sp else 16.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = if (accessMode == "SlowEasy") 26.sp else 22.sp,
                            color = Color(0xFF1E293B) // slate-800
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = when (lang) {
                                "EN" -> "Would you like to know more about the behind stories of this painting?"
                                "JA" -> "この作品に隠された秘話をもっと知りたくありませんか？"
                                else -> "이 예술 작품에 얽힌 뒷이야기나 화가의 다른 창작 의도가 궁금하신가요?"
                            },
                            fontFamily = FontFamily.Serif,
                            fontSize = 14.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            lineHeight = 20.sp,
                            color = Color(0xFF64748B) // slate-500
                        )
                    }
                }

                // Interactive horizontal chips (Q&A Recommendation pills) at the bottom of the card
                val qnaChips = when (lang) {
                    "EN" -> artwork.qnaEn.keys.toList()
                    "JA" -> artwork.qnaJa.keys.toList()
                    else -> artwork.qnaKo.keys.toList()
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(qnaChips) { chipText ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)), // slate-100
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .clickable {
                                    viewModel.setQuietMode(true)
                                    viewModel.sendUserMessage(chipText)
                                }
                        ) {
                            Text(
                                text = "🎨 $chipText",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF475569), // slate-700
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Playback Controller Controls
        Column(modifier = Modifier.fillMaxWidth()) {
            // Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isPlaying) {
                        val secs = (progress * (if (isDetailed) 45 else 20)).toInt()
                        "0:${secs.toString().padStart(2, '0')}"
                    } else "0:00",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B), // slate-500
                    fontWeight = FontWeight.Bold
                )

                Slider(
                    value = progress,
                    onValueChange = { viewModel.seekTo(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color(0xFF0061A4),
                        inactiveTrackColor = Color(0xFFE2E8F0),
                        thumbColor = Color(0xFF0061A4)
                    )
                )

                Text(
                    text = if (isDetailed) "0:45" else "0:20",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Bold
                )
            }

            // Summary vs Detailed commentary mode selector tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = !isDetailed,
                    onClick = { viewModel.selectNarrativeMode(false) },
                    label = {
                        Text(
                            text = if (lang == "EN") "20s Summary" else if (lang == "JA") "20秒要約" else "20초 요약",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF0061A4),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF1F5F9),
                        labelColor = Color(0xFF475569)
                    ),
                    border = null,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.padding(end = 8.dp)
                )

                FilterChip(
                    selected = isDetailed,
                    onClick = { viewModel.selectNarrativeMode(true) },
                    label = {
                        Text(
                            text = if (lang == "EN") "1m Detailed" else if (lang == "JA") "1分詳細解説" else "1분 상세 해설",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF0061A4),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF1F5F9),
                        labelColor = Color(0xFF475569)
                    ),
                    border = null,
                    shape = RoundedCornerShape(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Action Buttons Row: Play, Bookmark, Speeds, Output device
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Playback speed modifier chip
                AssistChip(
                    onClick = {
                        val nextSpeed = when (speed) {
                            0.8f -> 1.0f
                            1.0f -> 1.2f
                            1.2f -> 1.5f
                            else -> 0.8f
                        }
                        viewModel.changePlaybackSpeed(nextSpeed)
                    },
                    label = {
                        Text(
                            text = "${speed}x",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = "Speed Icon",
                            tint = Color(0xFF0061A4),
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFFF1F5F9),
                        labelColor = Color(0xFF1E293B)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    shape = RoundedCornerShape(50.dp)
                )

                // Middle: Large circle play/pause button
                IconButton(
                    onClick = { viewModel.toggleAudio() },
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color(0xFF0061A4), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause Button",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Right: Bookmark toggle button
                IconButton(
                    onClick = { viewModel.toggleBookmark(artwork.id) },
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color(0xFFE2E8F0), CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark Button",
                        tint = if (isBookmarked) Color(0xFF0EA5E9) else Color(0xFF475569),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatbotView(
    viewModel: AivyViewModel,
    artwork: Artwork,
    lang: String,
    accessMode: String
) {
    val selectedArtworkId by viewModel.selectedArtworkId.collectAsState()
    val chatHistory = viewModel.chatHistory[selectedArtworkId] ?: emptyList()
    val keyboardController = LocalSoftwareKeyboardController.current

    var textInput by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Scroll to bottom when new messages arrive
    LaunchedEffect(chatHistory.size) {
        if (chatHistory.isNotEmpty()) {
            listState.animateScrollToItem(chatHistory.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)) // Slate background
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Chatbot Header details
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { viewModel.setQuietMode(false) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Audio",
                        tint = Color(0xFF1E293B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "AIVY CHATBOT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFF1A1C1E)
                    )
                    Text(
                        text = when (lang) {
                            "EN" -> "Ask Intent & Behind Stories"
                            "JA" -> "意図と裏話を訊ねる"
                            else -> "작품 의도와 비하인드 탐구"
                        },
                        fontSize = 10.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Quick audio mode switch
            TextButton(onClick = { viewModel.setQuietMode(false) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Headset,
                        contentDescription = "Audio Guide",
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFF0061A4)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (lang == "EN") "Listen Mode" else if (lang == "JA") "音声モード" else "음성 듣기",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0061A4)
                    )
                }
            }
        }

        // Messages List Scroll Container
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(chatHistory) { msg ->
                val isUser = msg.sender == "user"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isUser) Color(0xFF0061A4) else Color.White
                        ),
                        border = if (!isUser) BorderStroke(1.dp, Color(0xFFE2E8F0)) else null,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = if (isUser) 20.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 20.dp
                        ),
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = msg.text,
                                fontSize = if (accessMode == "SlowEasy") 15.sp else 13.sp,
                                color = if (isUser) Color.White else Color(0xFF1E293B),
                                lineHeight = if (accessMode == "SlowEasy") 22.sp else 18.sp
                            )
                        }
                    }
                }
            }
        }

        // Quick Q&A Recommendation Chips based on the artwork
        val qnaChips = when (lang) {
            "EN" -> artwork.qnaEn.keys.toList()
            "JA" -> artwork.qnaJa.keys.toList()
            else -> artwork.qnaKo.keys.toList()
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = when (lang) {
                "EN" -> "Quick Questions about this painting:"
                "JA" -> "この作品に関する主な質問："
                else -> "작품에 대해 가장 많이 묻는 질문 칩:"
            },
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF64748B),
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(qnaChips) { chipText ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .clickable { viewModel.sendUserMessage(chipText) }
                ) {
                    Text(
                        text = "🎨 $chipText",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // TextInput and Send Button Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = {
                    Text(
                        text = when (lang) {
                            "EN" -> "Ask AIVY about this art..."
                            "JA" -> "AIVYに作品の秘密を尋ねる..."
                            else -> "작품의 의도, 비하인드 등을 물어보세요..."
                        },
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0061A4),
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (textInput.isNotBlank()) {
                        viewModel.sendUserMessage(textInput)
                        textInput = ""
                        keyboardController?.hide()
                    }
                }),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        viewModel.sendUserMessage(textInput)
                        textInput = ""
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFF0061A4), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun AudioWaveform(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    
    // Animate 4 vertical bars
    val h1 by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(400, easing = LinearEasing), RepeatMode.Reverse), label = "h1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.7f,
        animationSpec = infiniteRepeatable(tween(550, easing = LinearEasing), RepeatMode.Reverse), label = "h2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 0.1f, targetValue = 0.85f,
        animationSpec = infiniteRepeatable(tween(350, easing = LinearEasing), RepeatMode.Reverse), label = "h3"
    )
    val h4 by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 0.95f,
        animationSpec = infiniteRepeatable(tween(480, easing = LinearEasing), RepeatMode.Reverse), label = "h4"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val barCount = 4
        val gap = 6f
        val barW = (w - (gap * (barCount - 1))) / barCount

        val heights = if (isPlaying) listOf(h1, h2, h3, h4) else listOf(0.15f, 0.15f, 0.15f, 0.15f)

        for (i in 0 until barCount) {
            val barH = h * heights[i]
            val x = i * (barW + gap)
            val y = (h - barH) / 2f
            drawRoundRect(
                color = if (isPlaying) Color(0xFF0061A4) else Color.LightGray,
                topLeft = Offset(x, y),
                size = Size(barW, barH),
                cornerRadius = CornerRadius(4f, 4f)
            )
        }
    }
}
