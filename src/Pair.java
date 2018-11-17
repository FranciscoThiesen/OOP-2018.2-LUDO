import java.util.Vector;

public class Pair<T1, T2> {

    public T1 first;
    public T2 second;

    public Pair() {}

    public Pair( T1 st)
    {
        first = st;
    }

    public Pair(T1 st, T2 nd)
    {
        first = st;
        second = nd;
    }

}
