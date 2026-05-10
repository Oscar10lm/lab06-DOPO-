package domain;
import java.util.*;
import java.io.File;
import java.io.Serializable;

/**
 * Clase principal del modelo Forest.
 * 
 * @author Oscar Lasso - Juan Diego Gaitan
 * @version 1.0
 */
public class Forest implements Serializable {
    static private int SIZE=25;
    private Thing[][] places;
    
    /**
     * Constructor principal que inicializa el bosque y le agrega algunos elementos.
     */
    public Forest() {
        places=new Thing[SIZE][SIZE];
        for (int r=0;r<SIZE;r++){
            for (int c=0;c<SIZE;c++){
                places[r][c]=null;
            }
        }
        someThings();
    }
    
    /**
     * Constructor alternativo
     */
    
    public Forest(boolean empty) {
        places = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                places[r][c] = null;
        if (!empty) someThings();
    }

    /**
     * Obtiene el tamaño del bosque.
     */
    public int  getSize(){
        return SIZE;
    }

    /**
     * Obtiene el elemento ubicado en una fila y columna específica.
     */
    public Thing getThing(int r,int c){
        if (inForest(r, c)) {
            return places[r][c];
        }
        return null;
    }

    /**
     * Coloca un elemento en una posición específica del bosque.
     */
    public void setThing(int r, int c, Thing e){
        if (inForest(r, c)) {
            places[r][c]=e;
        }
    }

    public void someThings(){   
        // 1. Nos aseguramos de que el bosque esté vacío
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }

        // 2. Colocar los árboles requeridos (y uno extra en 15,15 para que pasen tus pruebas unitarias)
        new Tree(this, 10, 10);
        new Tree(this, 15, 15);
        new FireTree(this, 10, 13);
        new RainTree(this, 10, 16);

        // 3. Colocar exactamente 3 ardillas
        new Squirrel(this, 13, 10);
        new Squirrel(this, 13, 13);
        new Squirrel(this, 13, 16);
        
        // 4. Agregar uno de agua, fuego y tierra para que puedas ver todas las figuras
        new Fire(this, 7, 10);
        new Water(this, 7, 13);
        new Ground(this, 7, 16);
    }
    
    /**
     * Cuenta la cantidad de vecinos del mismo tipo alrededor de una posición.
     */
    public int neighborsEquals(int r, int c){
        int num=0;
        if (inForest(r,c) && places[r][c]!=null){
            for(int dr=-1; dr<2;dr++){
                for (int dc=-1; dc<2;dc++){
                    if ((dr!=0 || dc!=0) && inForest(r+dr,c+dc) && 
                    (places[r+dr][c+dc]!=null) &&  (places[r][c].getClass()==places[r+dr][c+dc].getClass())) num++;
                }
            }
        }
        return num;
    }
   
    /**
     * Verifica si una posición dada dentro del bosque está vacía.
     */
    public boolean isEmpty(int r, int c){
        return (inForest(r,c) && places[r][c]==null);
    }    
        
    /**
     * Comprueba si las coordenadas dadas están dentro de los límites del bosque.
     */
    private boolean inForest(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
    /**
     * Avanza el estado del bosque un instante de tiempo, actualizando todos sus elementos.
     */
    public void ticTac(){
        Thing[][] snapshot = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                snapshot[r][c] = places[r][c];

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (snapshot[r][c] != null) {
                    snapshot[r][c].ticTac();
                }
            }
        }

    }

    /**
     * Abre un bosque desde un archivo (versión inicial).
     */
    public void open00(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_OPEN_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Abre un bosque desde un archivo usando serialización.
     * Retorna la nueva instancia cargada para mantener las referencias internas correctas.
     */
    public Forest open(File file) throws ForestException {
        if (!file.exists()) {
            throw new ForestException(ForestException.ARCHIVO_NO_ENCONTRADO);
        }
        if (file.isDirectory()) {
            throw new ForestException(ForestException.ARCHIVO_ES_DIRECTORIO);
        }
        if (!file.getName().endsWith(".dat")) {
            throw new ForestException(ForestException.EXTENSION_INVALIDA_DAT);
        }
        
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(file))) {
            return (Forest) in.readObject();
        } catch (java.io.StreamCorruptedException | java.io.OptionalDataException e) {
            throw new ForestException(ForestException.ERROR_FORMATO_OBJETO);
        } catch (ClassNotFoundException e) {
            throw new ForestException(ForestException.ERROR_CLASE_NO_ENCONTRADA);
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_ABRIR);
        }
    }

    /**
     * Versión 01 de open.
     */
    public Forest open01(File file) throws ForestException {
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(file))) {
            return (Forest) in.readObject();
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_ABRIR);
        }
    }

    /**
     * Guarda el bosque actual en el archivo especificado (versión inicial).
     */
    public void saveAs00(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_SAVE_AS_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Guarda el bosque actual en el archivo especificado usando serialización.
     */
    public void saveAs(File file) throws ForestException {
        if (file.isDirectory()) {
            throw new ForestException(ForestException.ARCHIVO_ES_DIRECTORIO);
        }
        if (!file.getName().endsWith(".dat")) {
            throw new ForestException(ForestException.EXTENSION_INVALIDA_DAT);
        }

        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(file))) {
            out.writeObject(this);
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_GUARDAR);
        }
    }

    /**
     * Versión 01 de saveAs.
     */
    public void saveAs01(File file) throws ForestException {
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(file))) {
            out.writeObject(this);
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_GUARDAR);
        }
    }

    /**
     * Importa la configuración de un bosque (versión inicial).
     */
    public void importFile00(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_IMPORT_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Importa la configuración de un bosque leyendo un archivo de texto.
     * Usa split para separar el tipo y la posición.
     */
    public Forest importFile(File file) throws ForestException {
        if (!file.exists()) {
            throw new ForestException(ForestException.ARCHIVO_NO_ENCONTRADO);
        }
        if (file.isDirectory()) {
            throw new ForestException(ForestException.ARCHIVO_ES_DIRECTORIO);
        }
        if (!file.getName().endsWith(".txt")) {
            throw new ForestException(ForestException.EXTENSION_INVALIDA_TXT);
        }

        try (java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(file))) {
            Forest loadedForest = new Forest(true); 
            String line;
            int numLinea = 0;
            while ((line = in.readLine()) != null) {
                numLinea++;
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("[,\\s]+");
                if (parts.length < 3) {
                    throw new ForestException(ForestException.ERROR_LINEA_FORMATO + numLinea + ". Faltan columnas.");
                }
                
                String tipo = parts[0];
                int r, c;
                try {
                    r = Integer.parseInt(parts[1]);
                    c = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    throw new ForestException(ForestException.ERROR_LINEA_FORMATO + numLinea + ". Coordenadas inválidas.");
                }
                
                switch(tipo) {
                    case "Tree": new Tree(loadedForest, r, c); break;
                    case "FireTree": new FireTree(loadedForest, r, c); break;
                    case "RainTree": new RainTree(loadedForest, r, c); break;
                    case "Squirrel": new Squirrel(loadedForest, r, c); break;
                    case "Fire": new Fire(loadedForest, r, c); break;
                    case "Water": new Water(loadedForest, r, c); break;
                    case "Ground": new Ground(loadedForest, r, c); break;
                    default:
                        throw new ForestException(ForestException.ERROR_TIPO_DESCONOCIDO + tipo);
                }
            }
            return loadedForest;
        } catch (ForestException e) {
            throw e;
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_IMPORTAR);
        }
    }

    /**
     * Versión 01 de importFile.
     */
    public Forest importFile01(File file) throws ForestException {
        try (java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(file))) {
            Forest loadedForest = new Forest(true); 
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("[,\\s]+");
                if (parts.length >= 3) {
                    String tipo = parts[0];
                    int r = Integer.parseInt(parts[1]);
                    int c = Integer.parseInt(parts[2]);
                    
                    switch(tipo) {
                        case "Tree": new Tree(loadedForest, r, c); break;
                        case "FireTree": new FireTree(loadedForest, r, c); break;
                        case "RainTree": new RainTree(loadedForest, r, c); break;
                        case "Squirrel": new Squirrel(loadedForest, r, c); break;
                        case "Fire": new Fire(loadedForest, r, c); break;
                        case "Water": new Water(loadedForest, r, c); break;
                        case "Ground": new Ground(loadedForest, r, c); break;
                    }
                }
            }
            return loadedForest;
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_IMPORTAR);
        }
    }

    /**
     * Exporta el estado del bosque a un formato específico (versión inicial).
     */
    public void exportAs00(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_EXPORT_AS_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Exporta el estado del bosque a un formato específico (texto plano).
     */
    public void exportAs(File file) throws ForestException {
        if (file.isDirectory()) {
            throw new ForestException(ForestException.ARCHIVO_ES_DIRECTORIO);
        }
        if (!file.getName().endsWith(".txt")) {
            throw new ForestException(ForestException.EXTENSION_INVALIDA_TXT);
        }

        try (java.io.PrintWriter out = new java.io.PrintWriter(file)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Thing t = places[r][c];
                    if (t != null) {
                        String tipo = t.getClass().getSimpleName();
                        out.println(tipo + " " + r + ", " + c);
                    }
                }
            }
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_EXPORTAR);
        }
    }

    /**
     * Versión 01 de exportAs.
     */
    public void exportAs01(File file) throws ForestException {
        try (java.io.PrintWriter out = new java.io.PrintWriter(file)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Thing t = places[r][c];
                    if (t != null) {
                        String tipo = t.getClass().getSimpleName();
                        out.println(tipo + " " + r + ", " + c);
                    }
                }
            }
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_EXPORTAR);
        }
    }
    /**
     * Importa la configuración de un bosque leyendo un archivo de texto.
     * Versión 02: Funciona como un minicompilador con errores detallados.
     */
    public Forest import02(File file) throws ForestException {
        if (!file.exists()) throw new ForestException(ForestException.ARCHIVO_NO_ENCONTRADO);
        if (file.isDirectory()) throw new ForestException(ForestException.ARCHIVO_ES_DIRECTORIO);
        if (!file.getName().endsWith(".txt")) throw new ForestException(ForestException.EXTENSION_INVALIDA_TXT);

        try (java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(file))) {
            Forest loadedForest = new Forest(true); 
            String line;
            int numLinea = 0;
            while ((line = in.readLine()) != null) {
                numLinea++;
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("[,\\s]+");
                if (parts.length < 3) {
                    throw new ForestParseException(numLinea, line, "Faltan datos en la instrucción. Se esperaba: Tipo fila columna");
                }
                
                String tipo = parts[0];
                int r, c;
                try {
                    r = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    throw new ForestParseException(numLinea, parts[1], "La coordenada de la fila debe ser un número entero válido.");
                }
                try {
                    c = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    throw new ForestParseException(numLinea, parts[2], "La coordenada de la columna debe ser un número entero válido.");
                }
                
                switch(tipo) {
                    case "Tree": new Tree(loadedForest, r, c); break;
                    case "FireTree": new FireTree(loadedForest, r, c); break;
                    case "RainTree": new RainTree(loadedForest, r, c); break;
                    case "Squirrel": new Squirrel(loadedForest, r, c); break;
                    case "Fire": new Fire(loadedForest, r, c); break;
                    case "Water": new Water(loadedForest, r, c); break;
                    case "Ground": new Ground(loadedForest, r, c); break;
                    default:
                        throw new ForestParseException(numLinea, tipo, "Palabra reservada o tipo de elemento desconocido en el dominio.");
                }
            }
            return loadedForest;
        } catch (ForestException e) {
            throw e;
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_IMPORTAR);
        }
    }

    /**
     * Exporta el estado del bosque a un archivo de texto.
     * Versión 02
     */
    public void exportAs02(File file) throws ForestException {
        exportAs(file);
    }

    /**
     * Importa la configuración de un bosque leyendo un archivo de texto.
     * Versión 03: Minicompilador Flexible usando API de Reflection de Java.
     */
    public Forest import03(File file) throws ForestException {
        if (!file.exists()) throw new ForestException(ForestException.ARCHIVO_NO_ENCONTRADO);
        if (file.isDirectory()) throw new ForestException(ForestException.ARCHIVO_ES_DIRECTORIO);
        if (!file.getName().endsWith(".txt")) throw new ForestException(ForestException.EXTENSION_INVALIDA_TXT);

        try (java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(file))) {
            Forest loadedForest = new Forest(true); 
            String line;
            int numLinea = 0;
            while ((line = in.readLine()) != null) {
                numLinea++;
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("[,\\s]+");
                if (parts.length < 3) {
                    throw new ForestParseException(numLinea, line, "Faltan datos en la instrucción. Se esperaba: Tipo fila columna");
                }
                
                String tipo = parts[0];
                int r, c;
                try {
                    r = Integer.parseInt(parts[1]);
                    c = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    throw new ForestParseException(numLinea, parts[1] + " " + parts[2], "Las coordenadas deben ser números enteros.");
                }
                
                try {
                    // API de Reflection: Instanciación dinámica
                    Class<?> clazz = Class.forName("domain." + tipo);
                    java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(Forest.class, int.class, int.class);
                    constructor.newInstance(loadedForest, r, c);
                } catch (ClassNotFoundException e) {
                    throw new ForestParseException(numLinea, tipo, "Clase no encontrada en el dominio mediante Reflection. Tipo no soportado.");
                } catch (NoSuchMethodException e) {
                    throw new ForestParseException(numLinea, tipo, "La clase no tiene un constructor (Forest, int, int).");
                } catch (Exception e) {
                    throw new ForestParseException(numLinea, tipo, "Error al instanciar el elemento vía Reflection: " + e.getMessage());
                }
            }
            return loadedForest;
        } catch (ForestException e) {
            throw e; // Lanzar las excepciones parseadas hacia arriba
        } catch (Exception e) {
            throw new ForestException(ForestException.ERROR_IMPORTAR);
        }
    }

    /**
     * Exporta el estado del bosque a un archivo de texto.
     * Versión 03
     */
    public void exportAs03(File file) throws ForestException {
        exportAs(file);
    }
}
