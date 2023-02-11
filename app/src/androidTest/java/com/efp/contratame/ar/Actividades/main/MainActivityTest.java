package com.efp.contratame.ar.Actividades.main;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;

import com.efp.contratame.ar.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@LargeTest
public class MainActivityTest {

    public ActivityScenario<MainActivity> activityScenario;

    @Before
    public void setup(){
        activityScenario = ActivityScenario.launchActivityForResult(MainActivity.class);
        activityScenario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void testCrearRequerimiento(){
    }

}
