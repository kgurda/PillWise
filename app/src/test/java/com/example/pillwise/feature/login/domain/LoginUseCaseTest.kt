import com.example.pillwise.feature.login.data.LoginRepository
import com.example.pillwise.feature.login.domain.LoginUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class LoginUseCaseTest {

    private lateinit var loginRepository: LoginRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginRepository = mock()
        loginUseCase = LoginUseCase(loginRepository)
    }

    @Test
    fun `should successfully login when repository returns success`() {
        // Given
        val username = "testUser"
        val password = "testPassword"
        val expectedResult = Result.success(Unit)

        `when`(loginRepository.login(username, password)).thenReturn(expectedResult)

        // When
        val result = loginUseCase.execute(username, password)

        // Then
        verify(loginRepository).login(username, password)
        assertTrue(result.isSuccess)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should return failure when exception is thrown from repository`() {
        // Given
        val username = "testUser"
        val password = "testPassword"
        val expectedError = Throwable("Login failed")

        `when`(loginRepository.login(username, password)).thenReturn(Result.failure(expectedError))

        // When
        val result = loginUseCase.execute(username, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }
}