package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.ui.state.AivyViewModel
import com.example.ui.state.Exhibition

@Composable
fun ExploreScreen(
    viewModel: AivyViewModel,
    modifier: Modifier = Modifier
) {
    val exhibitions = viewModel.exhibitions
    val selectedExhibitionId by viewModel.selectedExhibitionId.collectAsState()
    val bookmarkedIds = viewModel.bookmarkedArtworkIds
    val language by viewModel.selectedLanguage.collectAsState()
    val perspective by viewModel.selectedPerspective.collectAsState()

    var activeFilter by remember { mutableStateOf("All") } // All, Seoul, Pop-up
    var showActionDialog by remember { mutableStateOf<String?>(null) } // "map", "ticket", "calendar", null

    val currentExhibition = exhibitions.find { it.id == selectedExhibitionId }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)) // Editorial Slate background
    ) {
        if (currentExhibition != null) {
            // Immersive Exhibition Detail Page Overlay
            ExhibitionDetailView(
                exhibition = currentExhibition,
                language = language,
                onBack = { viewModel.viewExhibitionDetail(null) },
                onTriggerAction = { action -> showActionDialog = action }
            )
        } else {
            // Main Curation Directory List
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = when (language) {
                            "EN" -> "EXPLORE EXHIBITIONS"
                            "JA" -> "周りの展示・ポップアップ"
                            else -> "둘러보기"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 2.sp,
                        color = Color(0xFF1A1C1E)
                    )
                    Text(
                        text = when (language) {
                            "EN" -> "Curation recommended based on your viewing tastes"
                            "JA" -> "あなたの観賞性向に合わせたおすすめ展覧会"
                            else -> "나의 관람 성향과 북마크 기록을 분석한 맞춤형 추천"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // AI Smart Personalized Recommendation banner based on notes or onboard preference
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE2F0FF)),
                    border = BorderStroke(1.dp, Color(0xFF93C5FD).copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Recommend",
                            tint = Color(0xFF0061A4),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            val analysisText = when (language) {
                                "EN" -> {
                                    if (bookmarkedIds.contains("starry_night") || bookmarkedIds.contains("the_scream")) {
                                        "You focused on intense colors and deep emotions. We highly recommend 'Van Gogh & Impressionist Masters'!"
                                    } else {
                                        "Based on your '$perspective' focus, we curated the golden aesthetics and fine light reflections in town."
                                    }
                                }
                                "JA" -> {
                                    if (bookmarkedIds.contains("starry_night") || bookmarkedIds.contains("the_scream")) {
                                        "強烈な色彩と感情表現に関心を示されました。ライトな筆跡が踊る「ゴッホと印象派展」が最適です！"
                                    } else {
                                        "選択された「$perspective」の観点に基づき、精巧な光と黄金モザイクを体験する展示を推薦します。"
                                    }
                                }
                                else -> {
                                    if (bookmarkedIds.contains("starry_night") || bookmarkedIds.contains("the_scream")) {
                                        "최근 색채와 강렬한 감정 표현(반 고흐, 뭉크)에 깊은 관심을 보이셨으니, 질감과 화려한 붓터치가 극대화된 [반 고흐 & 인상주의 거장전] 관람을 강력 추천합니다!"
                                    } else if (bookmarkedIds.contains("the_kiss")) {
                                        "장식성과 황금빛 장막(클림트)에 매료되셨군요. 현대적 감각으로 입체 재해석한 [빛의 시어터: 클림트 황금빛 판타지]를 추천합니다!"
                                    } else {
                                        "관심 관점($perspective)과 관람 이력을 바탕으로, 오감을 사로잡는 정밀한 광학/비잔틴식 미술 연합 전시를 추천해 드립니다."
                                    }
                                }
                            }

                            Text(
                                text = if (language == "EN") "AIVY's Personalized Curation" else if (language == "JA") "AIVYのパーソナライズ推薦" else "AIVY AI의 맞춤 큐레이션 분석",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0061A4)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = analysisText,
                                fontSize = 12.sp,
                                color = Color(0xFF1E2836),
                                lineHeight = 17.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Interactive Filters row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf(
                        "All" to (if (language == "EN") "All Exhibitions" else if (language == "JA") "すべて" else "전체 전시"),
                        "Seoul" to (if (language == "EN") "Seoul Region" else if (language == "JA") "ソウル市" else "서울 권역"),
                        "Popup" to (if (language == "EN") "Pop-up / New" else if (language == "JA") "ポップアップ" else "핫한 팝업·신규")
                    )

                    items(filters) { (code, title) ->
                        val isSelected = activeFilter == code
                        FilterChip(
                            selected = isSelected,
                            onClick = { activeFilter = code },
                            label = { Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF0061A4),
                                selectedLabelColor = Color.White,
                                containerColor = Color.White,
                                labelColor = Color(0xFF64748B)
                            ),
                            border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            shape = RoundedCornerShape(50.dp)
                        )
                    }
                }

                // Exhibition Directory cards
                val filteredExhibitions = when (activeFilter) {
                    "Seoul" -> exhibitions.filter { it.locationKo.contains("서울") }
                    "Popup" -> exhibitions.filter { it.isPopup || it.isNew }
                    else -> exhibitions
                }

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredExhibitions) { ex ->
                        val exTitle = when (language) {
                            "EN" -> ex.titleEn
                            "JA" -> ex.titleJa
                            else -> ex.titleKo
                        }
                        val exPeriod = when (language) {
                            "EN" -> ex.periodEn
                            "JA" -> ex.periodJa
                            else -> ex.periodKo
                        }
                        val exLoc = when (language) {
                            "EN" -> ex.locationEn
                            "JA" -> ex.locationJa
                            else -> ex.locationKo
                        }
                        val exDesc = when (language) {
                            "EN" -> ex.descriptionEn
                            "JA" -> ex.descriptionJa
                            else -> ex.descriptionKo
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.viewExhibitionDetail(ex.id) },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFEBEBEB)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column {
                                // Cover poster thumbnail (using beautiful color blocks with metadata text)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                        .background(Color(ex.posterColor))
                                        .padding(16.dp)
                                ) {
                                    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            // Badges
                                            Row {
                                                if (ex.isNew) {
                                                    Card(
                                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEB5757)),
                                                        shape = RoundedCornerShape(4.dp),
                                                        modifier = Modifier.padding(end = 4.dp)
                                                    ) {
                                                        Text(
                                                            "NEW",
                                                            fontSize = 8.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color.White,
                                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                        )
                                                    }
                                                }
                                                if (ex.isPopup) {
                                                    Card(
                                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2F80ED)),
                                                        shape = RoundedCornerShape(4.dp)
                                                    ) {
                                                        Text(
                                                            "POP-UP",
                                                            fontSize = 8.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color.White,
                                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                        )
                                                    }
                                                }
                                            }

                                            // Year/Curation mark
                                            Text(
                                                text = "AIVY SELECTED",
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White.copy(alpha = 0.6f),
                                                letterSpacing = 1.sp
                                            )
                                        }

                                        // Big text title inside poster
                                        Text(
                                            text = exTitle,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White,
                                            fontFamily = FontFamily.Serif,
                                            lineHeight = 22.sp,
                                            maxLines = 2
                                        )
                                    }
                                }

                                // Bottom Details segment
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CalendarToday,
                                            contentDescription = "Schedule",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = exPeriod,
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Place,
                                            contentDescription = "Place",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = exLoc,
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Text(
                                        text = exDesc,
                                        fontSize = 12.sp,
                                        color = Color(0xFF4F4F4F),
                                        lineHeight = 17.sp
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Tags
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        ex.tags.forEach { tag ->
                                            Box(
                                                modifier = Modifier
                                                    .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text(text = "#$tag", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
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

        // Action Simulated Dialog overlays
        if (showActionDialog != null) {
            AlertDialog(
                onDismissRequest = { showActionDialog = null },
                title = {
                    Text(
                        text = when (showActionDialog) {
                            "map" -> if (language == "EN") "Opening Navigation..." else if (language == "JA") "ナビ連携中..." else "길찾기 연동 (지도)"
                            "ticket" -> if (language == "EN") "Exhibition Ticketing" else if (language == "JA") "チケット予約連携" else "전시 예매하기"
                            else -> if (language == "EN") "Calendar Scheduled" else if (language == "JA") "カレンダー登録完了" else "캘린더 일정 저장"
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                text = {
                    Text(
                        text = when (showActionDialog) {
                            "map" -> if (language == "EN") "Launching Google Maps direction guide from current location to $selectedExhibitionId."
                            else if (language == "JA") "現在地から目的地までのナビを起動します。"
                            else "카카오맵/네이버 지도 앱으로 연동하여 현재 위치에서 전시관까지의 대중교통 및 도보 길찾기 안내를 로딩합니다."
                            
                            "ticket" -> if (language == "EN") "Connecting directly to standard ticket sales gateway for special coupon discounts."
                            else if (language == "JA") "提携プレイガイドの予約画面へ接続し、ドセント専用割引を適用します。"
                            else "인터파크/네이버 예약 공식 예매처로 연결됩니다. AIVY 제휴 회원가 및 학생 우대 할인쿠폰 혜택이 즉시 적용됩니다."
                            
                            else -> if (language == "EN") "Event has been saved to your calendar list with automated notification alert."
                            else if (language == "JA") "ご使用のカレンダーに展示スケジュールとリマインダーを登録しました。"
                            else "스마트폰 기본 캘린더에 전시 일정 일자와 리마인더 알림이 성공적으로 추가되었습니다. 시작 1일 전 푸시 알림이 발송됩니다."
                        },
                        fontSize = 13.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )
                },
                confirmButton = {
                    TextButton(onClick = { showActionDialog = null }) {
                        Text(
                            text = if (language == "EN") "OK" else if (language == "JA") "確認" else "확인",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2F80ED)
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White
            )
        }
    }
}

@Composable
fun ExhibitionDetailView(
    exhibition: Exhibition,
    language: String,
    onBack: () -> Unit,
    onTriggerAction: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    val exTitle = when (language) {
        "EN" -> exhibition.titleEn
        "JA" -> exhibition.titleJa
        else -> exhibition.titleKo
    }
    val exPeriod = when (language) {
        "EN" -> exhibition.periodEn
        "JA" -> exhibition.periodJa
        else -> exhibition.periodKo
    }
    val exLoc = when (language) {
        "EN" -> exhibition.locationEn
        "JA" -> exhibition.locationJa
        else -> exhibition.locationKo
    }
    val exDesc = when (language) {
        "EN" -> exhibition.descriptionEn
        "JA" -> exhibition.descriptionJa
        else -> exhibition.descriptionKo
    }
    val exFee = when (language) {
        "EN" -> exhibition.feeEn
        "JA" -> exhibition.feeJa
        else -> exhibition.feeKo
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // Hero Image Poster header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(Color(exhibition.posterColor))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Back button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .size(36.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                    // AIVY select badge
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("AIVY CURATED", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                // Title
                Text(
                    text = exTitle,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    lineHeight = 28.sp
                )
            }
        }

        // Info segment
        Column(modifier = Modifier.padding(20.dp)) {
            // Loc and date summary
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRowItem(icon = Icons.Default.CalendarToday, title = if (language == "EN") "Exhibition Period" else if (language == "JA") "期間" else "전시 기간", value = exPeriod)
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRowItem(icon = Icons.Default.Place, title = if (language == "EN") "Exhibition Hall" else if (language == "JA") "会場" else "전시 장소", value = exLoc)
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRowItem(icon = Icons.Default.Payment, title = if (language == "EN") "Admission Fee" else if (language == "JA") "入場料" else "관람 요금", value = exFee)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Long immersive description
            Text(
                text = if (language == "EN") "Introduction" else if (language == "JA") "展示紹介" else "전시 소개",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = exDesc + "\n\n" + (
                    if (language == "EN") "This special curation is fully equipped with AIVY's screenless companion features. Walk freely inside the gallery, let open-ear bone conduction deliver automated commentaries based on your coordinates, and raise follow-up questions any time without taking your eyes off the masterpieces."
                    else if (language == "JA") "本展示はAIVYによるバリアフリー音声ドセント連携に対応しています。展示室内の作品の前に立つと、自動的に音声解説が開始されます。作品に目を留めたまま、心ゆくまでAIとの対話をお楽しみください。"
                    else "본 전시관은 AIVY 스크린리스 지능형 오디오 연동이 지원되는 특화 전시장입니다. 관람객이 전시장 내 개별 작품에 접근하면 소형 비콘 신호를 수신해 모바일 화면 조작 없이 스마트폰과 골전도 이어폰으로 자동 해설이 낭독됩니다. 작품에 지속해서 시선을 집중하며 자유롭게 AI 도슨트와 깊이 있는 실시간 질의응답 대화를 경험해보세요."
                ),
                fontSize = 13.sp,
                color = Color(0xFF4F4F4F),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action triggers row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Direction guide
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTriggerAction("map") }
                ) {
                    IconButton(
                        onClick = { onTriggerAction("map") },
                        modifier = Modifier
                            .background(Color(0xFFF5F5F5), CircleShape)
                            .size(50.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Directions, contentDescription = "Map Launcher", tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (language == "EN") "Get Directions" else if (language == "JA") "アクセス" else "길찾기",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Booking ticket
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTriggerAction("ticket") }
                ) {
                    IconButton(
                        onClick = { onTriggerAction("ticket") },
                        modifier = Modifier
                            .background(Color(0xFFF5F5F5), CircleShape)
                            .size(50.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ConfirmationNumber, contentDescription = "Booking", tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (language == "EN") "Book Tickets" else if (language == "JA") "チケット予約" else "예매하기",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Calendar event addition
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTriggerAction("calendar") }
                ) {
                    IconButton(
                        onClick = { onTriggerAction("calendar") },
                        modifier = Modifier
                            .background(Color(0xFFF5F5F5), CircleShape)
                            .size(50.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendar Addition", tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (language == "EN") "Add Calendar" else if (language == "JA") "カレンダー登録" else "일정 추가",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailRowItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = Color.Gray, modifier = Modifier.size(16.dp).padding(top = 2.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.Black, modifier = Modifier.padding(top = 2.dp))
        }
    }
}
