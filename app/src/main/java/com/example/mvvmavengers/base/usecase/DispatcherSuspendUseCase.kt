
package com.example.mvvmavengers.base.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * DispatcherSuspendUseCase Class
 *
 * Executes business logic synchronously or asynchronously using Coroutines with provided Dispatcher
 */
abstract class DispatcherSuspendUseCase<Type, in Params>
    (private val coroutineDispatcher: CoroutineDispatcher) where Type : Any {

        abstract suspend fun execute(params: Params): ResultAvenger<Type>

        suspend operator fun invoke(params: Params): ResultAvenger<Type> =
            withContext(coroutineDispatcher) {
                execute(params)
            }
}
