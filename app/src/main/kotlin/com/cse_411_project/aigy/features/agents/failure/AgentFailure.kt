package com.cse_411_project.aigy.features.agents.failure

import com.cse_411_project.aigy.core.failure.Failure.FeatureFailure

class AgentFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentAgent : FeatureFailure()
}

