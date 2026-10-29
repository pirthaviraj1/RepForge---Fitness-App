package com.example.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.example.data.model.AnimationMode
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import kotlin.math.cos
import kotlin.math.sin

/**
 * Realistic Male Character Biomechanical Demonstrations:
 * Abs, Core, Cardio, HIIT, Calisthenics, Stretching, Mobility & Functional
 */

fun DrawScope.drawCoreCardioFunctionalDemonstration(
    animType: String,
    progress: Float,
    mode: AnimationMode
) {
    val w = size.width
    val h = size.height
    val floorY = h * 0.82f

    when (animType) {
        "CRUNCHES_STANDARD" -> {
            drawCrunches(w, h, floorY, progress, mode)
        }
        "BICYCLE_CRUNCHES" -> {
            drawBicycleCrunches(w, h, floorY, progress, mode)
        }
        "LEG_RAISES_LYING" -> {
            drawLyingLegRaises(w, h, floorY, progress, mode)
        }
        "HANGING_LEG_RAISES" -> {
            drawHangingLegRaises(w, h, floorY, progress, mode)
        }
        "PLANK_HOLD" -> {
            drawPlankHold(w, h, floorY, progress, mode)
        }
        "SIDE_PLANK" -> {
            drawSidePlank(w, h, floorY, progress, mode)
        }
        "MOUNTAIN_CLIMBERS" -> {
            drawMountainClimbers(w, h, floorY, progress, mode)
        }
        "RUSSIAN_TWISTS" -> {
            drawRussianTwists(w, h, floorY, progress, mode)
        }
        "AB_WHEEL_ROLLOUT" -> {
            drawAbWheelRollout(w, h, floorY, progress, mode)
        }
        "TREADMILL_RUNNING" -> {
            drawTreadmillRunning(w, h, floorY, progress, mode)
        }
        "EXERCISE_BIKE" -> {
            drawExerciseBike(w, h, floorY, progress, mode)
        }
        "JUMP_ROPE" -> {
            drawJumpRope(w, h, floorY, progress, mode)
        }
        "JUMPING_JACKS" -> {
            drawJumpingJacks(w, h, floorY, progress, mode)
        }
        "HIGH_KNEES" -> {
            drawHighKnees(w, h, floorY, progress, mode)
        }
        "BURPEES" -> {
            drawBurpees(w, h, floorY, progress, mode)
        }
        "KETTLEBELL_SWING" -> {
            drawKettlebellSwing(w, h, floorY, progress, mode)
        }
        "BATTLE_ROPES" -> {
            drawBattleRopes(w, h, floorY, progress, mode)
        }
        "MEDICINE_BALL_SLAM" -> {
            drawMedicineBallSlam(w, h, floorY, progress, mode)
        }
        "HAMSTRING_STRETCH" -> {
            drawHamstringStretch(w, h, floorY, progress, mode)
        }
        "QUADRICEPS_STRETCH" -> {
            drawQuadricepsStretch(w, h, floorY, progress, mode)
        }
        "COBRA_STRETCH" -> {
            drawCobraStretch(w, h, floorY, progress, mode)
        }
        "CHEST_DOORWAY_STRETCH" -> {
            drawChestDoorwayStretch(w, h, floorY, progress, mode)
        }
        "WORLDS_GREATEST_STRETCH" -> {
            drawWorldsGreatestStretch(w, h, floorY, progress, mode)
        }
        "CAT_COW_MOBILITY" -> {
            drawCatCowMobility(w, h, floorY, progress, mode)
        }
        "DEEP_SQUAT_PRY" -> {
            drawDeepSquatPry(w, h, floorY, progress, mode)
        }
        "L_SIT_HOLD" -> {
            drawLSitHold(w, h, floorY, progress, mode)
        }
        "MUSCLE_UP" -> {
            drawMuscleUp(w, h, floorY, progress, mode)
        }
        "TURKISH_GET_UP" -> {
            drawTurkishGetUp(w, h, floorY, progress, mode)
        }
        "SLED_PUSH" -> {
            drawSledPush(w, h, floorY, progress, mode)
        }
        "SEATED_CHEST_PRESS_MACHINE" -> {
            drawSeatedChestPressMachine(w, h, floorY, progress, mode)
        }
    }
}

// 1. Crunches (Floor Mat)
private fun DrawScope.drawCrunches(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val hipX = w * 0.48f
    val hipY = floorY - 8.dp.toPx()

    // Knees bent up
    val kneeX = w * 0.65f
    val kneeY = floorY - (h * 0.22f)
    val feetX = w * 0.72f
    val feetY = floorY - 4.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(feetX, feetY), 8.dp.toPx())

    // Upper torso curls up: progress 0 = flat, progress 1 = curled up towards knees
    val curlLift = progress * 24.dp.toPx()
    val shoulderX = (w * 0.35f) + (progress * 12.dp.toPx())
    val shoulderY = (floorY - 8.dp.toPx()) - curlLift
    val headX = shoulderX - 10.dp.toPx()
    val headY = shoulderY - 8.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Hands behind ears
    drawLimb(Offset(shoulderX, shoulderY), Offset(headX + 4.dp.toPx(), headY), 5.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + hipX) / 2f, (shoulderY + hipY) / 2f), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 2. Bicycle Crunches (Alternating pedal and elbow touch)
private fun DrawScope.drawBicycleCrunches(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val hipX = w * 0.50f
    val hipY = floorY - (h * 0.12f)
    val shoulderX = w * 0.36f
    val shoulderY = floorY - (h * 0.20f)
    val headX = shoulderX - 10.dp.toPx()
    val headY = shoulderY - 8.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())

    // One knee tucked, one leg extended
    val kneeInX = w * 0.55f + (progress * 10.dp.toPx())
    val kneeInY = floorY - (h * 0.22f)
    val legOutX = w * 0.78f
    val legOutY = floorY - (h * 0.12f)

    drawLimb(Offset(hipX, hipY), Offset(kneeInX, kneeInY), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(legOutX, legOutY), 8.dp.toPx())

    // Opposite elbow rotating towards tucked knee
    val elbowX = (shoulderX + kneeInX) / 2f
    val elbowY = kneeInY + 4.dp.toPx()
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY - 10.dp.toPx()), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 3. Lying Leg Raises (Floor)
private fun DrawScope.drawLyingLegRaises(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val headX = w * 0.25f
    val headY = floorY - 8.dp.toPx()
    val shoulderX = w * 0.35f
    val hipX = w * 0.52f
    val hipY = floorY - 8.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, headY), Offset(hipX, hipY), 9.dp.toPx())

    // Straight legs lift from 0° (progress 0) to 90° vertical (progress 1)
    val angle = progress * (Math.PI * 0.48).toFloat()
    val legLen = h * 0.30f
    val feetX = hipX + (cos(angle) * legLen)
    val feetY = hipY - (sin(angle) * legLen)

    drawLimb(Offset(hipX, hipY), Offset(feetX, feetY), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY - 12.dp.toPx()), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 4. Hanging Leg Raises (Pull-up rig)
private fun DrawScope.drawHangingLegRaises(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val barY = h * 0.16f
    drawPullUpRig(w, floorY, barY)

    val handsX = w * 0.50f
    val shoulderX = handsX
    val shoulderY = barY + (h * 0.20f)
    val headX = handsX
    val headY = shoulderY - 14.dp.toPx()
    val hipX = handsX
    val hipY = shoulderY + (h * 0.24f)

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Hanging arms
    drawLimb(Offset(shoulderX, shoulderY), Offset(handsX, barY), 7.dp.toPx())

    // Legs raise forward to 90°: 0 = hanging, 1 = horizontal L
    val angle = progress * (Math.PI * 0.50).toFloat()
    val legLen = h * 0.24f
    val feetX = hipX + (sin(angle) * legLen)
    val feetY = hipY + (cos(angle) * legLen)

    drawLimb(Offset(hipX, hipY), Offset(feetX, feetY), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY - 8.dp.toPx()), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 5. Plank Hold (Forearm Tabletop)
private fun DrawScope.drawPlankHold(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val feetX = w * 0.22f
    val elbowX = w * 0.68f
    val shoulderX = elbowX
    val shoulderY = floorY - (h * 0.20f)
    val headX = shoulderX + 16.dp.toPx()
    val headY = shoulderY - 6.dp.toPx()
    val hipX = w * 0.44f
    val hipY = shoulderY + (progress * 4.dp.toPx()) // slight natural breathing oscillation

    drawMaleHead(Offset(headX, headY))
    // Rigid bodyline
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(feetX, floorY - 4.dp.toPx()), 8.dp.toPx())
    // Forearms on floor
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, floorY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(elbowX, floorY - 4.dp.toPx()), Offset(elbowX + 16.dp.toPx(), floorY - 4.dp.toPx()), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + hipX) / 2f, hipY), 22.dp.toPx(), MetricCyan, progress)
    }
}

// 6. Side Plank
private fun DrawScope.drawSidePlank(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val feetX = w * 0.25f
    val elbowX = w * 0.65f
    val shoulderX = elbowX
    val shoulderY = floorY - (h * 0.26f)
    val headX = shoulderX + 14.dp.toPx()
    val headY = shoulderY - 8.dp.toPx()
    val hipX = w * 0.44f
    val hipY = floorY - (h * 0.18f)

    drawMaleHead(Offset(headX, headY))
    // Straight diagonal line
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(feetX, floorY - 4.dp.toPx()), 8.dp.toPx())
    // Supporting forearm
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, floorY - 4.dp.toPx()), 7.dp.toPx())
    // Top arm extended straight up
    drawLimb(Offset(shoulderX, shoulderY), Offset(shoulderX, shoulderY - 25.dp.toPx()), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 7. Mountain Climbers (Plank rapid knee drive)
private fun DrawScope.drawMountainClimbers(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val handsX = w * 0.70f
    val shoulderX = handsX
    val shoulderY = floorY - (h * 0.26f)
    val headX = handsX + 14.dp.toPx()
    val headY = shoulderY - 6.dp.toPx()
    val hipX = w * 0.45f
    val hipY = shoulderY + 4.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Supporting arms
    drawLimb(Offset(shoulderX, shoulderY), Offset(handsX, floorY - 4.dp.toPx()), 7.dp.toPx())

    // Alternating knee drive
    val kneeProg = sin(progress * Math.PI.toFloat() * 2f)
    val kneeDriveX = hipX + (18.dp.toPx() * (1f + kneeProg))
    val kneeDriveY = floorY - (h * 0.14f)
    drawLimb(Offset(hipX, hipY), Offset(kneeDriveX, kneeDriveY), 8.dp.toPx())
    // Rear straight leg
    drawLimb(Offset(hipX, hipY), Offset(w * 0.22f, floorY - 4.dp.toPx()), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 8. Russian Twists (Seated V-sit rotation)
private fun DrawScope.drawRussianTwists(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val hipX = w * 0.46f
    val hipY = floorY - 10.dp.toPx()
    val shoulderX = w * 0.35f
    val shoulderY = floorY - (h * 0.26f)
    val headX = shoulderX - 6.dp.toPx()
    val headY = shoulderY - 14.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())

    // V-sit bent legs floating
    val kneeX = w * 0.62f
    val kneeY = floorY - (h * 0.18f)
    val feetX = w * 0.72f
    val feetY = floorY - (h * 0.10f)
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(feetX, feetY), 8.dp.toPx())

    // Side-to-side rotation holding weight
    val twistX = hipX + (sin(progress * Math.PI.toFloat() * 2f) * 20.dp.toPx())
    val twistY = floorY - (h * 0.18f)
    drawLimb(Offset(shoulderX, shoulderY), Offset(twistX, twistY), 6.dp.toPx())
    drawCircle(KineticNeon, 8.dp.toPx(), Offset(twistX, twistY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((shoulderX + hipX) / 2f, (shoulderY + hipY) / 2f), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 9. Ab Wheel Rollout (Kneeling)
private fun DrawScope.drawAbWheelRollout(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val kneeX = w * 0.28f
    val kneeY = floorY - 4.dp.toPx()
    val hipShift = progress * 24.dp.toPx()
    val hipX = w * 0.38f + hipShift
    val hipY = floorY - (h * 0.18f) + (progress * 8.dp.toPx())

    // Wheel rolls forward: progress 0 = near knees, progress 1 = fully rolled out
    val wheelX = (w * 0.52f) + (progress * (w * 0.26f))
    val wheelY = floorY - 12.dp.toPx()

    val shoulderX = wheelX - 16.dp.toPx()
    val shoulderY = wheelY - 8.dp.toPx()
    val headX = shoulderX + 8.dp.toPx()
    val headY = shoulderY - 12.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(kneeX, kneeY), Offset(hipX, hipY), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(shoulderX, shoulderY), 9.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(wheelX, wheelY), 7.dp.toPx())

    // Ab wheel
    drawCircle(EquipmentPlateColor, 12.dp.toPx(), Offset(wheelX, wheelY))
    drawCircle(KineticNeon, 6.dp.toPx(), Offset(wheelX, wheelY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((hipX + shoulderX) / 2f, (hipY + shoulderY) / 2f), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 10. Treadmill Running
private fun DrawScope.drawTreadmillRunning(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // Treadmill Deck
    val deckStartX = w * 0.20f
    val deckEndX = w * 0.80f
    val deckY = floorY - 6.dp.toPx()
    drawLine(EquipmentFrameColor, Offset(deckStartX, deckY), Offset(deckEndX, deckY), 8.dp.toPx(), StrokeCap.Round)
    drawLine(EquipmentSteelColor, Offset(deckEndX, deckY), Offset(deckEndX - 8.dp.toPx(), deckY - (h * 0.35f)), 6.dp.toPx())

    val headX = w * 0.48f
    val headY = h * 0.24f
    val shoulderX = headX
    val shoulderY = h * 0.36f
    val hipX = headX
    val hipY = h * 0.56f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Running gait legs scissor
    val legPhase = sin(progress * Math.PI.toFloat() * 2f)
    val frontKneeX = hipX + (legPhase * 24.dp.toPx())
    val frontKneeY = floorY - (h * 0.16f)
    val rearKneeX = hipX - (legPhase * 24.dp.toPx())
    val rearKneeY = floorY - (h * 0.12f)

    drawLimb(Offset(hipX, hipY), Offset(frontKneeX, frontKneeY), 8.dp.toPx())
    drawLimb(Offset(frontKneeX, frontKneeY), Offset(frontKneeX + 4.dp.toPx(), deckY - 4.dp.toPx()), 7.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(rearKneeX, rearKneeY), 8.dp.toPx())
    drawLimb(Offset(rearKneeX, rearKneeY), Offset(rearKneeX - 6.dp.toPx(), deckY - 4.dp.toPx()), 7.dp.toPx())

    // Running arms swing oppositely
    drawLimb(Offset(shoulderX, shoulderY), Offset(shoulderX - (legPhase * 16.dp.toPx()), h * 0.48f), 6.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(shoulderX + (legPhase * 16.dp.toPx()), h * 0.48f), 6.dp.toPx())
}

// 11. Exercise Bike
private fun DrawScope.drawExerciseBike(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val bikeCenter = Offset(w * 0.50f, floorY - (h * 0.18f))
    // Flywheel
    drawCircle(EquipmentPlateColor, 20.dp.toPx(), bikeCenter)
    drawCircle(KineticNeon, 6.dp.toPx(), bikeCenter)
    // Frame & Seat
    val seatOffset = Offset(bikeCenter.x - 22.dp.toPx(), bikeCenter.y - (h * 0.18f))
    drawLine(EquipmentFrameColor, bikeCenter, seatOffset, 6.dp.toPx())
    drawLine(EquipmentFrameColor, seatOffset - Offset(10.dp.toPx(), 0f), seatOffset + Offset(10.dp.toPx(), 0f), 8.dp.toPx(), StrokeCap.Round)
    // Handlebars
    val barOffset = Offset(bikeCenter.x + 22.dp.toPx(), bikeCenter.y - (h * 0.22f))
    drawLine(EquipmentFrameColor, bikeCenter, barOffset, 6.dp.toPx())

    val hipX = seatOffset.x
    val hipY = seatOffset.y - 4.dp.toPx()
    val shoulderX = hipX + 12.dp.toPx()
    val shoulderY = hipY - (h * 0.18f)
    drawMaleHead(Offset(shoulderX + 6.dp.toPx(), shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Arms holding handlebars
    drawLimb(Offset(shoulderX, shoulderY), barOffset, 6.dp.toPx())

    // Pedaling circle
    val pedalAngle = progress * Math.PI.toFloat() * 2f
    val pedalX = bikeCenter.x + (cos(pedalAngle) * 14.dp.toPx())
    val pedalY = bikeCenter.y + (sin(pedalAngle) * 14.dp.toPx())
    val kneeX = (hipX + pedalX) / 2f + 8.dp.toPx()
    val kneeY = (hipY + pedalY) / 2f - 10.dp.toPx()

    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(pedalX, pedalY), 7.dp.toPx())
}

// 12. Jump Rope
private fun DrawScope.drawJumpRope(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val jumpLift = (sin(progress * Math.PI.toFloat() * 2f) * 12.dp.toPx()).coerceAtLeast(0f)
    val headX = w * 0.50f
    val headY = (h * 0.22f) - jumpLift
    val shoulderX = headX
    val shoulderY = h * 0.34f - jumpLift
    val hipX = headX
    val hipY = h * 0.58f - jumpLift

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    val feetY = floorY - 4.dp.toPx() - jumpLift
    drawLimb(Offset(hipX, hipY), Offset(headX - 6.dp.toPx(), feetY), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 6.dp.toPx(), feetY), 8.dp.toPx())

    // Hands turning rope
    val leftHand = Offset(headX - 22.dp.toPx(), h * 0.48f - jumpLift)
    val rightHand = Offset(headX + 22.dp.toPx(), h * 0.48f - jumpLift)
    drawLimb(Offset(shoulderX, shoulderY), leftHand, 6.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), rightHand, 6.dp.toPx())

    // Rope ellipse revolving
    val ropeUnderFeet = (progress % 1f) > 0.4f && (progress % 1f) < 0.6f
    val ropeY = if (ropeUnderFeet) floorY - 2.dp.toPx() else headY - 14.dp.toPx()
    drawLine(KineticNeon, leftHand, Offset(headX, ropeY), 2.dp.toPx())
    drawLine(KineticNeon, rightHand, Offset(headX, ropeY), 2.dp.toPx())
}

// 13. Jumping Jacks
private fun DrawScope.drawJumpingJacks(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // 0 = legs together arms down, 1 = star jump arms overhead
    val spread = progress
    val headX = w * 0.50f
    val headY = h * 0.22f
    val shoulderX = headX
    val shoulderY = h * 0.34f
    val hipX = headX
    val hipY = h * 0.58f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Legs spreading
    val legSpreadX = spread * 25.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(headX - 6.dp.toPx() - legSpreadX, floorY - 4.dp.toPx()), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX + 6.dp.toPx() + legSpreadX, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Arms raising overhead
    val armAngle = (Math.PI / 2.0) - (spread * Math.PI * 0.85)
    val armLen = h * 0.22f
    val leftArmX = (shoulderX - (cos(armAngle) * armLen)).toFloat()
    val rightArmX = (shoulderX + (cos(armAngle) * armLen)).toFloat()
    val armsY = (shoulderY - (sin(armAngle) * armLen)).toFloat()
    drawLimb(Offset(shoulderX, shoulderY), Offset(leftArmX, armsY), 6.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(rightArmX, armsY), 6.dp.toPx())
}

// 14. High Knees
private fun DrawScope.drawHighKnees(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val headX = w * 0.50f
    val headY = h * 0.22f
    val shoulderX = headX
    val shoulderY = h * 0.34f
    val hipX = headX
    val hipY = h * 0.56f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // One leg drives knee up to hip level
    val phase = sin(progress * Math.PI.toFloat() * 2f)
    if (phase > 0f) {
        val kneeUpX = hipX + 16.dp.toPx()
        val kneeUpY = hipY - 6.dp.toPx()
        drawLimb(Offset(hipX, hipY), Offset(kneeUpX, kneeUpY), 8.dp.toPx())
        drawLimb(Offset(kneeUpX, kneeUpY), Offset(kneeUpX, kneeUpY + 24.dp.toPx()), 7.dp.toPx())
        // Standing leg
        drawLimb(Offset(hipX, hipY), Offset(headX - 6.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())
    } else {
        val kneeUpX = hipX - 16.dp.toPx()
        val kneeUpY = hipY - 6.dp.toPx()
        drawLimb(Offset(hipX, hipY), Offset(kneeUpX, kneeUpY), 8.dp.toPx())
        drawLimb(Offset(kneeUpX, kneeUpY), Offset(kneeUpX, kneeUpY + 24.dp.toPx()), 7.dp.toPx())
        drawLimb(Offset(hipX, hipY), Offset(headX + 6.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())
    }
}

// 15. Burpees
private fun DrawScope.drawBurpees(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    if (progress < 0.4f) {
        // Floor push-up phase
        val p = progress / 0.4f
        val handsX = w * 0.65f
        val shoulderX = handsX
        val shoulderY = (floorY - (h * 0.16f)) + (p * 8.dp.toPx())
        val hipX = w * 0.40f
        val feetX = w * 0.22f
        drawMaleHead(Offset(shoulderX + 12.dp.toPx(), shoulderY - 4.dp.toPx()))
        drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, shoulderY), 8.dp.toPx())
        drawLimb(Offset(hipX, shoulderY), Offset(feetX, floorY - 4.dp.toPx()), 7.dp.toPx())
        drawLimb(Offset(shoulderX, shoulderY), Offset(handsX, floorY - 4.dp.toPx()), 6.dp.toPx())
    } else {
        // Explosive vertical leap phase
        val p = (progress - 0.4f) / 0.6f
        val leapY = (sin(p * Math.PI.toFloat()) * (h * 0.22f))
        val headX = w * 0.50f
        val headY = (h * 0.26f) - leapY
        val hipX = headX
        val hipY = (h * 0.50f) - leapY
        drawMaleHead(Offset(headX, headY))
        drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
        drawLimb(Offset(hipX, hipY), Offset(headX - 6.dp.toPx(), floorY - 4.dp.toPx() - leapY), 8.dp.toPx())
        drawLimb(Offset(hipX, hipY), Offset(headX + 6.dp.toPx(), floorY - 4.dp.toPx() - leapY), 8.dp.toPx())
        // Arms overhead
        drawLimb(Offset(headX, headY + 14.dp.toPx()), Offset(headX, headY - 14.dp.toPx()), 6.dp.toPx())
    }
}

// 16. Kettlebell Swing
private fun DrawScope.drawKettlebellSwing(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // 0 = bottom hip hinge between knees, 1 = floating at eye level
    val swingProg = progress
    val hipX = w * 0.45f - ((1f - swingProg) * 14.dp.toPx())
    val hipY = h * 0.52f
    val headX = w * 0.50f
    val headY = h * 0.26f + ((1f - swingProg) * 14.dp.toPx())
    val shoulderX = headX
    val shoulderY = headY + 16.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Kettlebell position
    val bellAngle = -0.6f + (swingProg * 1.8f)
    val armLen = h * 0.24f
    val bellX = shoulderX + (sin(bellAngle) * armLen)
    val bellY = shoulderY + (cos(bellAngle) * armLen)

    drawLimb(Offset(shoulderX, shoulderY), Offset(bellX, bellY), 6.dp.toPx())
    // Kettlebell
    drawCircle(EquipmentPlateColor, 12.dp.toPx(), Offset(bellX, bellY))
    drawCircle(KineticNeon, 6.dp.toPx(), Offset(bellX, bellY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 20.dp.toPx(), KineticNeon, progress)
    }
}

// 17. Battle Ropes
private fun DrawScope.drawBattleRopes(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val headX = w * 0.32f
    val headY = h * 0.30f
    val shoulderX = headX
    val shoulderY = h * 0.42f
    val hipX = headX - 8.dp.toPx()
    val hipY = h * 0.60f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    // Half squat stance
    drawLimb(Offset(hipX, hipY), Offset(headX + 4.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())

    // Arms waving ropes
    val wave = sin(progress * Math.PI.toFloat() * 4f) * 14.dp.toPx()
    val handX = headX + 16.dp.toPx()
    val handY = h * 0.52f + wave
    drawLimb(Offset(shoulderX, shoulderY), Offset(handX, handY), 6.dp.toPx())

    // Sine wave rope to floor anchor
    val anchorX = w * 0.85f
    val anchorY = floorY - 4.dp.toPx()
    val mid1X = (handX + anchorX) / 3f
    val mid1Y = (floorY - 12.dp.toPx()) - wave
    val mid2X = (handX + anchorX) * 2f / 3f
    val mid2Y = (floorY - 12.dp.toPx()) + wave

    drawLine(EquipmentPlateColor, Offset(handX, handY), Offset(mid1X, mid1Y), 5.dp.toPx())
    drawLine(KineticNeon, Offset(mid1X, mid1Y), Offset(mid2X, mid2Y), 5.dp.toPx())
    drawLine(EquipmentPlateColor, Offset(mid2X, mid2Y), Offset(anchorX, anchorY), 5.dp.toPx())
}

// 18. Medicine Ball Slam
private fun DrawScope.drawMedicineBallSlam(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // 0 = overhead extension, 1 = forceful floor slam
    val headX = w * 0.50f
    val drop = progress * (h * 0.20f)
    val headY = (h * 0.22f) + drop
    val shoulderX = headX
    val shoulderY = headY + 14.dp.toPx()
    val hipX = headX - (10.dp.toPx() * progress)
    val hipY = (h * 0.54f) + drop

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX, floorY - 4.dp.toPx()), 8.dp.toPx())

    val ballY = (headY - 18.dp.toPx()) + (progress * (h * 0.44f))
    val ballX = headX + (progress * 14.dp.toPx())

    drawLimb(Offset(shoulderX, shoulderY), Offset(ballX, ballY), 6.dp.toPx())
    drawCircle(EquipmentPlateColor, 14.dp.toPx(), Offset(ballX, ballY))
    drawCircle(KineticNeon, 6.dp.toPx(), Offset(ballX, ballY))
}

// 19. Hamstring Stretch (Forward Fold)
private fun DrawScope.drawHamstringStretch(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val hipX = w * 0.45f
    val hipY = h * 0.50f

    // Straight legs
    drawLimb(Offset(hipX, hipY), Offset(w * 0.45f, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Torso folds down towards knees
    val foldProg = progress
    val shoulderX = w * 0.48f + (foldProg * 6.dp.toPx())
    val shoulderY = (h * 0.56f) + (foldProg * 18.dp.toPx())
    val headX = shoulderX
    val headY = shoulderY + 12.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(hipX, hipY), Offset(shoulderX, shoulderY), 9.dp.toPx())
    // Hands reaching toward toes
    drawLimb(Offset(shoulderX, shoulderY), Offset(w * 0.45f, floorY - 8.dp.toPx()), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(w * 0.42f, (hipY + floorY) / 2f), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 20. Quadriceps Stretch (Standing one leg, pulling ankle behind glute)
private fun DrawScope.drawQuadricepsStretch(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val headX = w * 0.50f
    val headY = h * 0.22f
    val shoulderX = headX
    val shoulderY = h * 0.34f
    val hipX = headX
    val hipY = h * 0.58f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Standing straight leg
    drawLimb(Offset(hipX, hipY), Offset(headX - 6.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())

    // Bent leg pulled back
    val kneeBentX = hipX + 4.dp.toPx()
    val kneeBentY = h * 0.72f
    val footBentX = hipX - 10.dp.toPx()
    val footBentY = h * 0.62f
    drawLimb(Offset(hipX, hipY), Offset(kneeBentX, kneeBentY), 8.dp.toPx())
    drawLimb(Offset(kneeBentX, kneeBentY), Offset(footBentX, footBentY), 7.dp.toPx())

    // Hand grasping foot
    drawLimb(Offset(shoulderX, shoulderY), Offset(footBentX, footBentY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(kneeBentX, (hipY + kneeBentY) / 2f), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 21. Cobra Stretch (Prone spinal extension)
private fun DrawScope.drawCobraStretch(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val hipX = w * 0.40f
    val hipY = floorY - 8.dp.toPx()

    // Legs flat on mat
    drawLimb(Offset(hipX, hipY), Offset(w * 0.18f, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Chest presses upward: 0 = low, 1 = arched high
    val pressRise = progress * (h * 0.20f)
    val shoulderX = w * 0.55f
    val shoulderY = (floorY - 14.dp.toPx()) - pressRise
    val headX = shoulderX + 8.dp.toPx()
    val headY = shoulderY - 12.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(hipX, hipY), Offset(shoulderX, shoulderY), 9.dp.toPx())
    // Straight supporting arms
    drawLimb(Offset(shoulderX, shoulderY), Offset(shoulderX, floorY - 4.dp.toPx()), 7.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX - 12.dp.toPx(), shoulderY + 8.dp.toPx()), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 22. Chest Doorway Stretch
private fun DrawScope.drawChestDoorwayStretch(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // Door frame post
    val postX = w * 0.65f
    drawLine(EquipmentFrameColor, Offset(postX, h * 0.10f), Offset(postX, floorY), 10.dp.toPx())

    val headX = w * 0.50f
    val headY = h * 0.24f
    val shoulderX = headX
    val shoulderY = h * 0.36f
    val hipX = headX
    val hipY = h * 0.60f

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(headX, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Forearm anchored at 90° against post, torso rotates slightly forward
    val elbowX = postX
    val elbowY = h * 0.36f
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(elbowX, elbowY - 22.dp.toPx()), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX + 8.dp.toPx(), shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 23. World's Greatest Stretch
private fun DrawScope.drawWorldsGreatestStretch(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // Deep runner's lunge with thoracic rotation
    val hipX = w * 0.40f
    val hipY = floorY - (h * 0.18f)

    // Rear straight leg
    drawLimb(Offset(hipX, hipY), Offset(w * 0.18f, floorY - 4.dp.toPx()), 8.dp.toPx())
    // Front bent lunge knee
    val frontKneeX = w * 0.60f
    val frontKneeY = floorY - (h * 0.16f)
    drawLimb(Offset(hipX, hipY), Offset(frontKneeX, frontKneeY), 8.dp.toPx())
    drawLimb(Offset(frontKneeX, frontKneeY), Offset(frontKneeX, floorY - 4.dp.toPx()), 8.dp.toPx())

    val shoulderX = w * 0.50f
    val shoulderY = floorY - (h * 0.26f)
    drawMaleHead(Offset(shoulderX + 8.dp.toPx(), shoulderY - 10.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())

    // One hand on floor, other rotates to ceiling
    drawLimb(Offset(shoulderX, shoulderY), Offset(frontKneeX - 10.dp.toPx(), floorY - 4.dp.toPx()), 6.dp.toPx())
    // Rotating arm
    val rotAngle = progress * (Math.PI * 0.50).toFloat()
    val rotHandX = shoulderX + (cos(rotAngle) * 26.dp.toPx())
    val rotHandY = shoulderY - (sin(rotAngle) * 26.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(rotHandX, rotHandY), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 24. Cat-Cow Mobility
private fun DrawScope.drawCatCowMobility(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val kneeX = w * 0.35f
    val kneeY = floorY - 4.dp.toPx()
    val hipX = kneeX
    val hipY = floorY - (h * 0.22f)

    val handX = w * 0.65f
    val shoulderX = handX
    val shoulderY = floorY - (h * 0.22f)

    // Spine flexion (Cat) vs extension (Cow): progress 0 = arched dip, progress 1 = rounded cat
    val archOffset = ((progress - 0.5f) * 2f) * 16.dp.toPx()
    val midSpineX = (hipX + shoulderX) / 2f
    val midSpineY = ((hipY + shoulderY) / 2f) - archOffset

    val headX = shoulderX + 12.dp.toPx()
    val headY = shoulderY + (archOffset * 0.6f) - 6.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())
    drawLimb(Offset(shoulderX, shoulderY), Offset(handX, floorY - 4.dp.toPx()), 7.dp.toPx())

    // Arched spine
    drawLimb(Offset(hipX, hipY), Offset(midSpineX, midSpineY), 8.dp.toPx())
    drawLimb(Offset(midSpineX, midSpineY), Offset(shoulderX, shoulderY), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(midSpineX, midSpineY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 25. Deep Squat Pry / Malasana
private fun DrawScope.drawDeepSquatPry(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val headX = w * 0.50f
    val headY = floorY - (h * 0.32f)
    val shoulderX = headX
    val shoulderY = headY + 12.dp.toPx()
    val hipX = headX
    val hipY = floorY - (h * 0.14f)

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())

    // Wide deep knees
    val kneePry = progress * 8.dp.toPx()
    val leftKnee = Offset(headX - 24.dp.toPx() - kneePry, floorY - (h * 0.16f))
    val rightKnee = Offset(headX + 24.dp.toPx() + kneePry, floorY - (h * 0.16f))

    drawLimb(Offset(hipX, hipY), leftKnee, 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), rightKnee, 8.dp.toPx())
    drawLimb(leftKnee, Offset(headX - 16.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())
    drawLimb(rightKnee, Offset(headX + 16.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())

    // Elbows inside knees prying outward
    drawLimb(Offset(shoulderX, shoulderY), Offset(headX, floorY - (h * 0.20f)), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 26. L-Sit Hold
private fun DrawScope.drawLSitHold(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val barY = floorY - (h * 0.25f)
    drawParallelDipBars(floorY, w * 0.40f, w * 0.60f, barY)

    val handsX = w * 0.50f
    val shoulderX = handsX
    val shoulderY = barY - (h * 0.22f)
    val headX = handsX
    val headY = shoulderY - 14.dp.toPx()
    val hipX = handsX
    val hipY = barY - 4.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Supporting straight arms
    drawLimb(Offset(shoulderX, shoulderY), Offset(handsX, barY), 7.dp.toPx())

    // Perfect 90° horizontal legs
    val feetX = hipX + (h * 0.25f)
    val feetY = hipY
    drawLimb(Offset(hipX, hipY), Offset(feetX, feetY), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 27. Muscle-Up
private fun DrawScope.drawMuscleUp(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val barY = h * 0.35f
    drawPullUpRig(w, floorY, barY)

    // Transition from pull-up (progress 0) to above-bar dip (progress 1)
    val rise = progress * (h * 0.32f)
    val handsX = w * 0.50f
    val shoulderX = handsX
    val shoulderY = (barY + (h * 0.16f)) - rise
    val headX = handsX
    val headY = shoulderY - 14.dp.toPx()
    val hipX = handsX
    val hipY = shoulderY + (h * 0.22f)

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(handsX, hipY + (h * 0.20f)), 8.dp.toPx())

    // Arms transitioning over bar
    if (progress > 0.6f) {
        // Dip lockout over bar
        drawLimb(Offset(shoulderX, shoulderY), Offset(handsX, barY), 7.dp.toPx())
    } else {
        // Pulling up to chest
        drawLimb(Offset(shoulderX, shoulderY), Offset(handsX + 14.dp.toPx(), barY), 7.dp.toPx())
    }

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 28. Turkish Get-Up
private fun DrawScope.drawTurkishGetUp(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // 0 = lying on back, 1 = stood upright holding kettlebell overhead
    val getUpRise = progress * (h * 0.32f)
    val hipX = (w * 0.40f) + (progress * 16.dp.toPx())
    val hipY = (floorY - 10.dp.toPx()) - (getUpRise * 0.6f)
    val shoulderX = hipX + (progress * 10.dp.toPx())
    val shoulderY = (floorY - 16.dp.toPx()) - getUpRise
    val headX = shoulderX
    val headY = shoulderY - 14.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(hipX + 8.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())

    // Overhead locked arm with kettlebell
    val handX = shoulderX
    val handY = shoulderY - 32.dp.toPx()
    drawLimb(Offset(shoulderX, shoulderY), Offset(handX, handY), 7.dp.toPx())
    drawCircle(EquipmentPlateColor, 10.dp.toPx(), Offset(handX, handY))
    drawCircle(KineticNeon, 5.dp.toPx(), Offset(handX, handY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX, shoulderY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 29. Sled Push (Prowler Sled)
private fun DrawScope.drawSledPush(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // Sled posts
    val sledX = w * 0.70f
    val sledY = floorY - 4.dp.toPx()
    drawLine(EquipmentPlateColor, Offset(sledX - 10.dp.toPx(), sledY), Offset(sledX + 25.dp.toPx(), sledY), 8.dp.toPx())
    drawLine(EquipmentSteelColor, Offset(sledX, sledY), Offset(sledX, sledY - (h * 0.32f)), 7.dp.toPx())

    // Athlete in 45° sprint drive angle
    val hipX = w * 0.35f
    val hipY = floorY - (h * 0.24f)
    val shoulderX = w * 0.52f
    val shoulderY = floorY - (h * 0.28f)
    val headX = shoulderX + 8.dp.toPx()
    val headY = shoulderY - 10.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    // Driving arms against sled post
    drawLimb(Offset(shoulderX, shoulderY), Offset(sledX, sledY - (h * 0.26f)), 7.dp.toPx())

    // Driving legs
    val legPhase = sin(progress * Math.PI.toFloat() * 2f) * 16.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(hipX + legPhase, floorY - 4.dp.toPx()), 8.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(hipX - legPhase, floorY - 4.dp.toPx()), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 30. Seated Chest Press Machine
private fun DrawScope.drawSeatedChestPressMachine(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val machineX = w * 0.40f
    val seatY = floorY - (h * 0.30f)
    // Seated chair
    drawFlatBench(floorY, machineX - 25.dp.toPx(), machineX + 25.dp.toPx(), seatY)
    drawLine(EquipmentFrameColor, Offset(machineX - 25.dp.toPx(), seatY - (h * 0.28f)), Offset(machineX - 25.dp.toPx(), seatY), 10.dp.toPx())

    val hipX = machineX
    val hipY = seatY - 4.dp.toPx()
    val shoulderX = machineX - 8.dp.toPx()
    val shoulderY = seatY - (h * 0.20f)
    drawMaleHead(Offset(shoulderX, shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(machineX + 20.dp.toPx(), floorY - 4.dp.toPx()), 7.dp.toPx())

    // Machine press handles moving forward: progress 0 = back at chest, progress 1 = full press forward
    val pressTravel = progress * 32.dp.toPx()
    val handleX = machineX + 16.dp.toPx() + pressTravel
    val handleY = shoulderY + 8.dp.toPx()

    // Arms
    val elbowX = (shoulderX + handleX) / 2f - (8.dp.toPx() * (1f - progress))
    val elbowY = handleY + 12.dp.toPx()
    drawLimb(Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), 7.dp.toPx())
    drawLimb(Offset(elbowX, elbowY), Offset(handleX, handleY), 6.dp.toPx())

    // Machine lever
    drawLine(EquipmentSteelColor, Offset(handleX, handleY - 20.dp.toPx()), Offset(handleX, handleY + 20.dp.toPx()), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(shoulderX + 10.dp.toPx(), shoulderY), 20.dp.toPx(), MetricCyan, progress)
    }
}
