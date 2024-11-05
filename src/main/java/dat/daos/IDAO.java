package dat.daos;

import java.util.List;
import java.util.Set;

public interface IDAO<T> {

    Set<T> readAll();
    T read(int id);
    T create(T entity);
    T update(int id, T entity);
    void delete(int id);

}
