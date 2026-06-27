package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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

@Composable
fun OnboardingScreen(
    viewModel: AivyViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val perspective by viewModel.selectedPerspective.collectAsState()
    val accessMode by viewModel.accessibilityMode.collectAsState()

    var step by remember { mutableStateOf(1) }
    val scrollState = rememberScrollState()

    // Soft neutral warm white/light background representing gallery aesthetics
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)) // Slate background
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                // Aesthetic geometric gallery logo
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .border(1.5.dp, Color(0xFF0061A4), RoundedCornerShape(16.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFF0061A4), RoundedCornerShape(8.dp))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "AIVY",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 4.sp,
                    color = Color(0xFF1A1C1E)
                )

                Text(
                    text = "Screenless AI Docent",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Stepper progress indicator
                Row(
                    modifier = Modifier.width(120.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .padding(horizontal = 4.dp)
                                .background(
                                    if (step >= i) Color(0xFF0061A4) else Color(0xFFE2E8F0),
                                    RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }
            }

            // Interactive Content Segment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = step,
                    transitionSpec = {
                        slideInHorizontally { width -> if (targetState > initialState) width else -width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> if (targetState > initialState) -width else width } + fadeOut()
                    },
                    label = "onboarding_step_transition"
                ) { currentStep ->
                    when (currentStep) {
                        1 -> LanguageSelectionView(language) { viewModel.setLanguage(it) }
                        2 -> PerspectiveSelectionView(perspective, language) { viewModel.setPerspective(it) }
                        3 -> AccessibilitySelectionView(accessMode, language) { viewModel.setAccessibilityMode(it) }
                    }
                }
            }

            // Bottom Navigation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (step > 1) {
                    OutlinedButton(
                        onClick = { step-- },
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF475569)
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .height(54.dp)
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (language == "EN") "Back" else if (language == "JA") "戻る" else "이전",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Button(
                    onClick = {
                        if (step < 3) {
                            step++
                        } else {
                            viewModel.completeOnboarding()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0061A4),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .height(54.dp)
                        .weight(if (step > 1) 1f else 2f)
                        .padding(start = if (step > 1) 8.dp else 0.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (step < 3) {
                                if (language == "EN") "Next" else if (language == "JA") "次へ" else "다음"
                            } else {
                                if (language == "EN") "Start Journey" else if (language == "JA") "観賞を始める" else "시작하기"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow Forward",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageSelectionView(
    currentLang: String,
    onLangSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Language",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF1A1C1E),
            textAlign = TextAlign.Center
        )
        Text(
            text = "도슨트 해설과 음성 질의응답에 사용될 언어를 선택하세요.",
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        val languages = listOf(
            Triple("KR", "한국어 (Korean)", "작품 해설과 질의응답이 한국어로 진행됩니다."),
            Triple("EN", "English", "Commentaries and chat will be guided in English."),
            Triple("JA", "日本語 (Japanese)", "解説および音声対話が日本語で行われます。")
        )

        languages.forEach { (code, name, desc) ->
            val isSelected = currentLang == code
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF0061A4) else Color.White
                ),
                border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onLangSelect(code) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onLangSelect(code) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color(0xFF0061A4)
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (isSelected) Color.White else Color(0xFF1A1C1E)
                        )
                        Text(
                            text = desc,
                            fontSize = 12.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color(0xFF64748B),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PerspectiveSelectionView(
    currentPerspective: String,
    lang: String,
    onPerspectiveSelect: (String) -> Unit
) {
    val title = when (lang) {
        "EN" -> "Commentary Focus"
        "JA" -> "解説の主な観点"
        else -> "관심 관점 선택"
    }
    val subtitle = when (lang) {
        "EN" -> "Choose your preferred angle. Commentary focus will shift accordingly."
        "JA" -> "お好みの観点を選択してください。解説の焦点がその角度に調整されます。"
        else -> "선택하신 관점에 맞춰 AIVY가 작품 해설의 깊이와 질문 대응을 조율합니다."
    }

    val perspectives = listOf(
        Triple(
            "Philosophy",
            if (lang == "EN") "Artist Philosophy" else if (lang == "JA") "画家の哲学・意図" else "작가 철학과 예술 세계",
            if (lang == "EN") "Explore the life, thoughts, and spiritual motifs behind the paintings." else if (lang == "JA") "画家が生涯抱いた悩みや、作品に込めた精神的意図に焦点を当てます。" else "화가의 극적인 생애, 내면의 생각, 삶에 대한 성찰을 해설에 풍부하게 담습니다."
        ),
        Triple(
            "Technique",
            if (lang == "EN") "Artistic Techniques" else if (lang == "JA") "技法と色彩" else "미술 기법과 표현 방식",
            if (lang == "EN") "Learn about unique brushstrokes, gold leaf, and optical theory details." else if (lang == "JA") "インパスト、点描、黄金分割など、卓越した技術的詳細に焦점을当てます。" else "임파스토, 점묘, 캔버스 구조, 명암 대조 등 표현적이고 기하학적인 화법을 집중 해설합니다."
        ),
        Triple(
            "History",
            if (lang == "EN") "Historical Context" else if (lang == "JA") "歴史的背景" else "역사적 배경과 에피소드",
            if (lang == "EN") "Dive into centuries-old social environments and shocking reception stories." else if (lang == "JA") "作品が誕生した時代の社会的環境や、当時の衝撃的な世論を追体験します。" else "작품이 탄생한 시대적 조류, 소장 미술관의 역사, 세상을 바꾼 숨은 에피소드를 다룹니다."
        ),
        Triple(
            "Technology",
            if (lang == "EN") "Optical & Science" else if (lang == "JA") "光と科学の視점" else "광학 분석과 색채 과학",
            if (lang == "EN") "Understand color theories, camera obscura setups, and optical physics." else if (lang == "JA") "カメラ・オブスクラや補色対比など、美術に溶け込んだ科学的原理を解析します。" else "카메라 옵스쿠라의 원리, 눈 속에 일어나는 보색 혼합 등 미술 속에 녹아든 과학을 해설합니다."
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF1A1C1E),
            textAlign = TextAlign.Center
        )
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        perspectives.forEach { (code, name, desc) ->
            val isSelected = currentPerspective == code
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF0061A4) else Color.White
                ),
                border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onPerspectiveSelect(code) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (code) {
                                "Philosophy" -> Icons.Default.AutoAwesome
                                "Technique" -> Icons.Default.Brush
                                "History" -> Icons.Default.HistoryEdu
                                else -> Icons.Default.Science
                            },
                            contentDescription = name,
                            tint = if (isSelected) Color.White else Color(0xFF0061A4),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (isSelected) Color.White else Color(0xFF1A1C1E)
                        )
                    }
                    Text(
                        text = desc,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color(0xFF64748B),
                        modifier = Modifier.padding(top = 4.dp, start = 30.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AccessibilitySelectionView(
    currentMode: String,
    lang: String,
    onModeSelect: (String) -> Unit
) {
    val title = when (lang) {
        "EN" -> "Accessibility Mode"
        "JA" -> "バリアフリー選択"
        else -> "접근성 모드 설정"
    }
    val subtitle = when (lang) {
        "EN" -> "Set up support configurations for your convenience."
        "JA" -> "ご観賞に合わせて聞き取りやすい音声設定を選択してください。"
        else -> "사용자의 관람 환경과 연령을 고려하여 최적의 해설 구성을 맞춥니다."
    }

    val modes = listOf(
        Triple(
            "Standard",
            if (lang == "EN") "Standard Mode" else if (lang == "JA") "標準モード" else "표준 모드",
            if (lang == "EN") "Standard narration speed with deep professional art commentaries." else if (lang == "JA") "標準の読み上げ速度で、本格的で豊富な専門解説を提供します。" else "일반적인 속도와 어조로 미술관의 깊이 있는 예술적 해설을 제공합니다."
        ),
        Triple(
            "SlowEasy",
            if (lang == "EN") "Slow & Clear Mode" else if (lang == "JA") "ゆっくり・易しいモード" else "느린 속도·쉬운 표현 모드",
            if (lang == "EN") "Generous font sizes, slow clear TTS, and easy terms (Great for seniors, kids, or visually impaired)." else if (lang == "JA") "大きな文字、ゆっくりした朗読、簡単な用語を使用（低視力・高齢者向け）。" else "더 크고 선명한 글씨체, 약 20% 느린 속도의 낭독, 그리고 어려운 예술 용어를 배제한 쉬운 표현식 설명입니다."
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF1A1C1E),
            textAlign = TextAlign.Center
        )
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        modes.forEach { (code, name, desc) ->
            val isSelected = currentMode == code
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF0061A4) else Color.White
                ),
                border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable { onModeSelect(code) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (code == "Standard") Icons.Default.Hearing else Icons.Default.RecordVoiceOver,
                        contentDescription = name,
                        tint = if (isSelected) Color.White else Color(0xFF0061A4),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (isSelected) Color.White else Color(0xFF1A1C1E)
                        )
                        Text(
                            text = desc,
                            fontSize = 12.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color(0xFF64748B),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
