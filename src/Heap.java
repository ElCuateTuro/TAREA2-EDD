import java.util.EmptyStackException;

public class Heap {
    private ElementoMezcla[] heap;
    private int size;

    public Heap(ElementoMezcla[] datos) {
        buildMinHeap(datos);
    }

    // Constructor 2: Crea un Heap vacío con capacidad definida k
    public Heap(int capacidad) {
        this.heap = new ElementoMezcla[capacidad];
        this.size = 0;
    }

    private void heapify(int i) {
        minHeapify(heap, i);
    }

    private void minHeapify(ElementoMezcla[] A, int i) {
        int l = hijoIzquierdo(i);
        int r = hijoDerecho(i);
        int menor = i;

        // Comparación basada en el campo .valor de cada Elemento
        if (l < size && A[l].valor < A[i].valor) {
            menor = l;
        }

        if (r < size && A[r].valor < A[menor].valor) {
            menor = r;
        }

        if (menor != i) {
            swap(A, i, menor);
            minHeapify(A, menor);
        }
    }

    private void buildMinHeap(ElementoMezcla[] datos) {
        this.heap = datos.clone();
        this.size = heap.length;

        for (int i = (size / 2) - 1; i >= 0; i--) {
            minHeapify(this.heap, i);
        }
    }

    // INSERTAR DE ABAJO HACIA ARRIBA
    public void insert(ElementoMezcla elem) {
        if (size >= heap.length) {
            throw new IllegalStateException("El Heap está lleno");
        }
        heap[size] = elem;
        int i = size;
        size++;

        while (i > 0 && heap[i].valor < heap[padre(i)].valor) {
            swap(heap, i, padre(i));
            i = padre(i);
        }
    }

    // Extrae el mínimo en O(log k) y reordena hacia abajo
    public ElementoMezcla extractMin() {
        if (size < 1) {
            throw new EmptyStackException();
        }
        ElementoMezcla min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) {
            heapify(0);
        }
        return min;
    }

    private void swap(ElementoMezcla[] A, int i, int j) {
        ElementoMezcla aux = A[i];
        A[i] = A[j];
        A[j] = aux;
    }

    private int padre(int i) { return (i - 1) / 2; }
    private int hijoIzquierdo(int i) { return 2 * i + 1; }
    private int hijoDerecho(int i) { return 2 * i + 2; }
// ESTRUCTURA BASADA EN LAB 8
    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }
}