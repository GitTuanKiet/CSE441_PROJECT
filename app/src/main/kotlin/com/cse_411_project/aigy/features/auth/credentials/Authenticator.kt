/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cse_411_project.aigy.features.auth.credentials

import android.content.SharedPreferences


class Authenticator(private val sharedPreferences: SharedPreferences) {

    fun userLoggedIn(): Int {
        val decentralization = sharedPreferences.getString("decentralization", "")
        if (decentralization.isNullOrEmpty()) return -1
        return when (decentralization) {
            "user" -> 1
            "admin" -> 0
            else -> -1
        }
    }
}
