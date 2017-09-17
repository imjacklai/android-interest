package tw.ctl.interest.calculation

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import tw.ctl.interest.Entity

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
        val entity = Entity("1000", "10", "10", "1000")
        presenter?.calculate(entity)

        entity.simpleResult = "2,000.00"
        entity.compoundResult = "2,593.74"
        entity.investResult = "18,531.17"

        verify<CalculationView>(view).onResult(entity)
    }

    @Test
    @Throws(Exception::class)
    fun calculateWithoutInvest() {
        val entity = Entity("1000", "10", "10", "")
        presenter?.calculate(entity)

        entity.simpleResult = "2,000.00"
        entity.compoundResult = "2,593.74"
        entity.investResult = "---"

        verify<CalculationView>(view).onResult(entity)
    }

}