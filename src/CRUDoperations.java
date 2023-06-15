public interface CRUDoperations<T> {
    void create(T item);
    T read(String id);
    void update(T item);
    void delete(String id);
}
