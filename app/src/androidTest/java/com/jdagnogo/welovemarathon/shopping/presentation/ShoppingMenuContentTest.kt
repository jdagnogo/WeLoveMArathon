package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.submenu.SubMenuInteractions
import com.jdagnogo.welovemarathon.common.ui.component.hasDrawable
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
@ExperimentalMaterialApi
class ShoppingMenuContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screenNameShouldBeShopping() {
        val state = ShoppingState()

        composeTestRule.setContent {
            WeLoveMarathonTheme {
                ShoppingMenuContent(
                    state = state,
                    subMenuInteractions = SubMenuInteractions(
                        onBackPressed = {},
                        onItemSelected = {},
                        onMapSelected = {},
                    )
                )
            }
        }
        composeTestRule.onRoot().printToLog("Toto")
        composeTestRule.onNodeWithText("Shopping").assertIsDisplayed()
        composeTestRule.onNode(hasDrawable(R.drawable.ic_back)).assertIsDisplayed()
        composeTestRule.onNode(hasDrawable(R.drawable.map)).assertIsDisplayed()
    }
}
