package redes;

import estructuras.TablaHash;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabla de ruteo implementada con HashMap personalizado
 */
public class TablaRuteo {
    private TablaHash<String, Ruta> tabla;
    private int consultasExitosas;
    private int consultasFallidas;

    public TablaRuteo() {
        this.tabla = new TablaHash<>();
        this.consultasExitosas = 0;
        this.consultasFallidas = 0;
    }

    /**
     * Agrega o actualiza una ruta en la tabla
     * Aplica logica de menor metrica
     */
    public void agregarRuta(Ruta ruta) {
        String clave = ruta.getRedDestino();

        if (tabla.containsKey(clave)) {
            Ruta rutaExistente = tabla.get(clave);
            if (ruta.getMetrica() < rutaExistente.getMetrica()) {
                tabla.put(clave, ruta);
                System.out.println("[UPDATE] Ruta mejorada: " + clave);
            }
        } else {
            tabla.put(clave, ruta);
            System.out.println("[ADD] Nueva ruta: " + clave);
        }
    }

    /**
     * Busca la ruta apropiada para una IP destino
     */
    public Ruta buscarRuta(String ipDestino) {
        List<String> redesOrdenadas = obtenerRedesOrdenadas();

        for (String red : redesOrdenadas) {
            if (ipPerteneceARed(ipDestino, red)) {
                consultasExitosas++;
                return tabla.get(red);
            }
        }

        if (tabla.containsKey("0.0.0.0/0")) {
            consultasExitosas++;
            return tabla.get("0.0.0.0/0");
        }

        consultasFallidas++;
        return null;
    }

    /**
     * Elimina una ruta de la tabla
     */
    public boolean eliminarRuta(String redDestino) {
        if (tabla.containsKey(redDestino)) {
            tabla.remove(redDestino);
            System.out.println("[DELETE] Ruta eliminada: " + redDestino);
            return true;
        }
        return false;
    }

    /**
     * Obtiene las redes ordenadas por especificidad
     */
    private List<String> obtenerRedesOrdenadas() {
        List<String> redes = new ArrayList<>();

        if (tabla.containsKey("192.168.1.0/24")) redes.add("192.168.1.0/24");
        if (tabla.containsKey("172.16.0.0/16")) redes.add("172.16.0.0/16");
        if (tabla.containsKey("10.0.0.0/8")) redes.add("10.0.0.0/8");

        return redes;
    }

    /**
     * Verifica si una IP pertenece a una red
     */
    private boolean ipPerteneceARed(String ip, String red) {
        if (red.equals("0.0.0.0/0")) {
            return true;
        }

        String[] partesIp = ip.split("\\.");
        String[] partesRed = red.split("/")[0].split("\\.");

        if (red.contains("/24")) {
            return partesIp[0].equals(partesRed[0]) &&
                    partesIp[1].equals(partesRed[1]) &&
                    partesIp[2].equals(partesRed[2]);
        } else if (red.contains("/16")) {
            return partesIp[0].equals(partesRed[0]) &&
                    partesIp[1].equals(partesRed[1]);
        } else if (red.contains("/8")) {
            return partesIp[0].equals(partesRed[0]);
        }

        return false;
    }

    /**
     * Imprime estadisticas de la tabla
     */
    public void imprimirEstadisticas() {
        System.out.println("\nESTADISTICAS DE LA TABLA DE RUTEO:");
        System.out.println("- Total de rutas activas: " + tabla.size());
        System.out.println("- Consultas exitosas: " + consultasExitosas);
        System.out.println("- Consultas fallidas: " + consultasFallidas);

        int totalConsultas = consultasExitosas + consultasFallidas;
        double tasaExito = totalConsultas > 0 ? (consultasExitosas * 100.0 / totalConsultas) : 0;
        System.out.println("- Tasa de exito: " + String.format("%.1f", tasaExito) + "%");
        System.out.println("- Factor de carga del HashMap: " + String.format("%.2f", tabla.getFactorCarga()));
    }

    public int getTotalRutas() {
        return tabla.size();
    }
}