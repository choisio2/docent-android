package com.example.data

import androidx.compose.ui.graphics.Color

data class Artwork(
    val id: String,
    val titleKo: String,
    val titleEn: String,
    val titleJa: String,
    val artistKo: String,
    val artistEn: String,
    val artistJa: String,
    val year: String,
    val museumKo: String,
    val museumEn: String,
    val museumJa: String,
    val primaryColor: Color,
    val accentColor: Color,
    val summaryKo: String,
    val summaryEn: String,
    val summaryJa: String,
    val detailedKo: String,
    val detailedEn: String,
    val detailedJa: String,
    val intentKo: String,
    val intentEn: String,
    val intentJa: String,
    val behindKo: String,
    val behindEn: String,
    val behindJa: String,
    val qnaKo: Map<String, String>,
    val qnaEn: Map<String, String>,
    val qnaJa: Map<String, String>
)

object ArtworkRepository {
    val artworks = listOf(
        Artwork(
            id = "starry_night",
            titleKo = "별이 빛나는 밤",
            titleEn = "The Starry Night",
            titleJa = "星月夜",
            artistKo = "빈센트 반 고흐",
            artistEn = "Vincent van Gogh",
            artistJa = "フィンセント・ファン・ゴッホ",
            year = "1889",
            museumKo = "뉴욕 현대 미술관 (MoMA)",
            museumEn = "The Museum of Modern Art (MoMA)",
            museumJa = "ニューヨーク近代美術館 (MoMA)",
            primaryColor = Color(0xFF0F2042), // Deep blue
            accentColor = Color(0xFFFFD700),  // Yellow gold
            summaryKo = "요양원 창밖으로 본 밤하늘을 소용돌이치는 붓터치와 찬란한 노란색 별들로 그린 고흐의 대표작입니다.",
            summaryEn = "Painted from his asylum window, this masterpiece expresses Van Gogh's emotional state through swirling brushstrokes and glowing stars.",
            summaryJa = "療養所の窓から見た夜空を、うねるような筆致と輝く黄色い星々で描いたゴッホの代表作です。",
            detailedKo = "빈센트 반 고흐가 생레미의 요양원에 머물던 시절 그린 작품입니다. 격동적인 밤하늘은 그의 불안정한 정신 상태를 투영하는 동시에, 하늘을 향해 뻗어 있는 사이프러스 나무를 통해 삶과 죽음, 그리고 영원에 대한 그의 종교적 갈망을 보여줍니다.",
            detailedEn = "Vincent van Gogh painted this during his stay at the Saint-Rémy asylum. The turbulent sky projects his unstable state of mind, while the cypress tree stretching toward the heavens represents his religious longing for life, death, and eternity.",
            detailedJa = "フィンセント・ファン・ゴッホがサン＝レミの療養所に滞在していた時期に描かれた作品です。激動する夜空は彼の不安定な精神状態を投影すると同時に、天に向かって伸びる糸杉を通じて、生と死、そして永遠に対する彼の宗教的な渇望を示しています。",
            intentKo = "고흐는 눈에 보이는 그대로가 아닌, 자신이 느낀 격렬한 감정과 정신적 동요를 색채와 왜곡된 형태로 표현하고자 했습니다. 밤하늘의 소용돌이는 단순한 대기 현상이 아니라 영적 에너지의 시각화입니다.",
            intentEn = "Van Gogh sought to express intense emotion and spiritual turbulence through color and distorted forms, rather than literal depiction. The swirling sky is a visualization of spiritual energy.",
            intentJa = "ゴッホは、目に見える通りではなく、自身が感じた激しい感情や精神的動揺を色彩と歪んだ形態で表現しようとしました。夜空のうねりは、単なる大気現象ではなく、精神的エネルギーの視覚化です。",
            behindKo = "이 그림은 요양원 2층 창문에서 내다본 밤하늘을 바탕으로 그렸지만, 실제 창문에 있던 쇠창살은 생략되었습니다. 더불어 고흐는 남동생 테오에게 보낸 편지에서 이 작품을 '실패작'이라 부르며 상업적 가치가 없다고 생각했습니다.",
            behindEn = "While painted based on the view from his second-story window, the iron bars actually present were omitted. Surprisingly, Van Gogh called this painting a 'failure' in letters to his brother Theo, believing it held no commercial value.",
            behindJa = "この絵は療養所の2階の窓から見下ろした夜空をベースに描かれましたが、実際の窓にあった鉄格子は省略されています。また、ゴッホは弟のテオに宛てた手紙の中でこの作品を「失敗作」と呼び、商業的価値はないと考えていました。",
            qnaKo = mapOf(
                "이 기법은?" to "소용돌이치는 거친 붓터치를 겹쳐서 입체감을 주는 '임파스토(Impasto)' 기법입니다. 물감을 두껍게 칠해 질감을 강조했습니다.",
                "작가는 누구야?" to "네덜란드 출신의 후기 인상주의 화가 빈센트 반 고흐입니다. 강렬한 색채와 고유한 터치로 현대 미술의 시초가 되었습니다.",
                "비슷한 작품은?" to "고흐의 '아를의 별이 빛나는 밤(Starry Night Over the Rhône)'과 '밤의 카페 테라스'가 대표적인 밤하늘 연작입니다.",
                "사이프러스 나무의 의미?" to "왼쪽에 높이 솟은 어두운 갈색 나무는 사이프러스입니다. 전통적으로 공동묘지에 심겨 '죽음'을 상징하지만, 동시에 하늘을 찌를 듯 솟아올라 이승과 저승을 연결하는 통로를 의미하기도 합니다."
            ),
            qnaEn = mapOf(
                "What technique is used?" to "The Impasto technique, characterized by thick, layered paint application creating prominent 3D textures and heavy brushwork.",
                "Who is the artist?" to "Vincent van Gogh, a pioneering Dutch Post-Impressionist painter famous for his emotional, vibrant colors and expressive strokes.",
                "Similar artworks?" to "His other starry night scenes, such as 'Starry Night Over the Rhône' and 'Café Terrace at Night'.",
                "Meaning of the cypress?" to "The dark cypress tree on the left traditionally symbolizes mourning or death, but its flame-like reach also bridges the gap between earth and the cosmos."
            ),
            qnaJa = mapOf(
                "どんな技法？" to "絵の具を厚く塗り重ねて立体的な質感を与える「インパスト（厚塗り）」技法です。荒々しい筆致が特徴です。",
                "作者は誰？" to "オランダ出身の後期印象派の画家、フィンセント・ファン・ゴッホです。強烈な色彩と独特のタッチで現代美術に影響を与えました。",
                "似た作品は？" to "ゴッホの「ローヌ川の星月夜」や「夜のカフェテラス」が代表的な夜空の連作です。",
                "糸杉の意味は？" to "左側にそびえる暗い糸杉は、伝統的に「死」の象徴とされますが、天に向かって燃え上がるような姿は、地上と天界を結ぶ架け橋とも解釈されます。"
            )
        ),
        Artwork(
            id = "the_kiss",
            titleKo = "키스",
            titleEn = "The Kiss",
            titleJa = "接吻",
            artistKo = "구스타프 클림트",
            artistEn = "Gustav Klimt",
            artistJa = "グスタフ・クリムト",
            year = "1907-1908",
            museumKo = "빈 벨베데레 미술관",
            museumEn = "Österreichische Galerie Belvedere, Vienna",
            museumJa = "オーストリア・ギャラリー（ベルヴェデーレ宮殿）",
            primaryColor = Color(0xFFC5A059), // Gold tone
            accentColor = Color(0xFFD32F2F),  // Deep red
            summaryKo = "실제 금박을 입힌 기하학적 장식 속에 완전히 몰입한 연인의 영원한 결합을 노래한 클림트의 대표 명작입니다.",
            summaryEn = "Using actual gold leaf and geometric patterns, Klimt captures a couple completely dissolved into a single, eternal embrace.",
            summaryJa = "本物の金箔を施した幾何学的な装飾の中に、完全に没頭した恋人たちの永遠の結合を描いたクリムトの代表的名作です。",
            detailedKo = "구스타프 클림트의 '황금 시기' 절정을 상징하는 작품입니다. 꽃이 만발한 벼랑 끝에서 황금색 망토를 입은 두 연인이 입맞춤을 하고 있으며, 남성의 사각형 무늬와 여성의 둥근 꽃무늬가 음양의 조화를 이룹니다.",
            detailedEn = "Representing the peak of Gustav Klimt's 'Golden Phase'. Perched on a flower-covered cliff edge, two lovers wrapped in golden robes share an intimate kiss. The rectangular blocks on the man's robe contrast with the circular floral motifs of the woman's, symbolizing male and female principles.",
            detailedJa = "グスタフ・クリムトの「黄金時代」の絶頂期を象徴する作品です。花が咲き乱れる崖の縁で、黄金のローブをまとった二人の恋人がキスを交わしており、男性の四角い模様と女性の丸い花模様が陰陽の調和を成しています。",
            intentKo = "클림트는 영원하고 완전한 사랑의 상태를 우주적인 절대 공간인 '황금빛 장막'으로 표현했습니다. 벼랑 끝이라는 불안정한 배경은 사랑이 주는 극도의 황홀함과 아슬아슬함을 동시에 환기시킵니다.",
            intentEn = "Klimt expressed the state of eternal and absolute love as a cosmic, gilded sanctuary. The cliffside setting heightens the tension, evoking both ecstatic height and underlying vulnerability.",
            intentJa = "クリムトは、永遠かつ完全な愛の状態を、宇宙的な絶対空間である「黄金の帳」として表現しました。崖の縁という不安定な背景は、愛がもたらす極度の恍惚感と背中合わせの危うさを同時に呼び起こします。",
            behindKo = "당시 이 작품은 외설적이라는 보수적 학계의 비판을 받았으나, 작품이 미처 완성되기도 전에 오스트리아 국가 미술관이 당시 최고가로 선매입하였습니다. 그림 속 주인공은 클림트 본인과 그의 평생 동반자인 에밀리 플뢰게로 추정됩니다.",
            behindEn = "Though initially criticized as scandalous by conservative circles, the Austrian government purchased it before it was even finished for an unprecedented high price. The models are widely believed to be Klimt himself and his lifelong companion, Emilie Flöge.",
            behindJa = "当時、この作品は淫らであると保守的な学界から批判を受けましたが、完成する前にオーストリア政府が当時としては最高額で買い取りました。絵の主人公は、クリムト本人と彼の生涯の伴侶であったエミーリエ・フレーゲと推定されています。",
            qnaKo = mapOf(
                "이 기법은?" to "실제 금박과 은박을 물감과 함께 캔버스에 붙여 장식성을 극대화한 비잔틴 모자이크풍의 혼합 기법입니다.",
                "작가는 누구야?" to "오스트리아 비엔나 분리파의 거장인 구스타프 클림트입니다. 장식적이고 상징적인 화풍으로 세기말 인간의 욕망과 성을 탐구했습니다.",
                "비슷한 작품은?" to "클림트의 '아델레 블로흐-바우어의 초상 I' (황금의 아델레)과 '생명의 나무' 등이 황금기 대표작입니다.",
                "두 인물의 장식 차이는?" to "남성의 옷에는 흑백의 직사각형 무늬를 배열하여 강인함과 이성을 표현했고, 여성의 옷에는 파스텔톤의 둥근 꽃무늬를 사용해 유연함과 감수성을 상징적으로 대조했습니다."
            ),
            qnaEn = mapOf(
                "What technique is used?" to "Mixed media applying genuine gold and silver leaf onto the canvas, heavily influenced by Byzantine mosaic patterns.",
                "Who is the artist?" to "Gustav Klimt, leader of the Vienna Secession. Known for his decorative style exploring human eroticism and psychology.",
                "Similar artworks?" to "His other gold paintings like 'Portrait of Adele Bloch-Bauer I' and 'The Tree of Life'.",
                "Why the pattern difference?" to "The male figure features bold black-and-white rectangular shapes (representing structure/intellect), while the female features soft, circular floral shapes (representing fluidity/sensitivity)."
            ),
            qnaJa = mapOf(
                "どんな技法？" to "本物の金箔や銀箔を絵の具とともにキャンバスに貼り付け、装飾性を極限まで高めたビザンティン・モザイク調の混合技法です。",
                "作者は誰？" to "オーストリア・ウィーン分離派の巨匠グスタフ・クリムトです。装飾的で象徴的な画風で、世紀末の欲望や性を探求しました。",
                "似た作品は？" to "クリムトの「アデーレ・ブロッホ＝バウアーの肖像 I」や「生命の樹」などが黄金時代の代表作です。",
                "パターンの違いは？" to "男性の服には黒と白の直線の矩形模様を配して強靭さと理性を表し、女性の服にはパステルカラーの丸い花模様を用いて柔軟性と感受性を対比させています。"
            )
        ),
        Artwork(
            id = "water_lilies",
            titleKo = "수련",
            titleEn = "Water Lilies",
            titleJa = "睡蓮",
            artistKo = "클로드 모네",
            artistEn = "Claude Monet",
            artistJa = "クロード・モネ",
            year = "1916",
            museumKo = "오랑주리 미술관",
            museumEn = "Musée de l'Orangerie, Paris",
            museumJa = "オランジュリー美術館",
            primaryColor = Color(0xFF2C5542), // Deep water green
            accentColor = Color(0xFFE1BEE7),  // Soft pink lily
            summaryKo = "지베르니 연못 위에 반사되는 빛과 구름, 수련의 미묘한 색채 변화를 캔버스에 녹여낸 인상주의의 정수입니다.",
            summaryEn = "A serene capturing of reflected light, drifting clouds, and floating lilies on Monet's garden pond in Giverny.",
            summaryJa = "ジヴェルニーの池に反射する光や雲、睡蓮の繊細な色彩変化をキャンバスに溶け込ませた印象主義の極致です。",
            detailedKo = "클로드 모네가 말년에 지베르니 자택 정원의 수련 연못을 그린 연작 중 하나입니다. 지평선이나 구름의 경계가 없이, 오직 물의 표면에 투영된 연못 바닥의 수초와 수련, 그리고 반사된 하늘만이 신비로운 색조로 표현되어 캔버스가 하나의 거대한 명상의 공간이 됩니다.",
            detailedEn = "Part of Monet's legendary series capturing his Giverny water garden. With no horizon or shoreline, the painting focuses entirely on the water's surface, reflecting clouds, lilies, and aquatic plants in a meditative harmony of color.",
            detailedJa = "クロード・モネが晩年にジヴェルニーの自邸にある睡蓮の池を描いた連作の一つです。水平線や岸の境界がなく、ただ水の表面に投影された水草や睡蓮、そして反射した空だけが神秘的な色彩で描かれ、キャンバス全体が巨大な瞑想空間のようになります。",
            intentKo = "모네는 고정된 사물이 아니라 시간에 따라 쉼 없이 변화하는 '빛과 그림자'의 순간적인 일렁임을 그리고자 했습니다. 정체된 정물이 아닌, 움직이는 대기 그 자체를 기록하려는 시도였습니다.",
            intentEn = "Monet wanted to paint not the fixed objects themselves, but the fleeting, shifting play of light and atmosphere over time. It was an attempt to capture the changing nature of light.",
            intentJa = "モネは固定された事物ではなく、時間とともに絶えず変化する「光と影」の瞬間的なゆらめきを描こうとしました。静止した静物ではなく、流動する空気そのものを記録する試みでした。",
            behindKo = "모네는 이 시기 백내장으로 시력을 거의 잃어가고 있었습니다. 적색과 황색조가 강조된 거칠고 추상적인 화풍은 질병으로 왜곡된 시야의 극복 과정에서 탄생하여, 훗날 추상표현주의 미술의 선구적 역할을 하게 됩니다.",
            behindEn = "Monet was suffering from severe cataracts while painting this. The highly abstract, textured, and violet-heavy style was a direct result of his impaired vision, which later served as a bridge and major influence to Abstract Expressionism.",
            behindJa = "モネはこの時期、白内障で視力をほぼ失いかけていました。赤や黄色が強調された荒々しく抽象的な画風は、目の病による歪んだ視野を克服する過程で生まれ、後に抽象表現主義美術の先駆的な役割を果たすことになります。",
            qnaKo = mapOf(
                "이 기법은?" to "형태를 명확한 선으로 그리지 않고, 순수한 원색의 붓질을 중첩하여 시각적으로 섞이게 만드는 인상주의의 '분할 병치' 기법입니다.",
                "작가는 누구야?" to "프랑스의 대표적인 인상파 화가 클로드 모네입니다. 평생에 걸쳐 빛의 변화에 따른 사물의 인상을 탐구했습니다.",
                "비슷한 작품은?" to "루앙 대성당 연작, 건초더미 연작 등 동일한 대상을 다른 시간과 계절에 그린 연작들이 많습니다.",
                "원근법이 왜 없나요?" to "모네는 고전적인 원근법과 구도를 버리고 관람객이 연못 한가운데 둥둥 떠서 수면을 바라보는 듯한 몰입감을 주고자 화면 테두리를 없애고 수면만 가득 채웠습니다."
            ),
            qnaEn = mapOf(
                "What technique is used?" to "Impressionistic broken brushstrokes without sharp boundaries, where colors are mixed in the viewer's eye rather than blended on the palette.",
                "Who is the artist?" to "Claude Monet, the absolute giant of French Impressionism, dedicated entirely to capturing the transient sensations of light.",
                "Similar artworks?" to "His extensive 'Haystacks' and 'Rouen Cathedral' series painted under various times of day.",
                "Why no perspective?" to "Monet abandoned traditional horizons to place the viewer right inside the pond, submerged in the light and reflections without standard land-anchors."
            ),
            qnaJa = mapOf(
                "どんな技法？" to "形を明確な線で描かず、純粋な原色の筆跡を重ねることで、鑑賞者の眼の中で色彩が混ざり合うようにする印象主義の「色彩分割」技法です。",
                "作者は誰？" to "フランスの代表的な印象派の画家、クロード・モネです。生涯にわたり、光の変化による事物の印象を探求しました。",
                "似た作品は？" to "ルーアン大聖堂の連作や、積みわらの連作など、同じ対象を異なる時間や季節に描いた連作が多数あります。",
                "なぜ遠近法がないの？" to "モネは古典的な遠近法を捨て、鑑賞者が池の真ん中に浮かんで水面を見つめているような没入感を与えるため、周囲の風景を排除し、水面だけで画面を満たしました。"
            )
        ),
        Artwork(
            id = "girl_pearl_earring",
            titleKo = "진주 귀고리를 한 소녀",
            titleEn = "Girl with a Pearl Earring",
            titleJa = "真珠の耳飾りの少女",
            artistKo = "요하네스 베르메르",
            artistEn = "Johannes Vermeer",
            artistJa = "ヨハネス・フェルメール",
            year = "1665",
            museumKo = "마우릿하위스 미술관",
            museumEn = "Mauritshuis, The Hague",
            museumJa = "マウリッツハイス美術館",
            primaryColor = Color(0xFF1E2836), // Dark background
            accentColor = Color(0xFFD4AF37),  // Pearl/Gold turban
            summaryKo = "어두운 배경 속에서 살짝 뒤돌아보며 맑은 눈망울과 반짝이는 진주 귀고리로 시선을 사로잡는 북유럽의 모나리자입니다.",
            summaryEn = "Often called the 'Mona Lisa of the North,' this Dutch masterpiece captures a mysterious young girl looking over her shoulder.",
            summaryJa = "暗い背景の中からかすかに振り返り、澄んだ瞳と輝く真珠の耳飾りで視線を引きつける「北のモナ・リザ」です。",
            detailedKo = "요하네스 베르메르의 가장 유명한 작품으로, 검은 배경을 사용하여 빛을 받는 소녀의 우아한 옆모습과 투명한 눈동자, 그리고 입술의 하이라이트를 극대화했습니다. 그녀가 쓰고 있는 파란색과 노란색의 터번은 울트라마린 안료를 풍부하게 사용하여 더욱 이국적이고 선명합니다.",
            detailedEn = "Vermeer's absolute masterpiece. By placing the girl against an empty, pitch-black backdrop, he maximizes the luminescence of her soft skin, liquid eyes, and moist lips. Her turban of royal blue and yellow is painted with ultra-rare lapis lazuli pigment, adding exotic brilliance.",
            detailedJa = "ヨハネス・フェルメールの最も有名な作品で、黒い背景を使用することで、光を浴びる少女のエレガントな横顔と透明な瞳、そして唇のハイライトを極限まで引き出しています。彼女が巻いている青と黄色のターバンは、ウルトラマリンの顔料を豊富に使用しており、より異国情緒あふれる鮮やかさを放っています。",
            intentKo = "베르메르는 특정 순간의 정적과 빛의 부드러운 스며듬을 기록하고자 했습니다. 소녀의 시선은 정지된 화면 너머로 관람객과 내밀한 대화를 건네며, 은근하면서도 비밀스러운 신비감을 유발합니다.",
            intentEn = "Vermeer intended to freeze a momentary silence and the soft diffusion of daylight. The girl's turning gaze establishes an intimate dialogue with the viewer, creating an unresolved mystery.",
            intentJa = "フェルメールは、特定の瞬間の静寂と光の柔らかな浸透を記録しようとしました。少女の視線は、静止した画面の向こうから鑑賞者と内密な会話を交わしているようで、密やかで神秘的な感覚を呼び起こします。",
            behindKo = "이 그림은 인물 초상화가 아니라 '트로니(Tronie)'라고 불리는 가상의 인물 머리 연구화입니다. 또한 주인공이 찬 진주 귀고리는 광택의 묘사로 볼 때 값비싼 천연 진주가 아니라 광택제를 바른 유리방울이나 화가의 가공된 상상력의 산물로 보입니다.",
            behindEn = "This is not a formal portrait, but a 'Tronie'—a Dutch study of an imagined character in exotic attire. Furthermore, based on the physics of the highlight, the pearl is too large to be natural; it was likely polished Venetian glass or a product of Vermeer's artistic license.",
            behindJa = "この絵は人物の肖像画ではなく、「トローニ（Tronie）」と呼ばれる架空の人物の頭部を描いた研究画です。また、少女がつけている真珠の耳飾りは、光沢の描写から見て高価な天然真珠ではなく、艶出しを施したガラス球か、画家の想像力の産物と見られています。",
            qnaKo = mapOf(
                "이 기법은?" to "카메라 옵스쿠라(어두운 방에 구멍을 뚫어 이미지를 투영하는 기구)를 활용하여 정밀한 광학적 묘사를 구현했고, 울트라마린(청색) 안료를 아주 부드러운 유채 붓터치와 섞는 글레이징(Glazing) 기법을 사용했습니다.",
                "작가는 누구야?" to "17세기 네덜란드 황금기의 거장 요하네스 베르메르입니다. 빛의 마술사라 불리며, 평생 30여 점의 정교한 작품만 남겼습니다.",
                "비슷한 작품은?" to "베르메르의 '우유 따르는 여인', '회화의 기술' 등 소박한 일상의 한순간을 포착한 명작들이 많습니다.",
                "소녀가 쓴 터번의 청색은 뭔가요?" to "당시 금보다 비쌌던 보석 '청동석'을 갈아 만든 최고의 천연 파란색 물감 '울트라마린'입니다. 베르메르는 가난했음에도 이 고급 물감을 듬뿍 사용하여 고유의 찬란한 광택을 남겼습니다."
            ),
            qnaEn = mapOf(
                "What technique is used?" to "Optics exploration likely using a camera obscura, and Glazing (applying thin translucent oil layers over dry paint) for delicate skin tones.",
                "Who is the artist?" to "Johannes Vermeer, a master of the Dutch Golden Age famous for domestic interiors and light rendering. He left fewer than 36 known works.",
                "Similar artworks?" to "His works like 'The Milkmaid' or 'The Art of Painting' showing quiet household moments.",
                "Why is the blue turban special?" to "It was painted using ultramarine, a premium pigment made of ground lapis lazuli, which cost more than gold at the time. Vermeer insisted on it despite financial struggles."
            ),
            qnaJa = mapOf(
                "どんな技法？" to "カメラ・オブスクラ（暗箱を用いた光学装置）を活用して精密な光学的描写を具現化し、ウルトラマリン（青色）顔料を非常に柔らかい油彩の筆致と混ぜ合わせるグレーズ（薄塗り）技法が使用されています。",
                "作者は誰？" to "17世紀オランダ黄金期の巨匠ヨハネス・フェルメールです。「光の魔術師」と呼ばれ、生涯に30数点ほどの精巧な作品しか残しませんでした。",
                "似た作品は？" to "フェルメールの「牛乳を注ぐ女」や「絵画芸術」など、素朴な日常の一瞬を捉えた名作があります。",
                "ターバンの青さは何？" to "当時、金よりも高価だった宝石「ラピスラズリ」を削って作られた最高級の天然青色絵の具「ウルトラマリン」です。フェルメールは貧しかったにもかかわらず、この高級絵の具を贅沢に使用しました。"
            )
        ),
        Artwork(
            id = "the_scream",
            titleKo = "절규",
            titleEn = "The Scream",
            titleJa = "叫び",
            artistKo = "에드바르 뭉크",
            artistEn = "Edvard Munch",
            artistJa = "エドヴァルド・ムンク",
            year = "1893",
            museumKo = "노르웨이 국립 미술관",
            museumEn = "National Gallery, Oslo",
            museumJa = "ノルウェー国立美術館",
            primaryColor = Color(0xFFC67243), // Sunset orange
            accentColor = Color(0xFF2C3E50),  // Deep slate blue
            summaryKo = "붉게 타오르는 하늘과 왜곡된 피오르드 풍경 속에서 존재론적 불안을 귀를 막고 비명으로 마주하는 걸작입니다.",
            summaryEn = "An expression of existential dread under a blood-red sky, with a skeletal figure blocking its ears to block out nature's scream.",
            summaryJa = "赤く燃え上がる空と歪んだフィヨルドの風景の中で、存在論的な不安に耳を塞ぎ、叫びで立ち向かう傑作です。",
            detailedKo = "에드바르 뭉크의 상징주의이자 표현주의의 효시가 된 작품입니다. 뭉크는 가문 전체를 덮친 질병과 조울증으로 인한 평생의 두려움을 그렸으며, 하늘의 격정적인 핏빛 선들과 굽이치는 바다, 공포에 사로잡힌 해골 같은 인물이 극대화된 불안을 유발합니다.",
            detailedEn = "The ultimate symbol of modern anxiety and expressionism. Munch painted his lifelong struggle with panic attacks, depression, and family tragedies. The wavy, blood-red sunset lines and swirling fjord frame a central figure who appears almost skeletal with terror.",
            detailedJa = "エドヴァルド・ムンクの象徴主義であり、表現主義の嚆矢となった作品です。ムンクは一家を襲った病気や躁うつ病による生涯の恐怖を描いており、空の激情的な血色の線、うねる海、恐怖にとらわれた骸骨のような人物が極限の不安を引き起こします。",
            intentKo = "뭉크는 눈앞의 대자연이 거대한 초자연적 비명을 지르는 것을 보았고, 그 소름 끼치는 진동을 온 화면의 구부러진 선들로 시각화했습니다. 이는 인물 자체가 비명을 지르는 것이 아니라, 자연의 비명을 견디지 못해 귀를 막는 모습입니다.",
            intentEn = "Munch felt an infinite, supernatural scream passing through nature, and visualized this vibration through the wavy lines. The character is not actually screaming, but coverings its ears to shut out the overwhelming scream of nature.",
            intentJa = "ムンクは、目の前の大自然が巨大な超自然的な叫びを上げているのを感じ、その身の毛もよだつ振動を画面全体の曲がった線で視覚化しました。人物自体が叫んでいるのではなく、自然の叫びに耐えかねて耳を塞いでいる姿です。",
            behindKo = "뭉크는 파스텔, 템페라 등 다양한 재료로 '절규'를 4가지 판본으로 제작했습니다. 노르웨이 국립미술관 소장 판본의 왼쪽 상단 구석에는 아주 작게 연필로 '미친 사람만이 그릴 수 있는 그림이다'라고 적혀 있으며, 이는 뭉크가 직접 쓴 낙서로 밝혀졌습니다.",
            behindEn = "Munch created four versions of 'The Scream' using pastel, tempera, and crayon. In the top-left corner of the National Gallery version, there is a tiny pencil inscription: 'Could only have been painted by a madman,' which forensic studies confirmed was written by Munch himself.",
            behindJa = "ムンクはパステル、テンペラなど多様な素材で「叫び」を4つのバージョンで制作しました。ノルウェー国立美術館所蔵バージョンの左上隅には、鉛筆で非常に小さく「狂人にしか描けない絵だ」と書かれており、これはムンク本人の落書きであることが判明しています。",
            qnaKo = mapOf(
                "이 기법은?" to "선명한 선들을 평행하게 구부려 에너지와 불안의 진동을 나타내는 표현주의 기법입니다. 유채 물감 대신 템페라와 크레용을 섞어 건조하고 거친 느낌을 살렸습니다.",
                "작가는 누구야?" to "노르웨이 출신의 표현주의 거장 에드바르 뭉크입니다. 유년 시절 겪은 모친과 누이의 죽음 등으로 인한 정신적 고통을 예술로 극복했습니다.",
                "비슷한 작품은?" to "뭉크의 '불안(Anxiety)', '절망(Despair)'이 '절규'와 동일한 장소(오슬로 피오르드 다리)를 배경으로 한 3부작 연작입니다.",
                "하늘이 붉은 역사적 이유가 있나요?" to "단순히 심리적인 이유도 있지만, 역사적으로 1883년 인도네시아 크라카토아 화산 대폭발로 발생한 화산재가 전 세계 하늘을 수개월 동안 핏빛 저녁놀로 물들였던 대기 현상이 뭉크의 기억 속에 강렬히 남았기 때문이라는 설도 있습니다."
            ),
            qnaEn = mapOf(
                "What technique is used?" to "Expressionist technique of sweeping, wavy parallel lines visualizing raw anxiety and sonic vibration, combining tempera and pastels for dry textures.",
                "Who is the artist?" to "Edvard Munch of Norway, a pioneer of Expressionism. He turned his personal grief and madness into legendary universal symbols of anxiety.",
                "Similar artworks?" to "His paintings 'Anxiety' and 'Despair' featuring the same bridge in Oslo fjord as a thematic trilogy.",
                "Why is the sky blood-red?" to "Psychological terror, combined with the historical fact that the massive 1883 Krakatoa volcanic eruption blanketed European skies in brilliant red dust for months."
            ),
            qnaJa = mapOf(
                "どんな技法？" to "鮮明な線を平行に曲げることで、エネルギーと不安の振動を表現する表現主義的技法です。油絵の具の代わりにテンペラやクレヨンを混ぜて用い、乾燥した荒々しい質感を出しています。",
                "作者は誰？" to "ノルウェー出身の表現主義の巨匠エドヴァルド・ムンクです。幼少期に経験した母や姉の死などによる精神的苦痛を芸術で克服しました。",
                "似た作品は？" to "ムンクの「不安」や「絶望」が、「叫び」と同じ場所（オスロのフィヨルドに架かる橋）を背景にした3部作の連作です。",
                "空が赤い歴史的な理由は？" to "心理的な理由もありますが、歴史的に1883年のインドネシアのクラカタウ火山の大爆発によって発生した火山灰が、全世界の空を数ヶ月にわたり血のような夕焼けに染めた大気現象が、ムンクの記憶に強く残っていたためという説もあります。"
            )
        ),
        Artwork(
            id = "grande_jatte",
            titleKo = "그랑드 자트 섬의 일요일 오후",
            titleEn = "A Sunday Afternoon on the Island of La Grande Jatte",
            titleJa = "グランド・ジャット島の日曜日の午後",
            artistKo = "조르주 쇠라",
            artistEn = "Georges Seurat",
            artistJa = "ジョルジュ・スーラ",
            year = "1884-1886",
            museumKo = "시카고 미술관",
            museumEn = "Art Institute of Chicago",
            museumJa = "シカゴ美術館",
            primaryColor = Color(0xFF6E8B3D), // Grass green
            accentColor = Color(0xFF4682B4),  // River blue
            summaryKo = "수백만 개의 섬세한 원색 점들을 찍어 빛과 대기의 화사함을 과학적이고 정교하게 완성해낸 신인상주의 최고의 걸작입니다.",
            summaryEn = "A monumental Neo-Impressionist masterpiece composed of millions of tiny, distinct color dots that merge in the viewer's eye.",
            summaryJa = "数百万個の繊細な原色の点々を描き込み、光と空気の華やかさを科学的かつ精巧に完成させた新印象主義の最高傑作です.",
            detailedKo = "조르주 쇠라가 색채 대조와 보색 이론을 과학적으로 적용하여 그린 대표작입니다. 센강 유역의 그랑드 자트 섬에서 여가를 즐기는 파리 중산층의 모습을 2년에 걸쳐 수백만 개의 점으로만 그렸으며, 각 인물들은 고대 이집트 조각처럼 정적이고 기념비적인 균형을 이룹니다.",
            detailedEn = "A monumental icon of Neo-Impressionism. Seurat spent over two years applying optical blending theories, painting millions of tiny dots of contrasting and complementary colors. The Parisians enjoying their Sunday afternoon are rendered with static, sculptural gravity reminiscent of ancient Egyptian reliefs.",
            detailedJa = "ジョルジュ・スーラが色彩対比と補色理論を科学的に適用して描いた代表作です。セーヌ川沿いのグランド・ジャット島で余暇を楽しむパリの中産階級の姿を、2年かけて数百万個の点だけで描き、各人物は古代エジプトの彫刻のように静的で記念碑的なバランスを成しています。",
            intentKo = "쇠라는 감정에 의존하는 인상주의를 넘어, 시각 과학에 기반한 '광학적 혼합'을 이루려 했습니다. 팔레트에서 물감을 섞는 대신 캔버스 위에 원색을 배치해 뇌가 눈속에서 더욱 밝고 화사하게 색을 인지하도록 한 의도입니다.",
            intentEn = "Seurat wanted to transcend emotive Impressionism through systematic optics. Instead of physical mixing on the palette, placing pure dots adjacent on the canvas causes the viewer's brain to merge them, yielding a much brighter luminescence.",
            intentJa = "スーラは感情に依存する印象主義を超え、視覚科学に基づいた「光学的混合」を達成しようとしました。パレットで絵の具を混ぜる代わりに、キャンバス上に原色を配置することで、脳内でより明るく華やかに色彩を認識させる意図です。",
            behindKo = "쇠라가 이 거대한 캔버스(가로 3미터)를 완성하기 위해 그린 예비 유화 드로잉과 크로키만 70여 점이 넘습니다. 또한 맨 오른쪽에 있는 여성이 원숭이를 목줄에 매어 산책시키고 있는데, 당시 파리에서 원숭이는 '매춘'의 은유적 상징이기도 하여 중산층의 위선을 풍자하는 요소를 담고 있습니다.",
            behindEn = "Seurat created over 70 preparatory drawings and small oil sketches to complete this massive 10-foot-wide canvas. Intriguingly, the woman on the far right holds a monkey on a leash, which in 1880s Paris was a slang symbol for prostitution, subtly satirizing the hypocrisy of bourgeois society.",
            behindJa = "スーラがこの巨大なキャンバス（横3メートル）を完成させるために描いた、予備の油絵デッサンやクロッキーだけでも70点を超えます。また、一番右にいる女性がサルの首輪を引いて散歩させていますが、当時のパリでサルは「売春」の比喩的象徴でもあり、中産階級の偽善を風刺する要素を含んでいます。",
            qnaKo = mapOf(
                "이 기법은?" to "팔레트에서 색을 섞지 않고 순수한 색점들을 나란히 찍어 눈에서 혼합되게 하는 '점묘법(Pointillism)' 혹은 '분할주의(Divisionism)'입니다.",
                "작가는 누구야?" to "프랑스의 신인상주의 거장 조르주 쇠라입니다. 31세의 젊은 나이에 세상을 떠났지만 미술사에 거대한 획을 그었습니다.",
                "비슷한 작품은?" to "쇠라의 '아스니에르에서의 물놀이', '서커스 사이드쇼' 등이 점묘법을 완성해 나간 연작들입니다.",
                "그림 속 사람들이 왜 이리 딱딱해 보이나요?" to "쇠라는 찰나의 흔들림 대신 기하학적인 불변의 질서를 화폭에 구현하고자 했습니다. 인물들의 고정된 측면과 정면 자세는 피에로 델라 프란체스카 같은 고대 이탈리아 르네상스의 조화로운 구도를 차용한 것입니다."
            ),
            qnaEn = mapOf(
                "What technique is used?" to "Pointillism (or Divisionism), which uses tiny dots of pure color applied next to each other so they combine optically when viewed.",
                "Who is the artist?" to "Georges Seurat, the French Neo-Impressionist pioneer who tragically died young at age 31, but revolutionized post-impressionism.",
                "Similar artworks?" to "His other works 'Bathers at Asnières' and 'The Circus' showcasing mathematical pointillist precision.",
                "Why do people look so stiff?" to "Seurat rejected accidental movement in favor of geometric permanence and classic order. He modeled his rigid silhouettes on ancient friezes and Italian Renaissance symmetry."
            ),
            qnaJa = mapOf(
                "どんな技法？" to "パレットで色を混ぜず、純粋な色の点を並べて描くことで、鑑賞者の眼の中で混ざり合うようにする「点描画法（Pointillism）」または「分割主義」です。",
                "作者は誰？" to "フランスの新印象派の巨匠ジョルジュ・スーラです。31歳という若さで世を去りましたが、美術史に巨大な足跡を残しました。",
                "似た作品は？" to "スーラの「アニエールの水浴」や「サーカスの客寄せ」などが、点描画法を完成させていった連作です。",
                "なぜ登場人物がこんなに硬く見えるの？" to "スーラは、瞬間的な揺らぎの代わりに、幾何学的で不変の秩序を絵の中に具現化しようとしました。人物の固定された側面や正面の姿勢は、初期ルネサンスの古典的な調和と均衡を意識したものです。"
            )
        )
    )

    fun getArtworkById(id: String): Artwork? {
        return artworks.find { it.id == id }
    }
}
