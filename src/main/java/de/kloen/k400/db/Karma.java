package de.kloen.k400.db;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Karma {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    @OneToOne(optional = false)
    private K400User k400User;

    private int value;

    public Karma(K400User k400User, int value) {
        this.k400User = Preconditions.checkNotNull(k400User, "K400User can not be null.");
        this.value = value;
    }

    public Karma(K400User user) {
        this(user, 0);
    }

    public void addKarma(int karmaValue) {
        this.value += karmaValue;
    }

    public int value() {
        return value;
    }

    public K400User k400User() {
        return k400User;
    }

    public String title() {
        if (value >= 1000) {
            return "Retter der Verdammten";
        }
        if (between(value, 750, 999)) {
            return "Verteidiger des Ödlandes";
        }

        if (between(value, 500, 749)) {
            return "Schild der Hoffnung";
        }

        if (between(value, 250, 499)) {
            return "Verteidiger";
        }

        if (between(value, -249, 250)) {
            return "Wanderer";
        }

        if (value <= -1000) {
            return "Dämonenbrut";
        }

        if (between(value,  -999, -750)) {
            return "Geissel des Ödlandes";
        }

        if (between(value, -749, -500)) {
            return "Schwert der Verzweiflung";
        }

        if (between(value, -499, -250)) {
            return "Verräter";
        }

        throw new IllegalArgumentException("No title for karma value: " + value);
    }

    private static boolean between(int value, int min, int max) {
        return (value >= min && value <= max);
    }
}
