class Nodo {
    private int pos, altura;
    private String clave;
    private Nodo izquierda;
    private Nodo derecha;
    private int Fe;
    public Nodo(String ide,int pos) {
        this.pos = pos;
        this.clave=ide;
        izquierda = null;
        derecha = null;
    }
    public void setPos(int pos){
        this.pos=pos;
    }
    public int getPos(){
        return pos;
    }
    public void setClave(String ide){
        this.clave=ide;
    }
    public String getDato(){
        return clave;
    }
    public void setDerecha(Nodo derecha){
        this.derecha=derecha;
    }
    public Nodo getDerecha(){
        return derecha;
    }
    public void setIzquierda(Nodo izquierda){
        this.izquierda=izquierda;
    }
    public Nodo getIzquierda(){
        return izquierda;
    }
    public void setFe(int i){
        Fe = i;
    }
    public int getFe(){
        return Fe;
    }
    @Override
    public String toString() {
        return "Pocicion del CSV: "+getPos()+" , Clave:"+getDato(); 
    }
}