package game.repository;

import game.domain.Entity;

public interface Repository<TID, E extends Entity<TID>> {
    void add(E elem);
    void delete(TID id);
    void update(E elem, TID id);
    E findById(TID id);
    Iterable<E> findAll();
}
