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

package dev.nick.imageloader.loader;

import dev.nick.imageloader.display.ImageQuality;

public class DecodeSpec {

    public static DecodeSpec NONE_SPEC = new DecodeSpec(ImageQuality.RAW, null);

    ImageQuality quality;
    ViewSpec viewSpec;

    public DecodeSpec(ImageQuality quality, ViewSpec viewSpec) {
        this.quality = quality;
        this.viewSpec = viewSpec;
    }

    public ImageQuality getQuality() {
        return quality;
    }

    public ViewSpec getViewSpec() {
        return viewSpec;
    }
}