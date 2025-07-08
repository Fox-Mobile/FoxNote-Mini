package com.foxmobile.foxnotemini.composables

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foxmobile.foxnotemini.ui.theme.Black
import com.foxmobile.foxnotemini.ui.theme.FoxNoteMiniTheme
import com.foxmobile.foxnotemini.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    /* TODO noteParam: Note?,*/
    /* TODO noteViewModel: NoteViewModel*/
) {
    val onBackArrowClick = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
//   TODO val note = noteParam ?: Note(null, "", "", "", "")
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("FoxNote Mini", fontSize = 35.sp)

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackArrowClick?.onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            modifier = modifier.size(39.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
//                            TODO noteViewModel.onEvent(NoteEvent.SetID(note.id))
//                            noteViewModel.onEvent(NoteEvent.SetTitle(note.title))
//                            noteViewModel.onEvent(NoteEvent.SetContent(note.content))
//                            noteViewModel.onEvent(NoteEvent.SaveNote)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Back",
                            tint = Orange,
                            modifier = modifier.size(39.dp)
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxWidth()
                .fillMaxHeight(),

            verticalArrangement = Arrangement.Top,
        ){
            TextField(
                //TODO value = note.title,
                "Note",
                onValueChange = {},
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textStyle = TextStyle(
                    fontSize = 28.sp
                ),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Black,
                    unfocusedContainerColor = Black,
                    disabledContainerColor = Black,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text ="Title",
                        color = Color.White,
                        fontSize =  28.sp
                    )
                }
            )
            Spacer(modifier = modifier.padding(2.dp))
            TextField(
                // TODO value = note.content,
                "Note content",
                onValueChange = {},
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),

                textStyle = TextStyle(
                    fontSize = 22.sp
                ),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Black,
                    unfocusedContainerColor = Black,
                    disabledContainerColor = Black,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text ="Note content",
                        color = Color.White,
                        fontSize =  22.sp
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewNoteScreen(modifier: Modifier = Modifier) {
    FoxNoteMiniTheme {
        NoteScreen(/* TODO noteParam = Note(1, "Title", "Note content", LocalDate.now().toString(), LocalDateTime.now().toString()), noteViewModel = hiltViewModel<NoteViewModel>()*/)
    }
}