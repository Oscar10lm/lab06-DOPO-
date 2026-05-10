package domain;

/**
 * Excepción especializada que funciona como reporte de errores de un minicompilador.
 */
public class ForestParseException extends ForestException {
    private final int lineNumber;
    private final String erroneousWord;
    private final String causeDetail;

    public ForestParseException(int lineNumber, String erroneousWord, String causeDetail) {
        super(String.format("Error de compilación en línea %d | Palabra: '%s' | Causa: %s", 
              lineNumber, erroneousWord, causeDetail));
        this.lineNumber = lineNumber;
        this.erroneousWord = erroneousWord;
        this.causeDetail = causeDetail;
    }

    public int getLineNumber() { return lineNumber; }
    public String getErroneousWord() { return erroneousWord; }
    public String getCauseDetail() { return causeDetail; }
}
