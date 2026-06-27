package com.example.ui.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Artwork
import com.example.data.ArtworkRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val sender: String, // "user" or "aivy"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class Exhibition(
    val id: String,
    val titleKo: String,
    val titleEn: String,
    val titleJa: String,
    val periodKo: String,
    val periodEn: String,
    val periodJa: String,
    val locationKo: String,
    val locationEn: String,
    val locationJa: String,
    val descriptionKo: String,
    val descriptionEn: String,
    val descriptionJa: String,
    val isPopup: Boolean,
    val isNew: Boolean,
    val tags: List<String>,
    val feeKo: String,
    val feeEn: String,
    val feeJa: String,
    val posterColor: Long
)

class AivyViewModel : ViewModel() {

    // Onboarding State
    private val _selectedLanguage = MutableStateFlow("KR") // KR, EN, JA
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    private val _selectedPerspective = MutableStateFlow("Technique") // Philosophy, Technique, History, Technology
    val selectedPerspective: StateFlow<String> = _selectedPerspective.asStateFlow()

    private val _accessibilityMode = MutableStateFlow("Standard") // Standard, SlowEasy
    val accessibilityMode: StateFlow<String> = _accessibilityMode.asStateFlow()

    private val _isOnboardingCompleted = MutableStateFlow(true)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()

    // Screenless Audio Player State
    private val _selectedArtworkId = MutableStateFlow("starry_night")
    val selectedArtworkId: StateFlow<String> = _selectedArtworkId.asStateFlow()

    private val _recentlyViewedIds = MutableStateFlow<List<String>>(listOf("water_lilies", "the_kiss"))
    val recentlyViewedIds: StateFlow<List<String>> = _recentlyViewedIds.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0f)
    val playbackProgress: StateFlow<Float> = _playbackProgress.asStateFlow()

    private val _isDetailedNarrative = MutableStateFlow(false) // false: 20s Summary, true: 1m Detailed
    val isDetailedNarrative: StateFlow<Boolean> = _isDetailedNarrative.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1.0f) // 0.8f, 1.0f, 1.2f, 1.5f
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val _currentSubtitle = MutableStateFlow("")
    val currentSubtitle: StateFlow<String> = _currentSubtitle.asStateFlow()

    private val _outputDevice = MutableStateFlow("이어폰 (골전도 개방형)") // 스피커, 이어폰 (골전도 개방형)
    val outputDevice: StateFlow<String> = _outputDevice.asStateFlow()

    // Chatbot State
    private val _isQuietMode = MutableStateFlow(false) // "조용히 보기" chat mode
    val isQuietMode: StateFlow<Boolean> = _isQuietMode.asStateFlow()

    // Map of Artwork ID to list of chat messages
    val chatHistory = mutableStateMapOf<String, List<ChatMessage>>()

    // Bookmark State (Saved Notes)
    val bookmarkedArtworkIds = mutableStateListOf<String>()
    val bookmarkNotes = mutableStateMapOf<String, String>() // Personalized summaries

    // Current Tab Navigation State
    private val _currentTab = MutableStateFlow("docent") // onboarding, docent, notes, explore, settings
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    // Explore Page Navigation State
    private val _selectedExhibitionId = MutableStateFlow<String?>(null)
    val selectedExhibitionId: StateFlow<String?> = _selectedExhibitionId.asStateFlow()

    // Mock Audio playback ticker job
    private var playbackJob: Job? = null

    // Recommended Exhibitions Data List
    val exhibitions = listOf(
        Exhibition(
            id = "sim_daeun_universal",
            titleKo = "보편타당한 당신 ― 심이다은",
            titleEn = "The Universal You ― Da-eun Sim",
            titleJa = "普遍妥当なあなた ― シム・イダウン",
            periodKo = "2026.06.25 ~ 2027.04.11",
            periodEn = "Jun 25, 2026 ~ Apr 11, 2027",
            periodJa = "2026年7月10日 ~ 2026年10月15日",
            locationKo = "서울시립 북서울미술관",
            locationEn = "Buk-Seoul Museum of Art (SeMA)",
            locationJa = "ソウル市立北ソウル美術館",
            descriptionKo = "인간 중심의 청각 바깥에 있는 존재들의 소리에 주목하는 미디어 작가 심이다은의 개인전. 실시간 환경음과 오디오 스트리밍을 통해 일상의 소리가 낯설게 바뀌는 특별한 체험.",
            descriptionEn = "A solo exhibition by media artist Da-eun Sim, focusing on sounds beyond human-centered hearing through real-time environmental audio streaming.",
            descriptionJa = "人間中心の聴覚の外にある存在の音に注目するメディアアーティスト、シム・イダウンの個展。リアルタイムの環境音ストリーミングを通じて、日常の音が新しく変化する体験。",
            isPopup = false,
            isNew = true,
            tags = listOf("미디어아트", "사운드", "현대미술"),
            feeKo = "무료",
            feeEn = "Free",
            feeJa = "無料",
            posterColor = 0xFF0D9488
        ),
        Exhibition(
            id = "deep_blue_ocean",
            titleKo = "Deep Blue, 다시 흐르는 바다",
            titleEn = "Deep Blue, The Flowing Ocean Again",
            titleJa = "Deep Blue、再び流れる海",
            periodKo = "2026.05.01 ~ 2026.09.30",
            periodEn = "May 01, 2026 ~ Sep 30, 2026",
            periodJa = "2026年5月1日 ~ 2026年9月30日",
            locationKo = "그랜드 워커힐 서울",
            locationEn = "Grand Walkerhill Seoul",
            locationJa = "グランド・ウォーカーヒル・ソウル",
            descriptionKo = "깊고 푸른 바다 속 생명의 경이와 변화, 그리고 미래를 향한 희망을 담은 이머시브 캠페인. 빛과 사운드로 펼쳐지는 거대한 바다 여행 속에서 우리는 서로 연결된 삶과 미래를 마주하게 됩니다.",
            descriptionEn = "An immersive campaign capturing the wonders of life, changes, and hope in the deep blue ocean. Meet our interconnected lives and futures in a grand journey of light and sound.",
            descriptionJa = "深く青い海の中の生命の驚異と変化、그리고 미래를 향한 희망을 담은 이머시브 캠페인. 빛과 사운드로 펼쳐지는 거대한 바다 여행 속에서 우리는 서로 연결된 삶과 미래를 마주하게 됩니다.",
            isPopup = true,
            isNew = false,
            tags = listOf("이머시브", "푸른바다", "미디어아트"),
            feeKo = "성인 29,000원",
            feeEn = "Adult 29,000 KRW",
            feeJa = "一般 29,000ウォン",
            posterColor = 0xFF0284C7
        ),
        Exhibition(
            id = "yoo_youngkuk_mountain",
            titleKo = "유영국: 산은 내 안에 있다",
            titleEn = "Yoo Youngkuk: Mountain Within Me",
            titleJa = "劉永国：山は私の中にある",
            periodKo = "2026.05.19 ~ 2026.10.25",
            periodEn = "May 19, 2026 ~ Oct 25, 2026",
            periodJa = "2026年8月1日 ~ 2026年11月20日",
            locationKo = "서울시립미술관 서소문본관",
            locationEn = "Seoul Museum of Art (Seosomun)",
            locationJa = "ソウル市立美術館 西小門本館",
            descriptionKo = "한국 추상미술의 선구자 유영국 탄생 110주년 역대 최대 규모 회고전. 미공개 회화, 드로잉 등 170여 점을 통해 점, 선, 면, 색으로 구성된 내면의 아름다움을 조망합니다.",
            descriptionEn = "An extensive retrospective celebrating the 110th anniversary of Yoo Youngkuk, pioneer of Korean abstract art. Features over 170 masterpieces portraying nature and internal structures.",
            descriptionJa = "韓国抽象美術の先駆者、劉永国の生誕110周年を記念する過去最大規模の回顧展。未公開作を含む170余点を通じ、点・線・面・色で構成された内面を眺めます。",
            isPopup = false,
            isNew = true,
            tags = listOf("추상미술", "근대거장", "회고전"),
            feeKo = "무료",
            feeEn = "Free",
            feeJa = "無料",
            posterColor = 0xFF7C3AED
        )
    )

    init {
        // Initialize chat history with welcome messages for the default artwork
        initializeChatForArtwork("starry_night")
    }

    fun setLanguage(lang: String) {
        _selectedLanguage.value = lang
    }

    fun setPerspective(perspective: String) {
        _selectedPerspective.value = perspective
    }

    fun setAccessibilityMode(mode: String) {
        _accessibilityMode.value = mode
    }

    fun completeOnboarding() {
        _isOnboardingCompleted.value = true
        _currentTab.value = "docent"
    }

    fun selectArtwork(id: String) {
        if (_selectedArtworkId.value != id) {
            val previousId = _selectedArtworkId.value
            _selectedArtworkId.value = id
            // Pause any playing audio
            pauseAudio()
            _playbackProgress.value = 0f
            _isDetailedNarrative.value = false
            _currentSubtitle.value = ""
            initializeChatForArtwork(id)

            // Update recently viewed with previous ID
            val currentList = _recentlyViewedIds.value.filter { it != previousId && it != id }
            _recentlyViewedIds.value = (listOf(previousId) + currentList).take(5)
        }
    }

    fun setQuietMode(quiet: Boolean) {
        _isQuietMode.value = quiet
    }

    fun selectTab(tab: String) {
        _currentTab.value = tab
        if (tab != "explore") {
            _selectedExhibitionId.value = null
        }
    }

    fun viewExhibitionDetail(id: String?) {
        _selectedExhibitionId.value = id
    }

    fun toggleAudio() {
        if (_isPlaying.value) {
            pauseAudio()
        } else {
            startAudio()
        }
    }

    fun selectNarrativeMode(detailed: Boolean) {
        _isDetailedNarrative.value = detailed
        _playbackProgress.value = 0f
        if (_isPlaying.value) {
            startAudio()
        } else {
            updateSubtitleForProgress(0f)
        }
    }

    fun changePlaybackSpeed(speed: Float) {
        _playbackSpeed.value = speed
        // If playing, restart the job to accommodate speed change
        if (_isPlaying.value) {
            startAudio()
        }
    }

    fun changeOutputDevice(device: String) {
        _outputDevice.value = device
    }

    fun seekTo(progress: Float) {
        _playbackProgress.value = progress.coerceIn(0f, 1f)
        updateSubtitleForProgress(_playbackProgress.value)
    }

    private fun startAudio() {
        playbackJob?.cancel()
        _isPlaying.value = true
        
        val artwork = ArtworkRepository.getArtworkById(_selectedArtworkId.value) ?: return
        val textList = getSubtitlesList(artwork, _isDetailedNarrative.value, _selectedLanguage.value)
        
        // Tick rate: total simulation duration is 20s for summary, 45s for detailed
        val totalDurationMs = if (_isDetailedNarrative.value) 45000L else 20000L
        val intervalMs = 150L // Progress updates every 150ms
        
        playbackJob = viewModelScope.launch {
            val startProgress = _playbackProgress.value
            var elapsedMs = (startProgress * totalDurationMs).toLong()
            
            while (elapsedMs < totalDurationMs && _isPlaying.value) {
                // Incorporate playback speed
                val speedFactor = _playbackSpeed.value
                val adjustedInterval = (intervalMs / speedFactor).toLong()
                
                delay(adjustedInterval)
                elapsedMs += intervalMs
                val progress = (elapsedMs.toFloat() / totalDurationMs).coerceIn(0f, 1f)
                _playbackProgress.value = progress
                
                // Update subtitle
                val listIndex = ((progress * textList.size).toInt()).coerceIn(0, textList.size - 1)
                _currentSubtitle.value = textList[listIndex]
            }
            // Finished playing
            if (_playbackProgress.value >= 0.99f) {
                _isPlaying.value = false
                _playbackProgress.value = 0f
                _currentSubtitle.value = ""
            }
        }
    }

    fun pauseAudio() {
        _isPlaying.value = false
        playbackJob?.cancel()
    }

    private fun updateSubtitleForProgress(progress: Float) {
        val artwork = ArtworkRepository.getArtworkById(_selectedArtworkId.value) ?: return
        val textList = getSubtitlesList(artwork, _isDetailedNarrative.value, _selectedLanguage.value)
        if (textList.isNotEmpty()) {
            val listIndex = ((progress * textList.size).toInt()).coerceIn(0, textList.size - 1)
            _currentSubtitle.value = textList[listIndex]
        }
    }

    private fun getSubtitlesList(artwork: Artwork, detailed: Boolean, lang: String): List<String> {
        val fullText = when (lang) {
            "EN" -> if (detailed) artwork.detailedEn else artwork.summaryEn
            "JA" -> if (detailed) artwork.detailedJa else artwork.summaryJa
            else -> if (detailed) artwork.detailedKo else artwork.summaryKo
        }
        // Split text into beautiful bite-sized phrases for readable subtitles
        return fullText.split(". ", ",\n", "· ").filter { it.isNotBlank() }.map { it.trim() + "." }
    }

    private fun initializeChatForArtwork(artworkId: String) {
        if (!chatHistory.containsKey(artworkId)) {
            val artwork = ArtworkRepository.getArtworkById(artworkId) ?: return
            val welcomeText = when (_selectedLanguage.value) {
                "EN" -> "Hello! I am AIVY, your personal docent for '${artwork.titleEn}'. Feel free to ask about the behind-the-scenes stories, artistic intent, or specific techniques used in this work!"
                "JA" -> "こんにちは！ '${artwork.titleJa}'のパーソナル・ドセントのAIVYです。この作品の制作意図やビハ인ドストーリー、技法など、何でも気軽にお尋네ください！"
                else -> "안녕하세요! '${artwork.titleKo}'의 도슨트 AIVY입니다. 이 작품의 기법, 비하인드 스토리, 화가의 의도에 대해 무엇이든 편하게 물어보세요!"
            }
            chatHistory[artworkId] = listOf(ChatMessage(sender = "aivy", text = welcomeText))
        }
    }

    fun sendUserMessage(text: String) {
        val artworkId = _selectedArtworkId.value
        val artwork = ArtworkRepository.getArtworkById(artworkId) ?: return
        val history = chatHistory[artworkId]?.toMutableList() ?: mutableListOf()
        
        // Add User Message
        history.add(ChatMessage(sender = "user", text = text))
        chatHistory[artworkId] = history
        
        // Stop audio guide if playing, as they are asking a question (barge-in concept)
        pauseAudio()

        // Generate response with artificial tiny delay for conversational feeling
        viewModelScope.launch {
            delay(1000)
            val updatedHistory = chatHistory[artworkId]?.toMutableList() ?: mutableListOf()
            
            val replyText = findDummyResponse(artwork, text, _selectedLanguage.value)
            updatedHistory.add(ChatMessage(sender = "aivy", text = replyText))
            chatHistory[artworkId] = updatedHistory
        }
    }

    private fun findDummyResponse(artwork: Artwork, query: String, lang: String): String {
        val qnaMap = when (lang) {
            "EN" -> artwork.qnaEn
            "JA" -> artwork.qnaJa
            else -> artwork.qnaKo
        }

        // Try exact match in chips
        for ((key, value) in qnaMap) {
            if (query.contains(key, ignoreCase = true) || key.contains(query, ignoreCase = true)) {
                return value
            }
        }

        // Broad semantic fallbacks based on keywords
        val queryLower = query.lowercase()
        val intentKeywords = listOf("의도", "왜", "이유", "그린", "표현", "intent", "why", "purpose", "意図", "理由")
        val behindKeywords = listOf("비하인드", "스토리", "비화", "일화", "숨겨진", "behind", "story", "secret", "history", "裏話", "秘話", "エピソード")
        val artistKeywords = listOf("작가", "화가", "누구", "생애", "인물", "artist", "painter", "who", "creator", "作者", "画家")
        val techniqueKeywords = listOf("기법", "화법", "방법", "어떻게", "색칠", "technique", "style", "paint", "how", "技法", "描き方")

        return when {
            intentKeywords.any { queryLower.contains(it) } -> {
                when (lang) {
                    "EN" -> "My artistic intent analysis: " + artwork.intentEn
                    "JA" -> "制作者の意図： " + artwork.intentJa
                    else -> "이 작품의 핵심 의도입니다: " + artwork.intentKo
                }
            }
            behindKeywords.any { queryLower.contains(it) } -> {
                when (lang) {
                    "EN" -> "Here is an interesting behind-the-scenes secret: " + artwork.behindEn
                    "JA" -> "興味深い制作の裏話： " + artwork.behindJa
                    else -> "이 작품에 얽힌 흥미로운 비하인드 스토리입니다: " + artwork.behindKo
                }
            }
            artistKeywords.any { queryLower.contains(it) } -> {
                when (lang) {
                    "EN" -> "The artist of this work is ${artwork.artistEn}. He created this in ${artwork.year}. " + artwork.summaryEn
                    "JA" -> "この作品の作者は${artwork.artistJa}で、${artwork.year}年に制作されました。 " + artwork.summaryJa
                    else -> "이 작품의 작가는 ${artwork.artistKo}이며, ${artwork.year}년에 완성되었습니다. " + artwork.summaryKo
                }
            }
            techniqueKeywords.any { queryLower.contains(it) } -> {
                val defaultTech = qnaMap.keys.find { it.contains("기법") || it.contains("technique") || it.contains("技法") }
                if (defaultTech != null) qnaMap[defaultTech]!! else {
                    when (lang) {
                        "EN" -> "The painting features revolutionary brushstrokes and texture layering consistent with ${artwork.artistEn}'s unique style during ${artwork.year}."
                        "JA" -> "この絵画は、${artwork.year}年の${artwork.artistJa}の独特なスタイルに一致する、画期的な筆致と質感のレイヤーを特徴としています。"
                        else -> "이 작품은 ${artwork.year}년 당시 ${artwork.artistKo}의 독창적 화풍이 집약된 기법으로, 붓놀림과 섬세한 질감 묘사가 결합된 것이 특징입니다."
                    }
                }
            }
            else -> {
                // Default combined explanation
                when (lang) {
                    "EN" -> "That is a wonderful question! Let me tell you about the artistic intention. " + artwork.intentEn + "\n\nAlso, here is a fascinating behind-the-scenes story: " + artwork.behindEn
                    "JA" -> "とても素晴らしい質問ですね！まず、この作品の制作意図について説明します。 " + artwork.intentJa + "\n\nそして、このような興味深い裏話もあります。 " + artwork.behindJa
                    else -> "아주 좋은 질문입니다! 먼저 이 작품의 미술사적 의도를 짚어 드릴게요. " + artwork.intentKo + "\n\n더불어 작품에 숨겨진 재미있는 일화는 다음과 같습니다: " + artwork.behindKo
                }
            }
        }
    }

    fun toggleBookmark(artworkId: String) {
        if (bookmarkedArtworkIds.contains(artworkId)) {
            bookmarkedArtworkIds.remove(artworkId)
            bookmarkNotes.remove(artworkId)
        } else {
            bookmarkedArtworkIds.add(artworkId)
            
            // Generate a customized summary note for the card based on their questions or selected perspective!
            val artwork = ArtworkRepository.getArtworkById(artworkId) ?: return
            val askedQuestions = chatHistory[artworkId]?.filter { it.sender == "user" }?.map { "• ${it.text}" } ?: emptyList()
            
            val noteText = buildString {
                append(when (_selectedLanguage.value) {
                    "EN" -> "Personalized Guide Summary:\n" + artwork.summaryEn + "\n\n" + (if (askedQuestions.isNotEmpty()) "My Questions:\n" + askedQuestions.joinToString("\n") else "No custom questions asked.")
                    "JA" -> "パーソナライズされたガイド要約：\n" + artwork.summaryJa + "\n\n" + (if (askedQuestions.isNotEmpty()) "質問した内容：\n" + askedQuestions.joinToString("\n") else "質問履歴なし")
                    else -> "나의 개인 맞춤 도슨트 요약:\n" + artwork.summaryKo + "\n\n" + (if (askedQuestions.isNotEmpty()) "내가 관람 중 던진 질문들:\n" + askedQuestions.joinToString("\n") else "대화형 질문 없음 (오디오 요약 감상)")
                })
            }
            bookmarkNotes[artworkId] = noteText
        }
    }
}
