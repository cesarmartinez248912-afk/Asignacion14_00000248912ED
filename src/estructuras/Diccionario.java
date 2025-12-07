package estructuras;

/**
 * Interfaz para diccionario de tipo clave-valor
 */
public interface Diccionario<K, V> {

    void put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    int size();

    boolean isEmpty();
}