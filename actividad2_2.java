public class actividad2_2 {

    public static void main(String[] args) {
        Puente puente = new Puente();

        // Crear y empezar coches
        Coche coche1 = new Coche(puente, "norte", 1);
        Coche coche2 = new Coche(puente, "sur", 2);
        Coche coche3 = new Coche(puente, "norte", 3);
        Coche coche4 = new Coche(puente, "sur", 4);
        Coche coche5 = new Coche(puente, "sur", 5);
        

        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();
        coche5.start();
        
    }
    
    static class Puente {
        private int cochesNorte;
        private int cochesSur;
        private int cochesEsperandoNorte;
        private int cochesEsperandoSur;

        public Puente() {
            cochesNorte = 0;
            cochesSur = 0;
            cochesEsperandoNorte = 0;
            cochesEsperandoSur = 0;
        }

        public synchronized void entrarNorte(int id) throws InterruptedException {
            cochesEsperandoNorte++;
            while (cochesSur > 0 || (cochesEsperandoSur > 0 && cochesEsperandoNorte == 1)) {
                System.out.println("Coche con id " + id + " del NORTE está esperando.");
                wait();
            }
            cochesEsperandoNorte--;
            cochesNorte++;
            System.out.println("Coche con id " + id + " del NORTE está cruzando el puente.");
        }

        public synchronized void salirNorte(int id) {
            cochesNorte--;
            if (cochesNorte == 0) {
                notifyAll();
            }
            System.out.println("Coche con id " + id + " del NORTE ha terminado de cruzar el puente.");
        }

        public synchronized void entrarSur(int id) throws InterruptedException {
            cochesEsperandoSur++;
            while (cochesNorte > 0 || (cochesEsperandoNorte > 0 && cochesEsperandoSur == 1)) {
                System.out.println("Coche con id " + id + " del SUR está esperando.");
                wait();
            }
            cochesEsperandoSur--;
            cochesSur++;
            System.out.println("Coche con id " + id + " del SUR está cruzando el puente.");
        }

        public synchronized void salirSur(int id) {
            cochesSur--;
            if (cochesSur == 0) {
                notifyAll();
            }
            System.out.println("Coche con id " + id + " del SUR ha terminado de cruzar el puente.");
        }
    }

    static class Coche extends Thread {
        private final Puente puente;
        private final String direccion;
        private final int id;

        public Coche(Puente puente, String direccion, int id) {
            this.puente = puente;
            this.direccion = direccion;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                if (direccion.equals("norte")) {
                    puente.entrarNorte(id);
                    // Simular el cruce del puente
                    Thread.sleep(1000);
                    puente.salirNorte(id);
                } else if (direccion.equals("sur")) {
                    puente.entrarSur(id);
                    // Simular el cruce del puente
                    Thread.sleep(1000);
                    puente.salirSur(id);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
