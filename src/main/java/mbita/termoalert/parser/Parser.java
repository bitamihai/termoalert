package mbita.termoalert.parser;

public interface Parser<M, N> {

    N parse(M object);

}
