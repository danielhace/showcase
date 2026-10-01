package com.danielhc.showcase.feature.facedetection.presentation.facedetection

/**
 * A detected face as a square in preview-view pixel coordinates. The square is centered on the
 * detector's bounding box, with its side equal to the box's longer edge.
 */
data class FaceBoxUi(
    val trackingId: Int?,
    val left: Float,
    val top: Float,
    val size: Float,
)
