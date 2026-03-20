package com.almaz.playlistmaker.ui.search

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.Word
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.HistoryRequests
import com.almaz.playlistmaker.ui.TrackDetailsScreen

import com.almaz.playlistmaker.ui.TrackListItem
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    searchViewModel: SearchViewModel,
    goToTrackDetailsScreen: (Track) -> Unit,
) {
    val context = LocalContext.current

    val screenState by searchViewModel.searchScreenState.collectAsState()

    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(text) {
        searchViewModel.updateQuery(text)
    }

    LaunchedEffect(screenState) {
        when (screenState) {
            is SearchState.Success -> {
                focusManager.clearFocus()
            }
            else -> Unit
        }
    }
    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        searchViewModel.getHistoryList().collect { list ->
            historyList = list
        }
    }

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
                .then(
                    if (isFocused && text.isEmpty() && historyList.isNotEmpty()) {
                        Modifier.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    } else {
                        Modifier.clip(RoundedCornerShape(8.dp))
                    }
                )
                .background(
                    color = colorResource(R.color.light_gray),
                )
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = text,
            onValueChange = { newText ->
                text = newText

            },
            singleLine = true,
            cursorBrush = SolidColor(colorResource(R.color.blue_for_search_cursor)),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(R.font.yandexsanstextregular))
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                                    onClick = {
                                        text = ""
                                        searchViewModel.clearSearch()
                                    }
                                ),
                            painter = painterResource(R.drawable.clear_search_field),
                            contentDescription = null,
                            tint = colorResource(R.color.light_gray_for_search_field)
                        )
                    }
                }

            }
        )
        if (isFocused && text.isEmpty() && historyList.isNotEmpty()) {
            HistoryRequests(
                historyList = historyList,
                onClick = { word ->
                    text = word
                }
            )
        }


        when (screenState) {
            is SearchState.Initial -> {
                if (text.isEmpty()) {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.search))
                    }
                } else {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is SearchState.Searching -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list
                if (tracks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.no_songs_found),
                            color = Color.Red
                        )
                    }
                }
                else {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        items(tracks.size) { index ->
                            TrackListItem(
                                track = tracks[index],
                                onClick = { goToTrackDetailsScreen(tracks[index]) }
                            )
                        }
                    }
                }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(R.string.error),
                            color = Color.Red
                        )
                        Text(
                            error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                        Text(
                            modifier = Modifier.clickable { searchViewModel.performSearch(text) },
                            text = stringResource(R.string.update),
                            color = Color.Blue,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}