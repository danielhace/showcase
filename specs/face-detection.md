# Live face detection

Module: `:feature:facedetection:presentation`. Reached from the first Home item.

## Behavior

- Full-screen live camera preview — nothing else on screen except a back button (top-start) and a switch-camera button (bottom-center).
- Opens on the **front** camera; the switch button toggles front/back. Boxes from the previous lens are cleared on switch.
- Each detected face gets a **square** outline (side = longer edge of the detector's bounding box, centered on it), drawn as a thin stroke in the clay accent. Squares follow faces live; multiple faces are supported.
- Back button and system back both return to the Home list.

## Camera permission

- Requested once on first entry (not re-requested on rotation).
- Denied → message "Camera access is needed to detect faces." with **Allow camera** (re-prompts).
- Denied with "don't ask again" → same message with **Open settings** (app details page). Returning from settings with the permission granted starts the camera automatically.
- The back button stays available in every state.

## Implementation notes

- CameraX `LifecycleCameraController` + `PreviewView`, analysis via `camera-mlkit-vision`'s `MlKitAnalyzer` with `COORDINATE_SYSTEM_VIEW_REFERENCED`, so bounds are already in view coordinates (rotation, crop and front-lens mirroring handled by CameraX).
- ML Kit **bundled** face detection model (`com.google.mlkit:face-detection`), `PERFORMANCE_MODE_FAST`, tracking enabled, no landmarks/classification.
- Faces are a separate ViewModel flow (not part of screen state) read only inside the overlay `Canvas` draw lambda, so ~30 detections/s redraw the overlay without recomposing the screen.
- Navigation callbacks are wrapped in `dropUnlessResumed` (and Home → demo uses `launchSingleTop`) so double taps can't double-push or pop past Home.
