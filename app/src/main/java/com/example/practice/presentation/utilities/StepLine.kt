import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.StepLine(isActive: Boolean) {

    Box(
        modifier = Modifier
            .weight(1f)
            .height(2.dp)
            .background(
                if (isActive) Color.White
                else Color.White.copy(alpha = 0.4f)
            )
    )
}
