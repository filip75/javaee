package pl.tracks.resource.model;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Link {
    private URI href;
    private String method;
}
