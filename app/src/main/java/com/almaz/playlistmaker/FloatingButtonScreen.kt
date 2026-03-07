package com.almaz.playlistmaker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FloatingButtonScreen(
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            shape = CircleShape,
            modifier = Modifier.padding(32.dp).align(Alignment.BottomEnd),
            onClick =  {  showBottomSheet = true },
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        )
        {
            Icon(
                painter = painterResource(R.drawable.floating_button),
                contentDescription = null,
            )
        }
        ModalBottomSheet(
            modifier = Modifier,
            isShowPanel = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            content = "Плейлист"
        )
    }
}
@Composable
@Preview(showBackground = true) 
fun Prev() {
    FloatingButtonScreen()
}