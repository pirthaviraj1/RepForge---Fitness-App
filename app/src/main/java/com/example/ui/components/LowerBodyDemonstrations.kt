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
 * Lower Body (Legs, Glutes, Strength compounds)
 */

fun DrawScope.drawLowerBodyDemonstration(
    animType: String,
    progress: Float,
    mode: AnimationMode
) {
    val w = size.width
    val h = size.height
    val floorY = h * 0.82f

    when (animType) {
        "SQUAT_BARBELL", "SQUAT_BODYWEIGHT" -> {
            drawSquatMovement(w, h, floorY, progress, mode, isBarbell = animType == "SQUAT_BARBELL")
        }
        "JUMP_SQUATS" -> {
            drawJumpSquatMovement(w, h, floorY, progress, mode)
        }
        "LUNGES_WALKING" -> {
            drawLungeMovement(w, h, floorY, progress, mode)
        }
        "BULGARIAN_SPLIT_SQUAT" -> {
            drawBulgarianSplitSquat(w, h, floorY, progress, mode)
        }
        "CALF_RAISES" -> {
            drawCalfRaises(w, h, floorY, progress, mode)
        }
        "LEG_EXTENSION_MACHINE" -> {
            drawLegExtensionMachine(w, h, floorY, progress, mode)
        }
        "LEG_CURL_MACHINE" -> {
            drawLegCurlMachine(w, h, floorY, progress, mode)
        }
        "HIP_THRUST_BARBELL" -> {
            drawHipThrustBarbell(w, h, floorY, progress, mode)
        }
        "GLUTE_BRIDGE" -> {
            drawGluteBridge(w, h, floorY, progress, mode)
        }
        "DEADLIFT_BARBELL" -> {
            drawDeadliftMovement(w, h, floorY, progress, mode)
        }
        "LEG_PRESS_MACHINE" -> {
            drawLegPressMachine(w, h, floorY, progress, mode)
        }
        "PISTOL_SQUAT" -> {
            drawPistolSquat(w, h, floorY, progress, mode)
        }
    }
}

// 1. Squats (Barbell & Bodyweight)
private fun DrawScope.drawSquatMovement(
    w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode, isBarbell: Boolean
) {
    val hipDrop = progress * (h * 0.26f)
    val hipX = w * 0.45f
    val hipY = (h * 0.42f) + hipDrop
    val headX = w * 0.50f
    val headY = (h * 0.18f) + hipDrop
    val ankleX = w * 0.52f
    val ankleY = floorY - 4.dp.toPx()

    // Knee tracking
    val kneeCave = if (mode == AnimationMode.COMMON_MISTAKES) -8.dp.toPx() else 4.dp.toPx()
    val kneeX = w * 0.60f + kneeCave
    val kneeY = ((hipY + ankleY) / 2f) + (progress * 10.dp.toPx())

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Thigh & Shin
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(ankleX, ankleY), 8.dp.toPx())

    if (isBarbell) {
        val barY = headY + 12.dp.toPx()
        drawBarbell(Offset(headX - 35.dp.toPx(), barY), Offset(headX + 35.dp.toPx(), barY))
        // Hands holding bar
        drawLimb(Offset(headX - 10.dp.toPx(), barY + 4.dp.toPx()), Offset(headX - 22.dp.toPx(), barY), 6.dp.toPx())
        drawLimb(Offset(headX + 10.dp.toPx(), barY + 4.dp.toPx()), Offset(headX + 22.dp.toPx(), barY), 6.dp.toPx())
    } else {
        // Hands forward for balance
        drawLimb(Offset(headX + 4.dp.toPx(), headY + 16.dp.toPx()), Offset(headX + 26.dp.toPx(), headY + 20.dp.toPx()), 6.dp.toPx())
    }

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((hipX + kneeX) / 2f, (hipY + kneeY) / 2f), 20.dp.toPx(), MetricCyan, progress)
        drawMuscleGlow(Offset(hipX - 10.dp.toPx(), hipY), 16.dp.toPx(), KineticNeon, progress)
    }

    if (mode == AnimationMode.COMMON_MISTAKES) {
        // Red warning on knee cave
        drawCircle(CriticalRed, 7.dp.toPx(), Offset(kneeX, kneeY))
    }
}

// 2. Jump Squats (Explosive plyometric vertical leap)
private fun DrawScope.drawJumpSquatMovement(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // 0 = bottom squat, 1 = mid-air peak leap
    val verticalShift = if (progress > 0.5f) -((progress - 0.5f) * 2f) * (h * 0.22f) else (0.5f - progress) * 2f * (h * 0.18f)

    val headX = w * 0.50f
    val headY = (h * 0.32f) + verticalShift
    val hipX = headX
    val hipY = (h * 0.54f) + verticalShift

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    if (progress > 0.5f) {
        // Air extension: legs pointed straight down
        val feetY = hipY + (h * 0.24f)
        drawLimb(Offset(hipX, hipY), Offset(headX - 8.dp.toPx(), feetY), 8.dp.toPx())
        drawLimb(Offset(hipX, hipY), Offset(headX + 8.dp.toPx(), feetY), 8.dp.toPx())
        // Arms swinging back or overhead
        drawLimb(Offset(headX, headY + 16.dp.toPx()), Offset(headX + 22.dp.toPx(), headY - 10.dp.toPx()), 6.dp.toPx())
    } else {
        // Squat depth
        val kneeX = headX + 16.dp.toPx()
        val kneeY = hipY + 18.dp.toPx()
        drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())
        drawLimb(Offset(kneeX, kneeY), Offset(headX + 4.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())
        drawLimb(Offset(headX, headY + 16.dp.toPx()), Offset(headX + 22.dp.toPx(), headY + 20.dp.toPx()), 6.dp.toPx())
    }

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY + 14.dp.toPx()), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 3. Walking Lunges
private fun DrawScope.drawLungeMovement(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val drop = progress * (h * 0.20f)
    val headX = w * 0.48f
    val headY = (h * 0.24f) + drop
    val hipX = headX
    val hipY = (h * 0.48f) + drop

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Front leg stepping forward into 90 degree knee bend
    val frontKneeX = w * 0.65f
    val frontKneeY = floorY - (h * 0.16f)
    val frontFootX = frontKneeX - 2.dp.toPx()
    val frontFootY = floorY - 4.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(frontKneeX, frontKneeY), 8.dp.toPx())
    drawLimb(Offset(frontKneeX, frontKneeY), Offset(frontFootX, frontFootY), 8.dp.toPx())

    // Rear leg bent down near floor
    val rearKneeX = w * 0.35f
    val rearKneeY = (floorY - 10.dp.toPx()) - (10.dp.toPx() * (1f - progress))
    val rearFootX = w * 0.24f
    val rearFootY = floorY - 4.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(rearKneeX, rearKneeY), 8.dp.toPx())
    drawLimb(Offset(rearKneeX, rearKneeY), Offset(rearFootX, rearFootY), 7.dp.toPx())

    // Holding dumbbells by sides
    drawLimb(Offset(headX, headY + 16.dp.toPx()), Offset(headX, headY + (h * 0.22f)), 6.dp.toPx())
    drawDumbbell(Offset(headX, headY + (h * 0.22f)), angleRad = 0f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(frontKneeX - 8.dp.toPx(), frontKneeY), 18.dp.toPx(), MetricCyan, progress)
        drawMuscleGlow(Offset(hipX - 8.dp.toPx(), hipY), 16.dp.toPx(), KineticNeon, progress)
    }
}

// 4. Bulgarian Split Squat (Rear foot elevated on bench)
private fun DrawScope.drawBulgarianSplitSquat(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchX = w * 0.25f
    val benchHeight = h * 0.22f
    drawFlatBench(floorY, benchX - 22.dp.toPx(), benchX + 22.dp.toPx(), floorY - benchHeight)

    val drop = progress * (h * 0.22f)
    val headX = w * 0.52f
    val headY = (h * 0.22f) + drop
    val hipX = headX
    val hipY = (h * 0.46f) + drop

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Front working leg
    val frontKneeX = w * 0.68f
    val frontKneeY = floorY - (h * 0.16f)
    drawLimb(Offset(hipX, hipY), Offset(frontKneeX, frontKneeY), 8.dp.toPx())
    drawLimb(Offset(frontKneeX, frontKneeY), Offset(frontKneeX, floorY - 4.dp.toPx()), 8.dp.toPx())

    // Rear foot resting on bench
    drawLimb(Offset(hipX, hipY), Offset(benchX + 6.dp.toPx(), floorY - benchHeight), 7.dp.toPx())

    // Dumbbells in hands
    drawLimb(Offset(headX, headY + 16.dp.toPx()), Offset(headX, headY + (h * 0.22f)), 6.dp.toPx())
    drawDumbbell(Offset(headX, headY + (h * 0.22f)), angleRad = 0f)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(frontKneeX - 6.dp.toPx(), frontKneeY), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 5. Standing Calf Raises
private fun DrawScope.drawCalfRaises(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val heelLift = progress * 16.dp.toPx()
    val headX = w * 0.50f
    val headY = (h * 0.22f) - heelLift
    val shoulderX = headX
    val shoulderY = h * 0.34f - heelLift
    val hipX = headX
    val hipY = h * 0.58f - heelLift

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Straight legs with ankle flexion
    val kneeX = headX
    val kneeY = floorY - (h * 0.18f) - heelLift
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 8.dp.toPx())

    // Foot on balls of toes
    val footX = headX
    val ankleY = floorY - 6.dp.toPx() - heelLift
    drawLimb(Offset(kneeX, kneeY), Offset(footX, ankleY), 8.dp.toPx())
    // Toe contact line
    drawLine(Color.White, Offset(footX - 8.dp.toPx(), floorY - 2.dp.toPx()), Offset(footX + 12.dp.toPx(), floorY - 2.dp.toPx()), 4.dp.toPx(), StrokeCap.Round)

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(footX + 6.dp.toPx(), (kneeY + ankleY) / 2f), 16.dp.toPx(), MetricCyan, progress)
    }
}

// 6. Leg Extension Machine (Seated)
private fun DrawScope.drawLegExtensionMachine(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val machineX = w * 0.40f
    val seatY = floorY - (h * 0.32f)
    // Seated chair frame
    drawFlatBench(floorY, machineX - 25.dp.toPx(), machineX + 25.dp.toPx(), seatY)
    drawLine(EquipmentFrameColor, Offset(machineX - 25.dp.toPx(), seatY - (h * 0.25f)), Offset(machineX - 25.dp.toPx(), seatY), 10.dp.toPx())

    val hipX = machineX
    val hipY = seatY - 4.dp.toPx()
    val shoulderX = machineX - 12.dp.toPx()
    val shoulderY = seatY - (h * 0.20f)
    drawMaleHead(Offset(shoulderX, shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())

    // Knee over edge of seat
    val kneeX = machineX + 28.dp.toPx()
    val kneeY = seatY
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())

    // Shin extends up from 90° (progress 0) to full horizontal lockout (progress 1)
    val extAngle = (Math.PI / 2.0) - (progress * Math.PI * 0.45)
    val footX = kneeX + (sin(extAngle) * 32.dp.toPx()).toFloat()
    val footY = kneeY + (cos(extAngle) * 32.dp.toPx()).toFloat()
    drawLimb(Offset(kneeX, kneeY), Offset(footX, footY), 8.dp.toPx())

    // Padded machine roller on shin
    drawCircle(EquipmentFrameColor, 8.dp.toPx(), Offset(footX, footY))
    drawCircle(KineticNeon, 4.dp.toPx(), Offset(footX, footY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((hipX + kneeX) / 2f, seatY - 10.dp.toPx()), 18.dp.toPx(), MetricCyan, progress)
    }
}

// 7. Leg Curl Machine (Lying face down)
private fun DrawScope.drawLegCurlMachine(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchY = floorY - (h * 0.26f)
    drawFlatBench(floorY, w * 0.20f, w * 0.70f, benchY)

    val headX = w * 0.65f
    val headY = benchY - 10.dp.toPx()
    val shoulderX = w * 0.55f
    val hipX = w * 0.40f
    val kneeX = w * 0.28f
    val kneeY = benchY - 4.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, benchY - 6.dp.toPx()), Offset(hipX, benchY - 6.dp.toPx()), 9.dp.toPx())
    drawLimb(Offset(hipX, benchY - 6.dp.toPx()), Offset(kneeX, kneeY), 8.dp.toPx())

    // Lower leg curls up towards glutes: 0 = straight on bench, 1 = curled 90° up
    val curlAngle = progress * (Math.PI * 0.55).toFloat()
    val footX = kneeX + (cos(curlAngle) * 26.dp.toPx())
    val footY = kneeY - (sin(curlAngle) * 26.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(footX, footY), 7.dp.toPx())
    drawCircle(KineticNeon, 6.dp.toPx(), Offset(footX, footY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((hipX + kneeX) / 2f, benchY - 12.dp.toPx()), 16.dp.toPx(), MetricCyan, progress)
    }
}

// 8. Barbell Hip Thrust (Upper back against bench)
private fun DrawScope.drawHipThrustBarbell(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val benchX = w * 0.35f
    val benchY = floorY - (h * 0.22f)
    drawFlatBench(floorY, benchX - 18.dp.toPx(), benchX + 18.dp.toPx(), benchY)

    val hipRise = progress * (h * 0.18f)
    val shoulderX = benchX + 8.dp.toPx()
    val shoulderY = benchY - 6.dp.toPx()
    val headX = benchX
    val headY = benchY - 14.dp.toPx()

    val hipX = w * 0.52f
    val hipY = (floorY - 16.dp.toPx()) - hipRise
    val kneeX = w * 0.68f
    val kneeY = floorY - (h * 0.18f)
    val feetX = kneeX
    val feetY = floorY - 4.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(feetX, feetY), 8.dp.toPx())

    // Barbell on hips
    drawBarbell(Offset(hipX - 30.dp.toPx(), hipY - 6.dp.toPx()), Offset(hipX + 30.dp.toPx(), hipY - 6.dp.toPx()), plateRadius = 12.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 22.dp.toPx(), KineticNeon, progress)
    }
}

// 9. Glute Bridge (Floor)
private fun DrawScope.drawGluteBridge(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val shoulderX = w * 0.30f
    val shoulderY = floorY - 8.dp.toPx()
    val headX = shoulderX - 14.dp.toPx()
    val headY = floorY - 8.dp.toPx()

    val hipRise = progress * (h * 0.20f)
    val hipX = w * 0.50f
    val hipY = (floorY - 8.dp.toPx()) - hipRise
    val kneeX = w * 0.68f
    val kneeY = (floorY - (h * 0.20f))
    val feetX = kneeX
    val feetY = floorY - 4.dp.toPx()

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(feetX, feetY), 8.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 20.dp.toPx(), KineticNeon, progress)
    }
}

// 10. Barbell Deadlift (Floor to standing lockout)
private fun DrawScope.drawDeadliftMovement(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // 0 = bottom floor pull, 1 = standing lockout
    val liftProg = progress
    val headX = w * 0.46f
    val headY = (h * 0.36f) - (liftProg * (h * 0.16f))
    val shoulderX = headX
    val shoulderY = (h * 0.48f) - (liftProg * (h * 0.16f))
    val hipX = headX - (12.dp.toPx() * (1f - liftProg))
    val hipY = (h * 0.58f) - (liftProg * (h * 0.08f))

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())

    // Legs: knee bends in bottom, straightens at top
    val kneeX = headX + (12.dp.toPx() * (1f - liftProg))
    val kneeY = floorY - (h * 0.16f)
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(headX + 4.dp.toPx(), floorY - 4.dp.toPx()), 8.dp.toPx())

    // Barbell lifted vertically along shins to hips
    val barY = (floorY - 14.dp.toPx()) - (liftProg * (h * 0.24f))
    val barX = headX + 8.dp.toPx()
    drawLimb(Offset(shoulderX, shoulderY), Offset(barX, barY), 7.dp.toPx())
    drawBarbell(Offset(barX - 35.dp.toPx(), barY), Offset(barX + 35.dp.toPx(), barY), plateRadius = 14.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(hipX, hipY), 20.dp.toPx(), MetricCyan, progress)
        drawMuscleGlow(Offset(kneeX - 6.dp.toPx(), kneeY), 18.dp.toPx(), KineticNeon, progress)
    }
}

// 11. Leg Press Machine (45 degree sled press)
private fun DrawScope.drawLegPressMachine(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    // 45 degree angled sled frame
    val railStartX = w * 0.75f
    val railStartY = h * 0.18f
    val railEndX = w * 0.35f
    val railEndY = floorY - (h * 0.15f)
    drawLine(EquipmentFrameColor, Offset(railStartX, railStartY), Offset(railEndX, railEndY), 10.dp.toPx())

    // Seated chair at lower end
    drawFlatBench(floorY, w * 0.20f, w * 0.40f, floorY - (h * 0.22f))

    val hipX = w * 0.30f
    val hipY = floorY - (h * 0.24f)
    val shoulderX = w * 0.24f
    val shoulderY = floorY - (h * 0.36f)
    drawMaleHead(Offset(shoulderX, shoulderY - 14.dp.toPx()))
    drawLimb(Offset(shoulderX, shoulderY), Offset(hipX, hipY), 9.dp.toPx())

    // Feet on footplate sliding up along 45 degree rail
    val sledShift = progress * 32.dp.toPx()
    val plateX = (w * 0.55f) + sledShift
    val plateY = (floorY - (h * 0.32f)) - sledShift
    val kneeX = (hipX + plateX) / 2f - 6.dp.toPx()
    val kneeY = (hipY + plateY) / 2f - 14.dp.toPx()

    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(plateX, plateY), 8.dp.toPx())

    // Sled Footplate & weights
    drawLine(EquipmentSteelColor, Offset(plateX - 14.dp.toPx(), plateY - 14.dp.toPx()), Offset(plateX + 14.dp.toPx(), plateY + 14.dp.toPx()), 8.dp.toPx())
    drawCircle(KineticNeon, 10.dp.toPx(), Offset(plateX, plateY))

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset(kneeX, kneeY), 20.dp.toPx(), MetricCyan, progress)
    }
}

// 12. Pistol Squat (Single leg deep squat with other leg straight out)
private fun DrawScope.drawPistolSquat(w: Float, h: Float, floorY: Float, progress: Float, mode: AnimationMode) {
    val drop = progress * (h * 0.26f)
    val hipX = w * 0.42f
    val hipY = (h * 0.44f) + drop
    val headX = w * 0.48f
    val headY = (h * 0.20f) + drop

    drawMaleHead(Offset(headX, headY))
    drawLimb(Offset(headX, headY + 11.dp.toPx()), Offset(hipX, hipY), 9.dp.toPx())

    // Ground leg (squatting deeply)
    val kneeX = w * 0.58f
    val kneeY = hipY + (8.dp.toPx() * (1f - progress))
    val footX = w * 0.50f
    val footY = floorY - 4.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(kneeX, kneeY), 9.dp.toPx())
    drawLimb(Offset(kneeX, kneeY), Offset(footX, footY), 8.dp.toPx())

    // Floating straight leg
    val outFootX = w * 0.78f
    val outFootY = hipY + 4.dp.toPx()
    drawLimb(Offset(hipX, hipY), Offset(outFootX, outFootY), 8.dp.toPx())

    // Arms forward for balance
    drawLimb(Offset(headX, headY + 16.dp.toPx()), Offset(w * 0.70f, headY + 16.dp.toPx()), 6.dp.toPx())

    if (mode == AnimationMode.MUSCLE_HIGHLIGHT) {
        drawMuscleGlow(Offset((hipX + kneeX) / 2f, kneeY), 18.dp.toPx(), MetricCyan, progress)
    }
}
