/*
 * Copyright (C) 2022-2023 The shiro-jjwt-showcase team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.bmarwell.shiro.issuer.dto;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class LoginCredentials {

    @JsonbProperty("username")
    private String username;

    /**
     * This is a big code smell. Do never store your passwords potentially readable and permanently like so.
     */
    @JsonbProperty("password")
    private char[] password;

    public static LoginCredentials fromParameters(String username, String password) {
        return new LoginCredentials(username, password);
    }

    public LoginCredentials() {
        // jsonb
    }

    @JsonbCreator
    public LoginCredentials(@JsonbProperty("username") String username, @JsonbProperty("password") String password) {
        this.username = requireNonNull(username);
        this.password = requireNonNull(password).toCharArray();
    }

    public String getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LoginCredentials that = (LoginCredentials) other;

        return username.equals(that.username) && Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}
