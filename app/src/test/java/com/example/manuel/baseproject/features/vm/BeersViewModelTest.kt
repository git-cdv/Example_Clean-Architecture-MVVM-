package com.example.manuel.baseproject.features.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity
import com.example.manuel.baseproject.features.beers.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.features.beers.domain.usecase.RemoveBeerUseCase
import com.example.manuel.baseproject.features.beers.domain.usecase.SaveBeerUseCase
import com.example.manuel.baseproject.features.beers.vm.BeersViewModel
import com.example.manuel.baseproject.features.domain.utils.DomainBeersGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 *  https://codelabs.developers.google.com/codelabs/android-testing/#7
 * */

// TODO Refactor checking the list values and specifying the expectedResult and realResult
@ExperimentalCoroutinesApi
class BeersViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()

    private var mockGetBeersUseCase: GetBeersUseCase = mockk()
    private var mockSaveBeersUseCase: SaveBeerUseCase = mockk()
    private var mockRemoveBeerUseCase: RemoveBeerUseCase = mockk()
    private lateinit var viewModel: BeersViewModel

    @Test
    fun verifyBeersLiveDataIsNotEmptyWhenResultIsSuccess() {
        mainCoroutineRule.runBlockingTest {
            // given
            coEvery { mockGetBeersUseCase.execute() } returns Result.success(DomainBeersGenerator.getSortedBeers())

            // when
            viewModel = BeersViewModel(
                    getMealsByBeersUseCase = mockGetBeersUseCase,
                    saveBeerUseCase = mockSaveBeersUseCase,
                    removeBeerUseCase = mockRemoveBeerUseCase
            )
            // then
            Assert.assertEquals(true, viewModel.beersLiveData.getOrAwaitValue().isNotEmpty())
        }
    }

    @Test
    fun verifyAreEmptyBeersLiveDataIsTrueWhenResultIsSuccess() {
        mainCoroutineRule.runBlockingTest {
            // given
            coEvery { mockGetBeersUseCase.execute() } returns Result.success(BeersEntity(listOf()))

            // when
            viewModel = BeersViewModel(
                    getMealsByBeersUseCase = mockGetBeersUseCase,
                    saveBeerUseCase = mockSaveBeersUseCase,
                    removeBeerUseCase = mockRemoveBeerUseCase
            )

            // then
            Assert.assertEquals(true, viewModel.areEmptyBeersLiveData.getOrAwaitValue())
        }
    }

    @Test
    fun verifyIsErrorLiveDataIsTrueWhenResultIsError() {
        mainCoroutineRule.runBlockingTest {
            // given
            coEvery { mockGetBeersUseCase.execute() } returns Result.error(Exception())

            // when
            mainCoroutineRule.pauseDispatcher()
            viewModel = BeersViewModel(
                    getMealsByBeersUseCase = mockGetBeersUseCase,
                    saveBeerUseCase = mockSaveBeersUseCase,
                    removeBeerUseCase = mockRemoveBeerUseCase
            )
            mainCoroutineRule.resumeDispatcher()

            // then
            Assert.assertEquals(true, viewModel.isErrorLiveData.getOrAwaitValue())
        }
    }

    @Test
    fun verifyIsLoadingLiveDataWhenResultIsSuccess() {
        mainCoroutineRule.runBlockingTest {
            // given
            coEvery { mockGetBeersUseCase.execute() } returns Result.success(DomainBeersGenerator.getSortedBeers())

            // when
            mainCoroutineRule.pauseDispatcher()

            viewModel = BeersViewModel(
                    getMealsByBeersUseCase = mockGetBeersUseCase,
                    saveBeerUseCase = mockSaveBeersUseCase,
                    removeBeerUseCase = mockRemoveBeerUseCase
            )

            Assert.assertEquals(true, viewModel.isLoadingLiveData.getOrAwaitValue())

            mainCoroutineRule.resumeDispatcher()

            // then
            Assert.assertEquals(false, viewModel.isLoadingLiveData.getOrAwaitValue())
        }
    }

    private fun whenViewModelHandleLoadBeers() {
        viewModel = BeersViewModel(
                getMealsByBeersUseCase = mockGetBeersUseCase,
                saveBeerUseCase = mockSaveBeersUseCase,
                removeBeerUseCase = mockRemoveBeerUseCase
        )
    }

    @Test
    fun verifyIsLoadingLiveDataWhenResultIsError() {
        mainCoroutineRule.runBlockingTest {
            // given
            coEvery { mockGetBeersUseCase.execute() } returns Result.error(Exception())

            // when
            mainCoroutineRule.pauseDispatcher()
            whenViewModelHandleLoadBeers()
            Assert.assertEquals(true, viewModel.isLoadingLiveData.getOrAwaitValue())
            mainCoroutineRule.resumeDispatcher()

            // then
            Assert.assertEquals(false, viewModel.isLoadingLiveData.getOrAwaitValue())
        }
    }
}
