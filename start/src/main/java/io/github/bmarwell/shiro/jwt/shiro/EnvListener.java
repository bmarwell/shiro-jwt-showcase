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
package io.github.bmarwell.shiro.jwt.shiro;

import javax.enterprise.context.Dependent;
import javax.servlet.annotation.WebListener;
import org.apache.shiro.web.env.EnvironmentLoaderListener;

@Dependent
@WebListener
public class EnvListener extends EnvironmentLoaderListener {

    public EnvListener() {
        super();
    }
}
