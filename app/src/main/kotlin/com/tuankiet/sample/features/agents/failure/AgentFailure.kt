package com.tuankiet.sample.features.agents.failure

import com.tuankiet.sample.core.failure.Failure.FeatureFailure

class AgentFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentMovie : FeatureFailure()
}

