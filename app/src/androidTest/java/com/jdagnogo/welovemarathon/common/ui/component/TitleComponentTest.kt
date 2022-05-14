package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import org.junit.Rule
import org.junit.Test

class TitleComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenNoLeftIconTitleShouldNotDisplayTheLeftIcon() {
        composeTestRule.setContent {
            WeLoveMarathonTheme {
                TitleComponent(
                    iconLeft = null,
                    title = "Title"
                )
            }
        }

        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("iconRight").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("iconLeft").assertDoesNotExist()
    }

    @Test
    fun givenNoRightIconTitleShouldNotDisplayTheRightIcon() {
        composeTestRule.setContent {
            WeLoveMarathonTheme {
                TitleComponent(
                    iconRight = null,
                    title = "Title"
                )
            }
        }

        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("iconRight").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("iconLeft").assertIsDisplayed()
    }

    @Test
    fun givenAnRightIconTitleShouldDisplayThisRightIcon() {
        val expected = R.drawable.ic_wlm_logo
        composeTestRule.setContent {
            WeLoveMarathonTheme {
                TitleComponent(
                    iconRight = expected,
                    title = "Title"
                )
            }
        }

        composeTestRule.onNode(hasDrawable(expected)).assertIsDisplayed()
    }
}
