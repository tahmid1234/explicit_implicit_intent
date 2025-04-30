package com.example.assignment_02

import android.content.Context
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val BASIC_SAMPLE_PACKAGE = "com.example.assignment_02" // Replace with your actual package name
private const val LAUNCH_TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    private lateinit var device: UiDevice
    private lateinit var context: Context

    @Before
    fun setup() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        context = InstrumentationRegistry.getInstrumentation().targetContext

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        // Launch the app
        val intent = context.packageManager.getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE)?.apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    fun testStartExplicitActivityAndCheckChallenge() {
        // Click on the "Start Activity Explicitly" button
        val explicitButton: UiObject2 = device.wait(
            Until.findObject(By.text("Start Activity Explicitly")),
            LAUNCH_TIMEOUT
        )
        explicitButton.click()

        // Wait for the second activity to appear
        device.wait(
            Until.hasObject(By.text("Mobile Software Engineering Challenges")),
            LAUNCH_TIMEOUT
        )

        // Get the text of the second activity
        val secondActivityText: UiObject2? =
            device.findObject(By.text("Mobile Software Engineering Challenges"))

        // Assert that the "Mobile Software Engineering Challenges" text is displayed
        assertTrue("Second activity title is not displayed", secondActivityText != null)

        // Get the list of challenges from the SecondScreen composable
        val challenges = context.resources.getStringArray(R.array.challanges)
        println(challenges)
        println("***")

        //any
        val isAnyChallengeTitleDisplayed = challenges.any { challenge ->
            device.wait(Until.hasObject(By.text(" ${challenges.indexOf(challenge) + 1}. $challenge")), 2000) // Wait up to 2 seconds for each
        }
        assertTrue("At least one challenge title should be displayed", isAnyChallengeTitleDisplayed)
        //all
        val allChallengesDisplayed = challenges.all { challenge ->
            device.wait(Until.hasObject(By.text(" ${challenges.indexOf(challenge) + 1}. $challenge")), 5000) // Wait up to 5 seconds for each
        }
        assertTrue("All challenges should be displayed", allChallengesDisplayed)
    }
}