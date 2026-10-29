package com.example.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.example.data.model.AnimationMode
import com.example.ui.theme.CriticalRed
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import kotlin.math.cos
import kotlin.math.sin

/**
 * Realistic Male Character Biomechanical Demonstrations:
 * Upper Body (Chest, Biceps, Triceps, Forearms, Shoulders, Back)
 */

fun DrawScope.drawUpperBodyDemonstration(
    animType: String,
    progress: Float,
    mode: AnimationMode,
    isBeginner: Boolean = false
) {
    val w = size.width
    val h = size.height
    val floorY = h * 0.82f

    when (animType) {
        "PUSH_UP_STANDARD", "PUSH_UP_DIAMOND", "PUSH_UP_KNEE" -> {
            drawPushUpMovement(w, h, floorY, progress, mode, isKnee = isBeginner || animType == "PUSH_UP_KNEE", isDiamond = animType == "PUSH_UP_DIAMOND")
        }
        "PUSH_UP_INCLINE" -> {
            drawInclinePushUpMovement(w, h, floorY, progress, mode)
        }
        "PUSH_UP_DECLINE" -> {
            drawDeclinePushUpMovement(w, h, floorY, progress, mode)
        }
        "BENCH_PRESS_BARBELL" -> {
            drawBenchPressBarbell(w, h, floorY, progress, mode)
        }
        "BENCH_PRESS_DUMBBELL", "CHEST_FLY_DUMBBELL" -> {
            drawBenchPressDumbbell(w, h, floorY, progress, mode, isFly = animType == "CHEST_FLY_DUMBBELL")
        }
        "INCLINE_DUMBBELL_PRESS" -> {
            drawInclineDumbbellPress(w, h, floorY, progress, mode)
        }
        "CHEST_DIPS", "TRICEPS_DIPS" -> {
            drawDipsMovement(w, h, floorY, progress, mode, isChestLean = animType == "CHEST_DIPS")
        }
        "BICEPS_CURL_DUMBBELL", "HAMMER_CURL" -> {
            drawBicepCurlMovement(w, h, floorY, progress, mode, isHammer = animType == "HAMMER_CURL")
        }
        "CONCENTRATION_CURL" -> {
            drawConcentrationCurl(w, h, floorY, progress, mode)
        }
        "PREACHER_CURL" -> {
            drawPreacherCurl(w, h, floorY, progress, mode)
        }
        "TRICEPS_EXTENSION_OVERHEAD" -> {
            drawOverheadTricepsExtension(w, h, floorY, progress, mode)
        }
        "TRICEPS_PUSHDOWN_CABLE" -> {
            drawCableTricepsPushdown(w, h, floorY, progress, mode)
        }
        "SKULL_CRUSHERS" -> {
            drawSkullCrushers(w, h, floorY, progress, mode)
        }
        "WRIST_CURLS" -> {
            drawWristCurls(w, h, floorY, progress, mode)
        }
        "FARMERS_WALK" -> {
            drawFarmersWalk(w, h, floorY, progress, mode)
        }
        "OVERHEAD_PRESS_BARBELL", "SHOULDER_PRESS_DUMBBELL" -> {
            drawOverheadShoulderPress(w, h, floorY, progress, mode, isBarbell = animType.contains("BARBELL"))
        }
        "LATERAL_RAISES", "FRONT_RAISES" -> {
            drawLateralOrFrontRaises(w, h, floorY, progress, mode, isFront = animType == "FRONT_RAISES")
        }
        "REVERSE_FLY" -> {
            drawReverseFly(w, h, floorY, progress, mode)
        }
        "PIKE_PUSH_UP" -> {
            drawPikePushUp(w, h, floorY, progress, mode)
        }
        "CABLE_FACE_PULL" -> {
            drawCableFacePull(w, h, floorY, progress, mode)
        }
        "PULL_UP", "CHIN_UP" -> {
            drawPullUpOrChinUp(w, h, floorY, progress, mode, isChinUp = animType == "CHIN_UP")
        }
        "BARBELL_ROW" -> {
            drawBarbellBentOverRow(w, h, floorY, progress, mode)
        }
        "DUMBBELL_ROW" -> {
            drawSingleArmDumbbellRow(w, h, floorY, progress, mode)
        }
        "LAT_PULLDOWN" -> {
            drawLatPulldown(w, h, floorY, progress, mode)
        }
        "SEATED_CABLE_ROW" -> {
            drawSeatedCableRow(w, h, floorY, progress, mode)
        }
        "SUPERMAN_EXTENSION" -> {
            drawSupermanExtension(w, h, floorY, progress, mode)
        }
        "CABLE_CROSSOVER" -> {
            drawCableCrossover(w, h, floorY, progress, mode)
        }
        "INVERTED_ROWS" -> {
            drawInvertedRows(w, h, floorY, progress, mode)
        }
    }
}

// 1. Push-Ups (Floor)
private fun DrawScope.drawPushUpMovement(
    w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isKnee: Boolean, isDiamond: Boolean
) {
    val feetX = w * 0.20f
    val handsX = w * 0.70f
    val drop = progress * (h * 0.25f)
    val hipDrop = if (mode == AnimationMode.COMMON_MISTAKES) drop * 1.5f else drop * 0.7f

    val shoulderX = handsX
    val shoulderY = (floorY - (h * 0.32f)) + drop
    val headX = shoulderX + (w * 0.08f)
    val headY = shoulderY - 8.dp.toPx()
    val hipX = w * 0.44f
    val hipY = (floorY - (h * 0.22f)) + hipDrop

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx(),
        if (mode == AnimationMode.COMMON_MISTAKES && hipDrop > drop) CriticalRed else AvatarSkinColor)

    if (isKnee) {
        val kneeX = w * 0.35f
        val kneeY = floorY - 4.dp.toPx()
        drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 7.dp.toPx())
        drawLimb(Offset(kneeX, kneeY), Offset(feetX, floorY - 18.dp.toPx()), 6.dp.toPx())
    } else {
        drawLimb(Offset(hipX, hipY), Offset(feetX, floorY - 4.dp.toPx()), 7.dp.toPx())
    }

    val elbowX = if (isDiamond) handsX - (w * 0.06f * progress) else handsX - (w * 0.03f * progress)
    val elbowY = (shoulderY + floorY) / 2f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handsX, floorY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 8.dp.toPx(), shoulderY + 4.dp.toPx()), 20.dp.toPx(), MetricCyan, progress)
        drawMuscleGlow(Offset(elbowX, elbowY), 14.dp.toPx(), KineticNeon, progress)
    }
}

// 2. Incline Push-Up (Bench Elevated)
private fun DrawScope.drawInclinePushUpMovement(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchHeight = h * 0.22f
    val benchX = w * 0.72f
    drawFlatBench(floorY, benchX - 25.dp.toPx(), benchX + 25.dp.toPx(), floorY - benchHeight)

    val feetX = w * 0.20f
    val drop = progress * (h * 0.18f)
    val shoulderX = benchX
    val shoulderY = (floorY - benchHeight - (h * 0.20f)) + drop
    val hipX = w * 0.44f
    val hipY = (floorY - (h * 0.16f)) + (drop * 0.6f)

    drawMaleHead(Offset(shoulderX + (w * 0.07f), shoulderY - 6.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(feetX, floorY - 4.dp.toPx()), 7.dp.toPx())

    val elbowX = shoulderX - (w * 0.04f * progress)
    val elbowY = (shoulderY + (floorY - benchHeight)) / 2f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(benchX, floorY - benchHeight), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 6.dp.toPx(), shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 3. Decline Push-Up (Feet Elevated)
private fun DrawScope.drawDeclinePushUpMovement(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchX = w * 0.25f
    val benchHeight = h * 0.25f
    drawFlatBench(floorY, benchX - 25.dp.toPx(), benchX + 25.dp.toPx(), floorY - benchHeight)

    val feetX = benchX
    val handsX = w * 0.70f
    val drop = progress * (h * 0.22f)
    val shoulderX = handsX
    val shoulderY = (floorY - (h * 0.15f)) + drop
    val hipX = w * 0.45f
    val hipY = (floorY - benchHeight + 8.dp.toPx()) + (drop * 0.5f)

    drawMaleHead(Offset(shoulderX + (w * 0.08f), shoulderY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(feetX, floorY - benchHeight), 7.dp.toPx())

    val elbowX = handsX - (w * 0.03f * progress)
    val elbowY = (shoulderY + floorY) / 2f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handsX, floorY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 8.dp.toPx(), shoulderY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 4. Bench Press (Barbell)
private fun DrawScope.drawBenchPressBarbell(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchY = floorY - (h * 0.25f)
    drawFlatBench(floorY, w * 0.28f, w * 0.72f, benchY)

    val headX = w * 0.35f
    val headY = benchY - 12.dp.toPx()
    val shoulderX = w * 0.45f
    val shoulderY = benchY - 8.dp.toPx()
    val hipX = w * 0.62f
    val hipY = benchY - 6.dp.toPx()
    val feetX = w * 0.70f
    val feetY = floorY - 4.dp.toPx()

    // Torso & head on bench
    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(feetX, feetY), 7.dp.toPx())

    // Vertical press movement: 0 = top lockout, 1 = touching chest
    val barHeightTravel = (h * 0.22f) * (1f - progress)
    val barY = (benchY - 14.dp.toPx()) - barHeightTravel
    val handsX = shoulderX

    // Arms
    val elbowX = shoulderX + (12.dp.toPx() * (1f - progress))
    val elbowY = (shoulderY + barY) / 2f + (8.dp.toPx() * progress)
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handsX, barY), 6.dp.toPx())

    // Barbell & Plates
    drawBarbell(Offset(handsX - 40.dp.toPx(), barY), Offset(handsX + 40.dp.toPx(), barY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 22.dp.toPx(), MetricCyan, progress)
    }
}

// 5. Bench Press / Chest Fly (Dumbbell)
private fun DrawScope.drawBenchPressDumbbell(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isFly: Boolean) {
    val benchY = floorY - (h * 0.25f)
    drawFlatBench(floorY, w * 0.28f, w * 0.72f, benchY)

    val shoulderX = w * 0.45f
    val shoulderY = benchY - 8.dp.toPx()
    val hipX = w * 0.62f
    val hipY = benchY - 6.dp.toPx()
    drawMaleHead(Offset(w * 0.35f, benchY - 12.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(w * 0.70f, floorY - 4.dp.toPx()), 7.dp.toPx())

    if (isFly) {
        // Arc movement: arms open wide at progress 1, close together at progress 0
        val arcProg = 1f - progress
        val handX = shoulderX + (cos(arcProg * 1.4f) * 35.dp.toPx())
        val handY = (shoulderY - 12.dp.toPx()) - (sin(arcProg * 1.4f) * 45.dp.toPx())
        drawLimb(Offset(shoulderX, shoulderY), Offset(handX, handY), 7.dp.toPx())
        drawDumbbell(Offset(handX, handY), angleRad = 0.5f)
    } else {
        val pressY = (shoulderY - 14.dp.toPx()) - ((h * 0.20f) * (1f - progress))
        drawLimb(Offset(shoulderX, shoulderY), Offset(shoulderX + 8.dp.toPx(), (shoulderY + pressY) / 2f), 7.dp.toPx())
        drawLimb(Offset(shoulderX + 8.dp.toPx(), (shoulderY + pressY) / 2f), Offset(shoulderX, pressY), 6.dp.toPx())
        drawDumbbell(Offset(shoulderX, pressY), angleRad = 0f)
    }

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 6. Incline Dumbbell Press
private fun DrawScope.drawInclineDumbbellPress(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val bottomX = w * 0.42f
    val topX = w * 0.28f
    val bottomY = floorY - (h * 0.18f)
    val topY = floorY - (h * 0.44f)
    drawInclineBench(floorY, bottomX, topX, bottomY, topY)

    val shoulderX = (bottomX + topX) / 2f
    val shoulderY = (bottomY + topY) / 2f
    drawMaleHead(Offset(topX - 6.dp.toPx(), topY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(bottomX, bottomY), 8.dp.toPx())
    drawLimb(Offset(bottomX, bottomY), Offset(bottomX + 25.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    val pressDist = (h * 0.20f) * (1f - progress)
    val handX = shoulderX + (pressDist * 0.4f)
    val handY = (shoulderY - 14.dp.toPx()) - pressDist
    drawLimb(Offset(shoulderX, shoulderY), Offset(shoulderX + 12.dp.toPx(), (shoulderY + handY) / 2f), 7.dp.toPx())
    drawLimb(Offset(shoulderX + 12.dp.toPx(), (shoulderY + handY) / 2f), Offset(handX, handY), 6.dp.toPx())
    drawDumbbell(Offset(handX, handY), angleRad = -0.3f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 7. Dips (Parallel Bars)
private fun DrawScope.drawDipsMovement(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isChestLean: Boolean) {
    val barY = floorY - (h * 0.40f)
    drawParallelDipBars(floorY, w * 0.40f, w * 0.60f, barY)

    val drop = progress * (h * 0.20f)
    val leanOffset = if (isChestLean) 14.dp.toPx() else 4.dp.toPx()
    val shoulderX = (w * 0.50f) + (leanOffset * progress)
    val shoulderY = (barY - (h * 0.22f)) + drop
    val hipX = (w * 0.50f) - (leanOffset * progress * 0.5f)
    val hipY = shoulderY + (h * 0.24f)

    drawMaleHead(Offset(shoulderX, shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx())

    // Legs bent backward
    val kneeX = hipX - 12.dp.toPx()
    val kneeY = hipY + 22.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 7.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(kneeX - 10.dp.toPx(), kneeY - 10.dp.toPx()), 6.dp.toPx())

    // Arms gripping bar
    val elbowX = shoulderX - (16.dp.toPx() * progress)
    val elbowY = (shoulderY + barY) / 2f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(w * 0.50f, barY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        val glowColor = if (isChestLean) MetricCyan else KineticNeon
        drawMuscleGlow(Offset(shoulderX, shoulderY + 8.dp.toPx()), 18.dp.toPx(), glowColor, progress)
    }
}

// 8. Biceps Curls (Standing Dumbbell / Hammer)
private fun DrawScope.drawBicepCurlMovement(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isHammer: Boolean) {
    val headX = w * 0.48f
    val headY = h * 0.20f
    val shoulderX = headX
    val shoulderY = h * 0.32f
    val hipX = headX
    val hipY = h * 0.56f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    // Legs
    drawLimb(Offset(hipX, hipY), Offset(headX - 6.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 6.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Upper arm pinned
    val elbowX = shoulderX + 8.dp.toPx()
    val elbowY = h * 0.50f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())

    // Forearm curl curve
    val angleRad = (Math.PI / 2.0) - (progress * Math.PI * 0.65)
    val forearmLen = h * 0.18f
    val handX = (elbowX + (sin(angleRad) * forearmLen)).toFloat()
    val handY = (elbowY + (cos(angleRad) * forearmLen)).toFloat()
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())

    drawDumbbell(Offset(handX, handY), angleRad = if (isHammer) 1.57f else 0f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + elbowX) / 2f + 4.dp.toPx(), (shoulderY + elbowY) / 2f), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 9. Concentration Curl (Seated inner thigh)
private fun DrawScope.drawConcentrationCurl(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchY = floorY - (h * 0.30f)
    drawFlatBench(floorY, w * 0.30f, w * 0.70f, benchY)

    val hipX = w * 0.45f
    val hipY = benchY - 6.dp.toPx()
    val shoulderX = w * 0.55f
    val shoulderY = benchY - (h * 0.22f)

    drawMaleHead(Offset(shoulderX + 6.dp.toPx(), shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx())
    // Leg braced
    val kneeX = w * 0.68f
    val kneeY = benchY + 12.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(kneeX + 4.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Elbow braced on inner thigh
    val elbowX = kneeX - 8.dp.toPx()
    val elbowY = kneeY - 4.dp.toPx()
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())

    val angle = (Math.PI / 2.0) - (progress * Math.PI * 0.60)
    val handX = (elbowX - (sin(angle) * 30.dp.toPx())).toFloat()
    val handY = (elbowY - (cos(angle) * 30.dp.toPx())).toFloat()
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawDumbbell(Offset(handX, handY), angleRad = 0.4f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + elbowX) / 2f, (shoulderY + elbowY) / 2f), 16.dp.toPx(), MetricCyan, progress)
    }
}

// 10. Preacher Curl (Preacher Bench)
private fun DrawScope.drawPreacherCurl(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val padX = w * 0.52f
    val padY = floorY - (h * 0.32f)
    // Angled pad
    drawLine(EquipmentFrameColor, Offset(padX - 25.dp.toPx(), padY - 15.dp.toPx()), Offset(padX + 25.dp.toPx(), padY + 10.dp.toPx()), 10.dp.toPx(), StrokeCap.Round)

    val shoulderX = padX - 16.dp.toPx()
    val shoulderY = padY - 20.dp.toPx()
    drawMaleHead(Offset(shoulderX - 10.dp.toPx(), shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(padX - 30.dp.toPx(), floorY - (h * 0.20f)), 8.dp.toPx())

    // Arm on pad
    val elbowX = padX + 10.dp.toPx()
    val elbowY = padY + 4.dp.toPx()
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())

    val curlProg = 1f - progress
    val handX = elbowX - (cos(curlProg * 1.4f) * 30.dp.toPx())
    val handY = elbowY - (sin(curlProg * 1.4f) * 32.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawBarbell(Offset(handX - 16.dp.toPx(), handY), Offset(handX + 16.dp.toPx(), handY), plateRadius = 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(elbowX, elbowY - 10.dp.toPx()), 16.dp.toPx(), MetricCyan, progress)
    }
}

// 11. Overhead Triceps Extension
private fun DrawScope.drawOverheadTricepsExtension(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val headX = w * 0.50f
    val headY = h * 0.28f
    val shoulderX = headX
    val shoulderY = h * 0.40f
    val hipX = headX
    val hipY = h * 0.62f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX - 8.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 8.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Upper arm points straight up overhead
    val elbowX = shoulderX + 4.dp.toPx()
    val elbowY = h * 0.16f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())

    // Forearm drops behind head at progress 1, extends up at progress 0
    val forearmAngle = (Math.PI / 2.0) + (progress * Math.PI * 0.60)
    val handX = (elbowX - (cos(forearmAngle) * 28.dp.toPx())).toFloat()
    val handY = (elbowY + (sin(forearmAngle) * 28.dp.toPx())).toFloat()
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawDumbbell(Offset(handX, handY), angleRad = 0f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(elbowX - 4.dp.toPx(), (shoulderY + elbowY) / 2f), 16.dp.toPx(), KineticNeon, progress)
    }
}

// 12. Cable Triceps Pushdown
private fun DrawScope.drawCableTricepsPushdown(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val machineX = w * 0.68f
    // Machine Column & Pulley
    drawLine(EquipmentFrameColor, Offset(machineX, h * 0.10f), Offset(machineX, floorY), 8.dp.toPx())
    drawCircle(EquipmentSteelColor, 8.dp.toPx(), Offset(machineX - 12.dp.toPx(), h * 0.14f))

    val headX = w * 0.44f
    val headY = h * 0.22f
    val shoulderX = headX
    val shoulderY = h * 0.34f
    val hipX = headX - 6.dp.toPx()
    val hipY = h * 0.58f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX - 10.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 6.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Upper arm pinned to side
    val elbowX = shoulderX + 8.dp.toPx()
    val elbowY = h * 0.48f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())

    // Forearm pushes down from 90° (progress 0) to lockout (progress 1)
    val handAngle = 0.2f + (progress * 1.2f)
    val handX = elbowX + (sin(handAngle) * 30.dp.toPx())
    val handY = elbowY + (cos(handAngle) * 30.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())

    // Cable to hand
    drawLine(EquipmentSteelColor, Offset(machineX - 12.dp.toPx(), h * 0.14f), Offset(handX, handY), 2.dp.toPx())
    drawCircle(KineticNeon, 6.dp.toPx(), Offset(handX, handY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(elbowX - 6.dp.toPx(), (shoulderY + elbowY) / 2f), 16.dp.toPx(), KineticNeon, progress)
    }
}

// 13. Skull Crushers (Lying on bench with EZ bar)
private fun DrawScope.drawSkullCrushers(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchY = floorY - (h * 0.25f)
    drawFlatBench(floorY, w * 0.25f, w * 0.70f, benchY)

    val headX = w * 0.35f
    val shoulderX = w * 0.45f
    val shoulderY = benchY - 8.dp.toPx()
    val hipX = w * 0.62f
    val hipY = benchY - 6.dp.toPx()

    drawMaleHead(Offset(headX, benchY - 12.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(w * 0.70f, floorY - 4.dp.toPx()), 7.dp.toPx())

    // Upper arm vertical
    val elbowX = shoulderX - 4.dp.toPx()
    val elbowY = benchY - (h * 0.24f)
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())

    // Forearm hinges back to forehead at progress 1, extends up at progress 0
    val bend = progress * 1.5f
    val handX = elbowX - (sin(bend) * 26.dp.toPx())
    val handY = elbowY + (cos(bend) * 26.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawBarbell(Offset(handX - 18.dp.toPx(), handY), Offset(handX + 18.dp.toPx(), handY), plateRadius = 7.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(elbowX, (shoulderY + elbowY) / 2f), 16.dp.toPx(), KineticNeon, progress)
    }
}

// 14. Forearm Wrist Curls
private fun DrawScope.drawWristCurls(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchY = floorY - (h * 0.30f)
    drawFlatBench(floorY, w * 0.30f, w * 0.70f, benchY)

    val hipX = w * 0.45f
    val hipY = benchY - 6.dp.toPx()
    val shoulderX = w * 0.55f
    val shoulderY = benchY - (h * 0.22f)
    drawMaleHead(Offset(shoulderX + 6.dp.toPx(), shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx())

    // Forearm resting on thigh
    val kneeX = w * 0.65f
    val kneeY = benchY + 10.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(kneeX + 4.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    val wristX = kneeX + 12.dp.toPx()
    val wristY = kneeY
    drawLimb(Offset(shoulderX, shoulderY), Offset(wristX, wristY), 7.dp.toPx())

    // Wrist flexion
    val curlAngle = -0.4f + (progress * 0.8f)
    val handX = wristX + (cos(curlAngle) * 14.dp.toPx())
    val handY = wristY - (sin(curlAngle) * 14.dp.toPx())
    drawLimb(Offset(wristX, wristY), Offset(handX, handY), 5.dp.toPx())
    drawDumbbell(Offset(handX, handY), angleRad = 0f, length = 16.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + wristX) / 2f, wristY), 14.dp.toPx(), MetricCyan, progress)
    }
}

// 15. Farmer's Walk
private fun DrawScope.drawFarmersWalk(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val stepShift = (sin(progress * Math.PI.toFloat() * 2f) * 16.dp.toPx())
    val headX = (w * 0.50f) + stepShift * 0.3f
    val headY = h * 0.20f
    val shoulderX = headX
    val shoulderY = h * 0.32f
    val hipX = headX
    val hipY = h * 0.56f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Walking legs scissor
    val legOffset = sin(progress * Math.PI.toFloat() * 2f) * 20.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(hipX + legOffset, floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(hipX - legOffset, floorY - 4.dp.toPx()), 7.dp.toPx())

    // Arms hanging straight holding heavy dumbbells
    drawLimb(Offset(shoulderX - 12.dp.toPx(), shoulderY), Offset(shoulderX - 12.dp.toPx(), h * 0.56f), 7.dp.toPx())
    drawLimb(Offset(shoulderX + 12.dp.toPx(), shoulderY), Offset(shoulderX + 12.dp.toPx(), h * 0.56f), 7.dp.toPx())
    drawDumbbell(Offset(shoulderX - 12.dp.toPx(), h * 0.56f), angleRad = 0f, length = 24.dp.toPx(), plateSize = 10.dp.toPx())
    drawDumbbell(Offset(shoulderX + 12.dp.toPx(), h * 0.56f), angleRad = 0f, length = 24.dp.toPx(), plateSize = 10.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 12.dp.toPx(), h * 0.48f), 14.dp.toPx(), MetricCyan, progress)
        drawMuscleGlow(Offset(shoulderX + 12.dp.toPx(), h * 0.48f), 14.dp.toPx(), MetricCyan, progress)
    }
}

// 16. Overhead Shoulder Press (Barbell / Dumbbell)
private fun DrawScope.drawOverheadShoulderPress(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isBarbell: Boolean) {
    val headX = w * 0.50f
    val headY = h * 0.28f
    val shoulderX = headX
    val shoulderY = h * 0.40f
    val hipX = headX
    val hipY = h * 0.62f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX - 8.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 8.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Vertical press motion: 0 = top lockout, 1 = chin level
    val pressTravel = (h * 0.22f) * (1f - progress)
    val handY = shoulderY - pressTravel
    val leftHandX = shoulderX - 16.dp.toPx()
    val rightHandX = shoulderX + 16.dp.toPx()

    // Arms
    val leftElbowX = shoulderX - 22.dp.toPx()
    val rightElbowX = shoulderX + 22.dp.toPx()
    val elbowY = (shoulderY + handY) / 2f + (8.dp.toPx() * progress)
    drawLimb(Offset(shoulderX, shoulderY), Offset(leftElbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(leftElbowX, elbowY), Offset(leftHandX, handY), 6.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(rightElbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(rightElbowX, elbowY), Offset(rightHandX, handY), 6.dp.toPx())

    if (isBarbell) {
        drawBarbell(Offset(leftHandX - 20.dp.toPx(), handY), Offset(rightHandX + 20.dp.toPx(), handY))
    } else {
        drawDumbbell(Offset(leftHandX, handY), angleRad = 0f)
        drawDumbbell(Offset(rightHandX, handY), angleRad = 0f)
    }

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 10.dp.toPx(), shoulderY - 2.dp.toPx()), 18.dp.toPx(), MetricCyan, progress)
        drawMuscleGlow(Offset(shoulderX + 10.dp.toPx(), shoulderY - 2.dp.toPx()), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 17. Lateral Raises / Front Raises
private fun DrawScope.drawLateralOrFrontRaises(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isFront: Boolean) {
    val headX = w * 0.50f
    val headY = h * 0.22f
    val shoulderX = headX
    val shoulderY = h * 0.34f
    val hipX = headX
    val hipY = h * 0.58f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX - 8.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 8.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    if (isFront) {
        // Raise forward to 90 degrees
        val angle = (Math.PI / 2.0) - (progress * Math.PI * 0.50)
        val armLen = h * 0.24f
        val handX = (shoulderX + (cos(angle) * armLen)).toFloat()
        val handY = (shoulderY + (sin(angle) * armLen)).toFloat()
        drawLimb(Offset(shoulderX, shoulderY), Offset(handX, handY), 7.dp.toPx())
        drawDumbbell(Offset(handX, handY), angleRad = 0f)
    } else {
        // Lateral arc to sides
        val armLen = h * 0.24f
        val angle = (progress * (Math.PI * 0.45)).toFloat()
        val leftX = shoulderX - (sin(angle) * armLen)
        val rightX = shoulderX + (sin(angle) * armLen)
        val handsY = shoulderY + (cos(angle) * armLen)

        drawLimb(Offset(shoulderX, shoulderY), Offset(leftX, handsY), 7.dp.toPx())
        drawLimb(Offset(shoulderX, shoulderY), Offset(rightX, handsY), 7.dp.toPx())
        drawDumbbell(Offset(leftX, handsY), angleRad = 0.2f)
        drawDumbbell(Offset(rightX, handsY), angleRad = -0.2f)
    }

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 18. Reverse Fly (Bent-over rear delts)
private fun DrawScope.drawReverseFly(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val hipX = w * 0.40f
    val hipY = h * 0.50f
    val shoulderX = w * 0.58f
    val shoulderY = h * 0.46f

    drawMaleHead(Offset(shoulderX + 12.dp.toPx(), shoulderY - 4.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx())
    // Bent knees
    val kneeX = w * 0.46f
    val kneeY = floorY - (h * 0.16f)
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 7.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(w * 0.42f, floorY - 4.dp.toPx()), 7.dp.toPx())

    // Arms wing outward: progress 0 = hanging down, progress 1 = flying high
    val wingLift = progress * 32.dp.toPx()
    val handX = shoulderX - 4.dp.toPx()
    val handY = (shoulderY + 36.dp.toPx()) - wingLift
    drawLimb(Offset(shoulderX, shoulderY), Offset(handX, handY), 6.dp.toPx())
    drawDumbbell(Offset(handX, handY), angleRad = 0.4f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 8.dp.toPx(), shoulderY), 16.dp.toPx(), MetricCyan, progress)
    }
}

// 19. Pike Push-Up (Inverted V)
private fun DrawScope.drawPikePushUp(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val feetX = w * 0.30f
    val handsX = w * 0.65f
    val hipX = w * 0.45f
    val hipY = floorY - (h * 0.48f)

    val drop = progress * (h * 0.18f)
    val headX = handsX - 10.dp.toPx()
    val headY = (floorY - (h * 0.18f)) + drop
    val shoulderX = headX - 6.dp.toPx()
    val shoulderY = headY - 12.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    // Inverted V Torso & Legs
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(feetX, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Arms
    val elbowX = handsX + (10.dp.toPx() * progress)
    val elbowY = (shoulderY + floorY) / 2f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handsX, floorY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 20. Cable Face Pull
private fun DrawScope.drawCableFacePull(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val machineX = w * 0.72f
    drawLine(EquipmentFrameColor, Offset(machineX, h * 0.10f), Offset(machineX, floorY), 8.dp.toPx())
    drawCircle(EquipmentSteelColor, 8.dp.toPx(), Offset(machineX - 10.dp.toPx(), h * 0.28f))

    val headX = w * 0.42f
    val headY = h * 0.24f
    val shoulderX = headX
    val shoulderY = h * 0.36f
    val hipX = headX - 8.dp.toPx()
    val hipY = h * 0.60f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX - 12.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 4.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // High pull towards face: progress 0 = arms extended forward, progress 1 = elbows high flared back
    val pullDist = progress * 28.dp.toPx()
    val handX = (machineX - 35.dp.toPx()) - pullDist
    val handY = h * 0.30f
    val elbowX = shoulderX - (10.dp.toPx() * progress)
    val elbowY = h * 0.30f

    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawLine(EquipmentSteelColor, Offset(machineX - 10.dp.toPx(), h * 0.28f), Offset(handX, handY), 2.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 6.dp.toPx(), shoulderY), 16.dp.toPx(), MetricCyan, progress)
    }
}

// 21. Pull-Up / Chin-Up (Overhead Rig)
private fun DrawScope.drawPullUpOrChinUp(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isChinUp: Boolean) {
    val barY = h * 0.16f
    drawPullUpRig(w, floorY, barY)

    // Vertical pull: 0 = dead hang, 1 = chin over bar
    val pullRise = progress * (h * 0.26f)
    val handsX = w * 0.50f
    val shoulderX = handsX
    val shoulderY = (barY + (h * 0.30f)) - pullRise
    val headX = handsX
    val headY = shoulderY - 14.dp.toPx()
    val hipX = handsX
    val hipY = shoulderY + (h * 0.26f)

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Hanging legs slightly crossed
    drawLimb(Offset(hipX, hipY), Offset(handsX - 6.dp.toPx(), floorY - (h * 0.15f)), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(handsX + 6.dp.toPx(), floorY - (h * 0.15f)), 7.dp.toPx())

    // Arms
    val elbowSpread = if (isChinUp) 14.dp.toPx() else 24.dp.toPx()
    val elbowX = handsX + (elbowSpread * (1f - (progress * 0.3f)))
    val elbowY = (shoulderY + barY) / 2f + (8.dp.toPx() * (1f - progress))
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handsX + 16.dp.toPx(), barY), 6.dp.toPx())

    val leftElbowX = handsX - (elbowSpread * (1f - (progress * 0.3f)))
    drawLimb(Offset(shoulderX, shoulderY), Offset(leftElbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(leftElbowX, elbowY), Offset(handsX - 16.dp.toPx(), barY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        val glowColor = if (isChinUp) KineticNeon else MetricCyan
        drawMuscleGlow(Offset(shoulderX - 12.dp.toPx(), shoulderY + 8.dp.toPx()), 22.dp.toPx(), glowColor, progress)
        drawMuscleGlow(Offset(shoulderX + 12.dp.toPx(), shoulderY + 8.dp.toPx()), 22.dp.toPx(), glowColor, progress)
    }
}

// 22. Barbell Bent-Over Row
private fun DrawScope.drawBarbellBentOverRow(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val hipX = w * 0.40f
    val hipY = h * 0.50f
    val shoulderX = w * 0.58f
    val shoulderY = h * 0.44f

    drawMaleHead(Offset(shoulderX + 12.dp.toPx(), shoulderY - 6.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    val kneeX = w * 0.46f
    val kneeY = floorY - (h * 0.16f)
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(w * 0.42f, floorY - 4.dp.toPx()), 7.dp.toPx())

    // Row bar towards belly: 0 = arms hanging down, 1 = bar at lower ribcage
    val rowLift = progress * 30.dp.toPx()
    val elbowX = shoulderX - 14.dp.toPx()
    val elbowY = (shoulderY + 22.dp.toPx()) - (rowLift * 1.1f)
    val handX = shoulderX - 4.dp.toPx()
    val handY = (floorY - (h * 0.18f)) - rowLift

    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawBarbell(Offset(handX - 25.dp.toPx(), handY), Offset(handX + 25.dp.toPx(), handY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + hipX) / 2f, (shoulderY + hipY) / 2f), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 23. Single-Arm Dumbbell Row (Knee on bench)
private fun DrawScope.drawSingleArmDumbbellRow(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchY = floorY - (h * 0.28f)
    drawFlatBench(floorY, w * 0.25f, w * 0.65f, benchY)

    val supportHandX = w * 0.35f
    val shoulderX = w * 0.52f
    val shoulderY = benchY - 14.dp.toPx()
    val hipX = w * 0.38f
    val hipY = benchY - 8.dp.toPx()

    drawMaleHead(Offset(shoulderX + 10.dp.toPx(), shoulderY - 8.dp.toPx()))
    // Bracing arm
    drawLimb(Offset(shoulderX - 10.dp.toPx(), shoulderY), Offset(supportHandX, benchY), 7.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Bracing knee on bench
    drawLimb(Offset(hipX, hipY), Offset(w * 0.44f, benchY), 7.dp.toPx())
    // Standing foot on floor
    drawLimb(Offset(hipX, hipY), Offset(w * 0.65f, floorY - 4.dp.toPx()), 7.dp.toPx())

    // Rowing arm
    val rowLift = progress * 28.dp.toPx()
    val elbowX = shoulderX - 8.dp.toPx()
    val elbowY = (shoulderY + 12.dp.toPx()) - rowLift
    val handX = shoulderX
    val handY = (floorY - (h * 0.16f)) - rowLift

    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawDumbbell(Offset(handX, handY), angleRad = 0f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 10.dp.toPx(), shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 24. Lat Pulldown Machine
private fun DrawScope.drawLatPulldown(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val machineX = w * 0.50f
    val topY = h * 0.12f
    // Overhead pulley frame
    drawLine(EquipmentFrameColor, Offset(machineX, topY), Offset(machineX, floorY), 8.dp.toPx())
    drawCircle(EquipmentSteelColor, 8.dp.toPx(), Offset(machineX, topY))
    // Seat
    val seatY = floorY - (h * 0.25f)
    drawFlatBench(floorY, machineX - 25.dp.toPx(), machineX + 25.dp.toPx(), seatY)

    val headX = machineX
    val headY = seatY - (h * 0.28f)
    val shoulderX = machineX
    val shoulderY = seatY - (h * 0.18f)
    val hipX = machineX
    val hipY = seatY - 4.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(machineX + 20.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Pulling wide bar down to clavicle
    val barTravel = progress * (h * 0.20f)
    val barY = (topY + 10.dp.toPx()) + barTravel
    val barLeftX = machineX - 35.dp.toPx()
    val barRightX = machineX + 35.dp.toPx()

    // Cable
    drawLine(EquipmentSteelColor, Offset(machineX, topY), Offset(machineX, barY), 2.dp.toPx())
    // Bar
    drawLine(EquipmentSteelColor, Offset(barLeftX, barY), Offset(barRightX, barY), 4.dp.toPx(), StrokeCap.Round)

    // Arms
    val elbowY = (shoulderY + barY) / 2f + (10.dp.toPx() * progress)
    drawLimb(Offset(shoulderX, shoulderY), Offset(machineX - 22.dp.toPx(), elbowY), 7.dp.toPx())
    drawLimb(Offset(machineX - 22.dp.toPx(), elbowY), Offset(barLeftX + 5.dp.toPx(), barY), 6.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(machineX + 22.dp.toPx(), elbowY), 7.dp.toPx())
    drawLimb(Offset(machineX + 22.dp.toPx(), elbowY), Offset(barRightX - 5.dp.toPx(), barY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 12.dp.toPx(), shoulderY + 8.dp.toPx()), 20.dp.toPx(), MetricCyan, progress)
        drawMuscleGlow(Offset(shoulderX + 12.dp.toPx(), shoulderY + 8.dp.toPx()), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 25. Seated Cable Row
private fun DrawScope.drawSeatedCableRow(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val machineX = w * 0.72f
    val pulleyY = floorY - (h * 0.20f)
    drawLine(EquipmentFrameColor, Offset(machineX, h * 0.25f), Offset(machineX, floorY), 8.dp.toPx())
    drawCircle(EquipmentSteelColor, 8.dp.toPx(), Offset(machineX - 8.dp.toPx(), pulleyY))

    val hipX = w * 0.35f
    val hipY = floorY - 10.dp.toPx()
    val shoulderX = w * 0.40f
    val shoulderY = floorY - (h * 0.32f)

    drawMaleHead(Offset(shoulderX + 4.dp.toPx(), shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Feet braced forward against footplates
    drawLimb(Offset(hipX, hipY), Offset(machineX - 22.dp.toPx(), floorY - 12.dp.toPx()), 8.dp.toPx())

    // Row handle to abdomen
    val pullDist = progress * 24.dp.toPx()
    val handX = (machineX - 35.dp.toPx()) - pullDist
    val handY = floorY - (h * 0.18f)
    val elbowX = shoulderX - (12.dp.toPx() * progress)
    val elbowY = handY - 4.dp.toPx()

    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handX, handY), 6.dp.toPx())
    drawLine(EquipmentSteelColor, Offset(machineX - 8.dp.toPx(), pulleyY), Offset(handX, handY), 2.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 6.dp.toPx(), (shoulderY + hipY) / 2f), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 26. Superman Extension (Floor)
private fun DrawScope.drawSupermanExtension(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val matY = floorY - 4.dp.toPx()
    val lift = progress * (h * 0.12f)

    val hipX = w * 0.50f
    val hipY = matY - 4.dp.toPx()
    val shoulderX = w * 0.62f
    val shoulderY = (matY - 6.dp.toPx()) - lift
    val headX = shoulderX + 12.dp.toPx()
    val headY = shoulderY - 6.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Extended arms
    drawLimb(Offset(shoulderX, shoulderY), Offset(headX + 24.dp.toPx(), headY - 4.dp.toPx()), 6.dp.toPx())
    // Lifted legs
    val feetX = w * 0.28f
    val feetY = (matY - 4.dp.toPx()) - lift
    drawLimb(Offset(hipX, hipY), Offset(feetX, feetY), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + hipX) / 2f, (shoulderY + hipY) / 2f), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 27. Cable Crossover
private fun DrawScope.drawCableCrossover(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val leftColX = w * 0.20f
    val rightColX = w * 0.80f
    val pulleyY = h * 0.20f
    drawLine(EquipmentFrameColor, Offset(leftColX, pulleyY), Offset(leftColX, floorY), 6.dp.toPx())
    drawLine(EquipmentFrameColor, Offset(rightColX, pulleyY), Offset(rightColX, floorY), 6.dp.toPx())

    val headX = w * 0.50f
    val headY = h * 0.26f
    val shoulderX = headX
    val shoulderY = h * 0.38f
    val hipX = headX
    val hipY = h * 0.62f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX - 12.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 12.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Arms bring cables from wide out (progress 0) to crossing in front (progress 1)
    val spread = (1f - progress) * 28.dp.toPx()
    val leftHandX = headX - 6.dp.toPx() - spread
    val rightHandX = headX + 6.dp.toPx() + spread
    val handsY = h * 0.46f

    drawLimb(Offset(shoulderX, shoulderY), Offset(leftHandX, handsY), 7.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(rightHandX, handsY), 7.dp.toPx())
    drawLine(EquipmentSteelColor, Offset(leftColX, pulleyY), Offset(leftHandX, handsY), 2.dp.toPx())
    drawLine(EquipmentSteelColor, Offset(rightColX, pulleyY), Offset(rightHandX, handsY), 2.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 28. Inverted Rows
private fun DrawScope.drawInvertedRows(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val barY = floorY - (h * 0.35f)
    drawPullUpRig(w, floorY, barY, barStartX = w * 0.35f, barEndX = w * 0.65f)

    // Pull chest to bar from below
    val pullLift = progress * (h * 0.20f)
    val handsX = w * 0.50f
    val shoulderX = handsX
    val shoulderY = (floorY - (h * 0.18f)) - pullLift
    val hipX = w * 0.35f
    val hipY = shoulderY + 8.dp.toPx()
    val feetX = w * 0.20f

    drawMaleHead(Offset(shoulderX + 12.dp.toPx(), shoulderY - 4.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(feetX, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Arms pulling
    drawLimb(Offset(shoulderX, shoulderY), Offset(handsX, barY), 7.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + hipX) / 2f, shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}
