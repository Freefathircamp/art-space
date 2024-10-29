package lat.pam.artspace

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asAndroidColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import lat.pam.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                ArtSpace(Modifier)
            }
        }
    }
}

@Composable
fun ArtSpace(modifier: Modifier = Modifier) {
    // List of images as drawable resources
    val images = arrayOf(
        R.drawable.sayah,
        R.drawable.hesti_32,
        R.drawable.kang_nyanyang_v2
    )
    val authors = listOf("Author 1", "Author 2", "Author 3")
    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display the current image based on the index using AppImage
        AppImage(
            resource = images[currentIndex],
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )

        Detail(
            description = "Description for Image ${currentIndex + 1}",
            author = authors[currentIndex],
            modifier = Modifier.padding(16.dp)
        )

        Controller(
            modifier = Modifier.padding(16.dp),
            onPrevClick = {
                // Navigate to the previous image
                if (currentIndex > 0) {
                    currentIndex--
                } else {
                    currentIndex = images.size - 1 // Wrap to the last image
                }
            },
            onNextClick = {
                // Navigate to the next image
                if (currentIndex < images.size - 1) {
                    currentIndex++
                } else {
                    currentIndex = 0 // Wrap to the first image
                }
            }
        )
    }
}

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    @DrawableRes resource: Int,
    colorFilter: ColorFilter? = null
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            ImageView(context).apply {
                setImageResource(resource)
                colorFilter?.let { setColorFilter(it.asAndroidColorFilter()) }
            }
        },
        update = { imageView ->
            imageView.setImageResource(resource)
            colorFilter?.let { imageView.colorFilter = it.asAndroidColorFilter() }
        }
    )
}

@Composable
fun Detail(description: String, author: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = description, style = MaterialTheme.typography.bodySmall)
        Text(text = author, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Controller(modifier: Modifier = Modifier, onPrevClick: () -> Unit, onNextClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onPrevClick) {
            Text(text = "Prev")
        }
        Button(onClick = onNextClick) {
            Text(text = "Next")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpace(Modifier)
    }
}
