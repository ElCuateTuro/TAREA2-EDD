import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GeneradorDatos {

    /**
     * Genera un archivo de texto formateado con k arreglos ordenados aleatorios.
     *
     * @param n Total de elementos (n >= k)
     * @param k Cantidad de arreglos
     * @param nombreArchivo Nombre o ruta del archivo de salida
     * @param semilla Semilla para reproducir los resultados
     * @param maxValor Valor entero máximo permitido (ej. 1,000,000)
     */
    public static void generarArchivo(int n, int k, String nombreArchivo, long semilla, int maxValor) throws IOException {
        if (n < k) {
            throw new IllegalArgumentException("n debe ser mayor o igual a k (n >= k)");
        }

        Random rand = new Random(semilla);

        // 1. Distribuir n elementos entre k arreglos no vacíos
        int[] tamanos = new int[k];
        Arrays.fill(tamanos, 1); // Asignar al menos 1 elemento a cada arreglo
        int faltantes = n - k;

        for (int i = 0; i < faltantes; i++) {
            int idx = rand.nextInt(k);
            tamanos[idx]++;
        }

        // 2. Generar enteros aleatorios, ordenar cada arreglo y guardar en archivo
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
                StringBuilder sb = new StringBuilder();
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