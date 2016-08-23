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

package dev.nick.imageloader;

import android.graphics.Bitmap;

import java.util.ArrayList;

import dev.nick.imageloader.cache.CacheManager;
import dev.nick.imageloader.ui.DisplayOption;
import dev.nick.imageloader.ui.MediaChair;
import dev.nick.imageloader.ui.animator.ViewAnimator;
import dev.nick.imageloader.ui.art.ImageArt;
import dev.nick.imageloader.worker.DimenSpec;
import dev.nick.imageloader.worker.ProgressListener;
import dev.nick.imageloader.worker.task.DisplayTaskRecord;
import dev.nick.imageloader.worker.task.TaskManager;

class BitmapProgressListenerDelegate extends ProgressListenerDelegate<Bitmap> {


    BitmapProgressListenerDelegate(CacheManager<Bitmap> cacheManager,
                                   TaskManager taskManager,
                                   ProgressListener<Bitmap> listener,
                                   DimenSpec dimenSpec,
                                   DisplayOption<Bitmap> option,
                                   MediaChair<Bitmap> mediaChair,
                                   DisplayTaskRecord taskRecord,
                                   String url) {
        super(cacheManager, taskManager, listener, dimenSpec, option, mediaChair, taskRecord, url);
    }

    @Override
    protected void callOnComplete(Bitmap result) {
        if (!canceled) {
            UIThreadRouter.getSharedRouter().callOnComplete(listener, result);
        }
    }

    @Override
    public void onComplete(Bitmap result) {

        callOnComplete(result);

        if (result == null) {
            mLogger.warn("onComplete call with null result");
            return;
        }

        if (canceled) {
            cacheManager.cache(url, result);
            mLogger.verbose("Skip calling back, canceled");
            return;
        }

        final boolean isViewMaybeReused = option.isViewMaybeReused();

        mLogger.verbose("isViewMaybeReused: " + isViewMaybeReused);

        if (!isViewMaybeReused || !checkTaskDirty()) {
            ViewAnimator<Bitmap> animator = (option == null ? null : option.getAnimator());
            ArrayList<ImageArt<Bitmap>> imageArts = (option == null ? null : option.getArtist());
            UISettingApplier.getSharedApplier().applySettings(result, imageArts, settable, animator);
        }
        cacheManager.cache(url, result);
    }
}
