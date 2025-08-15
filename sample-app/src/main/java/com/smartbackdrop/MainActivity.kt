package com.smartbackdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.smartbackdrop.smartbackdrop.compose.SmartBackdrop
import com.smartbackdrop.smartbackdrop.core.*

/**
 * Main activity demonstrating the Smart Backdrop SDK.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SmartBackdropDemo()
            }
        }
    }
}

/**
 * Main demo screen showing the Smart Backdrop functionality.
 */
@Composable
fun SmartBackdropDemo() {
    var demoState by remember { mutableStateOf(DemoState()) }

    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        BackdropDisplay(
            imageUrl = demoState.imageUrl,
            config = demoState.config
        )
        DemoControls(
            state = demoState,
            onStateChange = { demoState = it }
        )
    }
}

/**
 * State data class for the demo.
 */
data class DemoState(
    val imageUrl: String = "https://fastly.picsum.photos/id/760/512/1080.jpg?hmac=lt6xWbnKDhpd5NTmzKV7pedcHEw5VQ9VHx0Y7-ZHHM8",
    val strategy: Strategy = Strategy.PaletteSolid,
    val edgePct: Float = 0.2f,
    val enforceContrast: Boolean = false
) {
    val config: BackdropConfig
        get() = BackdropConfig(
            strategy = strategy,
            enforceContrast = enforceContrast,
            edgeStripPct = edgePct
        )
}

/**
 * Displays the backdrop with the image.
 */
@Composable
private fun BackdropDisplay(
    imageUrl: String,
    config: BackdropConfig
) {
    SmartBackdrop(
        imageModel = imageUrl,
        modifier = Modifier.fillMaxWidth().height(260.dp),
        config = config
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Demo Image",
            contentScale = ContentScale.None,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Controls for adjusting the demo parameters.
 */
@Composable
private fun DemoControls(
    state: DemoState,
    onStateChange: (DemoState) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
    ) {
        StrategySelector(
            currentStrategy = state.strategy,
            onStrategyChange = { onStateChange(state.copy(strategy = it)) }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        EdgeStripControl(
            edgePct = state.edgePct,
            onEdgePctChange = { onStateChange(state.copy(edgePct = it)) }
        )
        
        ContrastControl(
            enforceContrast = state.enforceContrast,
            onEnforceContrastChange = { onStateChange(state.copy(enforceContrast = it)) }
        )
    }
}

/**
 * Strategy selection component.
 */
@Composable
private fun StrategySelector(
    currentStrategy: Strategy,
    onStrategyChange: (Strategy) -> Unit
) {
    Text("Strategy: ${currentStrategy.name}")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(Strategy.entries) { strategy ->
            FilterChip(
                selected = currentStrategy == strategy,
                onClick = { onStrategyChange(strategy) },
                label = { Text(strategy.name) }
            )
        }
    }
}

/**
 * Edge strip percentage control.
 */
@Composable
private fun EdgeStripControl(
    edgePct: Float,
    onEdgePctChange: (Float) -> Unit
) {
    Text("Edge strip: ${(edgePct * 100).toInt()}%")
    Slider(
        value = edgePct,
        onValueChange = onEdgePctChange,
        valueRange = 0.01f..0.2f
    )
}

/**
 * Contrast enforcement control.
 */
@Composable
private fun ContrastControl(
    enforceContrast: Boolean,
    onEnforceContrastChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = enforceContrast,
            onCheckedChange = onEnforceContrastChange
        )
        Text("Enforce contrast (WCAG ≥ 4.5:1)")
    }
}
