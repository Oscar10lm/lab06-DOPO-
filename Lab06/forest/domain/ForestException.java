package domain;

/**
 * Excepción personalizada para los errores del dominio Forest.
 * 
 * @author Oscar Lasso - Juan Diego Gaitan
 * @version 1.0
 */
public class ForestException extends Exception {

    public static final String OPCION_OPEN_EN_CONSTRUCCION = "Opción open en construcción. Archivo ";
    public static final String OPCION_SAVE_AS_EN_CONSTRUCCION = "Opción saveAs en construcción. Archivo ";
    public static final String OPCION_IMPORT_EN_CONSTRUCCION = "Opción import en construcción. Archivo ";
    public static final String OPCION_EXPORT_AS_EN_CONSTRUCCION = "Opción exportAs en construcción. Archivo ";
    public static final String ERROR_GUARDAR = "Error al guardar el archivo.";
    public static final String ERROR_ABRIR = "Error al abrir el archivo.";
    public static final String ERROR_EXPORTAR = "Error al exportar el archivo.";
    public static final String ERROR_IMPORTAR = "Error al importar el archivo.";
    
    public static final String ARCHIVO_NO_ENCONTRADO = "El archivo no existe.";
    public static final String ARCHIVO_ES_DIRECTORIO = "La ruta indicada es un directorio.";
    public static final String EXTENSION_INVALIDA_DAT = "El archivo debe terminar con la extensión .dat";
    public static final String ERROR_FORMATO_OBJETO = "El archivo no contiene un bosque válido o está corrupto.";
    public static final String ERROR_CLASE_NO_ENCONTRADA = "No se pudo cargar la definición del bosque (clase no encontrada).";
    
    public static final String EXTENSION_INVALIDA_TXT = "El archivo debe terminar con la extensión .txt";
    public static final String ERROR_LINEA_FORMATO = "Error en la línea: ";
    public static final String ERROR_TIPO_DESCONOCIDO = "Tipo de elemento desconocido: ";

    public ForestException(String message) {
        super(message);
    }
}
