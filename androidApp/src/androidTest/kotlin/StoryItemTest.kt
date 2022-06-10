import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.lduboscq.minimedia.android.MiniMediaTheme
import com.lduboscq.minimedia.android.StoryItem
import com.lduboscq.minimedia.convertLongToTime
import com.lduboscq.minimedia.domain.Media
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class StoryItemTest {

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
    fun checkDatasAndClick() {
        var clicked = 0

        composeTestRule.setContent {
            MiniMediaTheme {
                StoryItem(
                    story = story,
                    onClick = { clicked++ },
                )
            }
        }

        composeTestRule.onNodeWithTag("story-image", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(story.title).assertIsDisplayed()
        composeTestRule.onNodeWithText("By ${story.author} - ${convertLongToTime((story.date * 1000).toLong())}")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(story.sport.name.uppercase()).assertIsDisplayed()
        composeTestRule.onNodeWithTag("story-card").performClick()
        Assert.assertEquals(1, clicked)
    }
}
