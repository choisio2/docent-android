package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ArtworkRepository
import com.example.ui.components.ArtworkVisualizer
import com.example.ui.state.AivyViewModel

@Composable
fun NotesScreen(
    viewModel: AivyViewModel,
    modifier: Modifier = Modifier
) {
    val bookmarkedIds = viewModel.bookmarkedArtworkIds
    val notes = viewModel.bookmarkNotes
    val language by viewModel.selectedLanguage.collectAsState()
    val perspective by viewModel.selectedPerspective.collectAsState()

    var showShareDialog by remember { mutableStateOf(false) }
    var selectedShareTitle by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)) // Editorial Slate background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Editorial Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F9FC))
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "AIVY INSIGHT LOG",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0061A4),
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = when (language) {
                            "EN" -> "Saved "
                            "JA" -> "マイ "
                            else -> "나의 "
                        },
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFF1A1C1E)
                    )
                    Text(
                        text = when (language) {
                            "EN" -> "Gallery Notes"
                            "JA" -> "観賞ノート"
                            else -> "관람 요약 카드"
                        },
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Light,
                        fontSize = 24.sp,
                        color = Color(0xFF1A1C1E)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (language) {
                        "EN" -> "Personalized collection of art insights and Q&As"
                        "JA" -> "私だけのカスタムAI解説と質問記録"
                        else -> "대화 내용과 주요 해설을 요약한 나만의 도슨트 카드"
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Report Stat bar if bookmarks are present
            if (bookmarkedIds.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = when (language) {
                                    "EN" -> "Exhibition Progress"
                                    "JA" -> "今日の観賞リポート"
                                    else -> "오늘의 관람 요약"
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF64748B),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = when (language) {
                                    "EN" -> "${bookmarkedIds.size} of 6 Masterpieces Saved"
                                    "JA" -> "6作品中 ${bookmarkedIds.size}点 記録済み"
                                    else -> "6점 중 ${bookmarkedIds.size}점 북마크 완료"
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFF1A1C1E)
                            )
                        }

                        // Perspective focus tag indicator
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = when (perspective) {
                                        "Philosophy" -> if (language == "EN") "#Philosophy" else if (language == "JA") "#哲学" else "#예술철학"
                                        "Technique" -> if (language == "EN") "#Technique" else if (language == "JA") "#技法" else "#미술기법"
                                        "History" -> if (language == "EN") "#History" else if (language == "JA") "#歴史" else "#역사비화"
                                        else -> if (language == "EN") "#Science" else if (language == "JA") "#科学" else "#색채과학"
                                    },
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF001D36)
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(0xFFD1E4FF),
                                labelColor = Color(0xFF001D36)
                            ),
                            border = null,
                            shape = RoundedCornerShape(50.dp)
                        )
                    }
                }
            }

            // Bookmarks cards scrollable list
            if (bookmarkedIds.isEmpty()) {
                // Empty state view
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Empty Notes",
                        tint = Color(0xFF94A3B8), // slate-400
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = when (language) {
                            "EN" -> "Your Note is Empty"
                            "JA" -> "ノートが空っぽです"
                            else -> "작성된 관람 노트가 없습니다"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1C1E),
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (language) {
                            "EN" -> "While viewing paintings, tap the Bookmark button or ask questions to AIVY to compile your custom gallery notes."
                            "JA" -> "鑑賞中に[ブックマーク]するか、AIとの会話を行うと、あなただけのドセントリポートが自動生成されます。"
                            else -> "작품 관람 화면에서 [북마크] 버튼을 누르거나 대화형 챗봇으로 질문하면 나만의 맞춤 감상 일지가 카드로 정리되어 이곳에 기록됩니다."
                        },
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(bookmarkedIds) { id ->
                        val artwork = ArtworkRepository.getArtworkById(id)
                        if (artwork != null) {
                            val title = when (language) {
                                "EN" -> artwork.titleEn
                                "JA" -> artwork.titleJa
                                else -> artwork.titleKo
                            }
                            val artist = when (language) {
                                "EN" -> artwork.artistEn
                                "JA" -> artwork.artistJa
                                else -> artwork.artistKo
                            }
                            val museum = when (language) {
                                "EN" -> artwork.museumEn
                                "JA" -> artwork.museumJa
                                else -> artwork.museumKo
                            }

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    // Row: Mini Thumbnail & Info
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Small 1:1 view of the canvas drawing
                                        Box(
                                            modifier = Modifier
                                                .size(72.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                        ) {
                                            ArtworkVisualizer(
                                                artworkId = artwork.id,
                                                modifier = Modifier.fillMaxSize(),
                                                animate = false
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(16.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = title,
                                                fontSize = 15.sp,
                                                fontFamily = FontFamily.Serif,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1A1C1E)
                                            )
                                            Text(
                                                text = "$artist (${artwork.year})",
                                                fontSize = 12.sp,
                                                color = Color(0xFF475569),
                                                modifier = Modifier.padding(top = 2.dp)
                                            )
                                            Text(
                                                text = museum,
                                                fontSize = 10.sp,
                                                color = Color(0xFF64748B),
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Custom summary divider line
                                    HorizontalDivider(color = Color(0xFFF1F5F9))
                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Customized AI insights
                                    Text(
                                        text = notes[artwork.id] ?: "",
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily.Serif,
                                        color = Color(0xFF1E293B),
                                        lineHeight = 20.sp,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    // Card actions: delete and share note
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                selectedShareTitle = title
                                                showShareDialog = true
                                            },
                                            modifier = Modifier
                                                .background(Color.White, CircleShape)
                                                .border(1.dp, Color(0xFFE2E8F0), CircleShape)
                                                .size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = "Share",
                                                tint = Color(0xFF475569),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(10.dp))

                                        IconButton(
                                            onClick = { viewModel.toggleBookmark(artwork.id) },
                                            modifier = Modifier
                                                .background(Color.White, CircleShape)
                                                .border(1.dp, Color(0xFFFEE2E2), CircleShape)
                                                .size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = Color(0xFFEF4444),
                                                modifier = Modifier.size(16.dp)
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

        // Animated Share Dialog
        if (showShareDialog) {
            AlertDialog(
                onDismissRequest = { showShareDialog = false },
                title = {
                    Text(
                        text = if (language == "EN") "Share Exhibition Note" else if (language == "JA") "ノートを共有する" else "관람 노트를 공유하시겠습니까?",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1C1E),
                        fontSize = 16.sp
                    )
                },
                text = {
                    Text(
                        text = if (language == "EN") "Successfully generated high-contrast note card for '$selectedShareTitle'. Ready to copy or send to friends!"
                        else if (language == "JA") "'$selectedShareTitle'のカード風ノートが生成されました。コピーおよび友達に送信できます。"
                        else "'$selectedShareTitle' 작품의 대화 기록 요약 카드가 생성되었습니다. 이미지 파일로 저장하거나 SNS로 동행인과 관람 소감을 공유하세요.",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )
                },
                confirmButton = {
                    TextButton(onClick = { showShareDialog = false }) {
                        Text(
                            text = if (language == "EN") "Copy Link" else if (language == "JA") "リンクをコピー" else "공유 링크 복사",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0061A4)
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showShareDialog = false }) {
                        Text(
                            text = if (language == "EN") "Close" else if (language == "JA") "閉じる" else "닫기",
                            color = Color(0xFF64748B)
                        )
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }
    }
}
