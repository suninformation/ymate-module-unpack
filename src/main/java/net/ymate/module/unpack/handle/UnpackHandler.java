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
package net.ymate.module.unpack.handle;

import net.ymate.module.unpack.IUnpack;
import net.ymate.module.unpack.IUnpacker;
import net.ymate.platform.commons.util.ClassUtils;
import net.ymate.platform.core.beans.IBeanHandler;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/8/2 下午10:33
 */
public class UnpackHandler implements IBeanHandler {

    private IUnpacker owner;

    public UnpackHandler(IUnpacker owner) {
        this.owner = owner;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object handle(Class<?> targetClass) throws Exception {
        if (ClassUtils.isInterfaceOf(targetClass, IUnpack.class)) {
            owner.registerUnpack((Class<? extends IUnpack>) targetClass);
        }
        return null;
    }
}
