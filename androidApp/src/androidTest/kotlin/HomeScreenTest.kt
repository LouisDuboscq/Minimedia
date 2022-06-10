import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.lduboscq.minimedia.android.HomeScreen
import com.lduboscq.minimedia.android.MiniMediaTheme
import com.lduboscq.minimedia.presentation.HomeViewModel
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyListAndLoading() {
        composeTestRule.setContent {
            MiniMediaTheme {
                HomeScreen(
                    state = HomeViewModel.State(loading = true, medias = emptyList()),
                    onClickArticle = {}
                )
            }
        }

        composeTestRule.onNodeWithText("FEATURED").assertIsDisplayed()
        composeTestRule.onNodeWithTag("progress-indicator").assertIsDisplayed()
    }

    @Test
    fun emptyListAndNotLoading() {
        composeTestRule.setContent {
            MiniMediaTheme {
                HomeScreen(
                    state = HomeViewModel.State(loading = false, medias = emptyList()),
                    onClickArticle = {}
                )
            }
        }

        composeTestRule.onNodeWithText("FEATURED").assertIsDisplayed()
        composeTestRule.onNodeWithTag("progress-indicator").assertDoesNotExist()
    }
}
