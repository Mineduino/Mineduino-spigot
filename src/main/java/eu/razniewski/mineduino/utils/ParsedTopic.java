package eu.razniewski.mineduino.utils;

import java.util.Objects;
import java.util.Optional;

public class ParsedTopic {
    private String prefix;
    private String identifier;
    private String type;

    public ParsedTopic(String prefix, String identifier, String type) {
        this.prefix = prefix;
        this.identifier = identifier;
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }

    public static Optional<ParsedTopic> from(String topic) {

        String[] data = topic.split("/");
        if(data.length < 3) {
            return Optional.empty();
        }
        return Optional.of(new ParsedTopic(data[0], data[1], data[2]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedTopic that = (ParsedTopic) o;
        return Objects.equals(prefix, that.prefix) &&
                Objects.equals(identifier, that.identifier) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, identifier, type);
    }

    @Override
    public String toString() {
        return "ParsedTopic{" +
                "prefix='" + prefix + '\'' +
                ", identifier='" + identifier + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
