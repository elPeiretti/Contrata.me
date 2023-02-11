package com.efp.contratame.ar.Actividades.main;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.efp.contratame.ar.R;
import com.efp.contratame.ar.auxiliares.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@LargeTest
public class MainActivityTest {

    private EspressoIdlingResource espressoIdlingResource = EspressoIdlingResource.getInstance();

    @Rule
    public final ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<MainActivity>(
                    new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), MainActivity.class)
                            .putExtra("idUsuario","EwovJCClaEVZJuqM2Iua5R2F7Kx2")
                        .putExtra("mail","tomaspeiretti@gmail.com")
                        .putExtra("nombre","")
                        .putExtra("foto","")
                        .putExtra("sesion","firebase")
            );

    @Before
    public void setup(){
        IdlingRegistry.getInstance().register(espressoIdlingResource.getCountingIdlingResource());
        ActivityScenario<MainActivity> activityScenario = mActivityScenarioRule.getScenario();
        activityScenario.moveToState(Lifecycle.State.RESUMED);
    }

    @After
    public void finish(){
        IdlingRegistry.getInstance().unregister(espressoIdlingResource.getCountingIdlingResource());
    }


    @Test
    public void testCrearRequerimiento(){
        //seleccionar el primer TipoServicio
        onView(ViewMatchers.withId(R.id.rvServicios))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
    }

}
