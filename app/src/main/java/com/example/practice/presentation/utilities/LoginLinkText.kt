import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.practice.ui.theme.blue
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.secondaryColor


@Composable
fun ClickableTextCustom(
    fullText: String,
    clickableText: String,
    clickableColor: Color =blue,
    onClick: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append(fullText)

        pushStringAnnotation(tag = "CLICK", annotation = "click")
        withStyle(
            style = SpanStyle(
                color = clickableColor,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(clickableText)
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "CLICK", start = offset, end = offset)
                .firstOrNull()?.let { _ ->
                    onClick()
                }
        },
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 14.sp,
            color = Color.Black
        )
    )
}
