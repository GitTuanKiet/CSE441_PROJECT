package com.cse_411_project.aigy.features.agents.data

import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.failure.Failure.NetworkConnection
import com.cse_411_project.aigy.core.failure.Failure.ServerError
import com.cse_411_project.aigy.core.functional.Either
import com.cse_411_project.aigy.core.functional.Either.Left
import com.cse_411_project.aigy.core.functional.Either.Right
import com.cse_411_project.aigy.core.network.NetworkHandler
import com.cse_411_project.aigy.features.agents.interactor.Agent
import com.cse_411_project.aigy.features.agents.interactor.AgentDetails
import retrofit2.Call

interface AgentStoreRepository {
    fun agentDetails(identifier: String): Either<Failure, AgentDetails>
    fun agents(): Either<Failure, List<Agent>>
    fun categories(): Either<Failure, List<String>>

    class Network(
        private val networkHandler: NetworkHandler,
        private val service: AgentStoreService
    ) : AgentStoreRepository {

        override fun agentDetails(identifier: String): Either<Failure, AgentDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.agentDetails(identifier),
                    { it.toAgentDetails() },
                    AgentDetails.empty
                )
                false -> Left(NetworkConnection)
            }
        }

        override fun agents(): Either<Failure, List<Agent>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.agentStore(),
                    { it.toAgents() },
                    emptyList()
                )
                false -> Left(NetworkConnection)
            }
        }

        override fun categories(): Either<Failure, List<String>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.agentStore(),
                    { it.toCategories() },
                    emptyList()
                )
                false -> Left(NetworkConnection)
            }
        }

        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: R
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(response.body()?.let { transform(response.body()!!) } ?: default)
                    false -> Left(ServerError)
                }
            } catch (exception: Throwable) {
                Left(ServerError)
            }
        }
    }
}