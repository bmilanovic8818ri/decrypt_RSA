import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    public Client() throws Exception {


        Socket socket = new Socket("localhost", 2021);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        String msg = "Password-lol123";
        String e_string = in.readLine();
        String n_string = in.readLine();
        long ee = Long.parseLong(e_string);
        long nn = Long.parseLong(n_string);

        //OVO JE TEST OVDE

        int duzina = msg.length();
        out.println(duzina);
        long[] niz;
        niz = new long[duzina];
        for (int i = 0; i < duzina; i++) {
            char character = msg.charAt(i);
            long ascii = (long) character;
            long cipher = RSA(ascii, ee, nn);
            niz[i] = cipher;
        }

        for (int i = 0; i < duzina; i++) {
            out.println(niz[i]);
        }

        socket.close();
    }

    private long RSA(long msg, long e, long n) {
        return Numerical.power(msg, e, n);
    }

    public static void main(String[] args) throws Exception {
        new Client();
    }

}