import com.example.pillwise.feature.login.domain.LoginUseCase
import com.example.pillwise.feature.login.presentation.LoginViewModel
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
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        loginUseCase = mock()
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update username in uiState`() =
        runTest {
            // Given
            val username = "testUser"

            // When
            loginViewModel.setUsername(username)

            // Then
            assertEquals(username, loginViewModel.uiState.value.username)
        }

    @Test
    fun `should update password in uiState`() =
        runTest {
            // Given
            val password = "testPassword"

            // When
            loginViewModel.setPassword(password)

            // Then
            assertEquals(password, loginViewModel.uiState.value.password)
        }

    @Test
    fun `should update uiState when successfully logged in`() =
        runTest {
            // Given
            loginViewModel.setPassword("testuser")
            loginViewModel.setUsername("testpassword")

            `when`(loginUseCase.execute(any(), any())).thenReturn(Result.success(Unit))

            // When
            loginViewModel.login()
            advanceUntilIdle()

            // Then
            val uiState = loginViewModel.uiState.value
            assertEquals(true, uiState.loggedIn)
            assertEquals(false, uiState.isLoading)
            assertEquals(null, uiState.error)
        }

    @Test
    fun `should update uiState when login failed`() =
        runTest {
            // Given
            val error = "Login failed"
            loginViewModel.setPassword("testpassword")
            loginViewModel.setUsername("testusername")
            `when`(loginUseCase.execute(any(), any())).thenReturn(Result.failure(Exception(error)))

            // When
            loginViewModel.login()
            advanceUntilIdle()

            // Then
            val uiState = loginViewModel.uiState.value
            assertEquals(false, uiState.loggedIn)
            assertEquals(false, uiState.isLoading)
            assertEquals(error, uiState.error)
        }
}
