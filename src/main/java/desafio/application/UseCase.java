package desafio.application;

public abstract class UseCase<I, O> {
    abstract public O execute(I input);
}
