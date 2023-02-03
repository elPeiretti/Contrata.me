package com.efp.contratame.ar.Actividades;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.efp.contratame.ar.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CrearCuentaTest {

    public ActivityScenario<CrearCuenta> activityScenario;

    @Before
    public void setup(){
        activityScenario = ActivityScenario.launchActivityForResult(CrearCuenta.class);
        activityScenario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void testLlenarCamposComoCliente(){

        String mail = "esteEsUnMail@gmail.com";
        String pass = "123456789";

        //ingresar email y contrasena
        onView(withId(R.id.txt_EmaillCrear)).perform(typeText(mail));
        onView(withId(R.id.txt_contrasenaCrear)).perform(typeText(pass));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.rb_cliente)).perform(click());
        onView(withId(R.id.cb_terminos)).perform(click());

        onView(withText(mail)).check(matches(isDisplayed()));
        onView(withText(pass)).check(matches(isDisplayed()));
        onView(withId(R.id.rb_cliente)).check(matches(isChecked()));
        onView(withId(R.id.cb_terminos)).check(matches(isChecked()));
    }

    @Test
    public void testLlenarCamposComoPrestador(){
        String mail = "esteEsUnMail@gmail.com";
        String pass = "123456789";

        //ingresar email y contrasena
        onView(withId(R.id.txt_EmaillCrear)).perform(typeText(mail));
        onView(withId(R.id.txt_contrasenaCrear)).perform(typeText(pass));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.rb_prestador)).perform(click());
        onView(withId(R.id.cb_terminos)).perform(click());

        onView(withText(mail)).check(matches(isDisplayed()));
        onView(withText(pass)).check(matches(isDisplayed()));
        onView(withId(R.id.rb_prestador)).check(matches(isChecked()));
        onView(withId(R.id.cb_terminos)).check(matches(isChecked()));
    }
}