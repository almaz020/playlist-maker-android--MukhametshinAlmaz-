package com.almaz.playlist_maker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = colorResource(R.color.light_gray)
            )
            .height(36.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            modifier = Modifier
                .padding(
                    top = 11.dp,
                    start = 13.dp,
                    bottom = 11.dp,
                    end = 9.dp,
                ),
            painter = painterResource(R.drawable.search_gray),
            contentDescription = null,
        )
        Text(
            text = "Поиск",
            color = colorResource(R.color.light_gray_for_search_field),
            fontSize = 16.sp,
        )
    }
}