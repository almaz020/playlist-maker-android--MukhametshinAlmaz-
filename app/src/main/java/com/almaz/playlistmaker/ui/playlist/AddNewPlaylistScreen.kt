package com.almaz.playlistmaker.ui.playlist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.ui.view_model.AddNewPlaylistViewModel
import org.w3c.dom.Text
import java.io.File

@Composable
fun AddNewPlaylistScreen(
    onBack: () -> Unit,
    addPlaylistViewModel: AddNewPlaylistViewModel,
) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isFormEmpty by remember (name) {
        derivedStateOf { name.isNotBlank() }
    }
    val coverImageUri by addPlaylistViewModel.coverImageUri.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        uri ?: return@rememberLauncherForActivityResult

        val localPath = saveImageToInternalStorage(
            context = context,
            sourceUri = uri
        )

        addPlaylistViewModel.setCoverImageUri(localPath)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 29.dp, start = 24.dp, end = 24.dp, bottom = 29.dp)
                .clickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        imagePickerLauncher.launch("image/*")
                    } else {
                        // Для старых версий Android проверяем разрешение
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                imagePickerLauncher.launch("image/*")
                            }
                            else -> {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                }
        ) {
            if (coverImageUri != null) {
                // Показываем выбранное изображение
                AsyncImage(
                    model = File(coverImageUri.toString()),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 103.dp, start = 106.dp, end = 106.dp, bottom = 109.dp),

                )
            } else {
                // Показываем плейсхолдер
                Image(
                    modifier = Modifier.padding(top = 103.dp, start = 106.dp, end = 106.dp, bottom = 109.dp),
                    painter = painterResource(R.drawable.add_photo),
                    contentDescription = null,
                )
                Text(
                    text = "Выберите обложку",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
                )
            }

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
                addPlaylistViewModel.createNewPlaylist(name, description)
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

private fun saveImageToInternalStorage(
    context: Context,
    sourceUri: Uri
): String {

    val fileName = "playlist_${System.currentTimeMillis()}.jpg"

    val file = File(context.filesDir, fileName)

    context.contentResolver.openInputStream(sourceUri)?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return file.absolutePath
}
