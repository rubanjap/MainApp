package com.ruban.mainapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.findmyip.presentation.MainActivity
import com.ruban.mainapp.presentation.LaunchButton
import com.ruban.mainapp.ui.theme.MainAppTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainAppUiTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private val buttonText = "launch FindMyIP Library"

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun isNavigateLibraryModule() {

        composeTestRule.setContent {
            MainAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchButton(buttonText)
                }
            }
            composeTestRule.onNodeWithText(buttonText).performClick()

            composeTestRule.waitForIdle()
            val activityScenario = ActivityScenario.launch(MainActivity::class.java)
            assertNotNull(activityScenario)
            activityScenario?.onActivity {
                assertEquals(MainActivity::class.java, it.javaClass)
            }
        }
    }
}