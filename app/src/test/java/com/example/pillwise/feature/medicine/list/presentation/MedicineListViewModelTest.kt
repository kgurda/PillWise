import com.example.pillwise.data.local.entities.Medicine
import com.example.pillwise.feature.medicine.list.presentation.MedicineListViewModel
import com.example.pillwise.feature.medicine.list.presentation.data.MedicineListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MedicineListViewModelTest {
    private lateinit var medicineRepository: MedicineListRepository
    private lateinit var viewModel: MedicineListViewModel
    private var medicines: List<Medicine> =
        listOf(
            Medicine(name = "Medicine1", expirationDate = "date1", comment = null, image = null),
            Medicine(name = "Medicine2", expirationDate = "date2", comment = null, image = null),
            Medicine(name = "Medicine3", expirationDate = "date3", comment = null, image = null),
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        medicineRepository =
            object : MedicineListRepository {
                override fun getAll(): Flow<List<Medicine>> =
                    flow {
                        emit(medicines)
                    }
            }
        viewModel = MedicineListViewModel(medicineRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should get all items from repository`() = runTest {
        // When
        advanceUntilIdle()

        // Then
        assertEquals(viewModel.uiState.value.medicines, medicines)
    }
}
