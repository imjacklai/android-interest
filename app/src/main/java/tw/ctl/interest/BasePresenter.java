package tw.ctl.interest;

/**
 * Created by jacklai on 2017/3/1.
 */

public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();

}
