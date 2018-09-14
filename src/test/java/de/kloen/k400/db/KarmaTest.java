package de.kloen.k400.db;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(DataProviderRunner.class)
public class KarmaTest {

    @Test
    @UseDataProvider("karmaValueAndTitles")
    public void title(int karmaValue, String title) {
        Karma karma = new Karma(new K400User(), karmaValue);

        assertThat(karma.title()).isEqualTo(title);
    }

    @DataProvider
    public static Object[][] karmaValueAndTitles() {
        return new Object[][]{
                {0, "Wanderer"}, {-1, "Wanderer"}, {-249, "Wanderer"}, {249, "Wanderer"},
                {250, "Verteidiger"}, {499, "Verteidiger"},
                {500, "Schild der Hoffnung"}, {749, "Schild der Hoffnung"},
                {750, "Verteidiger des Ödlandes"}, {999, "Verteidiger des Ödlandes"},
                {1000, "Retter der Verdammten"}, {2000, "Retter der Verdammten"},
                {-250, "Verräter"}, {-251, "Verräter"},
                {-500, "Schwert der Verzweiflung"}, {-749, "Schwert der Verzweiflung"},
                {-750, "Geissel des Ödlandes"}, {-999, "Geissel des Ödlandes"},
                {-1000, "Dämonenbrut"}, {-2000, "Dämonenbrut"}
        };
    }
}