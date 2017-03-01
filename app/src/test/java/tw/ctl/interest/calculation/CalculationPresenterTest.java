package tw.ctl.interest.calculation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by jacklai on 2017/3/1.
 */

public class CalculationPresenterTest {

    @Mock
    private CalculationView view;

    private CalculationPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CalculationPresenter();
        presenter.attachView(view);
    }

    @After
    public void tearDown() throws Exception {
        presenter.detachView();
    }

    @Test
    public void calculate() throws Exception {
        String principal = "1000";
        String interest  = "10";
        String period    = "10";
        String invest    = "1000";

        presenter.calculate(principal, interest, period, invest);

        verify(view).onResult("2,000.00", "2,593.74", "18,531.17");
    }

}