import android.graphics.Bitmap
import com.example.pillwise.data.local.entities.Medicine
import com.example.pillwise.feature.medicine.creation.presentation.MedicineCreationViewModel
import com.example.pillwise.feature.medicine.creation.presentation.data.MedicineCreationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class MedicineCreationViewModelTest {
    private lateinit var medicineRepository: MedicineCreationRepository
    private lateinit var viewModel: MedicineCreationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        medicineRepository = mock()
        viewModel = MedicineCreationViewModel(medicineRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update name in uiState`() =
        runTest {
            // Given
            val name = "Aspirin"

            // When
            viewModel.updateName(name)

            // Then
            assertEquals(name, viewModel.uiState.value.name)
            assertEquals(true, viewModel.validationState.value.isNameValid)
            assertEquals(false, viewModel.uiState.value.isLoading)
        }

    @Test
    fun `should update expiration date in uiState`() =
        runTest {
            // Given
            val date = "2024-12-31"

            // When
            viewModel.updateExpirationDate(date)

            // Then
            assertEquals(date, viewModel.uiState.value.expirationDate)
            assertEquals(true, viewModel.validationState.value.isExpirationDateValid)
            assertEquals(false, viewModel.uiState.value.isLoading)
        }

    @Test
    fun `should update comment in uiState`() =
        runTest {
            // Given
            val comment = "Take with food."

            // When
            viewModel.updateComment(comment)

            // Then
            assertEquals(comment, viewModel.uiState.value.comment)
            assertEquals(false, viewModel.uiState.value.isLoading)
        }

    @Test
    fun `should update captured image in uiState`() =
        runTest {
            // Given
            val image = mock(Bitmap::class.java)

            // When
            viewModel.uploadPhoto(image)

            // Then
            assertEquals(image, viewModel.uiState.value.capturedImage)
        }

    @Test
    fun `should create medicine when input is valid`() =
        runTest {
            // Given
            val name = "Aspirin"
            val date = "2024-12-31"
            val comment = "Take with water."
            val image = mock(Bitmap::class.java)
            val captor = argumentCaptor<Medicine>()

            viewModel.updateName(name)
            viewModel.updateExpirationDate(date)
            viewModel.updateComment(comment)
            viewModel.uploadPhoto(image)

            // When
            viewModel.create()
            advanceUntilIdle()

            // Then
            assertEquals(false, viewModel.uiState.value.isLoading)
            assertEquals(true, viewModel.validationState.value.isNameValid)
            assertEquals(true, viewModel.validationState.value.isExpirationDateValid)
            assertEquals(true, viewModel.uiState.value.created)

            verify(medicineRepository, times(1)).create(captor.capture())
            val result = captor.firstValue
            assertEquals(name, result.name)
            assertEquals(date, result.expirationDate)
            assertEquals(comment, result.comment)
            assert(result.image is ByteArray)
        }

    @Test
    fun `should not create medicine when input is invalid`() =
        runTest {
            // Given
            viewModel.updateName("")
            viewModel.updateExpirationDate("")

            // When
            viewModel.create()
            advanceUntilIdle()

            // Then
            assertEquals(false, viewModel.uiState.value.isLoading)
            assertEquals(false, viewModel.validationState.value.isNameValid)
            assertEquals(false, viewModel.validationState.value.isExpirationDateValid)
            assertEquals(false, viewModel.uiState.value.created)

            verify(medicineRepository, never()).create(any())
        }

    @Test
    fun `should reset created state`() =
        runTest {
            // When
            viewModel.consumeCreatedAction()

            // Then
            assertEquals(false, viewModel.uiState.value.created)
        }
}
