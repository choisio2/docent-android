package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Artwork
import com.example.data.ArtworkRepository
import com.example.ui.components.ArtworkVisualizer
import com.example.ui.state.AivyViewModel

@Composable
fun ModernAudioGuideView(
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
    val recentlyViewedIds by viewModel.recentlyViewedIds.collectAsState()

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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // -------------------------------------------------------------
        // PORTRAIT ART VIEW CARD (Viewfinder + Title Metadata)
        // -------------------------------------------------------------
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                // Viewfinder Box (vertical portrait style)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(310.dp)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
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

                    // Floating Badges on top of camera
                    // Top Left: Recognized state pill (matching design HTML status)
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(14.dp)
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(50.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp),
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
                                .size(6.dp)
                                .graphicsLayer { alpha = dotAlpha }
                                .background(Color(0xFF4ADE80), CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "RECOGNIZED",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }

                    // Floating Chatbot Ask button in viewfinder bottom left
                    Button(
                        onClick = { viewModel.setQuietMode(true) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.9f),
                            contentColor = Color(0xFF1E293B)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "Chatbot Mode",
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = when (lang) {
                                    "EN" -> "Ask AIVY"
                                    "JA" -> "AIに質問"
                                    else -> "조용히 보기"
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Metadata Details beneath the viewfinder image (Inside the Art Card)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = title,
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$artist · ${artwork.year}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = museum,
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        // -------------------------------------------------------------
        // UNIFIED AUDIO CONTROLLER CARD (Cream/Yellow Pastel Point Color)
        // -------------------------------------------------------------
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7)), // Beautiful soft pastel yellow
            border = BorderStroke(1.dp, Color(0xFFFEF9C3)),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Caption header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AIVY insights",
                            tint = Color(0xFF854D0E),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isPlaying) "AIVY INSIGHTS" else "AIVY STANDBY",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF854D0E),
                            letterSpacing = 1.5.sp
                        )
                    }

                    // Audio wave
                    Box(
                        modifier = Modifier.size(width = 30.dp, height = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AudioWaveform(isPlaying = isPlaying)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Caption Transcript subtitle text
                Text(
                    text = if (subtitle.isBlank()) {
                        when (lang) {
                            "EN" -> "\"Tap Play to hear details. Or ask a chatbot question!\""
                            "JA" -> "\"再生を押すと音声解説が流れます。質問も可能です。\""
                            else -> "\"새벽 버튼을 누르면 골전도 맞춤형 오디오 해설이 출력됩니다.\""
                        }
                    } else "\"$subtitle\"",
                    fontFamily = FontFamily.Serif,
                    fontSize = if (accessMode == "SlowEasy") 16.sp else 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = if (accessMode == "SlowEasy") 24.sp else 20.sp,
                    color = Color(0xFF1E293B),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                // Slider Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val secs = if (isPlaying) {
                        (progress * (if (isDetailed) 45 else 20)).toInt()
                    } else 0
                    Text(
                        text = "0:${secs.toString().padStart(2, '0')}",
                        fontSize = 10.sp,
                        color = Color(0xFF854D0E),
                        fontWeight = FontWeight.Bold
                    )

                    Slider(
                        value = progress,
                        onValueChange = { viewModel.seekTo(it) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF1E293B),
                            inactiveTrackColor = Color(0xFFE2E8F0),
                            thumbColor = Color(0xFF1E293B)
                        )
                    )

                    Text(
                        text = if (isDetailed) "0:45" else "0:20",
                        fontSize = 10.sp,
                        color = Color(0xFF854D0E),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Summary vs Detailed tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FilterChip(
                        selected = !isDetailed,
                        onClick = { viewModel.selectNarrativeMode(false) },
                        label = {
                            Text(
                                text = if (lang == "EN") "20s Summary" else if (lang == "JA") "20秒要約" else "20초 요약",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF1E293B),
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.5f),
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
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF1E293B),
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.5f),
                            labelColor = Color(0xFF475569)
                        ),
                        border = null,
                        shape = RoundedCornerShape(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Main Controls Row: Speed, SkipPrev, Play/Pause, SkipNext, Bookmark
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Speed modifier
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
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Speed Icon",
                                tint = Color(0xFF1E293B),
                                modifier = Modifier.size(12.dp)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White.copy(alpha = 0.6f),
                            labelColor = Color(0xFF1E293B)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFFEF9C3)),
                        shape = RoundedCornerShape(50.dp)
                    )

                    // Skip previous, play/pause, skip next controls
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Skip Previous
                        IconButton(
                            onClick = { viewModel.seekTo(0f) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipPrevious,
                                contentDescription = "Rewind",
                                tint = Color(0xFF1E293B),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Play / Pause Circle
                        IconButton(
                            onClick = { viewModel.toggleAudio() },
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color(0xFF1E293B), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "PlayPause",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        // Skip Next
                        IconButton(
                            onClick = { viewModel.seekTo(0.99f) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = "Forward",
                                tint = Color(0xFF1E293B),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Bookmark toggle
                    IconButton(
                        onClick = { viewModel.toggleBookmark(artwork.id) },
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.6f), CircleShape)
                            .border(1.dp, Color(0xFFFEF9C3), CircleShape)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) Color(0xFFE5A93C) else Color(0xFF1E293B),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // Q&A DIRECT TOUCH RECOMMENDATIONS (Icon buttons side-by-side)
        // -------------------------------------------------------------
        val qnaChips = when (lang) {
            "EN" -> artwork.qnaEn.keys.toList()
            "JA" -> artwork.qnaJa.keys.toList()
            else -> artwork.qnaKo.keys.toList()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Button 1: 이 기법은?
            if (qnaChips.isNotEmpty()) {
                val q1 = qnaChips[0]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            viewModel.setQuietMode(true)
                            viewModel.sendUserMessage(q1)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFFFEF3C7), CircleShape), // Pastel Amber
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Help,
                            contentDescription = q1,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = q1,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Button 2: 작가는 누구야?
            if (qnaChips.size > 1) {
                val q2 = qnaChips[1]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            viewModel.setQuietMode(true)
                            viewModel.sendUserMessage(q2)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFFD1FAE5), CircleShape), // Pastel Mint
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = q2,
                            tint = Color(0xFF059669),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = q2,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Button 3: 비슷한 작품은?
            if (qnaChips.size > 2) {
                val q3 = qnaChips[2]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            viewModel.setQuietMode(true)
                            viewModel.sendUserMessage(q3)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFFE0F2FE), CircleShape), // Pastel Blue
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = q3,
                            tint = Color(0xFF0284C7),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = q3,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Clickable link for custom chat
        Text(
            text = when (lang) {
                "EN" -> "Ask more questions via chat ➔"
                "JA" -> "チャットでさらに質問する ➔"
                else -> "채팅으로 더 물어보기 ➔"
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0061A4),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable { viewModel.setQuietMode(true) }
                .padding(vertical = 12.dp)
                .fillMaxWidth()
        )

        // -------------------------------------------------------------
        // RECENTLY VIEWED ARTWORKS SECTION
        // -------------------------------------------------------------
        if (recentlyViewedIds.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ) {
                Text(
                    text = when (lang) {
                        "EN" -> "Recently Viewed"
                        "JA" -> "最近の観賞記録"
                        else -> "최근 작품 관람"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 6.dp)
                ) {
                    items(recentlyViewedIds) { id ->
                        val art = ArtworkRepository.getArtworkById(id)
                        if (art != null) {
                            val rTitle = when (lang) {
                                "EN" -> art.titleEn
                                "JA" -> art.titleJa
                                else -> art.titleKo
                            }
                            val rArtist = when (lang) {
                                "EN" -> art.artistEn
                                "JA" -> art.artistJa
                                else -> art.artistKo
                            }
                            Card(
                                modifier = Modifier
                                    .width(170.dp)
                                    .clickable { viewModel.selectArtwork(art.id) },
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                    ) {
                                        ArtworkVisualizer(
                                            artworkId = art.id,
                                            modifier = Modifier.fillMaxSize(),
                                            animate = false
                                        )
                                    }
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = rTitle,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E293B),
                                            maxLines = 1
                                        )
                                        Text(
                                            text = rArtist,
                                            fontSize = 9.sp,
                                            color = Color(0xFF64748B),
                                            maxLines = 1,
                                            modifier = Modifier.padding(top = 1.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
