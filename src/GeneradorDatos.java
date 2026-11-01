import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GeneradorDatos {
    public static void generarArchivo(int n, int k, String nombreArchivo, long semilla, int maxValor) throws IOException {
        if (n < k) {
            throw new IllegalArgumentException("n debe ser mayor o igual a k (n >= k)");
        }

        Random rand = new Random(semilla);

        int[] tamanos = new int[k];
        Arrays.fill(tamanos, 1); // Asignar al menos 1 elemento a cada arreglo, le pone 1 a todos los arreglos
        int faltantes = n - k;

        for (int i = 0; i < faltantes; i++) {
            int idx = rand.nextInt(k);
            tamanos[idx]++;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Primera línea: n k
            bw.write(n + " " + k);
            bw.newLine();

            for (int i = 0; i < k; i++) {
                int[] arr = new int[tamanos[i]];

                for (int j = 0; j < tamanos[i]; j++) {
                    arr[j] = rand.nextInt(maxValor) + 1; // Enteros positivos
                }

                // Ordenar individualmente el arreglo antes de escribirlo
                Arrays.sort(arr);

                // Escribir elementos separados por espacios finalizando en -1
                StringBuilder sb = new StringBuilder(); //StringBUilder: Utilizado en poo en persistencia de archivos.. append es como el texto completo de 1 conjunto y recien cuando se recooren todos los datos este es escrito en el txt
                for (int val : arr) {
                    sb.append(val).append(" ");
                }
                sb.append("-1");

                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }
}