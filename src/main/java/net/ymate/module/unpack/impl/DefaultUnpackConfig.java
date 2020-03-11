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
import net.ymate.module.unpack.annotation.UnpackConf;
import net.ymate.platform.core.configuration.IConfigReader;
import net.ymate.platform.core.module.IModuleConfigurer;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/08/02 下午 22:27
 */
public class DefaultUnpackConfig implements IUnpackConfig {

    private boolean enabled = true;

    private Set<String> disabledUnpacks = new HashSet<>();

    private boolean initialized;

    public static IUnpackConfig defaultConfig() {
        return builder().build();
    }

    public static IUnpackConfig create(IModuleConfigurer moduleConfigurer) {
        return new DefaultUnpackConfig(null, moduleConfigurer);
    }

    public static IUnpackConfig create(Class<?> mainClass, IModuleConfigurer moduleConfigurer) {
        return new DefaultUnpackConfig(mainClass, moduleConfigurer);
    }

    public static Builder builder() {
        return new Builder();
    }

    private DefaultUnpackConfig() {
    }

    private DefaultUnpackConfig(Class<?> mainClass, IModuleConfigurer moduleConfigurer) {
        IConfigReader configReader = moduleConfigurer.getConfigReader();
        //
        UnpackConf confAnn = mainClass == null ? null : mainClass.getAnnotation(UnpackConf.class);
        //
        enabled = configReader.getBoolean(ENABLED, confAnn == null || confAnn.enabled());
        //
        String[] disabledUnpackList = configReader.getArray(DISABLED_UNPACK_LIST, confAnn != null ? confAnn.disabledUnpacks() : null);
        if (ArrayUtils.isNotEmpty(disabledUnpackList)) {
            disabledUnpacks.addAll(Arrays.asList(disabledUnpackList));
        }
    }

    @Override
    public void initialize(IUnpacker owner) throws Exception {
        if (!initialized) {
            initialized = true;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (!initialized) {
            this.enabled = enabled;
        }
    }

    @Override
    public Set<String> getDisabledUnpacks() {
        return Collections.unmodifiableSet(disabledUnpacks);
    }

    public void addDisabledUnpack(String disabledUnpack) {
        if (!initialized) {
            disabledUnpacks.add(disabledUnpack);
        }
    }

    public static final class Builder {

        private final DefaultUnpackConfig config = new DefaultUnpackConfig();

        private Builder() {
        }

        public Builder enabled(boolean enabled) {
            config.setEnabled(enabled);
            return this;
        }

        public Builder addDisabledUnpack(String... disabledUnpacks) {
            if (disabledUnpacks != null && disabledUnpacks.length > 0) {
                Arrays.stream(disabledUnpacks).forEach(config::addDisabledUnpack);
            }
            return this;
        }

        public IUnpackConfig build() {
            return config;
        }
    }
}