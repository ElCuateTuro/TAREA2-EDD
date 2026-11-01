public class BinomialHeap {
    private NodoBinomial cabeza;
    //SIEMPRE EN MINHEAP!!
    class NodoBinomial{
        ElementoMezcla dato;
        int grado;
        NodoBinomial padre, hijo, hermano;

        NodoBinomial(ElementoMezcla dato){
            this.dato = dato;
            this.grado = 0;
            this.padre = null;
            this.hijo = null;
            this.hermano = null;
        }
    }

    public boolean vacio(){
        return cabeza == null;
    }

    private void enlazarA(NodoBinomial y, NodoBinomial x){
        y.padre = x;
        y.hermano = x.hijo; //tener cuidado con este!!! cuidado con no eliminar hijo antes de asignarlo!!
        x.hijo = y;
        x.grado++;
    }

    private NodoBinomial fusionarListas(NodoBinomial h1, NodoBinomial h2){
        if(h1 == null)return h2;
        if(h2 == null)return h1;

        NodoBinomial inicio = null;
        NodoBinomial ultimo = null;
        if(h1.grado <= h2.grado){
            inicio = h1;
            h1 = h1.hermano; //el primero es el de menor grado, todo va ordenado de grado ASCENDENTE!! PORQUE ES MIHEAP RECORDADR!!
        }else{
            inicio = h2;
            h2 = h2.hermano;
        }
        ultimo = inicio;

        while(h1 != null && h2 != null){
            if(h1.grado <= h2.grado){
                    ultimo.hermano = h1;
                    h1 = h1.hermano;
            }else{
                ultimo.hermano = h2;
                h2 = h2.hermano;
            }
            ultimo = ultimo.hermano;
        }
        if(h1 != null)ultimo.hermano = h1;
        if(h2 != null)ultimo.hermano = h2;

        return inicio;
    }

    public void union(BinomialHeap otro) {
        if (otro == null || otro.cabeza == null) return;

        this.cabeza = fusionarListas(this.cabeza, otro.cabeza);
        if (this.cabeza == null) return;

        NodoBinomial prev = null;
        NodoBinomial curr = this.cabeza;
        NodoBinomial next = curr.hermano;
 //ESTRUCTURA BASADA EN LABORATORIO N°9
        while (next != null) {
            if ((curr.grado != next.grado) ||
                    (next.hermano != null && next.hermano.grado == curr.grado)) {
                prev = curr;
                curr = next;
            }
            else if (curr.dato.valor <= next.dato.valor) {
                curr.hermano = next.hermano;
                enlazarA(next, curr);
            }
            else {
                if (prev == null) {
                    this.cabeza = next;
                } else {
                    prev.hermano = next;
                }
                enlazarA(curr, next);
                curr = next;
            }
            next = curr.hermano;
        }
    }

    public void insert(ElementoMezcla elem) {
        BinomialHeap temp = new BinomialHeap();
        temp.cabeza = new NodoBinomial(elem);
        this.union(temp);
    }

    public ElementoMezcla extractMin() {
        if (cabeza == null) return null;

        NodoBinomial minNode = cabeza;
        NodoBinomial minPrev = null;

        NodoBinomial prev = cabeza;
        NodoBinomial curr = cabeza.hermano;

        while (curr != null) {
            if (curr.dato.valor < minNode.dato.valor) {
                minNode = curr;
                minPrev = prev;
            }
            prev = curr;
            curr = curr.hermano;
        }
        //Remover la raíz mínima de la lista principal
        if (minPrev == null) {
            cabeza = minNode.hermano;
        } else {
            minPrev.hermano = minNode.hermano;
        }

        NodoBinomial hijoCurr = minNode.hijo;
        NodoBinomial listaHijosInvertida = null;

        while (hijoCurr != null) {
            NodoBinomial siguiente = hijoCurr.hermano;
            hijoCurr.hermano = listaHijosInvertida;
            hijoCurr.padre = null;
            listaHijosInvertida = hijoCurr;
            hijoCurr = siguiente;
        }

        BinomialHeap heapHijos = new BinomialHeap();
        heapHijos.cabeza = listaHijosInvertida;
        this.union(heapHijos);

        return minNode.dato;
    }

}
