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

package dev.nick.accessoriestest.media;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.Animation;

import dev.nick.accessories.injection.Injector;
import dev.nick.accessories.injection.annotation.binding.BindView;
import dev.nick.accessories.logger.LoggerManager;
import dev.nick.accessories.media.loader.MediaLoader;
import dev.nick.accessories.media.loader.ui.MediaHolder;
import dev.nick.accessories.media.loader.worker.ProgressListener;
import dev.nick.accessories.media.loader.worker.result.Cause;
import dev.nick.accessories.media.loader.worker.result.ErrorListener;
import dev.nick.accessoriestest.R;

public class AssetGifTest extends BaseTest {

    final String urlAssets = "assets://test_movie.gif";

    @BindView(R.id.movie)
    SimpleGifView simpleGifView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_movie_layout);
        setTitle(getClass().getSimpleName());
        Injector.shared().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MediaLoader.shared()
                .loadMovie()
                .from(urlAssets)
                .errorListener(new ErrorListener() {
                    @Override
                    public void onError(@NonNull Cause cause) {
                        LoggerManager.getLogger(getClass()).error(cause);
                    }
                })
                .progressListener(new ProgressListener<Movie>() {
                    @Override
                    public void onStartLoading() {
                        LoggerManager.getLogger(getClass()).debug("onStartLoading");
                    }

                    @Override
                    public void onProgressUpdate(float progress) {
                        LoggerManager.getLogger(getClass()).debug("onProgressUpdate:" + progress);
                    }

                    @Override
                    public void onCancel() {
                        LoggerManager.getLogger(getClass()).debug("onCancel");
                    }

                    @Override
                    public void onComplete(Movie result) {
                        LoggerManager.getLogger(getClass()).debug("onComplete:" + result);
                    }
                })
                .into(new MediaHolder<Movie>() {
                    @Override
                    public void seat(@NonNull Movie image) {
                        LoggerManager.getLogger(getClass()).debug("seat");
                        simpleGifView.setMovie(image);
                    }

                    @Override
                    public int getWidth() {
                        return 1000;
                    }

                    @Override
                    public int getHeight() {
                        return 500;
                    }

                    @Override
                    public void startAnimation(Animation animation) {
                        LoggerManager.getLogger(getClass()).debug("startAnimation");
                    }
                })
                .start();
    }
}
