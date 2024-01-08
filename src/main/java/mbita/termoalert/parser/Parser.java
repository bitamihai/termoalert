package mbita.termoalert.parser;

public interface Parser<M, N> {
    public N parse(M object);
}
