package de.kloen.k400.db;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
public class Karma {

    public static final Map<String, Range> titles = ImmutableMap.<String, Range>builder()
            .put("Retter der Verdammten", Range.atLeast(1000))
            .put("Verteidiger des Ödlandes", Range.closed(750, 999))
            .put("Schild der Hoffnung", Range.closed(500, 749))
            .put("Verteidiger", Range.closed(250, 499))
            .put("Wanderer", Range.closed(-249, 250))
            .put("Dämonenbrut", Range.atMost(-1000))
            .put("Geissel des Ödlandes", Range.closed(-999, -750))
            .put("Schwert der Verzweiflung", Range.closed(-749, -500))
            .put("Verräter", Range.closed(-499, -250))
            .build();

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
        return titles.entrySet()
                .stream()
                .filter(range -> range.getValue().contains(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No title for karma value: " + this.value))
                .getKey();

    }
}
