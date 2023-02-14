package org.acme;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface BookResource extends PanacheEntityResource<Book, Long> {
}
