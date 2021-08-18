package dvp.app.azmanga.ui.screens.library

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun LibraryScreen() {
    LazyColumn {
        // Add a single item
        item {
            Text(text = "First item")
        }

        // Add 5 items
        items(100) { index ->
            Text(text = "Item: $index")
        }

        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}