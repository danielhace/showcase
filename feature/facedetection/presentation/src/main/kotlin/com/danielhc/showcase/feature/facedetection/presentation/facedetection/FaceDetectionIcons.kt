package com.danielhc.showcase.feature.facedetection.presentation.facedetection

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/**
 * The two glyphs this screen needs, defined locally (Material icon paths, Apache 2.0) instead of
 * pulling in the multi-megabyte material-icons-extended artifact for them.
 */
internal object FaceDetectionIcons {

    val Back: ImageVector by lazy {
        icon(
            name = "Back",
            autoMirror = true,
            pathData = "M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z",
        )
    }

    val FlipCamera: ImageVector by lazy {
        icon(
            name = "FlipCamera",
            autoMirror = false,
            pathData = "M19,8l-4,4h3c0,3.31 -2.69,6 -6,6 -1.01,0 -1.97,-0.25 -2.8,-0.7l-1.46,1.46" +
                "C8.97,19.54 10.43,20 12,20c4.42,0 8,-3.58 8,-8h3l-4,-4z" +
                "M6,12c0,-3.31 2.69,-6 6,-6 1.01,0 1.97,0.25 2.8,0.7l1.46,-1.46" +
                "C15.03,4.46 13.57,4 12,4c-4.42,0 -8,3.58 -8,8H1l4,4 4,-4H6z",
        )
    }

    private fun icon(name: String, autoMirror: Boolean, pathData: String): ImageVector =
        ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            autoMirror = autoMirror,
        ).addPath(pathData = addPathNodes(pathData), fill = SolidColor(Color.Black)).build()
}
