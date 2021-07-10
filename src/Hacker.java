import java.util.concurrent.ExecutorService;

public class Hacker implements Runnable {
    String sifraP;
    String originalnaP;
    long kljuc;
    long n;
    int duzina;
    long[] niz;
    ExecutorService executorService;

    public Hacker(String sifraP, String originalnaP, long kljuc, long n, int duzina, ExecutorService executorService) {
        this.sifraP = sifraP;
        this.originalnaP = originalnaP;
        this.kljuc = kljuc;
        this.n = n;
        this.duzina = duzina;
        this.niz = new long[duzina];
        this.executorService = executorService;
    }

    @Override
    public void run() {
        String[] arrOfStr = sifraP.split(",", duzina);
        for (int i = 0; i < arrOfStr.length; i++) {
            long character = Long.parseLong(arrOfStr[i]);
            niz[i] = character;
        }
        String poruka = "";
        for (int i = 0; i < duzina; i++) {
            long txt = RSA(niz[i], kljuc, n);
            char znak = (char) txt;
            poruka = poruka + znak;
        }

        if (poruka.equals(originalnaP)) {
            System.out.println("Poruka je nadjena sa kljucem: " + kljuc);
            System.out.println("Poruku je dekriptovao haker sa imenom: " + Thread.currentThread().getName());
            System.out.println("Dekrpitovana poruka je: " + poruka);

            executorService.shutdownNow();
        }
    }

    private long RSA(long msg, long e, long n) {
        return Numerical.power(msg, e, n);
    }

}
