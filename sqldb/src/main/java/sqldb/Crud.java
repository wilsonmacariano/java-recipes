package sqldb;

import java.util.List;

public interface Crud<T> {

    T add(T t);

    List<T> all();

}
