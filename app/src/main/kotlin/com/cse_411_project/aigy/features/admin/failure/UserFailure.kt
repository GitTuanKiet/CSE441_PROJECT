package com.cse_411_project.aigy.features.admin.failure

import com.cse_411_project.aigy.core.failure.Failure.FeatureFailure
class UserFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentUser : FeatureFailure()
    class DeleteUserError : FeatureFailure()
    class ChangeUserError : FeatureFailure()
}