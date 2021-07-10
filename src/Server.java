import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private long d, e, n;

    public Server() throws Exception {

        ServerSocket ss = new ServerSocket(2021);

        while (true) {

            Socket socket = ss.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            generisiRSAkljuceve();

            out.println(e);
            out.println(n);

            String sifraP = "";
            String originalnaP = "";
            String duzina_string = in.readLine();
            int duzina = Integer.parseInt(duzina_string);
            long[] niz;
            niz = new long[duzina];
            for (int i = 0; i < duzina; i++) {
                String poruka_string = in.readLine();
                long poruka_long = Long.parseLong(poruka_string);
                niz[i] = poruka_long;

            }
            for (int i = 0; i < niz.length; i++) {
                int flag = i;
                if (flag == duzina - 1) {
                    sifraP = sifraP + niz[i];
                } else {
                    sifraP = sifraP + niz[i] + ",";
                }
            }

            for (int i = 0; i < duzina; i++) {
                long txt = RSA(niz[i], d, n);
                char znak = (char) txt;
                originalnaP = originalnaP + znak;
            }

            ExecutorService hakeri = Executors.newFixedThreadPool(4);
            AtomicInteger kljuc = new AtomicInteger(0);

            long brojac = 0;
            while (true) {
                hakeri.submit(new Hacker(sifraP, originalnaP, kljuc.addAndGet(1), n, duzina, hakeri));

                if (brojac % 50000 == 0) {
                    System.out.println(kljuc);
                }
                brojac++;

                if(hakeri.isShutdown()){
                    break;
                }
            }
            ss.close();
        }
    }

    private long RSA(long msg, long e, long n) {
        return Numerical.power(msg, e, n);
    }


    private void generisiRSAkljuceve() {

        long p = (long) (Math.random() * 2500);
        long q = (long) (Math.random() * 2500 );

        while (!Numerical.isPrime(p))
            p++;

        while (!Numerical.isPrime(q))
            q--;

        n = p * q;

        long fi = (p - 1) * (q - 1);

        e = (long) (Math.random() * (fi - 2) + 2);

        while (Numerical.gcd(e, fi) != 1)
            e++;

        d = Numerical.inverse(e, fi);
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }

}

