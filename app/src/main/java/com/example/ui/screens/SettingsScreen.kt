package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.state.AivyViewModel

@Composable
fun SettingsScreen(
    viewModel: AivyViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val perspective by viewModel.selectedPerspective.collectAsState()
    val accessMode by viewModel.accessibilityMode.collectAsState()
    val speed by viewModel.playbackSpeed.collectAsState()
    val device by viewModel.outputDevice.collectAsState()

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)) // Editorial Slate background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = when (language) {
                        "EN" -> "AIVY SETTINGS"
                        "JA" -> "ドセント詳細設定"
                        else -> "설정"
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 2.sp,
                    color = Color(0xFF1A1C1E)
                )
                Text(
                    text = when (language) {
                        "EN" -> "Configure preferences and bone-conduction channels"
                        "JA" -> "バリアフリー環境設定と動作確認"
                        else -> "관람 환경 맞춤화 및 장치 시뮬레이션 제어"
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 1: Device Configuration
            SectionTitle(title = if (language == "EN") "My Devices & Simulation" else if (language == "JA") "骨伝導動作環境" else "장치 연동 및 오디오 시뮬레이션")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = if (language == "EN") "Hardware Mode" else if (language == "JA") "出力デバイスの役割" else "골전도 웨어러블 하드웨어 시뮬레이터",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == "EN") "ACTIVE — Open-Ear Bone-Conduction simulation filters sound to prevent masking, ensuring you can hear both the docent and ambient museum conversations simultaneously."
                        else if (language == "JA") "動作中 — 周囲の会話や展示環境の音声を打ち消さないオープンイヤー型のイコライジング。作品からシ선을外さずに、臨場感あふれる美術鑑賞が可能です。"
                        else "현재 골전도 오픈이어(Open-Ear) 시뮬레이션 필터가 활성화 상태입니다. 전시장 주변의 미세한 관람 음향이나 동행인과의 자연스러운 대화 소리가 차단(마스킹)되지 않고 해설 음성과 함께 투명하게 어우러지도록 데시벨 필터링이 적용되어 있습니다.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0xFFF1F1F1))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Simulated output selection
                    SettingRowItem(
                        icon = Icons.Default.Hearing,
                        title = if (language == "EN") "Audio Target Channel" else if (language == "JA") "出力先" else "오디오 출력 장치",
                        value = if (device == "이어폰 (골전도 개방형)") {
                            if (language == "EN") "Open-Ear Headset" else if (language == "JA") "骨伝導イヤホン" else "골전도 이어폰"
                        } else {
                            if (language == "EN") "Smartphone Speaker" else if (language == "JA") "スピーカー" else "스마트폰 스피커"
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Section 2: Docent Configuration
            SectionTitle(title = if (language == "EN") "Docent Profile" else if (language == "JA") "ドセント解説設定" else "도슨트 프로필 및 해설 설정")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    SettingRowClickable(
                        icon = Icons.Default.Language,
                        title = if (language == "EN") "Language Settings" else if (language == "JA") "言語" else "사용 언어 설정",
                        value = when (language) {
                            "EN" -> "English"
                            "JA" -> "日本語 (Japanese)"
                            else -> "한국어 (Korean)"
                        },
                        onClick = {
                            val next = when (language) {
                                "KR" -> "EN"
                                "EN" -> "JA"
                                else -> "KR"
                            }
                            viewModel.setLanguage(next)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFFF1F1F1))
                    Spacer(modifier = Modifier.height(12.dp))

                    SettingRowClickable(
                        icon = Icons.Default.FilterAlt,
                        title = if (language == "EN") "Commentary Perspective" else if (language == "JA") "解説の焦点" else "맞춤형 해설 관점",
                        value = when (perspective) {
                            "Philosophy" -> if (language == "EN") "Philosophy" else if (language == "JA") "哲学" else "작가 철학과 예술 세계"
                            "Technique" -> if (language == "EN") "Technique" else if (language == "JA") "技法" else "미술 기법과 표현 방식"
                            "History" -> if (language == "EN") "History" else if (language == "JA") "歴史" else "역사적 배경과 에피소드"
                            else -> if (language == "EN") "Science" else if (language == "JA") "科学" else "광학 분석과 색채 과학"
                        },
                        onClick = {
                            val next = when (perspective) {
                                "Philosophy" -> "Technique"
                                "Technique" -> "History"
                                "History" -> "Technology"
                                else -> "Philosophy"
                            }
                            viewModel.setPerspective(next)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFFF1F1F1))
                    Spacer(modifier = Modifier.height(12.dp))

                    SettingRowClickable(
                        icon = Icons.Default.Hearing,
                        title = if (language == "EN") "Accessibility Mode" else if (language == "JA") "バリアフリー" else "접근성 도우미 설정",
                        value = if (accessMode == "SlowEasy") {
                            if (language == "EN") "Slow & Clear" else if (language == "JA") "ゆっくり・易しい" else "느린 속도·쉬운 표현"
                        } else {
                            if (language == "EN") "Standard Mode" else if (language == "JA") "標準" else "표준 모드"
                        },
                        onClick = {
                            val next = if (accessMode == "Standard") "SlowEasy" else "Standard"
                            viewModel.setAccessibilityMode(next)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFFF1F1F1))
                    Spacer(modifier = Modifier.height(12.dp))

                    SettingRowClickable(
                        icon = Icons.Default.Speed,
                        title = if (language == "EN") "Speech Speed multiplier" else if (language == "JA") "朗読速度" else "기본 음성 속도 배율",
                        value = "${speed}x",
                        onClick = {
                            val next = when (speed) {
                                0.8f -> 1.0f
                                1.0f -> 1.2f
                                1.2f -> 1.5f
                                else -> 0.8f
                            }
                            viewModel.changePlaybackSpeed(next)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Button to trigger onboarding restart (for testing the app easily)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.selectTab("onboarding") },
                    border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.4f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFFEF4444)
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = if (language == "EN") "Restart Onboarding Setup" else if (language == "JA") "初期設定（オンボーディング）をやり直す" else "온보딩 최초 설정 단계로 돌아가기",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF64748B),
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 24.dp, bottom = 6.dp)
    )
}

@Composable
fun SettingRowItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = title, tint = Color(0xFF0061A4), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontSize = 13.sp, color = Color(0xFF1A1C1E), fontWeight = FontWeight.Bold)
        }
        Text(text = value, fontSize = 13.sp, color = Color(0xFF64748B), fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SettingRowClickable(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = title, tint = Color(0xFF0061A4), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontSize = 13.sp, color = Color(0xFF1A1C1E), fontWeight = FontWeight.Bold)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, fontSize = 13.sp, color = Color(0xFF0061A4), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Go", tint = Color(0xFF94A3B8), modifier = Modifier.size(16.dp))
        }
    }
}
