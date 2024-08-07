package honeyimleaving.toyproject.honeyimleaving.fragment;


public interface FragmentReturnInterface<T> {

    public T getFragementReturn();
    public String getErrorString();
    public boolean isError();
}
