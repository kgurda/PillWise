import com.example.pillwise.data.local.entities.Medicine
import com.example.pillwise.feature.medicine.list.presentation.MedicineListViewModel
import com.example.pillwise.feature.medicine.list.presentation.data.MedicineListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MedicineListViewModelTest {
    private lateinit var medicineRepository: MedicineListRepository
    private lateinit var viewModel: MedicineListViewModel
    private var medicines: List<Medicine> =
        listOf(
            Medicine(id = 1L, name = "Medicine1", expirationDate = "date1", comment = null, image = null),
            Medicine(id = 2L, name = "Medicine2", expirationDate = "date2", comment = null, image = null),
            Medicine(id = 3L, name = "Medicine3", expirationDate = "date3", comment = null, image = null)
        )

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        medicineRepository = mock()
        `when`(medicineRepository.getAll()).thenReturn(flow { emit(medicines) })
        viewModel = MedicineListViewModel(medicineRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should get all items from repository`() =
        runTest {
            // When
            advanceUntilIdle()

            // Then
            assertEquals(viewModel.uiState.value.medicines, medicines)
        }

    @Test
    fun `should delete item from uiState`() =
        runTest {
            // Given
            val idToDelete = 1L
            viewModel = MedicineListViewModel(medicineRepository)

            // When
            viewModel.deleteItem(idToDelete)
            advanceUntilIdle()

            // Then
            val expectedMedicines = medicines.filter { it.id != idToDelete }
            assertEquals(expectedMedicines, viewModel.uiState.value.medicines)
        }
}
