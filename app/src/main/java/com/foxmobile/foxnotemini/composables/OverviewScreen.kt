package com.foxmobile.foxnotemini.composables

import com.foxmobile.foxnotemini.ui.theme.FoxNoteMiniTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onNoteClick: (/* TODO Note*/) -> Unit,
    onAddButtonClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("FoxNote Mini", fontSize = 35.sp)

                },
                scrollBehavior = scrollBehavior
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddButtonClick()
                },
                modifier = Modifier.size(60.dp)
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add note", Modifier.size(55.dp))
            }
        }

    ) { padding ->

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            for (i  in 1..10) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .clickable {
                                onNoteClick(/* TODO Note(i, "title $i", "content $i", LocalDate.now().toString(), LocalDateTime.now().toString())*/)
                            }
                    ) {
                        NoteContainer(
                            /* TODO note = Note(i, "title $i", "content $i", LocalDate.now().toString(), LocalDateTime.now().toString()),*/
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOverviewScreen(modifier: Modifier = Modifier) {
    FoxNoteMiniTheme {
        OverviewScreen(onNoteClick = {}, onAddButtonClick = {})
    }
}