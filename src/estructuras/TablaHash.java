package estructuras;

import java.util.LinkedList;

/**
 * Tabla hash con encadenamiento separado
 */
public class TablaHash<K, V> implements Diccionario<K, V> {

    private class Nodo<K, V> {
        K key;
        V value;

        public Nodo(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Nodo<K, V>>[] tabla;
    private int size;
    private int capacidad;
    private static final double FACTOR_CARGA_MAX = 0.75;

    /**
     * Constructor que inicializa la tabla con capacidad 11
     */
    @SuppressWarnings("unchecked")
    public TablaHash() {
        this.capacidad = 11;
        this.tabla = new LinkedList[capacidad];
        this.size = 0;

        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    /**
     * Calcula el índice en el arreglo
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacidad;
    }

    /**
     * Inserta o actualiza un par clave-valor
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("La clave no puede ser null");
        }

        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                nodo.value = value;
                return;
            }
        }

        lista.add(new Nodo<>(key, value));
        size++;

        if ((double) size / capacidad >= FACTOR_CARGA_MAX) {
            resize();
        }
    }

    /**
     * Obtiene el valor asociado a una clave
     */
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }

        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                return nodo.value;
            }
        }

        return null;
    }

    /**
     * Elimina un elemento por su clave
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }

        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (int i = 0; i < lista.size(); i++) {
            Nodo<K, V> nodo = lista.get(i);
            if (nodo.key.equals(key)) {
                lista.remove(i);
                size--;
                return nodo.value;
            }
        }

        return null;
    }

    /**
     * Verifica si existe una clave
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Retorna el número de elementos
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Redimensiona la tabla cuando se supera el factor de carga
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<Nodo<K, V>>[] tablaVieja = tabla;
        capacidad = capacidad * 2;
        tabla = new LinkedList[capacidad];

        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }

        size = 0;
        for (LinkedList<Nodo<K, V>> bucket : tablaVieja) {
            for (Nodo<K, V> nodo : bucket) {
                put(nodo.key, nodo.value);
            }
        }
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getFactorCarga() {
        return (double) size / capacidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TablaHash [size: ").append(size)
                .append(", capacidad: ").append(capacidad)
                .append(", factor: ").append(String.format("%.2f", getFactorCarga()))
                .append("]\n");

        for (int i = 0; i < capacidad; i++) {
            if (!tabla[i].isEmpty()) {
                sb.append("[").append(i).append("]: ");
                for (Nodo<K, V> nodo : tabla[i]) {
                    sb.append("(").append(nodo.key).append(",").append(nodo.value).append(") ");
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}