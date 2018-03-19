package tw.ctl.interest.calculation

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import tw.ctl.interest.model.Record

class CalculationPresenterTest {

    private val view = mock(CalculationView::class.java)
    private var presenter: CalculationPresenter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter = CalculationPresenter()
        presenter?.attachView(view)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        presenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun calculateWithInvest() {
        val record = Record("1000", "10", "10", "1000")
        presenter?.calculate(record)

        record.simpleResult = "2,000.00"
        record.compoundResult = "2,593.74"
        record.investResult = "18,531.17"

        verify<CalculationView>(view).onResult(record)
    }

    @Test
    @Throws(Exception::class)
    fun calculateWithoutInvest() {
        val record = Record("1000", "10", "10", "")
        presenter?.calculate(record)

        record.simpleResult = "2,000.00"
        record.compoundResult = "2,593.74"
        record.investResult = "---"

        verify<CalculationView>(view).onResult(record)
    }

}