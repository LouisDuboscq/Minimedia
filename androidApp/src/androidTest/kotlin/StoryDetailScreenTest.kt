import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.lduboscq.minimedia.android.MiniMediaTheme
import com.lduboscq.minimedia.android.StoryDetailScreen
import com.lduboscq.minimedia.android.StoryItem
import com.lduboscq.minimedia.convertLongToTime
import com.lduboscq.minimedia.domain.Media
import com.lduboscq.minimedia.presentation.DetailViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class StoryDetailScreenTest {

    private val story = Media.Story(
        id = 1,
        title = "a story",
        teaser = "a teaser",
        image = "an image",
        date = 1588110350.85,
        sport = Media.Sport(1, "Football"),
        author = "an author"
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkDatasAndClickBack() {
        var clicked = 0
        composeTestRule.setContent {
            MiniMediaTheme {
                StoryDetailScreen(
                    state = DetailViewModel.State(
                        loading = false,
                        story = story
                    ),
                    navigateBack = { clicked++ },
                )
            }
        }
        composeTestRule.onNodeWithText(story.sport.name.uppercase()).assertIsDisplayed()
        composeTestRule.onNodeWithText(story.title).assertIsDisplayed()
        composeTestRule.onNodeWithText("By ").assertIsDisplayed()
        composeTestRule.onNodeWithText(story.author).assertIsDisplayed()
        composeTestRule.onNodeWithText(convertLongToTime((story.date * 1000).toLong()))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(story.teaser).assertIsDisplayed()
        composeTestRule.onNodeWithTag("story-image").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back-button").performClick()
        Assert.assertEquals(1, clicked)
    }
}
