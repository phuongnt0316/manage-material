package vn.com.devmaster.services.managematerial.mapper;

import java.util.List;

public interface EntityMapper<E, D>{
    public E toEntity(D d);
    public List<E> toEntity(List<D> d);
    D toDto(E e);
    List<D> toDto(List<E> e);
}
