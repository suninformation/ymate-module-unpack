/*
 * Copyright 2007-2020 the original author or authors.
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
package net.ymate.module.unpack.impl;

import net.ymate.module.unpack.IUnpackConfig;
import net.ymate.module.unpack.IUnpacker;
import net.ymate.platform.core.module.IModuleConfigurer;
import net.ymate.platform.core.module.impl.DefaultModuleConfigurable;

/**
 * @author 刘镇 (suninformation@163.com) on 2020/02/16 16:03
 */
public class DefaultUnpackConfigurable extends DefaultModuleConfigurable {

    public static Builder builder() {
        return new Builder();
    }

    private DefaultUnpackConfigurable() {
        super(IUnpacker.MODULE_NAME);
    }

    public static final class Builder {

        private final DefaultUnpackConfigurable configurable = new DefaultUnpackConfigurable();

        private Builder() {
        }

        public Builder enabled(boolean enabled) {
            configurable.addConfig(IUnpackConfig.ENABLED, String.valueOf(enabled));
            return this;
        }

        public Builder disabledUnpackList(String disabledUnpackList) {
            configurable.addConfig(IUnpackConfig.DISABLED_UNPACK_LIST, disabledUnpackList);
            return this;
        }

        public IModuleConfigurer build() {
            return configurable.toModuleConfigurer();
        }
    }
}
