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

import net.ymate.platform.core.beans.annotation.Ignored;
import net.ymate.platform.core.support.IInitialization;

import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/08/02 下午 22:27
 */
@Ignored
public interface IUnpackConfig extends IInitialization<IUnpacker> {

    String ENABLED = "enabled";

    String DISABLED_UNPACK_LIST = "disabled_unpack_list";

    /**
     * 解包器模块是否已启用, 默认值: true
     *
     * @return 返回false表示禁用
     */
    boolean isEnabled();

    /**
     * 禁止解包列表, 多个包名称之间使用'|'分隔, 默认值：空
     *
     * @return 返回被禁止的包名称集合
     */
    Set<String> getDisabledUnpacks();
}