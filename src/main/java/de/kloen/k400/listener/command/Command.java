package de.kloen.k400.listener.command;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class Command {

    private String value;

    private String description;

    public Command(String value, String description) {
        this.value = Preconditions.checkNotNull(value);
        this.description = Preconditions.checkNotNull(description);
    }

    public String value() {
        return value;
    }

    public String description() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equal(value, command.value) &&
                Objects.equal(description, command.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, description);
    }
}
