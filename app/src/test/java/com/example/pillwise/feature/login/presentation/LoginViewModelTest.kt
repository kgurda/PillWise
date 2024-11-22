import com.example.pillwise.feature.login.data.model.LoginResultEntity
import com.example.pillwise.feature.login.domain.LoginUseCase
import com.example.pillwise.feature.login.presentation.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    private val loginUseCase: LoginUseCase = mock(LoginUseCase::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login successful updates uiState to logged in`() = runTest {
        // Arrange
        `when`(loginUseCase.execute(any(), any())).thenReturn(Result.success(LoginResultEntity("Something")))

        // Act
        loginViewModel.login("testuser", "testpassword")

        // Assert
        val uiState = loginViewModel.uiState.first()
        assertEquals(true, uiState.loggedIn)
        assertEquals(false, uiState.isLoading)
        assertEquals(null, uiState.error)
    }

    @Test
    fun `login failure updates uiState to error`() = runTest {
        // Arrange
        `when`(loginUseCase.execute(any(), any())).thenReturn(Result.failure(Exception("Login failed")))

        // Act
        loginViewModel.login("testuser", "testpassword")

        // Assert
        val uiState = loginViewModel.uiState.first()
        assertEquals(false, uiState.loggedIn)
        assertEquals(false, uiState.isLoading)
        assertEquals("anything", uiState.error)
    }
}