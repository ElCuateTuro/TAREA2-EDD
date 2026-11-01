import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String nombreArchivo = "N2/src/entrada.txt";
        int n = 10000;
        int k = 10;
        long semilla = 20260515L; // Semilla de prueba
        int maxValor = 1_000_000;// Ruta del archivo de entrada a probar


        System.out.println("===== SISTEMA DE HEAPS Y BINOMIAL HEAPS (MIN) =====");

        try {
            // 1. Generar los datos aleatorios en el archivo txt
            System.out.println("Generando datos...");
            GeneradorDatos.generarArchivo(n, k, nombreArchivo, semilla, maxValor);
            System.out.println("Archivo generado correctamente.\n");

            // 2. Leer el archivo generado y ejecutar la mezcla
            int[][] arreglos = leerArchivo(nombreArchivo);

            System.out.println("--- Evaluando k-MergeHeap ---");
            medirYEjecutarHeap(arreglos);

            System.out.println("\n--- Evaluando k-MergeCB ---");
            medirYEjecutarCB(arreglos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[][] leerArchivo(String ruta) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(ruta));

        if (!sc.hasNextInt()) {
            sc.close();
            return new int[0][0];
        }

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[][] arreglos = new int[k][];

        for (int i = 0; i < k; i++) {
            List<Integer> listaTemporal = new ArrayList<>();
            while (sc.hasNextInt()) {
                int valor = sc.nextInt();
                if (valor == -1) {
                    break;
                }
                listaTemporal.add(valor);
            }

            arreglos[i] = new int[listaTemporal.size()];
            for (int j = 0; j < listaTemporal.size(); j++) {
                arreglos[i][j] = listaTemporal.get(j);
            }
        }

        sc.close();
        return arreglos;
    }

    public static int[] kMergeHeap(int[][] arreglos) {
        int k = arreglos.length;
        int n = 0;
        for (int[] arr : arreglos) n += arr.length;

        int[] B = new int[n];
        Heap heap = new Heap(k);

        // Insertar el primer elemento de cada arreglo no vacío
        for (int i = 0; i < k; i++) {
            if (arreglos[i].length > 0) {
                heap.insert(new ElementoMezcla(arreglos[i][0], i, 1));
            }
        }

        // Extraer el mínimo e insertar el siguiente del mismo arreglo
        for (int j = 0; j < n; j++) {
            ElementoMezcla min = heap.extractMin();
            B[j] = min.valor;

            if (min.iSiguiente < arreglos[min.idArreglo].length) {
                heap.insert(new ElementoMezcla(arreglos[min.idArreglo][min.iSiguiente], min.idArreglo, min.iSiguiente + 1));
            }
        }

        return B;
    }

    public static int[] kMergeCB(int[][] arreglos) {
        int k = arreglos.length;
        int n = 0;
        for (int[] arr : arreglos) n += arr.length;

        int[] B = new int[n];
        BinomialHeap heap = new BinomialHeap();

        for (int i = 0; i < k; i++) {
            if (arreglos[i].length > 0) {
                heap.insert(new ElementoMezcla(arreglos[i][0], i, 1));
            }
        }

        for (int j = 0; j < n; j++) {
            ElementoMezcla min = heap.extractMin();
            B[j] = min.valor;

            if (min.iSiguiente < arreglos[min.idArreglo].length) {
                heap.insert(new ElementoMezcla(arreglos[min.idArreglo][min.iSiguiente], min.idArreglo, min.iSiguiente + 1));
            }
        }

        return B;
    }

    private static void medirYEjecutarHeap(int[][] arreglos) {
        // Calentamiento
        for (int i = 0; i < 3; i++) {
            kMergeHeap(arreglos);
        }

        final int REPETICIONES = 10;
        long tiempoTotal = 0;
        int[] resultado = null;

        for (int i = 0; i < REPETICIONES; i++) {
            long inicio = System.nanoTime();
            resultado = kMergeHeap(arreglos);
            long fin = System.nanoTime();
            tiempoTotal += (fin - inicio);
        }

        verificarResultado(arreglos, resultado);
        double promedioMs = tiempoTotal / (double) REPETICIONES / 1_000_000.0;
        System.out.printf("Tiempo promedio (k-MergeHeap): %.3f ms%n", promedioMs);
    }

    private static void medirYEjecutarCB(int[][] arreglos) {
        // Calentamiento
        for (int i = 0; i < 3; i++) {
            kMergeCB(arreglos);
        }

        final int REPETICIONES = 10;
        long tiempoTotal = 0;
        int[] resultado = null;

        for (int i = 0; i < REPETICIONES; i++) {
            long inicio = System.nanoTime();
            resultado = kMergeCB(arreglos);
            long fin = System.nanoTime();
            tiempoTotal += (fin - inicio);
        }

        verificarResultado(arreglos, resultado);
        double promedioMs = tiempoTotal / (double) REPETICIONES / 1_000_000.0;
        System.out.printf("Tiempo promedio (k-MergeCB): %.3f ms%n", promedioMs);
    }

    public static boolean verificarResultado(int[][] arreglosOriginales, int[] resultado) {
        int nEsperado = 0;
        for (int[] arr : arreglosOriginales) nEsperado += arr.length;

        if (resultado.length != nEsperado) {
            System.err.println("[ERROR] El tamaño de B es incorrecto.");
            return false;
        }

        for (int i = 0; i < resultado.length - 1; i++) {
            if (resultado[i] > resultado[i + 1]) {
                System.err.println("[ERROR] El arreglo no está ordenado en el índice " + i);
                return false;
            }
        }
        return true;
    }
}