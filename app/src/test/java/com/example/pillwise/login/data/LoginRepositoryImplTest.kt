package com.example.pillwise.login.data

import com.example.pillwise.feature.login.data.LoginRepositoryImpl
import org.junit.Test

// you can use Truth, mockk, mockito etc - whatever you are comfortable with
class LoginRepositoryImplTest {

    @Test
    fun `test login somehow`() {
        val repository = LoginRepositoryImpl()

        val result = repository.login()

        assert(result.isSuccess)
    }

}