package com.almaz.playlistmaker.ui.playlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.ui.PanelHeader
import org.w3c.dom.Text

@Composable
fun AddNewPlaylistScreen(
    onBack: () -> Unit,
    onCreateClicked: (String, String) -> Unit,
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isFormEmpty by remember (name) {
        derivedStateOf { name.isNotBlank() }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PanelHeader(
            title = stringResource(R.string.new_playlist),
            isButtonEnabled = true,
            onBack = onBack
        )
        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 29.dp, start = 24.dp, end = 24.dp, bottom = 29.dp)
        ) {
            Image(
                modifier = Modifier.padding(top = 103.dp, start = 106.dp, end = 106.dp, bottom = 109.dp),
                painter = painterResource(R.drawable.add_photo),
                contentDescription = null,
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ,
            value = name,
            onValueChange = { name = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.blue),
                unfocusedBorderColor = colorResource(R.color.light_gray_for_search_field),
                disabledBorderColor = colorResource(R.color.light_gray_for_search_field),
                errorBorderColor = Color.Red,
                focusedLabelColor = colorResource(R.color.blue),
                unfocusedLabelColor = Color.Black,
                cursorColor = colorResource(R.color.blue),
            ),
            label = { Text(text = stringResource(R.string.name), fontFamily = FontFamily(Font(R.font.yandexsanstextregular))) },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.yandexsanstextregular))
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ,
            value = description,
            onValueChange = { description = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.blue),
                unfocusedBorderColor = colorResource(R.color.light_gray_for_search_field),
                disabledBorderColor = colorResource(R.color.light_gray_for_search_field),
                errorBorderColor = Color.Red,
                focusedLabelColor = colorResource(R.color.blue),
                unfocusedLabelColor = Color.Black,
                cursorColor = colorResource(R.color.blue),
            ),
            label = { Text(text = stringResource(R.string.description), fontFamily = FontFamily(Font(R.font.yandexsanstextregular))) },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.yandexsanstextregular))
            )
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 17.dp, end = 17.dp, bottom = 32.dp)
                .height(44.dp)
                ,
            onClick = {
                onCreateClicked(name, description)
                name = ""
                description = ""
                Toast.makeText(context, "Плейлист успешно создан", Toast.LENGTH_SHORT).show()
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = if(!isFormEmpty) colorResource(R.color.light_gray_for_search_field) else Color.Blue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = stringResource(R.string.create)
            )
        }
    }
}
