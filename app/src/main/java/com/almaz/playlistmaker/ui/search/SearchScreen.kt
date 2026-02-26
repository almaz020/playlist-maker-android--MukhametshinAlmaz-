package com.almaz.playlistmaker.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.ui.TrackListItem
import com.almaz.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},

    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
) {

    val screenState by viewModel.searchScreenState.collectAsState()

    var text by rememberSaveable { mutableStateOf("") }

    Column {
        PanelHeader(
            title = stringResource(R.string.search),
            isButtonEnabled = true,
            onBack = onBack
        )

        BasicTextField(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                .clip(
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = colorResource(R.color.light_gray),
                )
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
                viewModel.search(text)

            },
            singleLine = true,
            cursorBrush = SolidColor(colorResource(R.color.blue_for_search_cursor)),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(R.font.yandexsanstextregular))
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(
                            top = 11.dp,
                            start = 13.dp,
                            bottom = 11.dp,
                            end = 9.dp,
                        ),
                        painter = painterResource(R.drawable.search_gray),
                        contentDescription = null,
                        tint = colorResource(R.color.light_gray_for_search_field)
                    )
                    Box(Modifier.weight(1f)) {
                        if (text.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search),
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.yandexsanstextregular))
                                )
                            )
                        }
                        innerTextField()
                    }
                    if (!text.isEmpty()) {
                        Icon(
                            modifier = Modifier
                                .padding(
                                    top = 12.dp,
                                    end = 14.dp,
                                    bottom = 12.dp
                                )
                                .clickable(
                                    onClick = { text = "" }
                                ),
                            painter = painterResource(R.drawable.clear_search_field),
                            contentDescription = null,
                            tint = colorResource(R.color.light_gray_for_search_field)
                        )
                    }
                }
            }
        )

        when (screenState) {
            is SearchState.Initial -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Введите строку для поиска")
                }
            }

            is SearchState.Searching -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    items(tracks.size) { index ->
                        TrackListItem(track = tracks[index])
                    }
                }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: $error", color = Color.Red)
                }
            }
        }
    }
}