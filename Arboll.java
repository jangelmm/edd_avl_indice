import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class Arboll{
     private JTextArea textArea;
     private JScrollPane scrollPane;
     private Nodo raiz;
     public Arboll(){
         textArea = new JTextArea(30, 70);
         textArea.setForeground(Color.YELLOW); // Cambiar el color del texto
         textArea.setBackground(Color.GRAY); // Cambiar el color de fondo
         textArea.setEditable(false);
         scrollPane = new JScrollPane(textArea);
         raiz=null;
     }
     public static void main (String [] args) throws IOException{
         Arboll lD=new Arboll();
         lD.menu();
     }
     public void menu()throws IOException{
        creaRamas("Libro1.csv");
        int opcion = 1;
        while(opcion != 0){
            opcion = leerInt(
                    "Seleccione una opción:\n" +
                    "1. Buscar por clave\n" +
                    "2. Buscar por posicion\n" +
                    "3. Mostrar CSV\n" +
                    "4. Mostrar arbol en orden\n"+
                    "0. Salir");
            
            switch (opcion) {
                case 1:
                    Nodo aux=buscar(leerString("dame la clave abuscar"));
                    String a;
                    if(aux!= null){
                        a= leerLineaCSV("Libro1.csv", aux.getPos()).replace(";", " ");
                    }
                    else 
                        a="clave no encontrada";
                    imprimir(a);
                    JOptionPane.showMessageDialog(null, scrollPane, "Lista de Ciudades",
                    JOptionPane.PLAIN_MESSAGE);
                    textArea.setText("");
                    break;
                case 2:
                    a=leerLineaCSV("Libro1.csv", leerInt("Dame la pocicion a imprimir:"));
                    if(a!=null){
                        imprimir(a.replace(";"," "));
                    }else 
                        imprimir("la pcocicion no existe");
                    JOptionPane.showMessageDialog(null, scrollPane, "Lista de Ciudades",
                    JOptionPane.PLAIN_MESSAGE);
                    textArea.setText("");
                    break;
                case 3:
                    File archivoEntrada=new File("Libro1.csv");
                    FileReader lectorArchivo=new FileReader(archivoEntrada);
                    BufferedReader lectorBuf=new BufferedReader(lectorArchivo);
                    lectorBuf.readLine();
                    while(lectorArchivo.ready()){
                        imprimir(lectorBuf.readLine().replace(";", " "));
                    }
                    JOptionPane.showMessageDialog(null, scrollPane, "Lista de Ciudades", 
                    JOptionPane.PLAIN_MESSAGE);
                    textArea.setText("");
                    break;
                case 4:
                    inOrden();
                    JOptionPane.showMessageDialog(null, scrollPane, "Lista de Ciudades",
                    JOptionPane.PLAIN_MESSAGE);
                    textArea.setText("");
                    break;
                case 0:
                    break;
                default:
                    imprimir("Opción no válida. Intente nuevamente.\n");
                    break;
            }
        }
        
     }
     public void inOrden(){
        ayudaInOrden(raiz);
     }
     private void ayudaInOrden(Nodo aux){
         if(aux!=null){
             ayudaInOrden(aux.getIzquierda());
             imprimir("Pocicion del CSV: "+aux.getPos()+" , Clave:"+aux.getDato());
             ayudaInOrden(aux.getDerecha());
         }
     }
      public Nodo buscar(String dato) {
         return buscarRecursivo(raiz, dato);
     }
     private Nodo buscarRecursivo(Nodo nodo, String dato) {
         if (nodo == null || nodo.getDato().equals(dato)) {
             return nodo;
         }
         if (dato.compareTo(nodo.getDato()) < 0) {
             return buscarRecursivo(nodo.getIzquierda(), dato);
         } else {
             return buscarRecursivo(nodo.getDerecha(), dato);
         }
     }
     public boolean insertar(Nodo nuevo){
         if(raiz==null){
             raiz=nuevo;
             return true;
         }
         Nodo aux = insertarAVL(nuevo, raiz);
         if(aux==null) return false;
         raiz = aux;
         return true;
     }
     public Nodo insertarAVL(Nodo nuevo, Nodo subAr){
         Nodo nuevoPadre=subAr;
         if (nuevo.getDato().compareTo(subAr.getDato()) < 0) {
                if (subAr.getIzquierda() == null) {
                    subAr.setIzquierda(nuevo);
                } else {
                    subAr.setIzquierda(insertarAVL(nuevo, subAr.getIzquierda()));
                    if ((obtenerFe(subAr.getIzquierda()) - obtenerFe(subAr.getDerecha())) == 2) {
                        if (nuevo.getDato().compareTo(subAr.getIzquierda().getDato()) < 0) {
                            nuevoPadre = rotacionIzquierda(subAr);
                        } else {
                            nuevoPadre = rotacionDobleIzquierda(subAr);
                        }
                    }
                }
         } else if (nuevo.getDato().compareTo(subAr.getDato()) > 0) {
                if (subAr.getDerecha() == null) {
                    subAr.setDerecha(nuevo);
                } else {
                    subAr.setDerecha(insertarAVL(nuevo, subAr.getDerecha()));
                    if ((obtenerFe(subAr.getDerecha()) - obtenerFe(subAr.getIzquierda())) == 2) {
                        if (nuevo.getDato().compareTo(subAr.getDerecha().getDato()) > 0) {
                            nuevoPadre = rotacionDerecha(subAr);
                        } else {
                            nuevoPadre = rotacionDobleDerecha(subAr);
                        }
                    }
                }
            } else {
                return null;
         }
         if (subAr.getIzquierda() == null && subAr.getDerecha() != null) {
             subAr.setFe(subAr.getDerecha().getFe() + 1);
         } else if (subAr.getDerecha() == null && subAr.getIzquierda() != null) {
             subAr.setFe(subAr.getIzquierda().getFe() + 1);
         } else {
             subAr.setFe(Math.max(obtenerFe(subAr.getIzquierda()), obtenerFe(subAr.getDerecha())) + 1);
         }
        
         return nuevoPadre;
     }
     public int obtenerFe(Nodo  aux) {
         if (aux == null) return -1;
         return aux.getFe();
     }
     public Nodo rotacionIzquierda(Nodo n) {
         Nodo aux = n.getIzquierda();
         if(aux!=null){
             if(aux.getDerecha()==null)
                 n.setIzquierda(null);
             else 
                 n.setIzquierda(aux.getDerecha());
             aux.setDerecha(n);
             n.setFe(Math.max(obtenerFe(n.getIzquierda()), obtenerFe(n.getDerecha())) + 1);
             aux.setFe(Math.max(obtenerFe(aux.getIzquierda()), obtenerFe(aux.getDerecha())) + 1);
        }
         return aux;
     }
     public Nodo rotacionDerecha(Nodo n) {
         Nodo aux = n.getDerecha();
         if(aux!=null){
             if(aux.getIzquierda()==null)
                 n.setDerecha(null);
             else
                 n.setDerecha(aux.getIzquierda());
             aux.setIzquierda(n);
             n.setFe(Math.max(obtenerFe(n.getIzquierda()), obtenerFe(n.getDerecha())) + 1);
             aux.setFe(Math.max(obtenerFe(aux.getIzquierda()), obtenerFe(aux.getDerecha())) + 1);
        }
         return aux;
     }
     public Nodo rotacionDobleIzquierda(Nodo n) {
         n.setIzquierda(rotacionDerecha(n.getIzquierda()));
         Nodo aux = rotacionIzquierda(n);
         return aux;
     }
     public Nodo rotacionDobleDerecha(Nodo n) {
         n.setDerecha(rotacionIzquierda(n.getDerecha()));
         Nodo aux = rotacionDerecha(n);
         return aux;
     }
     public void agregar(Nodo nuevoNodo) {
        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            agregarRecursivo(raiz, nuevoNodo);
        }
     }
     private void agregarRecursivo(Nodo actual, Nodo nuevoNodo) {
        if (nuevoNodo.getDato().compareTo(actual.getDato()) < 0) {
            if (actual.getIzquierda() == null) {
                actual.setIzquierda(nuevoNodo);
            } else {
                agregarRecursivo(actual.getIzquierda(), nuevoNodo);
            }
        } else if (nuevoNodo.getDato().compareTo(actual.getDato()) > 0) {
            if (actual.getDerecha() == null) {
                actual.setDerecha(nuevoNodo);
            } else {
                agregarRecursivo(actual.getDerecha(), nuevoNodo);
            }
        }
      }
     private String creaRamas(String archivoCSV) {
         try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
             String linea=br.readLine();
             linea=linea.replace(" ","");
             int contador = 1;
             while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length < 7) {
                    String[] temp = new String[7];
                    System.arraycopy(partes, 0, temp, 0, partes.length);
                    Arrays.fill(temp, partes.length, 7, "*");
                    partes = temp; 
                }
                int poblacion = partes[2].contains("*") ? 0 : Integer.parseInt(partes[2]);
                int superficie = partes[3].contains("*") ? 0 : Integer.parseInt(partes[3]);
                String clave;
                if(partes[0].length()<3 || partes[1].length()<3)
                  clave=partes[0].substring(0, partes[0].length())+partes[1].substring(0, partes[1].length());
                else clave=partes[0].substring(0, 3)+partes[1].substring(0, 3);
                int p = partes[2].contains("*") ? 0 : Integer.parseInt(partes[2]);
                int s = partes[3].contains("*") ? 0 : Integer.parseInt(partes[3]);
                clave += (p+s);
                insertar(new Nodo(clave, contador)); 
                contador++;
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }
     private String leerLineaCSV(String archivoCSV, int numeroLinea) {
         try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
             String linea;
             int contador = 0;
             while ((linea = br.readLine()) != null) {
                 if (contador == numeroLinea) {
                     return linea;
                 }
                 contador++;
             }
         } catch (IOException e) {
         e.printStackTrace();
         }
         return null;
     }
     public void setNumero(int numero) throws ExceptionNumero{
         if(numero < 0 && !(Math.floor(numero) == numero)){
         throw new ExceptionNumero("Alerta: Número negativo ");
         }
     }
     public void setString(String x) throws ExceptionChar {
        if (!x.matches("[\\s\\S]*")) {
            throw new ExceptionChar("La cadena contiene caracteres que no son letras");
        }
     }
     public String leerString(String s){
         boolean estado=true;
         String nombre="";
         while(estado){
             try{
                 nombre= (JOptionPane.showInputDialog(s));
                 setString(nombre);
                 estado=false;
             }catch(ExceptionChar e){
                 JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, " Ingrese el dato que se le pide");
             }
         }
         return nombre;
      }
     public int leerInt (String s){
         boolean pedirnumero = true;
         int numeroUsuario = 0;
             while (pedirnumero) {
             try{
                 numeroUsuario = Integer.parseInt
                 (JOptionPane.showInputDialog(null,s));
                 setNumero(numeroUsuario);
                 pedirnumero = false;
             }catch(ExceptionNumero e){
                 JOptionPane.showMessageDialog(null," Intenta nuevamente");
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
             }
         }
         return numeroUsuario;
     }
     private void imprimir(String s){
        textArea.append(s+"\n"); 
     }
}
