package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R

@Composable
fun ArtworkVisualizer(
    artworkId: String,
    modifier: Modifier = Modifier,
    animate: Boolean = true
) {
    // Breathing/Pulse animation for live art feel
    val infiniteTransition = rememberInfiniteTransition(label = "artwork_anim")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    val drawableRes = when (artworkId) {
        "starry_night" -> com.example.R.drawable.starry_night
        "the_kiss" -> com.example.R.drawable.kiss
        "water_lilies" -> com.example.R.drawable.water_lilies
        "girl_pearl_earring" -> com.example.R.drawable.girl_with_a_pearl_earring
        "the_scream" -> com.example.R.drawable.sream
        "grande_jatte" -> com.example.R.drawable.a_sunday_on_la_grande_jatte
        else -> null
    }

    val context = androidx.compose.ui.platform.LocalContext.current
    val isImageValid = remember(artworkId) {
        if (drawableRes == null) false
        else {
            try {
                context.resources.openRawResource(drawableRes).use { stream ->
                    stream.available() > 0
                }
            } catch (e: Exception) {
                false
            }
        }
    }

    val useFallback = !isImageValid

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        if (!useFallback && drawableRes != null) {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = artworkId,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }

        if (useFallback) {
            Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            when (artworkId) {
                "starry_night" -> {
                    // Starry Night: Swirling sky and glowing yellow star gradients
                    // Background deep blue
                    drawRect(color = Color(0xFF0F1E36))

                    // Hills
                    val hillPath = Path().apply {
                        moveTo(0f, h * 0.8f)
                        quadraticTo(w * 0.3f, h * 0.75f, w * 0.6f, h * 0.85f)
                        quadraticTo(w * 0.8f, h * 0.9f, w, h * 0.8f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(hillPath, color = Color(0xFF162A4E))

                    // Swirls in the sky
                    val swirlPath1 = Path().apply {
                        moveTo(w * 0.2f, h * 0.4f)
                        cubicTo(w * 0.4f, h * 0.2f, w * 0.6f, h * 0.6f, w * 0.8f, h * 0.4f)
                    }
                    drawPath(
                        path = swirlPath1,
                        color = Color(0xFF4A90E2).copy(alpha = 0.4f),
                        style = Stroke(width = 12f * (if (animate) pulseScale else 1f), cap = StrokeCap.Round)
                    )

                    val swirlPath2 = Path().apply {
                        moveTo(w * 0.15f, h * 0.48f)
                        cubicTo(w * 0.38f, h * 0.3f, w * 0.58f, h * 0.7f, w * 0.85f, h * 0.48f)
                    }
                    drawPath(
                        path = swirlPath2,
                        color = Color(0xFFFFFDD0).copy(alpha = 0.3f),
                        style = Stroke(width = 8f, cap = StrokeCap.Round)
                    )

                    // Glowing Stars
                    val stars = listOf(
                        Offset(w * 0.2f, h * 0.25f) to 25f,
                        Offset(w * 0.5f, h * 0.2f) to 35f,
                        Offset(w * 0.8f, h * 0.22f) to 40f,
                        Offset(w * 0.65f, h * 0.45f) to 20f,
                        Offset(w * 0.35f, h * 0.55f) to 22f
                    )

                    stars.forEach { (pos, radius) ->
                        val glowRad = radius * 2.2f * (if (animate) pulseScale else 1f)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFFFFD700), Color(0xFFFFD700).copy(alpha = 0f)),
                                center = pos,
                                radius = glowRad
                            ),
                            center = pos,
                            radius = glowRad
                        )
                        drawCircle(color = Color.White, radius = radius * 0.5f, center = pos)
                    }

                    // Crescent Moon
                    val moonCenter = Offset(w * 0.85f, h * 0.15f)
                    val moonRadius = 30f
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFFE082), Color(0xFFFFB300).copy(alpha = 0f)),
                            center = moonCenter,
                            radius = moonRadius * 3f
                        ),
                        center = moonCenter,
                        radius = moonRadius * 3f
                    )
                    drawCircle(color = Color(0xFFFFD54F), radius = moonRadius, center = moonCenter)
                    drawCircle(color = Color(0xFF0F1E36), radius = moonRadius * 0.8f, center = moonCenter + Offset(-8f, -4f))

                    // Cypress tree silhouette on left
                    val cypressPath = Path().apply {
                        moveTo(w * 0.12f, h)
                        cubicTo(w * 0.05f, h * 0.7f, w * 0.08f, h * 0.4f, w * 0.15f, h * 0.35f)
                        cubicTo(w * 0.2f, h * 0.45f, w * 0.22f, h * 0.75f, w * 0.25f, h)
                        close()
                    }
                    drawPath(cypressPath, color = Color(0xFF0B1424))
                }

                "the_kiss" -> {
                    // The Kiss: Rich golden mosaic and geometric elements
                    val goldGrad = Brush.linearGradient(
                        colors = listOf(Color(0xFFD4AF37), Color(0xFFF3E5AB), Color(0xFF996515)),
                        start = Offset(0f, 0f),
                        end = Offset(w, h)
                    )
                    drawRect(brush = goldGrad)

                    // Abstract embracing lovers represented by elegant golden pod shape
                    val podCenter = Offset(w * 0.5f, h * 0.5f)
                    val podW = w * 0.45f * (if (animate) pulseScale else 1f)
                    val podH = h * 0.75f

                    drawOval(
                        color = Color(0xFF3E2723).copy(alpha = 0.15f),
                        topLeft = Offset(podCenter.x - podW * 0.55f, podCenter.y - podH * 0.45f),
                        size = Size(podW * 1.1f, podH * 1.05f)
                    )

                    drawOval(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFFF9C4), Color(0xFFFFD54F), Color(0xFFF57F17)),
                            center = podCenter,
                            radius = podH * 0.5f
                        ),
                        topLeft = Offset(podCenter.x - podW * 0.5f, podCenter.y - podH * 0.5f),
                        size = Size(podW, podH)
                    )

                    // Patterns inside the pod
                    // Male rectangular patterns (black and white squares)
                    for (i in 0..6) {
                        val rx = podCenter.x - podW * 0.35f + (i * 12f)
                        val ry = podCenter.y - podH * 0.2f + (i * 25f)
                        drawRect(
                            color = if (i % 2 == 0) Color.Black else Color.White,
                            topLeft = Offset(rx, ry),
                            size = Size(18f, 30f)
                        )
                    }

                    // Female circular patterns (colorful dots and rings)
                    val flowerColors = listOf(Color(0xFFE91E63), Color(0xFF3F51B5), Color(0xFF4CAF50), Color(0xFFFFEB3B))
                    for (i in 0..10) {
                        val cx = podCenter.x + podW * 0.1f + (i % 3 * 18f) - (i / 3 * 8f)
                        val cy = podCenter.y - podH * 0.3f + (i * 20f)
                        drawCircle(
                            color = flowerColors[i % flowerColors.size],
                            radius = 8f,
                            center = Offset(cx, cy)
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 4f,
                            center = Offset(cx, cy)
                        )
                    }

                    // Floral cliff background at the bottom
                    val cliffPath = Path().apply {
                        moveTo(0f, h * 0.85f)
                        quadraticTo(w * 0.5f, h * 0.78f, w, h * 0.88f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(cliffPath, color = Color(0xFF33691E))

                    // Tiny dots represent tiny wild flowers on cliff
                    for (x in 10..w.toInt() step 25) {
                        drawCircle(
                            color = Color(0xFFE040FB),
                            radius = 4f,
                            center = Offset(x.toFloat(), h * 0.85f + (x % 15))
                        )
                        drawCircle(
                            color = Color(0xFFFFFF00),
                            radius = 3f,
                            center = Offset(x.toFloat() + 8f, h * 0.87f + (x % 12))
                        )
                    }
                }

                "water_lilies" -> {
                    // Water Lilies: Soft impressionistic pond depth and reflections
                    val pondGrad = Brush.radialGradient(
                        colors = listOf(Color(0xFF1E3F35), Color(0xFF0F2620)),
                        center = Offset(w * 0.5f, h * 0.5f),
                        radius = w * 0.8f
                    )
                    drawRect(brush = pondGrad)

                    // Soft background brushstrokes representing cloud reflection (soft blue & gold)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF81D4FA).copy(alpha = 0.25f), Color.Transparent),
                            center = Offset(w * 0.3f, h * 0.3f),
                            radius = w * 0.4f
                        ),
                        center = Offset(w * 0.3f, h * 0.3f),
                        radius = w * 0.4f
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFFECB3).copy(alpha = 0.2f), Color.Transparent),
                            center = Offset(w * 0.7f, h * 0.4f),
                            radius = w * 0.35f
                        ),
                        center = Offset(w * 0.7f, h * 0.4f),
                        radius = w * 0.35f
                    )

                    // Drawing floating pads (flat ellipses)
                    val pads = listOf(
                        Offset(w * 0.25f, h * 0.6f) to Size(90f, 30f),
                        Offset(w * 0.68f, h * 0.5f) to Size(120f, 40f),
                        Offset(w * 0.45f, h * 0.78f) to Size(150f, 48f),
                        Offset(w * 0.8f, h * 0.75f) to Size(80f, 26f),
                        Offset(w * 0.15f, h * 0.4f) to Size(65f, 22f)
                    )

                    pads.forEach { (pos, dim) ->
                        val scaleW = dim.width * (if (animate) pulseScale else 1f)
                        drawOval(
                            color = Color(0xFF1B4D3E),
                            topLeft = Offset(pos.x - scaleW * 0.5f, pos.y - dim.height * 0.5f),
                            size = Size(scaleW, dim.height)
                        )
                        // Dark rim
                        drawOval(
                            color = Color(0xFF0E3227),
                            topLeft = Offset(pos.x - scaleW * 0.5f, pos.y - dim.height * 0.5f),
                            size = Size(scaleW, dim.height),
                            style = Stroke(width = 2f)
                        )
                    }

                    // Glowing Lilies (pink & white flowers on top of some pads)
                    val lilyFlowers = listOf(
                        Offset(w * 0.65f, h * 0.46f),
                        Offset(w * 0.48f, h * 0.74f),
                        Offset(w * 0.22f, h * 0.57f)
                    )

                    lilyFlowers.forEach { pos ->
                        // Outer glowing aura
                        val radius = 18f * (if (animate) pulseScale else 1f)
                        drawCircle(
                            color = Color(0xFFFFD1DC).copy(alpha = 0.6f),
                            radius = radius * 1.5f,
                            center = pos
                        )
                        // Flower petals abstract circles
                        drawCircle(color = Color.White, radius = radius * 0.8f, center = pos)
                        drawCircle(color = Color(0xFFF48FB1), radius = radius * 0.5f, center = pos)
                        drawCircle(color = Color(0xFFFFEB3B), radius = radius * 0.2f, center = pos)
                    }
                }

                "girl_pearl_earring" -> {
                    // Girl with a Pearl Earring: Stark contrast and iconic glowing pearl
                    drawRect(color = Color(0xFF11161B))

                    // Golden-brown garment silhouette
                    val shoulderPath = Path().apply {
                        moveTo(w * 0.2f, h)
                        quadraticTo(w * 0.35f, h * 0.65f, w * 0.5f, h * 0.55f)
                        quadraticTo(w * 0.68f, h * 0.6f, w * 0.85f, h)
                        close()
                    }
                    drawPath(shoulderPath, color = Color(0xFF8B6B23))

                    // Neck / Face abstract shape
                    val faceCenter = Offset(w * 0.5f, h * 0.42f)
                    drawOval(
                        color = Color(0xFFFFDAB9), // Peach/skin tone
                        topLeft = Offset(faceCenter.x - 45f, faceCenter.y - 65f),
                        size = Size(90f, 120f)
                    )

                    // Exotic blue turban
                    val turbanPath = Path().apply {
                        moveTo(w * 0.4f, h * 0.3f)
                        cubicTo(w * 0.35f, h * 0.15f, w * 0.65f, h * 0.12f, w * 0.62f, h * 0.28f)
                        cubicTo(w * 0.68f, h * 0.35f, w * 0.58f, h * 0.4f, w * 0.4f, h * 0.3f)
                        close()
                    }
                    drawPath(turbanPath, color = Color(0xFF1E3F66))

                    // Golden back drape of turban
                    val drapePath = Path().apply {
                        moveTo(w * 0.58f, h * 0.32f)
                        lineTo(w * 0.72f, h * 0.65f)
                        lineTo(w * 0.62f, h * 0.72f)
                        lineTo(w * 0.52f, h * 0.38f)
                        close()
                    }
                    drawPath(drapePath, color = Color(0xFFD4AF37))

                    // The Pearl Earring! Center of visual attention, glowing silver white
                    val pearlPos = Offset(w * 0.45f, h * 0.56f)
                    val pearlSize = 14f * (if (animate) pulseScale else 1f)
                    
                    // Shadow aura
                    drawCircle(
                        color = Color.Black.copy(alpha = 0.5f),
                        radius = pearlSize * 1.3f,
                        center = pearlPos + Offset(2f, 2f)
                    )
                    // Silver droplet teardrop shape
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.White, Color(0xFFB0BEC5), Color(0xFF455A64)),
                            center = pearlPos - Offset(2f, 2f),
                            radius = pearlSize
                        ),
                        radius = pearlSize,
                        center = pearlPos
                    )
                    // Shiny white highlight dot
                    drawCircle(
                        color = Color.White,
                        radius = 3f,
                        center = pearlPos - Offset(4f, 4f)
                    )
                }

                "the_scream" -> {
                    // The Scream: Flowing wavy sunset orange/yellow/blue sky
                    val skyGrad = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE64A19), Color(0xFFF57C00), Color(0xFFFBC02D), Color(0xFF1976D2))
                    )
                    drawRect(brush = skyGrad)

                    // Wavy sunset bands
                    for (i in 0..3) {
                        val offsetH = h * 0.1f * i
                        val wavePath = Path().apply {
                            moveTo(0f, offsetH + h * 0.05f)
                            cubicTo(w * 0.25f, offsetH - 20f, w * 0.75f, offsetH + 40f, w, offsetH + h * 0.05f)
                        }
                        drawPath(
                            path = wavePath,
                            color = Color(0xFFFFEB3B).copy(alpha = 0.3f),
                            style = Stroke(width = 16f, cap = StrokeCap.Round)
                        )
                    }

                    // Diagonal wooden bridge from bottom-left to center-right
                    val bridgePath = Path().apply {
                        moveTo(0f, h * 0.55f)
                        lineTo(w * 0.65f, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(bridgePath, color = Color(0xFF5D4037))

                    // Bridge railings
                    drawLine(
                        color = Color(0xFF3E2723),
                        start = Offset(0f, h * 0.55f),
                        end = Offset(w * 0.65f, h),
                        strokeWidth = 8f
                    )
                    drawLine(
                        color = Color(0xFF3E2723),
                        start = Offset(0f, h * 0.63f),
                        end = Offset(w * 0.45f, h),
                        strokeWidth = 4f
                    )

                    // Distant swirling blue fjord water
                    val fjordPath = Path().apply {
                        moveTo(w * 0.4f, h * 0.4f)
                        cubicTo(w * 0.6f, h * 0.35f, w * 0.8f, h * 0.55f, w, h * 0.45f)
                        lineTo(w, h * 0.85f)
                        quadraticTo(w * 0.6f, h * 0.95f, w * 0.45f, h * 0.85f)
                        close()
                    }
                    drawPath(fjordPath, color = Color(0xFF0D47A1))

                    // The organic central screaming figure
                    val figCenter = Offset(w * 0.32f, h * 0.7f + floatOffset)
                    // Torso
                    val torsoPath = Path().apply {
                        moveTo(figCenter.x - 15f, h)
                        quadraticTo(figCenter.x, figCenter.y + 15f, figCenter.x, figCenter.y)
                        quadraticTo(figCenter.x + 5f, figCenter.y + 15f, figCenter.x + 15f, h)
                        close()
                    }
                    drawPath(torsoPath, color = Color(0xFF1A237E))

                    // Head (skeletal bulbous shape)
                    drawOval(
                        color = Color(0xFFEBE0D0),
                        topLeft = Offset(figCenter.x - 12f, figCenter.y - 30f),
                        size = Size(24f, 38f)
                    )

                    // Dark hollow eyes and mouth
                    drawCircle(color = Color.Black, radius = 3.5f, center = Offset(figCenter.x - 5f, figCenter.y - 18f))
                    drawCircle(color = Color.Black, radius = 3.5f, center = Offset(figCenter.x + 5f, figCenter.y - 18f))
                    drawOval(
                        color = Color.Black,
                        topLeft = Offset(figCenter.x - 4f, figCenter.y - 8f),
                        size = Size(8f, 15f)
                    )

                    // Hands holding face
                    val handL = Path().apply {
                        moveTo(figCenter.x - 15f, figCenter.y + 10f)
                        quadraticTo(figCenter.x - 14f, figCenter.y - 10f, figCenter.x - 8f, figCenter.y - 12f)
                    }
                    val handR = Path().apply {
                        moveTo(figCenter.x + 15f, figCenter.y + 10f)
                        quadraticTo(figCenter.x + 14f, figCenter.y - 10f, figCenter.x + 8f, figCenter.y - 12f)
                    }
                    drawPath(handL, color = Color(0xFFEBE0D0), style = Stroke(width = 3f, cap = StrokeCap.Round))
                    drawPath(handR, color = Color(0xFFEBE0D0), style = Stroke(width = 3f, cap = StrokeCap.Round))
                }

                "grande_jatte" -> {
                    // La Grande Jatte: Pointillism-inspired dotted greenery and Seine river
                    // Base grass color
                    drawRect(color = Color(0xFF7CB342))

                    // River Seine at top left
                    val riverPath = Path().apply {
                        moveTo(0f, 0f)
                        quadraticTo(w * 0.4f, h * 0.1f, w * 0.55f, h * 0.28f)
                        lineTo(0f, h * 0.38f)
                        close()
                    }
                    drawPath(riverPath, color = Color(0xFF4FC3F7))

                    // Soft yellow sunlight glow area
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFFF9C4).copy(alpha = 0.6f), Color.Transparent),
                            center = Offset(w * 0.7f, h * 0.15f),
                            radius = w * 0.5f
                        ),
                        center = Offset(w * 0.7f, h * 0.15f),
                        radius = w * 0.5f
                    )

                    // Giant shadow casting from a tree on the right
                    val shadowPath = Path().apply {
                        moveTo(w, h * 0.35f)
                        quadraticTo(w * 0.5f, h * 0.65f, w * 0.3f, h)
                        lineTo(w, h)
                        close()
                    }
                    drawPath(shadowPath, color = Color(0xFF33691E).copy(alpha = 0.5f))

                    // Draw abstract pointillist dotted trees
                    val treeFoliageColors = listOf(Color(0xFF2E7D32), Color(0xFF1B5E20), Color(0xFF558B2F), Color(0xFFFDD835))
                    val foliageCenters = listOf(
                        Offset(w * 0.85f, h * 0.22f) to 75f,
                        Offset(w * 0.15f, h * 0.12f) to 50f
                    )

                    foliageCenters.forEach { (center, rad) ->
                        // Draw trunk
                        drawLine(
                            color = Color(0xFF5D4037),
                            start = center,
                            end = Offset(center.x, h * 0.7f),
                            strokeWidth = 10f
                        )
                        // Layered multi-color dot circles
                        for (i in 0..25) {
                            val dotOffset = Offset(
                                (Math.sin(i.toDouble() * 1.5) * (rad * 0.8 * Math.random())).toFloat(),
                                (Math.cos(i.toDouble() * 1.5) * (rad * 0.8 * Math.random())).toFloat()
                            )
                            drawCircle(
                                color = treeFoliageColors[i % treeFoliageColors.size],
                                radius = 6f + (i % 6),
                                center = center + dotOffset
                            )
                        }
                    }

                    // Elegant abstract representations of Parisians (stiff elegant cylinders/capsules)
                    // Couple on right with a umbrella
                    val coupleX = w * 0.72f + (if (animate) floatOffset * 0.2f else 0f)
                    // Man silhouette (dark high-hat cylinder)
                    drawRect(color = Color(0xFF1B232E), topLeft = Offset(coupleX, h * 0.45f), size = Size(18f, 50f))
                    drawRect(color = Color(0xFF1B232E), topLeft = Offset(coupleX - 2f, h * 0.41f), size = Size(22f, 8f)) // Hat brim
                    drawRect(color = Color(0xFF1B232E), topLeft = Offset(coupleX + 2f, h * 0.34f), size = Size(14f, 10f)) // Hat
                    
                    // Woman silhouette (bustle dress shape in dark burgundy)
                    val dressPath = Path().apply {
                        moveTo(coupleX + 22f, h * 0.48f)
                        lineTo(coupleX + 38f, h * 0.85f)
                        quadraticTo(coupleX + 48f, h * 0.86f, coupleX + 50f, h * 0.88f)
                        lineTo(coupleX + 18f, h * 0.88f)
                        lineTo(coupleX + 20f, h * 0.48f)
                        close()
                    }
                    drawPath(dressPath, color = Color(0xFF4A148C))
                    drawCircle(color = Color(0xFFFFFDD0), radius = 6f, center = Offset(coupleX + 24f, h * 0.44f)) // face

                    // Red parasol/umbrella
                    drawCircle(
                        color = Color(0xFFD32F2F),
                        radius = 24f,
                        center = Offset(coupleX + 24f, h * 0.33f)
                    )
                    drawLine(
                        color = Color.Black,
                        start = Offset(coupleX + 24f, h * 0.33f),
                        end = Offset(coupleX + 24f, h * 0.45f),
                        strokeWidth = 3f
                    )

                    // Tiny pet monkey! (represented by a tiny brown dot on a thin black line leash)
                    drawCircle(color = Color(0xFF5D4037), radius = 4f, center = Offset(coupleX + 28f, h * 0.86f))
                    drawLine(
                        color = Color.Black.copy(alpha = 0.7f),
                        start = Offset(coupleX + 22f, h * 0.75f),
                        end = Offset(coupleX + 28f, h * 0.86f),
                        strokeWidth = 1.5f
                    )
                }
                
                else -> {
                    // Fallback visualizer: clean geometric artsy placeholder
                    val brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFE0E0E0), Color(0xFFF5F5F5), Color(0xFFBDBDBD)),
                        start = Offset(0f, 0f),
                        end = Offset(w, h)
                    )
                    drawRect(brush = brush)
                }
            }
        }
        }
    }
}
