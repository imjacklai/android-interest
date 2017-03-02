package tw.ctl.interest.calculation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tw.ctl.interest.Entity;

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
        Entity entity = new Entity();
        entity.principal = "1000";
        entity.interest  = "10";
        entity.period    = "10";
        entity.invest    = "1000";

        presenter.calculate(entity);

        entity.simpleResult = "2,000.00";
        entity.compoundResult = "2,593.74";
        entity.investResult = "18,531.17";

        verify(view).onResult(entity);
    }

}