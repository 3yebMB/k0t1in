package dev.m13d.k0t1in.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.note.NoteActivity
import dev.m13d.k0t1in.viewmodel.main.MainViewModel
import dev.m13d.k0t1in.viewmodel.main.MainViewState
import dev.m13d.k0t1in.viewmodel.note.NoteViewModel
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

class MainActivityTest {
    private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE_ID"
    private val viewModel: MainViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<MainViewState>()
    private val testNotes = listOf(Note("333", "title", "body"),
            Note("444", "title1", "body1"),
            Note("555", "title2", "body2"))

    private val testModules = module {
        single { viewModel }
        single { mockk<NoteViewModel>(relaxed = true) }
    }

    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {

//        StandAloneContext.loadKoinModules(listOf(
//        module {
//                    viewModel { viewModel }
//                    viewModel { mockk<NoteViewModel>(relaxed = true) }
//                }))


        loadKoinModules(testModules)

        every { viewModel.getViewState() } returns viewStateLiveData

        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(MainViewState(notes = testNotes))
    }

    @Test
    fun check_data_is_displayed() {
        onView(withId(R.id.mainRecycler))
                .perform(scrollToPosition<MainAdapter.NoteViewHolder>(1))
        onView(withText(testNotes[1].note)).check(matches(isDisplayed()))
    }

    @Test
    fun check_detail_activity_intent_sent() {
        onView(withId(R.id.mainRecycler))
                .perform(actionOnItemAtPosition<MainAdapter.NoteViewHolder>(1, click()))

        intended(allOf(hasComponent(NoteActivity::class.java.name), hasExtra(EXTRA_NOTE, testNotes[1].id)))
    }

    @After
    fun tearDown() {
//        StandAloneContext.stopKoin()
        unloadKoinModules(testModules)
    }

}