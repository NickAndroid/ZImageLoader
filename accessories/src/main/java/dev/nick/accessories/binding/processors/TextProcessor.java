/*
 * Copyright (c) 2016 Nick Guo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.nick.accessories.binding.processors;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import dev.nick.accessories.binding.annotation.binding.BindText;

class TextProcessor extends FieldProcessor {

    public TextProcessor(Context appContext) {
        super(appContext);
    }

    @Override
    protected Object parseField(Field field) {
        BindText bindText = field.getAnnotation(BindText.class);
        int id = bindText.value();
        checkId(id);
        return getContext().getText(id);
    }

    @NonNull
    @Override
    public Class<? extends Annotation> targetAnnotation() {
        return BindText.class;
    }
}
