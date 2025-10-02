package com.example.jorge.mandopc.utilities

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope

class DragHandler {

    suspend fun handleGestures(
        scope: PointerInputScope,
        onDrag: (dragX: Int, dragY: Int) -> Unit,
        onDragFinish: () -> Unit
    ) {
        var pointerDownPosition: Offset = Offset.Zero
        var isDragging = false
        val dragThreshold = 10f

        scope.awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()

                event.changes.forEach { change ->
                    when {
                        change.pressed && !change.previousPressed -> {
                            pointerDownPosition = change.position
                            isDragging = false
                            change.consume()
                        }

                        change.pressed && change.position != Offset.Zero -> {

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

                        !change.pressed && change.previousPressed -> {
                            val distance = (change.position - pointerDownPosition).getDistance()
                            if (isDragging && distance > dragThreshold) {
                                onDragFinish()
                            }
                            isDragging = false
                            change.consume()
                        }
                    }
                }
            }
        }
    }
}
