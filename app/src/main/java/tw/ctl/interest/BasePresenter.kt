package tw.ctl.interest

/**
 * Created by jacklai on 2017/3/1.
 */

interface BasePresenter<in V> {

    fun attachView(view: V)

    fun detachView()

}
