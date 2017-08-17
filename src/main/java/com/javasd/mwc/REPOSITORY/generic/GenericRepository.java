package com.javasd.mwc.REPOSITORY.generic;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import java.util.List;

public interface GenericRepository<T extends DomainObject> {

    public T get(Long id);
    public List<T> getAll();
    public T save(T object);
    public T update(T object);
    public void delete(Long id);

}
