import java.util.concurrent.Semaphore;

public class actividad2_1 {
    private int recursosDisponibles;
    private final Semaphore semaforo;

    public actividad2_1(int recursosTotales) {
        this.recursosDisponibles = recursosTotales;
        this.semaforo = new Semaphore(1); // Semáforo binario
    }

    public void reserva(int r) throws InterruptedException {
        semaforo.acquire(); // Adquirir el semáforo antes de acceder al recurso
        
        try {
            while (recursosDisponibles < r) {
                // Esperar hasta que haya suficientes recursos disponibles
                semaforo.release(); // Liberar el semáforo para que otros hilos puedan seguir
                Thread.sleep(100); // Cooldown antes de volver a intentarlo
                semaforo.acquire(); // Intentar adquirir el semáforo de nuevo
            }
            recursosDisponibles -= r; // Guardo recursos
        } finally {
            semaforo.release(); // liberar el semáforo
        }
    }

    public void libera(int l) {
        try {
            semaforo.acquire(); // Adquirir el semaforo antes de acceder al recurso
            recursosDisponibles += l; // Liberar recursos
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release(); // libero el semaforo
        }
    }
}
