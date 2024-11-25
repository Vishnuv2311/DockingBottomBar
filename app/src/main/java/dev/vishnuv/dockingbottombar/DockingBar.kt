package dev.vishnuv.dockingbottombar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vishnuv.dockingbottombar.ui.theme.DockingBottomBarTheme
import kotlinx.coroutines.launch

@Composable
fun DockingBar(modifier: Modifier = Modifier) {
    var activeIndex by remember { mutableIntStateOf(0) }

    val icons = listOf(
        R.drawable.ic_lense,
        R.drawable.ic_chat,
        R.drawable.ic_add,
        R.drawable.ic_color,
        R.drawable.ic_setting
    )

    val yAnimation = remember { Animatable(0f) }
    val scaleAnimation = remember { Animatable(1f) }

    val enterTweenSpec = tween<Float>(
        durationMillis = 200,
        easing = EaseOutElastic
    )

    val exitTweenSpec = tween<Float>(
        durationMillis = 400,
        easing = EaseOut
    )

    val coroutineScope = rememberCoroutineScope()



    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(15.dp))
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    icons.forEachIndexed { index, icon ->
                        val isActive by mutableStateOf(activeIndex == index)
                        val iconScale = if (isActive) scaleAnimation.value else 1f
                        val translationY = if (isActive) yAnimation.value.dp else 0.dp

                        Box(modifier = Modifier
                            .scale(iconScale)
                            .offset(y = translationY)
                            .clickable {
                                coroutineScope.launch {
                                    activeIndex = index
                                    yAnimation.animateTo(
                                        -20f,
                                        animationSpec = enterTweenSpec
                                    )
                                    scaleAnimation.animateTo(
                                        1.2f,
                                        animationSpec = exitTweenSpec
                                    )
                                    yAnimation.animateTo(
                                        0f,
                                        animationSpec = enterTweenSpec
                                    )
                                    scaleAnimation.animateTo(
                                        1f,
                                        animationSpec = exitTweenSpec
                                    )
                                }
                            }
                            .background(
                                Color.Gray,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(12.dp)) {
                            Icon(
                                painter = painterResource(id = icon), contentDescription = null,
                            )
                        }

                    }


                }

            }
        }
    }


}


@Preview
@Composable
private fun DockingBarPreview() {
    DockingBottomBarTheme {
        DockingBar()
    }
}