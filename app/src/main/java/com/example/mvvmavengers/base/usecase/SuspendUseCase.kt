
package com.example.mvvmavengers.base.usecase

/**
 * SuspendUseCase Class
 *
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class SuspendUseCase<Type, in Params> where Type : Any {

        abstract suspend fun execute(params: Params): ResultAvenger<Type>

        suspend operator fun invoke(params: Params): ResultAvenger<Type> =
            execute(params)
}
