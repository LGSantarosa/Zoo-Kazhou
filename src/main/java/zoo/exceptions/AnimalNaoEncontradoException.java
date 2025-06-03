package zoo.exceptions;

public class AnimalNaoEncontradoException extends Exception {
    public AnimalNaoEncontradoException(String nome) {
        super("Animal " + nome + " não encontrado!");
    }
} 