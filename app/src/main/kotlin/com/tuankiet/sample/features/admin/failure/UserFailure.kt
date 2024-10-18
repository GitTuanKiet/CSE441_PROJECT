package com.tuankiet.sample.features.admin.failure

import com.tuankiet.sample.core.failure.Failure.FeatureFailure
class UserFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentUser : FeatureFailure()
    class DeleteUserError : FeatureFailure()
    class ChangeUserError : FeatureFailure()
}