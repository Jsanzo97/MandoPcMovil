package com.example.jorge.mandopc.utilities

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope

class DragHandler {

    suspend fun handleGestures(
        scope: PointerInputScope,
        onDrag: (dragX: Int, dragY: Int) -> Unit,
        onDragFinish: () -> Unit,
        onTwoFingerClick: () -> Unit,
        onTwoFingerScroll: (scrollX: Int, scrollY: Int) -> Unit
    ) {
        var pointerDownPosition: Offset = Offset.Zero
        var isDragging = false
        val dragThreshold = 10f

        var twoFingerDownPositions: Map<Long, Offset> = emptyMap()
        var isTwoFingerGesture = false
        var twoFingerStartX = 0f
        var twoFingerStartY = 0f
        var isTwoFingerScrolling = false
        var justFinishedTwoFingerGesture = false

        scope.awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val activePointers = event.changes.filter { it.pressed }
                val pointerCount = activePointers.size

                when {
                    pointerCount == 2 -> {
                        val pointer1 = activePointers[0]
                        val pointer2 = activePointers[1]

                        if (!isTwoFingerGesture) {
                            twoFingerDownPositions = mapOf(
                                pointer1.id.value to pointer1.position,
                                pointer2.id.value to pointer2.position
                            )
                            val avgPosition = (pointer1.position + pointer2.position) / 2f
                            twoFingerStartX = avgPosition.x
                            twoFingerStartY = avgPosition.y
                            isTwoFingerGesture = true
                            isTwoFingerScrolling = false
                        } else {
                            val avgPosition = (pointer1.position + pointer2.position) / 2f
                            val currentX = avgPosition.x
                            val currentY = avgPosition.y
                            val deltaX = currentX - twoFingerStartX
                            val deltaY = currentY - twoFingerStartY

                            if (kotlin.math.abs(deltaX) > dragThreshold ||
                                kotlin.math.abs(deltaY) > dragThreshold) {
                                isTwoFingerScrolling = true
                                onTwoFingerScroll(deltaX.toInt(), deltaY.toInt())
                                twoFingerStartX = currentX
                                twoFingerStartY = currentY
                            }
                        }

                        pointer1.consume()
                        pointer2.consume()
                    }

                    pointerCount < 2 && isTwoFingerGesture -> {
                        if (!isTwoFingerScrolling) {
                            val pointer1Pos = twoFingerDownPositions.values.elementAtOrNull(0)
                            val pointer2Pos = twoFingerDownPositions.values.elementAtOrNull(1)

                            if (pointer1Pos != null && pointer2Pos != null) {
                                val avgStartPos = (pointer1Pos + pointer2Pos) / 2f
                                val deltaX = kotlin.math.abs(twoFingerStartX - avgStartPos.x)
                                val deltaY = kotlin.math.abs(twoFingerStartY - avgStartPos.y)
                                val totalMovement = kotlin.math.sqrt(deltaX * deltaX + deltaY * deltaY)

                                if (totalMovement < dragThreshold) {
                                    onTwoFingerClick()
                                }
                            }
                        }

                        isTwoFingerGesture = false
                        isTwoFingerScrolling = false
                        twoFingerDownPositions = emptyMap()
                        isDragging = false
                        justFinishedTwoFingerGesture = true

                        event.changes.forEach { it.consume() }
                    }

                    pointerCount == 1 -> {
                        val change = activePointers[0]

                        if (justFinishedTwoFingerGesture) {
                            change.consume()
                        } else {
                            when {
                                !change.previousPressed -> {
                                    pointerDownPosition = change.position
                                    isDragging = false
                                    change.consume()
                                }

                                change.position != Offset.Zero -> {
                                    onDrag(
                                        (change.position.x - pointerDownPosition.x).toInt(),
                                        (change.position.y - pointerDownPosition.y).toInt()
                                    )

                                    val distance = (change.position - pointerDownPosition).getDistance()
                                    if (distance > dragThreshold) {
                                        isDragging = true
                                    }

                                    change.consume()
                                }
                            }
                        }
                    }

                    pointerCount == 0 -> {
                        event.changes.forEach { change ->
                            if (!change.pressed && change.previousPressed) {
                                if (!justFinishedTwoFingerGesture) {
                                    val distance = (change.position - pointerDownPosition).getDistance()
                                    if (isDragging && distance > dragThreshold) {
                                        onDragFinish()
                                    }
                                }
                                isDragging = false
                                justFinishedTwoFingerGesture = false
                                change.consume()
                            }
                        }
                    }
                }
            }
        }
    }
}
