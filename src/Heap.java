import java.util.EmptyStackException;

public class Heap {
    //EN MINHEAP!!
    private int[] heap;
    private int size;

    public Heap(int[] datos){
        buildMinHeap(datos);
    }

    private void heapify(int i){
        minHeapify(heap, i);
    }

    private void minHeapify(int A[], int i){
        int l = hijoIzquierdo(i);
        int r = hijoDerecho(i);
        int menor = i;

        if(l < size && A[l] < A[i]){
            menor = l;
        }

        if(r < size && A[r] < A[menor]){
            menor = r;
        }

        if(menor != i){
            swap(A, i, menor);
            minHeapify(A, menor);
        }
    }

    private void buildMinHeap(int[] Datos){
        //IMPLEMENTAR TOMI :))
    }

    public int[] heapSort(){
        //IMPLEMENTARTOMASPORFAVORTELOPIDOUnU
    }

    public int extractMin(){
        if(size < 1){
            throw new EmptyStackException();
        }
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return min;
    }
    private void swap(int[] A, int i, int j){
        int aux = A[i];
        A[i] = A[j];
        A[j] = aux;
    }

    private int hijoIzquierdo(int i){return 2*i+1;}
    private int hijoDerecho(int i){return 2*i+2;}
}
