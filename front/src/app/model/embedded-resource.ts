import {Link} from "./link";

export class EmbeddedResource<V> {
  _embedded: Map<String, V>;
  _links: Map<String, Link>;
}
