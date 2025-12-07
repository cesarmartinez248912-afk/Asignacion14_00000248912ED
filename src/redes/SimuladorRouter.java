package redes;

/**
 * Simulador de router que procesa paquetes
 */
public class SimuladorRouter {
    private TablaRuteo tablaRuteo;

    public SimuladorRouter() {
        this.tablaRuteo = new TablaRuteo();
        inicializarRutas();
    }

    /**
     * Inicializa la tabla con rutas tipicas de un router empresarial
     */
    private void inicializarRutas() {
        tablaRuteo.agregarRuta(new Ruta(
                "0.0.0.0/0", "0.0.0.0",
                "200.100.50.1", "eth0", 10, "STATIC"
        ));

        tablaRuteo.agregarRuta(new Ruta(
                "192.168.1.0/24", "255.255.255.0",
                "0.0.0.0", "eth1", 0, "CONNECTED"
        ));

        tablaRuteo.agregarRuta(new Ruta(
                "10.0.0.0/8", "255.0.0.0",
                "192.168.1.254", "eth1", 20, "OSPF"
        ));

        tablaRuteo.agregarRuta(new Ruta(
                "172.16.0.0/16", "255.255.0.0",
                "192.168.1.253", "eth2", 5, "STATIC"
        ));
    }

    /**
     * Simula el procesamiento de paquetes entrantes
     */
    public void procesarPaquetes() {
        String[] paquetesEntrantes = {
                "192.168.1.50",
                "10.5.3.2",
                "172.16.10.5",
                "8.8.8.8",
                "192.168.2.1",
                "10.0.0.1"
        };

        System.out.println("\nSIMULADOR DE ROUTER - FORWARDING\n");

        for (String ipDestino : paquetesEntrantes) {
            Ruta ruta = tablaRuteo.buscarRuta(ipDestino);

            if (ruta != null) {
                System.out.println("Paquete -> " + ipDestino);
                System.out.println("Ruta encontrada: " + ruta.getRedDestino());

                if (ruta.getNextHop().equals("0.0.0.0")) {
                    System.out.println("Next Hop: 0.0.0.0 (directamente conectado)");
                } else {
                    System.out.println("Next Hop: " + ruta.getNextHop());
                }

                System.out.println("Interfaz salida: " + ruta.getInterfaz());
                System.out.println("Protocolo: " + ruta.getProtocolo() +
                        " (metrica: " + ruta.getMetrica() + ")");
                System.out.println("ACCION: FORWARD\n");
            } else {
                System.out.println("Paquete -> " + ipDestino);
                System.out.println("ACCION: DROP (No route to host)\n");
            }
        }

        tablaRuteo.imprimirEstadisticas();
    }

    public static void main(String[] args) {
        SimuladorRouter simulador = new SimuladorRouter();
        simulador.procesarPaquetes();
    }
}