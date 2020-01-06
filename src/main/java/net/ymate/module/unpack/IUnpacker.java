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

import net.ymate.platform.core.IApplication;
import net.ymate.platform.core.beans.annotation.Ignored;
import net.ymate.platform.core.support.IDestroyable;
import net.ymate.platform.core.support.IInitialization;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/08/02 下午 22:27
 */
@Ignored
public interface IUnpacker extends IInitialization<IApplication>, IDestroyable {

    String MODULE_NAME = "module.unpack";

    /**
     * 获取所属应用容器
     *
     * @return 返回所属应用容器实例
     */
    IApplication getOwner();

    /**
     * 获取配置
     *
     * @return 返回配置对象
     */
    IUnpackConfig getConfig();

    /**
     * 注册待提取资源
     *
     * @param name        待提取资源名称
     * @param targetClass 目标类型
     */
    void registerUnpack(String name, Class<? extends IUnpack> targetClass);

    /**
     * 注册待提取资源
     *
     * @param targetClass 目标类型
     */
    void registerUnpack(Class<? extends IUnpack> targetClass);

    /**
     * 执行资源提取
     */
    void unpack();
}