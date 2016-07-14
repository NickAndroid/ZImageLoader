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

package dev.nick.imageloader.display;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.animation.Animation;

/**
 * Interface that indicate this is a view that can be set with a image.
 * like the {@link android.widget.ImageView}
 */
public interface ImageSettable {
    @UiThread
    void setImageBitmap(@NonNull Bitmap bitmap);

    boolean setBackgroundDrawable(@Nullable Drawable drawable);

    @UiThread
    void setImageResource(int resId);

    int getWidth();

    int getHeight();

    @UiThread
    void startAnimation(Animation animation);
}
