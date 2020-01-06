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

import net.ymate.platform.core.ApplicationEvent;
import net.ymate.platform.core.event.Events;
import net.ymate.platform.core.event.IEventListener;
import net.ymate.platform.core.event.IEventRegister;
import net.ymate.platform.core.event.annotation.EventRegister;
import net.ymate.platform.core.module.ModuleEvent;

/**
 * @author 刘镇 (suninformation@163.com) on 16/9/30 下午3:33
 */
@EventRegister
public class UnpackEventProcessor implements IEventRegister {

    @Override
    public void register(Events events) {
        events.registerListener(ModuleEvent.class, (IEventListener<ModuleEvent>) context -> {
            if (context.getEventName() == ModuleEvent.EVENT.MODULE_INITIALIZED) {
                if (context.getSource() instanceof IUnpack) {
                    Unpacker.get().registerUnpack(context.getSource().getName(), ((IUnpack) context.getSource()).getClass());
                }
            }
            return false;
        }).registerListener(ApplicationEvent.class, (IEventListener<ApplicationEvent>) context -> {
            if (context.getEventName() == ApplicationEvent.EVENT.APPLICATION_INITIALIZED) {
                Unpacker.get().unpack();
            }
            return false;
        });
    }
}
