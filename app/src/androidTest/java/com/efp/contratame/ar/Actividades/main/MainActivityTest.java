package com.efp.contratame.ar.Actividades.main;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.Manifest;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import com.efp.contratame.ar.R;
import com.efp.contratame.ar.misc.EspressoIdlingResource;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.repository.RequerimientoRepository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Random;

@RunWith(JUnit4.class)
@LargeTest
public class MainActivityTest {

    private EspressoIdlingResource espressoIdlingResource = EspressoIdlingResource.getInstance();
    private String idUsuario = "EwovJCClaEVZJuqM2Iua5R2F7Kx2";

    @Rule
    public final ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<MainActivity>(
                    new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), MainActivity.class)
                            .putExtra("idUsuario", idUsuario)
                        .putExtra("mail","tomaspeiretti@gmail.com")
                        .putExtra("nombre","")
                        .putExtra("foto","")
                        .putExtra("sesion","firebase")
            );

    @Rule
    public GrantPermissionRule permissionLocation = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

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

        //seleccionar "crear Requerimiento"
        onView(ViewMatchers.withId(R.id.btnCrearRequerimiento))
                .perform(click());
        //ingresar el titulo
        String titulo = "TEST - titulo"+ new Random().nextInt();
        onView(ViewMatchers.withId(R.id.titulo_edit_text))
                .perform(typeText(titulo));
        //ingresar descripcion
        String desc = "TEST TEST TEST TEST";
        onView(ViewMatchers.withId(R.id.descripcion_edit_text))
                .perform(typeText(desc));
        // TODO datepicker?

        onView(withText(titulo)).check(matches(isDisplayed()));
        onView(withText(desc)).check(matches(isDisplayed()));
        onView(withId(R.id.spinnerRurbos)).check(matches(isDisplayed()));
        onView(withId(R.id.button_agregar_foto)).check(matches(isEnabled()));
        onView(withId(R.id.google_map)).check(matches(isEnabled()));

        // bajar y guardar requerimiento
        onView(ViewMatchers.withId(R.id.button_publicar))
                .perform(scrollTo(), click());

        // chequear que se guardo el requerimiento
        EspressoIdlingResource.getInstance().increment();
        RequerimientoRepository.createInstance().getAllRequerimientosFrom(idUsuario,
                new RequerimientoDataSource.GetAllRequerimientosFromCallback() {
                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onResult(List<Requerimiento> requerimientoList) {
                        Assert.assertEquals(1,requerimientoList.stream().filter(r -> r.getTitulo().equals(titulo)).count());
                        EspressoIdlingResource.getInstance().decrement();
                    }
                });

        // ver que volvio al menu ppal
        onView(withId(R.id.rvServicios))
                .check(matches(isDisplayed()));
    }

}
