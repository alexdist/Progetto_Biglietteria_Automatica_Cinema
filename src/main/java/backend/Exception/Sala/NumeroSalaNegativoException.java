package backend.Exception.Sala;

public class NumeroSalaNegativoException extends IllegalArgumentException {
    public NumeroSalaNegativoException() {
        super("Il numero della sala non può essere negativo.");
    }

    // Costruttore che accetta un messaggio personalizzato
    public NumeroSalaNegativoException(String message) {
        super(message);
    }
}
