package pl.tracks.resource.model;

import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import java.util.Map;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class EmbeddedResource<V> {
    @Singular
    @JsonbProperty("_links")
    private Map<String, Link> links;

    @Singular("embedded")
    @JsonbProperty("_embedded")
    private Map<String, V> embedded;

    @Builder
    private EmbeddedResource(Map<String, Link> links, Map<String, V> embedded) {
        if (embedded.size() > 1) {
            throw new IllegalArgumentException("There can be only one embedded object.");
        }
        this.links = links;
        this.embedded = embedded;
    }
}
