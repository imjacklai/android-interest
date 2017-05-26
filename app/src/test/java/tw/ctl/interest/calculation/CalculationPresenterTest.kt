package tw.ctl.interest.calculation

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import tw.ctl.interest.Entity

import org.mockito.Mockito.verify

/**
 * Created by jacklai on 2017/3/1.
 */

class CalculationPresenterTest {

    @Mock
    private val view: CalculationView? = null

    private var presenter: CalculationPresenter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = CalculationPresenter()
        presenter!!.attachView(view!!)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        presenter!!.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun calculate() {
        val entity = Entity()
        entity.principal = "1000"
        entity.interest = "10"
        entity.period = "10"
        entity.invest = "1000"

        presenter!!.calculate(entity)

        entity.simpleResult = "2,000.00"
        entity.compoundResult = "2,593.74"
        entity.investResult = "18,531.17"

        verify<CalculationView>(view).onResult(entity)
    }

}