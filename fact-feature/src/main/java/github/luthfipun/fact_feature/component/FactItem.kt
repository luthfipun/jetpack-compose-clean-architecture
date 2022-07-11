package github.luthfipun.fact_feature.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import github.luthfipun.common.randomColors

@Composable
fun FactItem(factString: String){
    CardFact {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = factString,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal
        )
    }
}

@Composable
fun CardFact(
    content: @Composable () -> Unit
){
    val stripeColor = randomColors()
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Box(modifier = Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(Color(stripeColor)))
            content()
        }
    }
}