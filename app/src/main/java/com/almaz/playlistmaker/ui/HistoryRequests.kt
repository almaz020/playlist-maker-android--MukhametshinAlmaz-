package com.almaz.playlistmaker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almaz.playlistmaker.R

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding( start = 16.dp, end = 16.dp,)
            .clip( shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
            .background( color = colorResource(R.color.light_gray),),

    ) {
        items(historyList.size) { index ->
            if (index == 0) {
                HorizontalDivider(
                    modifier = Modifier.alpha(0.5f).padding(start = 9.dp, end = 9.dp),
                    thickness = 2.dp,
                    color = colorResource(R.color.light_gray_for_search_field)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(historyList[index]) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 15.dp, top = 14.dp, bottom = 14.dp)
                        ,
                    painter = painterResource(R.drawable.info),
                    contentDescription = null,
                    tint = colorResource(R.color.light_gray_for_search_field)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    modifier = Modifier.padding(top = 11.dp, bottom = 11.dp),
                    text = historyList[index],
                    fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
                    fontSize = 16.sp
                )
            }

        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewHistory() {
    HistoryRequests(listOf("Track 1", "Track 2", "Track 3")) { }
}