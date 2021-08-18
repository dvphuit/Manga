/*
 * Copyright 2021 The Android Open Source Project
 * Copyright 2021 RinOrz (凛)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Github home page: https://github.com/RinOrz
 */
package com.mxalbert.sharedelements

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import androidx.compose.ui.zIndex

@Composable
fun SharedElement(
  key: Any,
  screenKey: Any,
  transitionSpec: SharedElementsTransitionSpec = DefaultSharedElementsTransitionSpec,
  onFractionChanged: ((Float) -> Unit)? = null,
  placeholder: @Composable (() -> Unit)? = null,
  content: @Composable () -> Unit
) {
  val elementInfo = remember(key, screenKey, transitionSpec, onFractionChanged) {
    SharedElementInfo(key, screenKey, transitionSpec, onFractionChanged)
  }
  val realPlaceholder = placeholder ?: content
  BaseSharedElement(
    elementInfo,
    realPlaceholder,
    { Placeholder(it) },
    { ElementContainer(modifier = it, content = content) }
  )
}

@Composable
private fun Placeholder(state: SharedElementsTransitionState) {
  with(LocalDensity.current) {
    val fraction = state.fraction
    val startBounds = state.startBounds
    val endBounds = state.endBounds

    val fadeFraction = state.spec?.fadeProgressThresholds?.applyTo(fraction) ?: fraction
    val scaleFraction = state.spec?.scaleProgressThresholds?.applyTo(fraction) ?: fraction
    val (startScaleX, startScaleY) = calculateScale(startBounds, endBounds, scaleFraction)
    val offset = calculateOffset(
      startBounds, endBounds,
      fraction, state.pathMotion,
      startBounds.width * startScaleX
    ).round()

    @Composable
    fun Container(
      compositionLocalValues: CompositionLocalValues,
      bounds: Rect,
      scaleX: Float,
      scaleY: Float,
      isStart: Boolean,
      content: @Composable () -> Unit,
      zIndex: Float = 0f,
    ) {
      val alpha = calculateAlpha(state.direction, state.spec?.fadeMode, fadeFraction, isStart)
      if (alpha > 0) compositionLocalValues.provided {
        ElementContainer(
          modifier = Modifier.size(
            bounds.width.toDp(),
            bounds.height.toDp()
          ).offset { offset }.graphicsLayer {
            this.transformOrigin = TopLeft
            this.scaleX = scaleX
            this.scaleY = scaleY
            this.alpha = alpha
          }.run {
            if (zIndex == 0f) this else zIndex(zIndex)
          },
          content = content
        )
      }
    }

    Container(
      state.startCompositionLocalValues,
      startBounds,
      startScaleX,
      startScaleY,
      true,
      state.startPlaceholder
    )

    if (endBounds != null) {
      val (endScaleX, endScaleY) = calculateScale(endBounds, startBounds, 1 - scaleFraction)
      Container(
        state.endCompositionLocalValues!!,
        endBounds,
        endScaleX,
        endScaleY,
        false,
        state.endPlaceholder!!,
        if (state.spec?.fadeMode == FadeMode.Out) -1f else 0f
      )
    }
  }
}
