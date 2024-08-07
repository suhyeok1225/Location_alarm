package honeyimleaving.toyproject.honeyimleaving.fragment;


public class FragmentReturnErrCheckUtil {

    public static String getErrorFragment(FragmentReturnInterface fragmentReturnInterface) {
        return fragmentReturnInterface.getErrorString();
    }

    public static boolean isErrorFragment(FragmentReturnInterface fragmentReturnInterface) {
        return fragmentReturnInterface.isError();
    }
}
