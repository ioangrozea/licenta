package licenta.entity;

import java.util.Collection;

public interface LombokAddDelegetor<E> {
    boolean add(E e);
    boolean addAll(Collection<? extends E> c);
}
