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
package net.ymate.module.unpack;

import net.ymate.module.unpack.annotation.Unpack;
import net.ymate.module.unpack.handle.UnpackHandler;
import net.ymate.module.unpack.impl.DefaultUnpackConfig;
import net.ymate.platform.commons.util.FileUtils;
import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.core.ApplicationEvent;
import net.ymate.platform.core.IApplication;
import net.ymate.platform.core.IApplicationConfigurer;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.beans.IBeanLoadFactory;
import net.ymate.platform.core.beans.IBeanLoader;
import net.ymate.platform.core.event.IEventListener;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.IModuleConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/08/02 下午 22:27
 */
public class Unpacker implements IModule, IUnpacker {

    private static final Log LOG = LogFactory.getLog(Unpacker.class);

    private static volatile IUnpacker instance;

    private IApplication owner;

    private IUnpackConfig config;

    private Map<String, Class<? extends IUnpack>> unpackers = new HashMap<>();

    private boolean initialized;

    public static IUnpacker get() {
        IUnpacker inst = instance;
        if (inst == null) {
            synchronized (Unpacker.class) {
                inst = instance;
                if (inst == null) {
                    instance = inst = YMP.get().getModuleManager().getModule(Unpacker.class);
                }
            }
        }
        return inst;
    }

    @Override
    public String getName() {
        return IUnpacker.MODULE_NAME;
    }

    @Override
    public void initialize(IApplication owner) throws Exception {
        if (!initialized) {
            //
            YMP.showModuleVersion("ymate-module-unpack", this);
            //
            this.owner = owner;
            IApplicationConfigurer configurer = owner.getConfigureFactory().getConfigurer();
            if (config == null) {
                IModuleConfigurer moduleConfigurer = configurer.getModuleConfigurer(MODULE_NAME);
                config = moduleConfigurer == null ? DefaultUnpackConfig.defaultConfig() : DefaultUnpackConfig.create(moduleConfigurer);
            }
            if (!config.isInitialized()) {
                config.initialize(this);
            }
            if (config.isEnabled()) {
                IBeanLoadFactory beanLoaderFactory = configurer.getBeanLoadFactory();
                if (beanLoaderFactory != null) {
                    IBeanLoader beanLoader = beanLoaderFactory.getBeanLoader();
                    if (beanLoader != null) {
                        beanLoader.registerHandler(Unpack.class, new UnpackHandler(this));
                    }
                }
                owner.getEvents().registerListener(ApplicationEvent.class, (IEventListener<ApplicationEvent>) context -> {
                    if (ApplicationEvent.EVENT.APPLICATION_INITIALIZED.equals(context.getEventName())) {
                        unpack();
                    }
                    return false;
                });
            }
            initialized = true;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void close() {
        if (initialized) {
            initialized = false;
            //
            unpackers = null;
            //
            config = null;
            owner = null;
        }
    }

    @Override
    public IApplication getOwner() {
        return owner;
    }

    @Override
    public IUnpackConfig getConfig() {
        return config;
    }

    @Override
    public void registerUnpack(String name, Class<? extends IUnpack> targetClass) {
        if (config.isEnabled() && StringUtils.isNotBlank(name) && targetClass != null) {
            unpackers.put(name, targetClass);
        }
    }

    @Override
    public void registerUnpack(Class<? extends IUnpack> targetClass) {
        if (config.isEnabled() && targetClass != null) {
            Unpack unpackAnn = targetClass.getAnnotation(Unpack.class);
            if (unpackAnn != null) {
                Arrays.stream(unpackAnn.value()).forEachOrdered(name -> unpackers.put(name, targetClass));
            }
        }
    }

    @Override
    public synchronized void unpack() {
        boolean flag = false;
        File targetPath = new File(RuntimeUtils.getRootPath(false));
        String rootPath = RuntimeUtils.getRootPath();
        for (Map.Entry<String, Class<? extends IUnpack>> entry : unpackers.entrySet()) {
            if (!config.getDisabledUnpacks().contains(entry.getKey())) {
                File locker = new File(rootPath, String.format(".unpack%s%s", File.separator, entry.getKey()));
                if (!locker.exists()) {
                    try {
                        flag = true;
                        LOG.info(String.format("Unpacking %s...", entry.getValue()));
                        if (FileUtils.unpackJarFile(entry.getKey(), targetPath, entry.getValue())) {
                            locker.getParentFile().mkdirs();
                            locker.createNewFile();
                        }
                    } catch (IOException e) {
                        LOG.warn(String.format("Synchronizing resource [%s] exception", entry.getKey()), RuntimeUtils.unwrapThrow(e));
                    }
                }
            }
        }
        if (flag) {
            LOG.info("Synchronizing resource completed.");
        }
    }
}
