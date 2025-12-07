package test;

import estructuras.TablaHash;

public class TestTablaHash {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas de TablaHash\n");

        testBasico();
        testActualizar();
        testEliminar();
        testResize();
        testColisiones();
        testVarios();

        System.out.println("\nPruebas finalizadas");
    }

    private static void testBasico() {
        System.out.println("Test 1: Operaciones basicas");
        TablaHash<String, Integer> mapa = new TablaHash<>();

        mapa.put("manzana", 5);
        mapa.put("pera", 3);
        mapa.put("naranja", 7);

        System.out.println("Elementos insertados: 3");
        System.out.println("Size: " + mapa.size());
        System.out.println("manzana: " + mapa.get("manzana"));
        System.out.println("pera: " + mapa.get("pera"));
        System.out.println("naranja: " + mapa.get("naranja"));
        System.out.println("uva (no existe): " + mapa.get("uva"));
        System.out.println();
    }

    private static void testActualizar() {
        System.out.println("Test 2: Actualizacion");
        TablaHash<String, Integer> mapa = new TablaHash<>();

        mapa.put("producto", 100);
        System.out.println("Valor inicial: " + mapa.get("producto"));

        mapa.put("producto", 200);
        System.out.println("Valor actualizado: " + mapa.get("producto"));
        System.out.println("Size sigue en 1: " + mapa.size());
        System.out.println();
    }

    private static void testEliminar() {
        System.out.println("Test 3: Eliminacion");
        TablaHash<String, Integer> mapa = new TablaHash<>();

        mapa.put("a", 1);
        mapa.put("b", 2);
        mapa.put("c", 3);

        System.out.println("Size inicial: " + mapa.size());

        Integer borrado = mapa.remove("b");
        System.out.println("Valor eliminado de b: " + borrado);
        System.out.println("Size despues: " + mapa.size());
        System.out.println("Buscar b: " + mapa.get("b"));
        System.out.println("containsKey(b): " + mapa.containsKey("b"));
        System.out.println("containsKey(a): " + mapa.containsKey("a"));

        Integer noExiste = mapa.remove("z");
        System.out.println("Eliminar z inexistente: " + noExiste);
        System.out.println();
    }

    private static void testResize() {
        System.out.println("Test 4: Factor de carga y resize");
        TablaHash<Integer, String> mapa = new TablaHash<>();

        System.out.println("Capacidad inicial: " + mapa.getCapacidad());
        System.out.println("Factor inicial: " + String.format("%.2f", mapa.getFactorCarga()));

        for (int i = 1; i <= 15; i++) {
            mapa.put(i, "valor" + i);
            if (i == 8 || i == 9 || i == 15) {
                System.out.println("Con " + i + " elementos:");
                System.out.println("  Capacidad: " + mapa.getCapacidad());
                System.out.println("  Factor: " + String.format("%.2f", mapa.getFactorCarga()));
            }
        }

        System.out.println("Size final: " + mapa.size());
        System.out.println();
    }

    private static void testColisiones() {
        System.out.println("Test 5: Colisiones");
        TablaHash<String, Integer> mapa = new TablaHash<>();

        mapa.put("gato", 1);
        mapa.put("casa", 7);
        mapa.put("sol", 3);
        mapa.put("luna", 4);
        mapa.put("mar", 5);

        System.out.println("Insertados 5 elementos");
        System.out.println(mapa);

        System.out.println("Probando acceso:");
        System.out.println("gato: " + mapa.get("gato"));
        System.out.println("casa: " + mapa.get("casa"));
        System.out.println("sol: " + mapa.get("sol"));
        System.out.println("luna: " + mapa.get("luna"));
        System.out.println("mar: " + mapa.get("mar"));
        System.out.println();
    }

    private static void testVarios() {
        System.out.println("Test 6: Casos varios");
        TablaHash<String, String> mapa = new TablaHash<>();

        System.out.println("isEmpty en tabla vacia: " + mapa.isEmpty());
        System.out.println("size en tabla vacia: " + mapa.size());

        mapa.put("clave", "valor");
        System.out.println("isEmpty despues de insertar: " + mapa.isEmpty());

        mapa.put("claveNull", null);
        System.out.println("Insertado valor null: " + mapa.get("claveNull"));

        mapa.remove("clave");
        mapa.remove("claveNull");
        System.out.println("isEmpty tras eliminar todo: " + mapa.isEmpty());

        System.out.println("get en tabla vacia: " + mapa.get("nada"));

        System.out.println("\nPrueba con 100 elementos:");
        TablaHash<Integer, String> grande = new TablaHash<>();

        for (int i = 0; i < 100; i++) {
            grande.put(i, "valor" + i);
        }

        System.out.println("Insertados: 100");
        System.out.println("Size: " + grande.size());
        System.out.println("Capacidad final: " + grande.getCapacidad());
        System.out.println("Factor: " + String.format("%.2f", grande.getFactorCarga()));

        System.out.println("Elemento 0: " + grande.get(0));
        System.out.println("Elemento 50: " + grande.get(50));
        System.out.println("Elemento 99: " + grande.get(99));
        System.out.println();
    }
}