import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.ui.theme.blue

@Composable
fun SecurityTipCard(
    icon: Int,
    title: String,
    description: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                color = blue,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                expanded = !expanded
            }

            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {




            Column(modifier = Modifier.padding(20.dp)) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = blue,
                        modifier = Modifier.size(25.dp)
                    )


                    Spacer(modifier = Modifier.width(16.dp))

                    Text(title, fontWeight = FontWeight.Bold)

                }
                Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)) {
                    Text(
                        text = description,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        maxLines = if (expanded) Int.MAX_VALUE else 1
                    )
                }
                }





    }
}
