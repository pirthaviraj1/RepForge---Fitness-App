package com.example.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CriticalRed
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Biomechanical Avatar & Gym Equipment Drawing Primitives
 * Provides realistic male athletic character anatomy, muscular proportions,
 * performance gym apparel, cross-trainers, and realistic equipment visuals.
 */

// Realistic Athletic Male Character Color Palette (Meticulously matched to Reference Character)
val MaleSkinBase = Color(0xFFDCAB8C)      // Warm tan/olive athletic skin tone
val MaleSkinShadow = Color(0xFFB57D5C)    // Rich muscular depth contour shadow
val MaleSkinHighlight = Color(0xFFF1CBB6) // Specular skin highlight on muscle peaks
val MaleSkinDeepCut = Color(0xFF865134)   // Deep muscle striation / cut lines
val MaleHairBase = Color(0xFF151210)      // Deep black voluminous wavy curls
val MaleHairHighlight = Color(0xFF2E2620) // Textured hair sheen & curl crests
val MaleTankTopColor = Color(0xFF141416)  // Signature matte black gym stringer tank top
val MaleTankTopLogo = Color(0xFFF8FAFC)   // Crisp white athletic gym emblem
val MaleShortsColor = Color(0xFF161619)   // Athletic black training joggers / shorts
val MaleShortsShadow = Color(0xFF0D0D10)
val MaleShortsStripe = Color(0xFF00E676)  // Kinetic neon green accent edge
val MaleSmartwatchBand = Color(0xFF1E2024)// Black fitness smartwatch on wrist
val MaleSmartwatchFace = Color(0xFF0A0B0E)// Dark glass smartwatch screen
val MaleSneakerSole = Color(0xFFE2E8F0)   // Clean white rubber outsole
val MaleSneakerUpper = Color(0xFF0F172A)  // Charcoal-black sneaker upper
val MaleSneakerTrim = Color(0xFF00E676)   // Kinetic neon green accent trims

// Compatibility aliases
val AvatarSkinColor = MaleSkinBase
val AvatarSpineColor = MaleSkinShadow
val EquipmentSteelColor = Color(0xFF94A3B8)
val EquipmentFrameColor = Color(0xFF334155)
val EquipmentPlateColor = Color(0xFF1E293B)
val GymMatColor = Color(0xFF1E293B)

/**
 * Realistic Athletic Male Head with voluminous wavy curls, sculpted jaw, ear, and neck
 * matching the reference male fitness coach.
 */
fun DrawScope.drawRealisticMaleHead(
    center: Offset,
    radius: Float = 13.dp.toPx(),
    facingRight: Boolean = true,
    angleRad: Float = 0f
) {
    val dir = if (facingRight) 1f else -1f

    // 1. Strong muscular neck column
    val neckWidth = radius * 0.95f
    val neckHeight = radius * 1.05f
    val neckPath = Path().apply {
        moveTo(center.x - (neckWidth * 0.5f), center.y + (radius * 0.4f))
        lineTo(center.x + (neckWidth * 0.5f), center.y + (radius * 0.4f))
        lineTo(center.x + (neckWidth * 0.65f), center.y + (radius * 0.4f) + neckHeight)
        lineTo(center.x - (neckWidth * 0.65f), center.y + (radius * 0.4f) + neckHeight)
        close()
    }
    drawPath(neckPath, color = MaleSkinShadow)

    // Sternocleidomastoid neck muscle definition
    drawLine(
        color = MaleSkinDeepCut.copy(alpha = 0.55f),
        start = Offset(center.x + (dir * radius * 0.22f), center.y + (radius * 0.5f)),
        end = Offset(center.x - (dir * radius * 0.08f), center.y + (radius * 0.4f) + neckHeight),
        strokeWidth = 1.6.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 2. Cranium Base (Oval)
    drawOval(
        color = MaleSkinBase,
        topLeft = Offset(center.x - radius, center.y - radius),
        size = Size(radius * 2f, radius * 2.2f)
    )

    // 3. Sculpted Masculine Angular Jawline & Chin
    val jawPath = Path().apply {
        moveTo(center.x - (dir * radius * 0.75f), center.y + (radius * 0.1f))
        lineTo(center.x + (dir * radius * 0.88f), center.y + (radius * 0.35f)) // jaw angle
        lineTo(center.x + (dir * radius * 0.62f), center.y + (radius * 1.18f))  // chin tip
        lineTo(center.x - (dir * radius * 0.22f), center.y + (radius * 1.12f))  // under chin
        close()
    }
    drawPath(jawPath, color = MaleSkinBase)

    // Jaw contour shadow
    drawPath(
        jawPath,
        color = MaleSkinShadow.copy(alpha = 0.55f),
        style = Stroke(width = 1.4.dp.toPx())
    )

    // 4. Voluminous Wavy Textured Hair (Natural messy aesthetic curls as in reference photo)
    val hairPath = Path().apply {
        moveTo(center.x - (dir * radius * 1.15f), center.y + (radius * 0.25f))
        // Nape taper
        lineTo(center.x - (dir * radius * 1.15f), center.y - (radius * 0.4f))
        // High back curl
        quadraticTo(
            center.x - (dir * radius * 1.25f), center.y - (radius * 0.95f),
            center.x - (dir * radius * 0.7f), center.y - (radius * 1.45f)
        )
        // Voluminous top textured wave crests
        quadraticTo(
            center.x - (dir * radius * 0.25f), center.y - (radius * 1.7f),
            center.x + (dir * radius * 0.25f), center.y - (radius * 1.55f)
        )
        quadraticTo(
            center.x + (dir * radius * 0.65f), center.y - (radius * 1.6f),
            center.x + (dir * radius * 1.05f), center.y - (radius * 1.05f)
        )
        // Front cascading tousled curls over forehead / fringe
        quadraticTo(
            center.x + (dir * radius * 1.25f), center.y - (radius * 0.55f),
            center.x + (dir * radius * 0.95f), center.y - (radius * 0.12f)
        )
        // Fringe curl dip
        quadraticTo(
            center.x + (dir * radius * 0.65f), center.y - (radius * 0.25f),
            center.x + (dir * radius * 0.35f), center.y - (radius * 0.45f)
        )
        // Temple line
        lineTo(center.x - (dir * radius * 0.25f), center.y - (radius * 0.25f))
        close()
    }
    drawPath(hairPath, color = MaleHairBase)

    // Highlight strands on top wave crests
    drawCircle(
        color = MaleHairHighlight,
        radius = radius * 0.4f,
        center = Offset(center.x - (dir * radius * 0.3f), center.y - (radius * 1.15f))
    )
    drawCircle(
        color = MaleHairHighlight,
        radius = radius * 0.35f,
        center = Offset(center.x + (dir * radius * 0.35f), center.y - (radius * 1.1f))
    )

    // 5. Ear with inner cartilage definition
    val earCenter = Offset(center.x - (dir * radius * 0.42f), center.y + (radius * 0.22f))
    drawOval(
        color = MaleSkinBase,
        topLeft = Offset(earCenter.x - 3.dp.toPx(), earCenter.y - 4.5.dp.toPx()),
        size = Size(6.dp.toPx(), 9.dp.toPx())
    )
    drawCircle(
        color = MaleSkinShadow,
        radius = 1.8.dp.toPx(),
        center = earCenter
    )

    // 6. Masculine Dark Eyebrow & Piercing Eye
    val browStart = Offset(center.x + (dir * radius * 0.22f), center.y - (radius * 0.08f))
    val browEnd = Offset(center.x + (dir * radius * 0.62f), center.y + (radius * 0.02f))
    drawLine(
        color = MaleHairBase,
        start = browStart,
        end = browEnd,
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Round
    )

    val eyeCenter = Offset(center.x + (dir * radius * 0.45f), center.y + (radius * 0.14f))
    drawCircle(
        color = MaleHairBase,
        radius = 1.6.dp.toPx(),
        center = eyeCenter
    )
}

/**
 * Legacy alias for drawRealisticMaleHead
 */
fun DrawScope.drawMaleHead(
    center: Offset,
    radius: Float = 12.5.dp.toPx(),
    color: Color = AvatarSkinColor
) {
    drawRealisticMaleHead(center = center, radius = radius, facingRight = true)
}

/**
 * Realistic Muscular Male Torso:
 * Broad V-Taper, Trapezius, Pectoralis Major chest slabs, Rectus Abdominis 6-pack cuts,
 * and External Obliques.
 */
fun DrawScope.drawRealisticTorso(
    shoulder: Offset,
    hip: Offset,
    isSideView: Boolean = false,
    facingRight: Boolean = true,
    isCommonMistake: Boolean = false
) {
    val dx = hip.x - shoulder.x
    val dy = hip.y - shoulder.y
    val len = sqrt(dx * dx + dy * dy).coerceAtLeast(1f)
    val ux = dx / len
    val uy = dy / len
    val nx = -uy
    val ny = ux
    val dir = if (facingRight) 1f else -1f

    val shoulderWidth = 17.dp.toPx()
    val waistWidth = 10.dp.toPx()
    val hipWidth = 11.5.dp.toPx()

    // 1. Torso Silhouette V-Taper Path
    val torsoPath = Path().apply {
        // Left shoulder
        moveTo(shoulder.x - nx * shoulderWidth, shoulder.y - ny * shoulderWidth)
        // Trapezius neck slope
        lineTo(shoulder.x, shoulder.y - 4.dp.toPx())
        // Right shoulder
        lineTo(shoulder.x + nx * shoulderWidth, shoulder.y + ny * shoulderWidth)
        // Upper ribs / serratus
        lineTo(shoulder.x + nx * (shoulderWidth * 0.85f) + ux * (len * 0.35f),
               shoulder.y + ny * (shoulderWidth * 0.85f) + uy * (len * 0.35f))
        // Tapered athletic waist
        lineTo(hip.x + nx * waistWidth - ux * (len * 0.15f),
               hip.y + ny * waistWidth - uy * (len * 0.15f))
        // Hip flare
        lineTo(hip.x + nx * hipWidth, hip.y + ny * hipWidth)
        lineTo(hip.x - nx * hipWidth, hip.y - ny * hipWidth)
        // Opposite waist
        lineTo(hip.x - nx * waistWidth - ux * (len * 0.15f),
               hip.y - ny * waistWidth - uy * (len * 0.15f))
        // Opposite ribs
        lineTo(shoulder.x - nx * (shoulderWidth * 0.85f) + ux * (len * 0.35f),
               shoulder.y - ny * (shoulderWidth * 0.85f) + uy * (len * 0.35f))
        close()
    }

    val torsoBaseColor = if (isCommonMistake) CriticalRed.copy(alpha = 0.75f) else MaleSkinBase
    drawPath(torsoPath, color = torsoBaseColor)

    // Side shadow for 3D depth
    val shadowPath = Path().apply {
        moveTo(shoulder.x - nx * shoulderWidth, shoulder.y - ny * shoulderWidth)
        lineTo(shoulder.x, shoulder.y)
        lineTo(hip.x, hip.y)
        lineTo(hip.x - nx * hipWidth, hip.y - ny * hipWidth)
        close()
    }
    drawPath(shadowPath, color = MaleSkinShadow.copy(alpha = 0.45f))

    if (!isSideView) {
        // 2. Clavicle / Collarbone Line
        drawLine(
            color = MaleSkinShadow,
            start = Offset(shoulder.x - nx * (shoulderWidth * 0.75f) + ux * 3.dp.toPx(),
                           shoulder.y - ny * (shoulderWidth * 0.75f) + uy * 3.dp.toPx()),
            end = Offset(shoulder.x + nx * (shoulderWidth * 0.75f) + ux * 3.dp.toPx(),
                         shoulder.y + ny * (shoulderWidth * 0.75f) + uy * 3.dp.toPx()),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )

        // 3. Pectoralis Major (Chest Slabs)
        val pecCenterY = shoulder.y + uy * (len * 0.28f)
        val pecCenterX = shoulder.x + ux * (len * 0.28f)
        val pecLowerY = shoulder.y + uy * (len * 0.42f)
        val pecLowerX = shoulder.x + ux * (len * 0.42f)

        // Sternal cleft (center line)
        drawLine(
            color = MaleSkinDeepCut,
            start = Offset(shoulder.x + ux * 4.dp.toPx(), shoulder.y + uy * 4.dp.toPx()),
            end = Offset(pecLowerX, pecLowerY),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Lower pectoral fold curves (Left & Right)
        val leftPec = Path().apply {
            moveTo(pecLowerX, pecLowerY)
            quadraticTo(
                pecCenterX - nx * (shoulderWidth * 0.45f), pecLowerY - ny * (shoulderWidth * 0.45f) + uy * 2.dp.toPx(),
                shoulder.x - nx * (shoulderWidth * 0.8f) + ux * (len * 0.35f),
                shoulder.y - ny * (shoulderWidth * 0.8f) + uy * (len * 0.35f)
            )
        }
        val rightPec = Path().apply {
            moveTo(pecLowerX, pecLowerY)
            quadraticTo(
                pecCenterX + nx * (shoulderWidth * 0.45f), pecLowerY + ny * (shoulderWidth * 0.45f) + uy * 2.dp.toPx(),
                shoulder.x + nx * (shoulderWidth * 0.8f) + ux * (len * 0.35f),
                shoulder.y + ny * (shoulderWidth * 0.8f) + uy * (len * 0.35f)
            )
        }
        drawPath(leftPec, color = MaleSkinDeepCut, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
        drawPath(rightPec, color = MaleSkinDeepCut, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))

        // Pectoral muscle highlights
        drawCircle(
            color = MaleSkinHighlight.copy(alpha = 0.5f),
            radius = 4.5.dp.toPx(),
            center = Offset(pecCenterX - nx * 6.dp.toPx(), pecCenterY - ny * 6.dp.toPx())
        )
        drawCircle(
            color = MaleSkinHighlight.copy(alpha = 0.5f),
            radius = 4.5.dp.toPx(),
            center = Offset(pecCenterX + nx * 6.dp.toPx(), pecCenterY + ny * 6.dp.toPx())
        )

        // 4. Rectus Abdominis 6-Pack Cuts
        val abCenterStart = Offset(pecLowerX, pecLowerY)
        val abCenterEnd = Offset(hip.x - ux * 4.dp.toPx(), hip.y - uy * 4.dp.toPx())

        // Linea Alba (midline)
        drawLine(
            color = MaleSkinDeepCut,
            start = abCenterStart,
            end = abCenterEnd,
            strokeWidth = 1.6.dp.toPx(),
            cap = StrokeCap.Round
        )

        // 3 Horizontal Tendon Intersections
        for (i in 1..3) {
            val ratio = 0.45f + (i * 0.12f)
            val cutCenterX = shoulder.x + ux * (len * ratio)
            val cutCenterY = shoulder.y + uy * (len * ratio)
            val cutSpan = (waistWidth * 0.65f)

            drawLine(
                color = MaleSkinDeepCut,
                start = Offset(cutCenterX - nx * cutSpan, cutCenterY - ny * cutSpan),
                end = Offset(cutCenterX + nx * cutSpan, cutCenterY + ny * cutSpan),
                strokeWidth = 1.4.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    } else {
        // Profile Torso details: Latissimus dorsi & pectoral profile curve
        val latPath = Path().apply {
            moveTo(shoulder.x - (dir * nx * 4.dp.toPx()), shoulder.y)
            quadraticTo(
                shoulder.x + (dir * nx * (shoulderWidth * 0.6f)) + ux * (len * 0.3f),
                shoulder.y + (dir * ny * (shoulderWidth * 0.6f)) + uy * (len * 0.3f),
                hip.x, hip.y
            )
        }
        drawPath(latPath, color = MaleSkinDeepCut, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
    }
}

/**
 * Realistic Muscular Arm:
 * 3D rounded Deltoid cap, bulging Biceps Brachii, Triceps horseshoe,
 * tapered Brachioradialis forearm, and clenched gripping hand.
 */
fun DrawScope.drawRealisticArm(
    shoulder: Offset,
    elbow: Offset,
    hand: Offset,
    isFrontArm: Boolean = true,
    highlightMuscles: Boolean = false
) {
    // 1. Deltoid Cap (Shoulder muscle mass)
    val deltRadius = 9.5.dp.toPx()
    drawCircle(
        color = MaleSkinBase,
        radius = deltRadius,
        center = shoulder
    )
    drawCircle(
        color = MaleSkinShadow.copy(alpha = 0.5f),
        radius = deltRadius * 0.8f,
        center = Offset(shoulder.x - 1.5.dp.toPx(), shoulder.y + 2.dp.toPx())
    )
    drawCircle(
        color = MaleSkinHighlight.copy(alpha = 0.55f),
        radius = deltRadius * 0.45f,
        center = Offset(shoulder.x + 1.5.dp.toPx(), shoulder.y - 1.5.dp.toPx())
    )

    // 2. Upper Arm (Biceps & Triceps volume)
    val dx1 = elbow.x - shoulder.x
    val dy1 = elbow.y - shoulder.y
    val len1 = sqrt(dx1 * dx1 + dy1 * dy1).coerceAtLeast(1f)
    val nx1 = -dy1 / len1
    val ny1 = dx1 / len1

    val upperArmPath = Path().apply {
        moveTo(shoulder.x - nx1 * 8.dp.toPx(), shoulder.y - ny1 * 8.dp.toPx())
        // Bicep belly bulge
        quadraticTo(
            (shoulder.x + elbow.x) / 2f + nx1 * 10.5.dp.toPx(),
            (shoulder.y + elbow.y) / 2f + ny1 * 10.5.dp.toPx(),
            elbow.x + nx1 * 6.5.dp.toPx(),
            elbow.y + ny1 * 6.5.dp.toPx()
        )
        // Elbow olecranon
        lineTo(elbow.x - nx1 * 6.dp.toPx(), elbow.y - ny1 * 6.dp.toPx())
        // Tricep contour
        quadraticTo(
            (shoulder.x + elbow.x) / 2f - nx1 * 8.5.dp.toPx(),
            (shoulder.y + elbow.y) / 2f - ny1 * 8.5.dp.toPx(),
            shoulder.x - nx1 * 8.dp.toPx(),
            shoulder.y - ny1 * 8.dp.toPx()
        )
        close()
    }
    drawPath(upperArmPath, color = MaleSkinBase)

    // Bicep / Tricep muscle cut line
    drawLine(
        color = MaleSkinDeepCut.copy(alpha = 0.45f),
        start = Offset((shoulder.x + elbow.x) / 2f - nx1 * 2.dp.toPx(), (shoulder.y + elbow.y) / 2f - ny1 * 2.dp.toPx()),
        end = Offset((shoulder.x + elbow.x) / 2f + nx1 * 7.dp.toPx(), (shoulder.y + elbow.y) / 2f + ny1 * 7.dp.toPx()),
        strokeWidth = 1.5.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 3. Forearm (Brachioradialis taper into wrist)
    val dx2 = hand.x - elbow.x
    val dy2 = hand.y - elbow.y
    val len2 = sqrt(dx2 * dx2 + dy2 * dy2).coerceAtLeast(1f)
    val nx2 = -dy2 / len2
    val ny2 = dx2 / len2

    val forearmPath = Path().apply {
        moveTo(elbow.x - nx2 * 6.5.dp.toPx(), elbow.y - ny2 * 6.5.dp.toPx())
        // Swelling brachioradialis
        quadraticTo(
            elbow.x + (dx2 * 0.35f) + nx2 * 8.5.dp.toPx(),
            elbow.y + (dy2 * 0.35f) + ny2 * 8.5.dp.toPx(),
            hand.x + nx2 * 4.5.dp.toPx(),
            hand.y + ny2 * 4.5.dp.toPx()
        )
        // Wrist
        lineTo(hand.x - nx2 * 4.5.dp.toPx(), hand.y - ny2 * 4.5.dp.toPx())
        quadraticTo(
            elbow.x + (dx2 * 0.35f) - nx2 * 7.5.dp.toPx(),
            elbow.y + (dy2 * 0.35f) - ny2 * 7.5.dp.toPx(),
            elbow.x - nx2 * 6.5.dp.toPx(),
            elbow.y - ny2 * 6.5.dp.toPx()
        )
        close()
    }
    drawPath(forearmPath, color = MaleSkinBase)

    // 4. Clenched Athletic Hand / Gripping Fist
    val handRadius = 6.dp.toPx()
    drawCircle(
        color = MaleSkinBase,
        radius = handRadius,
        center = hand
    )
    // Thumb knuckle contour
    drawCircle(
        color = MaleSkinShadow,
        radius = 2.8.dp.toPx(),
        center = Offset(hand.x + nx2 * 3.dp.toPx(), hand.y + ny2 * 3.dp.toPx())
    )

    if (highlightMuscles) {
        drawMuscleGlow(
            center = Offset((shoulder.x + elbow.x) / 2f + nx1 * 4.dp.toPx(), (shoulder.y + elbow.y) / 2f + ny1 * 4.dp.toPx()),
            radius = 16.dp.toPx(),
            color = KineticNeon
        )
    }
}

/**
 * Realistic Athletic Leg:
 * Dark performance compression gym shorts, Quadriceps with vastus medialis teardrop,
 * patella knee joint, Gastrocnemius calf belly, and cross-trainer gym sneakers.
 */
fun DrawScope.drawRealisticLeg(
    hip: Offset,
    knee: Offset,
    ankle: Offset,
    isFrontLeg: Boolean = true,
    highlightMuscles: Boolean = false
) {
    val dx1 = knee.x - hip.x
    val dy1 = knee.y - hip.y
    val len1 = sqrt(dx1 * dx1 + dy1 * dy1).coerceAtLeast(1f)
    val nx1 = -dy1 / len1
    val ny1 = dx1 / len1

    // 1. Performance Compression Gym Shorts (covers upper 65% of thigh)
    val shortsLen = len1 * 0.65f
    val shortsEndX = hip.x + (dx1 / len1) * shortsLen
    val shortsEndY = hip.y + (dy1 / len1) * shortsLen

    val shortsPath = Path().apply {
        moveTo(hip.x - nx1 * 12.dp.toPx(), hip.y - ny1 * 12.dp.toPx())
        lineTo(hip.x + nx1 * 12.dp.toPx(), hip.y + ny1 * 12.dp.toPx())
        lineTo(shortsEndX + nx1 * 10.dp.toPx(), shortsEndY + ny1 * 10.dp.toPx())
        lineTo(shortsEndX - nx1 * 10.dp.toPx(), shortsEndY - ny1 * 10.dp.toPx())
        close()
    }
    drawPath(shortsPath, color = MaleShortsColor)

    // Athletic Neon Stripe along outer leg
    val stripeSide = if (isFrontLeg) 1f else -1f
    drawLine(
        color = MaleShortsStripe,
        start = Offset(hip.x + (stripeSide * nx1 * 11.dp.toPx()), hip.y + (stripeSide * ny1 * 11.dp.toPx())),
        end = Offset(shortsEndX + (stripeSide * nx1 * 9.5.dp.toPx()), shortsEndY + (stripeSide * ny1 * 9.5.dp.toPx())),
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Round
    )

    // Shorts hem cuff
    drawLine(
        color = MaleShortsShadow,
        start = Offset(shortsEndX - nx1 * 10.dp.toPx(), shortsEndY - ny1 * 10.dp.toPx()),
        end = Offset(shortsEndX + nx1 * 10.dp.toPx(), shortsEndY + ny1 * 10.dp.toPx()),
        strokeWidth = 2.dp.toPx()
    )

    // 2. Muscular Thigh / Quadriceps (between shorts hem and knee)
    val quadPath = Path().apply {
        moveTo(shortsEndX - nx1 * 9.dp.toPx(), shortsEndY - ny1 * 9.dp.toPx())
        // Vastus medialis teardrop expansion
        quadraticTo(
            knee.x - (dx1 / len1 * 4.dp.toPx()) + nx1 * 10.dp.toPx(),
            knee.y - (dy1 / len1 * 4.dp.toPx()) + ny1 * 10.dp.toPx(),
            knee.x + nx1 * 7.dp.toPx(),
            knee.y + ny1 * 7.dp.toPx()
        )
        // Knee joint cap
        lineTo(knee.x - nx1 * 7.dp.toPx(), knee.y - ny1 * 7.dp.toPx())
        close()
    }
    drawPath(quadPath, color = MaleSkinBase)

    // Patella Knee Cap
    drawCircle(
        color = MaleSkinHighlight.copy(alpha = 0.6f),
        radius = 4.5.dp.toPx(),
        center = knee
    )

    // 3. Lower Leg (Gastrocnemius Calf Muscle belly)
    val dx2 = ankle.x - knee.x
    val dy2 = ankle.y - knee.y
    val len2 = sqrt(dx2 * dx2 + dy2 * dy2).coerceAtLeast(1f)
    val nx2 = -dy2 / len2
    val ny2 = dx2 / len2

    val calfPath = Path().apply {
        moveTo(knee.x - nx2 * 7.dp.toPx(), knee.y - ny2 * 7.dp.toPx())
        // Posterior calf diamond bulge
        quadraticTo(
            knee.x + (dx2 * 0.35f) - nx2 * 9.5.dp.toPx(),
            knee.y + (dy2 * 0.35f) - ny2 * 9.5.dp.toPx(),
            ankle.x - nx2 * 4.5.dp.toPx(),
            ankle.y - ny2 * 4.5.dp.toPx()
        )
        // Achilles tendon to ankle
        lineTo(ankle.x + nx2 * 4.5.dp.toPx(), ankle.y + ny2 * 4.5.dp.toPx())
        // Shin anterior line
        quadraticTo(
            knee.x + (dx2 * 0.35f) + nx2 * 6.5.dp.toPx(),
            knee.y + (dy2 * 0.35f) + ny2 * 6.5.dp.toPx(),
            knee.x + nx2 * 7.dp.toPx(),
            knee.y + ny2 * 7.dp.toPx()
        )
        close()
    }
    drawPath(calfPath, color = MaleSkinBase)

    // Calf muscle contour shading
    drawCircle(
        color = MaleSkinHighlight.copy(alpha = 0.5f),
        radius = 4.5.dp.toPx(),
        center = Offset(knee.x + (dx2 * 0.35f) - nx2 * 4.dp.toPx(), knee.y + (dy2 * 0.35f) - ny2 * 4.dp.toPx())
    )

    // 4. Cross-Training Athletic Gym Sneaker
    val toeDir = if (isFrontLeg) 1f else -1f
    val sneakerLength = 18.dp.toPx()
    val sneakerHeight = 7.dp.toPx()

    // Sneaker Upper
    val sneakerPath = Path().apply {
        moveTo(ankle.x - 5.dp.toPx(), ankle.y)
        lineTo(ankle.x + (toeDir * sneakerLength * 0.85f), ankle.y + 2.dp.toPx()) // toe box
        lineTo(ankle.x + (toeDir * sneakerLength), ankle.y + sneakerHeight)       // toe tip
        lineTo(ankle.x - 7.dp.toPx(), ankle.y + sneakerHeight)                    // heel
        close()
    }
    drawPath(sneakerPath, color = MaleSneakerUpper)

    // White Rubber Outsole
    val solePath = Path().apply {
        moveTo(ankle.x - 7.dp.toPx(), ankle.y + sneakerHeight)
        lineTo(ankle.x + (toeDir * sneakerLength), ankle.y + sneakerHeight)
        lineTo(ankle.x + (toeDir * sneakerLength), ankle.y + sneakerHeight + 3.dp.toPx())
        lineTo(ankle.x - 7.dp.toPx(), ankle.y + sneakerHeight + 3.dp.toPx())
        close()
    }
    drawPath(solePath, color = MaleSneakerSole)

    // Neon Laces & Athletic Accent
    drawLine(
        color = MaleSneakerTrim,
        start = Offset(ankle.x, ankle.y + 2.dp.toPx()),
        end = Offset(ankle.x + (toeDir * 7.dp.toPx()), ankle.y + 4.dp.toPx()),
        strokeWidth = 1.5.dp.toPx(),
        cap = StrokeCap.Round
    )

    if (highlightMuscles) {
        drawMuscleGlow(
            center = Offset(knee.x - (dx1 / len1 * 10.dp.toPx()), knee.y - (dy1 / len1 * 10.dp.toPx())),
            radius = 18.dp.toPx(),
            color = KineticNeon
        )
    }
}

/**
 * Realistic Male Athletic Character Anatomy Renderer:
 * Completely removes any stick-figure representation and renders fully sculpted
 * male muscular anatomy across all exercises and workouts:
 * - Broad muscular V-taper torso with pectorals, 6-pack abs, obliques, and compression gym shorts
 * - Muscular thighs with compression shorts and quadriceps teardrop definition
 * - Muscular calves with gastrocnemius curve and athletic cross-trainer sneakers
 * - Muscular arms with deltoid caps, bulging biceps, triceps, and clenched athletic fists
 */
fun DrawScope.drawLimb(
    start: Offset,
    end: Offset,
    strokeWidth: Float,
    color: Color = AvatarSkinColor
) {
    val dx = end.x - start.x
    val dy = end.y - start.y
    val len = sqrt(dx * dx + dy * dy).coerceAtLeast(1f)
    val ux = dx / len
    val uy = dy / len
    val nx = -uy
    val ny = ux

    val isMistake = (color == CriticalRed)
    val skinBase = if (isMistake) CriticalRed.copy(alpha = 0.85f) else MaleSkinBase
    val skinShadow = if (isMistake) CriticalRed.copy(alpha = 0.95f) else MaleSkinShadow
    val skinHighlight = if (isMistake) Color.White.copy(alpha = 0.45f) else MaleSkinHighlight
    val deepCut = if (isMistake) Color(0xFF660000) else MaleSkinDeepCut

    // 1. TORSO DETECTION (connecting shoulder to hip: strokeWidth >= 8.2dp and len >= 26dp)
    val isTorso = strokeWidth >= 8.2.dp.toPx() && len >= 26.dp.toPx()

    if (isTorso) {
        val shoulderWidth = 18.dp.toPx()
        val waistWidth = 11.dp.toPx()
        val hipWidth = 13.5.dp.toPx()

        // Torso V-taper muscular silhouette
        val torsoPath = Path().apply {
            moveTo(start.x - nx * shoulderWidth, start.y - ny * shoulderWidth)
            lineTo(start.x, start.y - uy * 3.dp.toPx()) // trapezius center
            lineTo(start.x + nx * shoulderWidth, start.y + ny * shoulderWidth)
            // Ribcage curve
            lineTo(start.x + nx * (shoulderWidth * 0.82f) + ux * (len * 0.35f),
                   start.y + ny * (shoulderWidth * 0.82f) + uy * (len * 0.35f))
            // Tapered waist
            lineTo(end.x + nx * waistWidth - ux * (len * 0.22f),
                   end.y + ny * waistWidth - uy * (len * 0.22f))
            // Hip flare
            lineTo(end.x + nx * hipWidth, end.y + ny * hipWidth)
            lineTo(end.x - nx * hipWidth, end.y - ny * hipWidth)
            // Opposite waist
            lineTo(end.x - nx * waistWidth - ux * (len * 0.22f),
                   end.y - ny * waistWidth - uy * (len * 0.22f))
            // Opposite ribcage
            lineTo(start.x - nx * (shoulderWidth * 0.82f) + ux * (len * 0.35f),
                   start.y - ny * (shoulderWidth * 0.82f) + uy * (len * 0.35f))
            close()
        }
        drawPath(torsoPath, color = skinBase)

        // 3D Shadow depth on posterior side
        val torsoShadow = Path().apply {
            moveTo(start.x - nx * shoulderWidth, start.y - ny * shoulderWidth)
            lineTo(start.x, start.y)
            lineTo(end.x, end.y)
            lineTo(end.x - nx * hipWidth, end.y - ny * hipWidth)
            close()
        }
        drawPath(torsoShadow, color = skinShadow.copy(alpha = 0.45f))

        // Torso Landmark Measurements
        val shortsTop = 0.60f
        val shortsStartX = start.x + ux * (len * shortsTop)
        val shortsStartY = start.y + uy * (len * shortsTop)

        // Signature Gym Stringer Tank Top (Matte black as in reference photo)
        val tankTopPath = Path().apply {
            // Left strap over trapezius
            moveTo(start.x - nx * (shoulderWidth * 0.6f), start.y - ny * (shoulderWidth * 0.6f))
            // Deep scoop neckline exposing clavicles and upper chest cleavage
            quadraticTo(
                start.x + ux * (len * 0.22f),
                start.y + uy * (len * 0.22f),
                start.x + nx * (shoulderWidth * 0.6f),
                start.y + ny * (shoulderWidth * 0.6f)
            )
            // Right strap to deep armhole
            lineTo(start.x + nx * (shoulderWidth * 0.55f) + ux * (len * 0.12f),
                   start.y + ny * (shoulderWidth * 0.55f) + uy * (len * 0.12f))
            // Deep armhole cut exposing lats
            quadraticTo(
                start.x + nx * (shoulderWidth * 0.35f) + ux * (len * 0.35f),
                start.y + ny * (shoulderWidth * 0.35f) + uy * (len * 0.35f),
                shortsStartX + nx * (waistWidth * 1.02f),
                shortsStartY + ny * (waistWidth * 1.02f)
            )
            // Waist hem
            lineTo(shortsStartX - nx * (waistWidth * 1.02f), shortsStartY - ny * (waistWidth * 1.02f))
            // Left deep armhole cut exposing lats
            quadraticTo(
                start.x - nx * (shoulderWidth * 0.35f) + ux * (len * 0.35f),
                start.y - ny * (shoulderWidth * 0.35f) + uy * (len * 0.35f),
                start.x - nx * (shoulderWidth * 0.55f) + ux * (len * 0.12f),
                start.y - ny * (shoulderWidth * 0.55f) + uy * (len * 0.12f)
            )
            close()
        }
        drawPath(tankTopPath, color = MaleTankTopColor)

        // White Gym Emblem on Upper Left Chest (as in reference photo)
        val logoCenterX = start.x - nx * (shoulderWidth * 0.38f) + ux * (len * 0.30f)
        val logoCenterY = start.y - ny * (shoulderWidth * 0.38f) + uy * (len * 0.30f)
        val logoPath = Path().apply {
            moveTo(logoCenterX, logoCenterY - 2.5.dp.toPx())
            lineTo(logoCenterX + 3.dp.toPx(), logoCenterY + 2.dp.toPx())
            lineTo(logoCenterX - 3.dp.toPx(), logoCenterY + 2.dp.toPx())
            close()
        }
        drawPath(logoPath, color = MaleTankTopLogo)

        // Performance Gym Training Joggers / Shorts (lower portion of torso at hips)
        val shortsPath = Path().apply {
            moveTo(shortsStartX - nx * (waistWidth * 1.05f), shortsStartY - ny * (waistWidth * 1.05f))
            lineTo(shortsStartX + nx * (waistWidth * 1.05f), shortsStartY + ny * (waistWidth * 1.05f))
            lineTo(end.x + nx * (hipWidth * 1.05f), end.y + ny * (hipWidth * 1.05f))
            lineTo(end.x - nx * (hipWidth * 1.05f), end.y - ny * (hipWidth * 1.05f))
            close()
        }
        drawPath(shortsPath, color = MaleShortsColor)

        // Subtle athletic side stripe on joggers
        drawLine(
            color = MaleShortsStripe,
            start = Offset(shortsStartX + nx * (waistWidth * 1.05f), shortsStartY + ny * (waistWidth * 1.05f)),
            end = Offset(end.x + nx * (hipWidth * 1.05f), end.y + ny * (hipWidth * 1.05f)),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Pectoral Muscle Slabs (Chest)
        val pecCenterRatio = 0.28f
        val pecLowerRatio = 0.42f
        val pecLowerX = start.x + ux * (len * pecLowerRatio)
        val pecLowerY = start.y + uy * (len * pecLowerRatio)

        // Sternal cleavage line
        drawLine(
            color = deepCut,
            start = Offset(start.x + ux * 3.dp.toPx(), start.y + uy * 3.dp.toPx()),
            end = Offset(pecLowerX, pecLowerY),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Curved pectoral lower boundaries (Left & Right chest slabs)
        val leftPec = Path().apply {
            moveTo(pecLowerX, pecLowerY)
            quadraticTo(
                start.x - nx * (shoulderWidth * 0.45f) + ux * (len * pecLowerRatio),
                start.y - ny * (shoulderWidth * 0.45f) + uy * (len * pecLowerRatio),
                start.x - nx * (shoulderWidth * 0.82f) + ux * (len * pecCenterRatio),
                start.y - ny * (shoulderWidth * 0.82f) + uy * (len * pecCenterRatio)
            )
        }
        val rightPec = Path().apply {
            moveTo(pecLowerX, pecLowerY)
            quadraticTo(
                start.x + nx * (shoulderWidth * 0.45f) + ux * (len * pecLowerRatio),
                start.y + ny * (shoulderWidth * 0.45f) + uy * (len * pecLowerRatio),
                start.x + nx * (shoulderWidth * 0.82f) + ux * (len * pecCenterRatio),
                start.y + ny * (shoulderWidth * 0.82f) + uy * (len * pecCenterRatio)
            )
        }
        drawPath(leftPec, color = deepCut, style = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round))
        drawPath(rightPec, color = deepCut, style = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round))

        // Pectoral muscle specular highlights
        drawCircle(
            color = skinHighlight,
            radius = 4.dp.toPx(),
            center = Offset(start.x - nx * 6.dp.toPx() + ux * (len * pecCenterRatio),
                            start.y - ny * 6.dp.toPx() + uy * (len * pecCenterRatio))
        )
        drawCircle(
            color = skinHighlight,
            radius = 4.dp.toPx(),
            center = Offset(start.x + nx * 6.dp.toPx() + ux * (len * pecCenterRatio),
                            start.y + ny * 6.dp.toPx() + uy * (len * pecCenterRatio))
        )

        // 6-Pack Rectus Abdominis Cuts
        // Vertical Linea Alba midline
        drawLine(
            color = deepCut,
            start = Offset(pecLowerX, pecLowerY),
            end = Offset(shortsStartX, shortsStartY),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )

        // 2 Horizontal Tendon Cuts across abs
        for (i in 1..2) {
            val cutRatio = pecLowerRatio + (i * 0.08f)
            val cutX = start.x + ux * (len * cutRatio)
            val cutY = start.y + uy * (len * cutRatio)
            val cutSpan = (waistWidth * 0.55f)
            drawLine(
                color = deepCut,
                start = Offset(cutX - nx * cutSpan, cutY - ny * cutSpan),
                end = Offset(cutX + nx * cutSpan, cutY + ny * cutSpan),
                strokeWidth = 1.3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Rounded Deltoid Cap on shoulders
        drawCircle(color = skinBase, radius = 8.5.dp.toPx(), center = Offset(start.x - nx * (shoulderWidth * 0.88f), start.y - ny * (shoulderWidth * 0.88f)))
        drawCircle(color = skinBase, radius = 8.5.dp.toPx(), center = Offset(start.x + nx * (shoulderWidth * 0.88f), start.y + ny * (shoulderWidth * 0.88f)))
        return
    }

    // 2. LEG DETECTION (strokeWidth between 7.2dp and 8.2dp, or thigh/calf)
    val isLeg = strokeWidth >= 7.2.dp.toPx()

    if (isLeg) {
        val thighRadius = 10.dp.toPx()
        val kneeRadius = 7.dp.toPx()
        val ankleRadius = 6.dp.toPx()

        // Distinguish Thigh vs Lower Leg (Calf)
        val isUpperLeg = len >= 24.dp.toPx()

        val rS = if (isUpperLeg) thighRadius else kneeRadius
        val rE = if (isUpperLeg) kneeRadius else ankleRadius
        val rMid = (rS + rE) * 0.65f

        // Muscular Leg Flesh Contour
        val legPath = Path().apply {
            moveTo(start.x - nx * rS, start.y - ny * rS)
            // Quadriceps / Gastrocnemius muscle belly swell
            quadraticTo(
                (start.x + end.x) / 2f + nx * (rMid * 1.18f),
                (start.y + end.y) / 2f + ny * (rMid * 1.18f),
                end.x + nx * rE,
                end.y + ny * rE
            )
            lineTo(end.x - nx * rE, end.y - ny * rE)
            // Posterior hamstring / calf line
            quadraticTo(
                (start.x + end.x) / 2f - nx * (rMid * 1.05f),
                (start.y + end.y) / 2f - ny * (rMid * 1.05f),
                start.x - nx * rS,
                start.y - ny * rS
            )
            close()
        }
        drawPath(legPath, color = skinBase)

        // Muscle striation shading
        drawLine(
            color = skinShadow,
            start = Offset(start.x - nx * (rS * 0.6f), start.y - ny * (rS * 0.6f)),
            end = Offset(end.x - nx * (rE * 0.6f), end.y - ny * (rE * 0.6f)),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Specular highlight on quad / calf
        drawLine(
            color = skinHighlight,
            start = Offset(start.x + nx * (rS * 0.35f), start.y + ny * (rS * 0.35f)),
            end = Offset(end.x + nx * (rE * 0.35f), end.y + ny * (rE * 0.35f)),
            strokeWidth = 2.2.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Joint caps
        drawCircle(color = skinBase, radius = rS, center = start)
        drawCircle(color = skinBase, radius = rE, center = end)

        // If upper leg: Compression shorts on top 50%
        if (isUpperLeg) {
            val shortsPortion = 0.52f
            val shortsPath = Path().apply {
                moveTo(start.x - nx * (rS * 1.08f), start.y - ny * (rS * 1.08f))
                lineTo(start.x + nx * (rS * 1.08f), start.y + ny * (rS * 1.08f))
                lineTo(start.x + ux * (len * shortsPortion) + nx * (rMid * 1.05f),
                       start.y + uy * (len * shortsPortion) + ny * (rMid * 1.05f))
                lineTo(start.x + ux * (len * shortsPortion) - nx * (rMid * 1.05f),
                       start.y + uy * (len * shortsPortion) - ny * (rMid * 1.05f))
                close()
            }
            drawPath(shortsPath, color = MaleShortsColor)

            // Neon green performance stripe on shorts
            drawLine(
                color = MaleShortsStripe,
                start = Offset(start.x + nx * (rS * 1.08f), start.y + ny * (rS * 1.08f)),
                end = Offset(start.x + ux * (len * shortsPortion) + nx * (rMid * 1.05f),
                             start.y + uy * (len * shortsPortion) + ny * (rMid * 1.05f)),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        } else {
            // Lower leg: draw Cross-Training Gym Sneaker at the foot (at `end`)
            val shoeLen = 16.dp.toPx()
            val shoeH = 7.dp.toPx()
            val toeDir = if (ux >= 0f) 1f else -1f

            // Charcoal Upper
            val shoeUpper = Path().apply {
                moveTo(end.x - 5.dp.toPx(), end.y)
                lineTo(end.x + (toeDir * shoeLen * 0.85f), end.y + 2.dp.toPx())
                lineTo(end.x + (toeDir * shoeLen), end.y + shoeH)
                lineTo(end.x - 6.dp.toPx(), end.y + shoeH)
                close()
            }
            drawPath(shoeUpper, color = MaleSneakerUpper)

            // White Rubber Outsole
            drawLine(
                color = MaleSneakerSole,
                start = Offset(end.x - 6.dp.toPx(), end.y + shoeH),
                end = Offset(end.x + (toeDir * shoeLen), end.y + shoeH),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Square
            )

            // Neon green lace trim
            drawLine(
                color = MaleSneakerTrim,
                start = Offset(end.x, end.y + 2.dp.toPx()),
                end = Offset(end.x + (toeDir * 6.dp.toPx()), end.y + 4.dp.toPx()),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        return
    }

    // 3. ARM DETECTION (strokeWidth < 7.2dp: Biceps, Triceps, Forearm & Gripping Hand)
    val armUpperRadius = 8.5.dp.toPx()
    val armLowerRadius = 5.5.dp.toPx()
    val rMidArm = 7.8.dp.toPx()

    val armPath = Path().apply {
        moveTo(start.x - nx * armUpperRadius, start.y - ny * armUpperRadius)
        // Biceps Brachii muscular bulge
        quadraticTo(
            (start.x + end.x) / 2f + nx * rMidArm,
            (start.y + end.y) / 2f + ny * rMidArm,
            end.x + nx * armLowerRadius,
            end.y + ny * armLowerRadius
        )
        lineTo(end.x - nx * armLowerRadius, end.y - ny * armLowerRadius)
        // Triceps contour
        quadraticTo(
            (start.x + end.x) / 2f - nx * (rMidArm * 0.85f),
            (start.y + end.y) / 2f - ny * (rMidArm * 0.85f),
            start.x - nx * armUpperRadius,
            start.y - ny * armUpperRadius
        )
        close()
    }
    drawPath(armPath, color = skinBase)

    // Arm muscle depth contour
    drawLine(
        color = skinShadow,
        start = Offset(start.x - nx * (armUpperRadius * 0.55f), start.y - ny * (armUpperRadius * 0.55f)),
        end = Offset(end.x - nx * (armLowerRadius * 0.55f), end.y - ny * (armLowerRadius * 0.55f)),
        strokeWidth = 1.6.dp.toPx(),
        cap = StrokeCap.Round
    )

    // Bicep specular highlight
    drawLine(
        color = skinHighlight,
        start = Offset(start.x + nx * (armUpperRadius * 0.35f), start.y + ny * (armUpperRadius * 0.35f)),
        end = Offset(end.x + nx * (armLowerRadius * 0.35f), end.y + ny * (armLowerRadius * 0.35f)),
        strokeWidth = 1.8.dp.toPx(),
        cap = StrokeCap.Round
    )

    // Deltoid cap at start
    drawCircle(color = skinBase, radius = armUpperRadius, center = start)

    // Sleek Fitness Smartwatch on Wrist (as in reference photo)
    val watchCenter = Offset(end.x - ux * 3.5.dp.toPx(), end.y - uy * 3.5.dp.toPx())
    drawCircle(
        color = MaleSmartwatchBand,
        radius = armLowerRadius * 1.15f,
        center = watchCenter
    )
    drawRect(
        color = MaleSmartwatchFace,
        topLeft = Offset(watchCenter.x - 2.5.dp.toPx(), watchCenter.y - 2.5.dp.toPx()),
        size = Size(5.dp.toPx(), 5.dp.toPx())
    )

    // Clenched Athletic Gripping Hand / Fist at end
    val fistRadius = 5.5.dp.toPx()
    drawCircle(color = skinBase, radius = fistRadius, center = end)
    drawCircle(
        color = skinShadow,
        radius = 2.4.dp.toPx(),
        center = Offset(end.x + nx * 2.2.dp.toPx(), end.y + ny * 2.2.dp.toPx())
    )
}

fun DrawScope.drawMuscleGlow(
    center: Offset,
    radius: Float,
    color: Color = MetricCyan,
    pulse: Float = 0.7f
) {
    drawCircle(
        color = color.copy(alpha = (0.35f + 0.35f * pulse).coerceIn(0.1f, 0.9f)),
        radius = radius,
        center = center
    )
}

/**
 * Realistic Olympic Barbell with chrome steel bar, knurling marks,
 * and 45lb/20kg Olympic rubber bumper plates with inner steel hubs.
 */
fun DrawScope.drawBarbell(
    start: Offset,
    end: Offset,
    barColor: Color = EquipmentSteelColor,
    plateColor: Color = EquipmentPlateColor,
    plateRadius: Float = 14.dp.toPx()
) {
    // 1. Chrome Olympic Bar with shine
    drawLine(
        color = Color(0xFFE2E8F0),
        start = start,
        end = end,
        strokeWidth = 4.5.dp.toPx(),
        cap = StrokeCap.Square
    )
    drawLine(
        color = Color(0xFF94A3B8),
        start = start,
        end = end,
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Square
    )

    // 2. Left & Right Olympic Bumper Plates
    listOf(start, end).forEach { pos ->
        // Outer rubber plate with raised beveled rim
        drawCircle(
            color = Color(0xFF141820),
            radius = plateRadius,
            center = pos
        )
        drawCircle(
            color = Color(0xFF222B38),
            radius = plateRadius * 0.88f,
            center = pos
        )
        // Kinetic Neon weight ring indicator
        drawCircle(
            color = KineticNeon.copy(alpha = 0.9f),
            radius = plateRadius * 0.55f,
            center = pos
        )
        // Center steel hub & sleeve collar
        drawCircle(
            color = Color(0xFFCBD5E1),
            radius = plateRadius * 0.32f,
            center = pos
        )
        drawCircle(
            color = Color(0xFF0F172A),
            radius = plateRadius * 0.16f,
            center = pos
        )
    }
}

/**
 * Realistic Hexagon Contoured Dumbbell with knurled steel grip
 * and beveled urethane hex heads.
 */
fun DrawScope.drawDumbbell(
    center: Offset,
    angleRad: Float = 0f,
    length: Float = 22.dp.toPx(),
    plateSize: Float = 8.dp.toPx()
) {
    val dx = cos(angleRad) * (length / 2f)
    val dy = sin(angleRad) * (length / 2f)
    val p1 = Offset(center.x - dx, center.y - dy)
    val p2 = Offset(center.x + dx, center.y + dy)

    // Chrome knurled handle
    drawLine(
        color = Color(0xFFCBD5E1),
        start = p1,
        end = p2,
        strokeWidth = 3.5.dp.toPx(),
        cap = StrokeCap.Round
    )

    // Hex Dumbbell Heads with beveled 3D facets
    listOf(p1, p2).forEach { p ->
        drawCircle(
            color = Color(0xFF1E293B),
            radius = plateSize,
            center = p
        )
        drawCircle(
            color = KineticNeon,
            radius = plateSize * 0.55f,
            center = p
        )
    }
}

/**
 * Heavy-Duty Commercial Flat Bench with stitched leather cushion,
 * steel subframe, and rubber non-slip feet.
 */
fun DrawScope.drawFlatBench(
    floorY: Float,
    startX: Float,
    endX: Float,
    benchY: Float
) {
    // 1. Leather Cushion with Neon edge stitch
    drawLine(
        color = Color(0xFF161B22),
        start = Offset(startX, benchY),
        end = Offset(endX, benchY),
        strokeWidth = 12.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = KineticNeon.copy(alpha = 0.45f),
        start = Offset(startX + 4.dp.toPx(), benchY - 4.dp.toPx()),
        end = Offset(endX - 4.dp.toPx(), benchY - 4.dp.toPx()),
        strokeWidth = 1.5.dp.toPx()
    )

    // 2. Steel Legs with rubber feet
    listOf(startX + 16.dp.toPx(), endX - 16.dp.toPx()).forEach { legX ->
        drawLine(
            color = Color(0xFF475569),
            start = Offset(legX, benchY + 6.dp.toPx()),
            end = Offset(legX, floorY),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Square
        )
        // Rubber foot pad
        drawRect(
            color = Color(0xFF0F172A),
            topLeft = Offset(legX - 6.dp.toPx(), floorY - 3.dp.toPx()),
            size = Size(12.dp.toPx(), 4.dp.toPx())
        )
    }
}

/**
 * Adjustable Commercial Incline Bench with contoured backrest and seat base.
 */
fun DrawScope.drawInclineBench(
    floorY: Float,
    bottomX: Float,
    topX: Float,
    bottomY: Float,
    topY: Float
) {
    // Angled leather backrest
    drawLine(
        color = Color(0xFF161B22),
        start = Offset(bottomX, bottomY),
        end = Offset(topX, topY),
        strokeWidth = 12.dp.toPx(),
        cap = StrokeCap.Round
    )
    // Seat base
    drawLine(
        color = Color(0xFF161B22),
        start = Offset(bottomX - 18.dp.toPx(), bottomY + 2.dp.toPx()),
        end = Offset(bottomX, bottomY),
        strokeWidth = 10.dp.toPx(),
        cap = StrokeCap.Round
    )
    // Steel spine supports
    drawLine(
        color = Color(0xFF475569),
        start = Offset(bottomX, bottomY),
        end = Offset(bottomX, floorY),
        strokeWidth = 4.5.dp.toPx()
    )
    drawLine(
        color = Color(0xFF475569),
        start = Offset((bottomX + topX) / 2f, (bottomY + topY) / 2f),
        end = Offset((bottomX + topX) / 2f, floorY),
        strokeWidth = 4.5.dp.toPx()
    )
}

/**
 * Powder-Coated Steel Pull-Up Rig
 */
fun DrawScope.drawPullUpRig(
    w: Float,
    floorY: Float,
    barY: Float,
    barStartX: Float = w * 0.25f,
    barEndX: Float = w * 0.75f
) {
    // Vertical Posts
    drawLine(
        color = Color(0xFF242C38),
        start = Offset(barStartX, barY),
        end = Offset(barStartX, floorY),
        strokeWidth = 7.dp.toPx()
    )
    drawLine(
        color = Color(0xFF242C38),
        start = Offset(barEndX, barY),
        end = Offset(barEndX, floorY),
        strokeWidth = 7.dp.toPx()
    )
    // Horizontal Bar (Chrome)
    drawLine(
        color = Color(0xFFCBD5E1),
        start = Offset(barStartX - 10.dp.toPx(), barY),
        end = Offset(barEndX + 10.dp.toPx(), barY),
        strokeWidth = 5.5.dp.toPx(),
        cap = StrokeCap.Round
    )
}

/**
 * Heavy-Duty Parallel Dip Bars
 */
fun DrawScope.drawParallelDipBars(
    floorY: Float,
    barLeftX: Float,
    barRightX: Float,
    barY: Float
) {
    listOf(barLeftX, barRightX).forEach { barX ->
        // Chrome gripping bar
        drawLine(
            color = Color(0xFFCBD5E1),
            start = Offset(barX - 16.dp.toPx(), barY),
            end = Offset(barX + 16.dp.toPx(), barY),
            strokeWidth = 6.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Upright post
        drawLine(
            color = Color(0xFF242C38),
            start = Offset(barX, barY),
            end = Offset(barX, floorY),
            strokeWidth = 6.dp.toPx()
        )
    }
}
