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

package dev.nick.imageloader.cache;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;

public class MovieCacheManager implements CacheManager<Movie> {

    public MovieCacheManager(CachePolicy cachePolicy, Context context) {
        // Nothing
    }

    @Override
    public Movie get(String url) {
        return null;
    }

    @Override
    public String getCachePath(String url) {
        return null;
    }

    @Override
    public boolean cache(@NonNull String url, @NonNull Movie value) {
        return false;
    }

    @Override
    public boolean isDiskCacheEnabled() {
        return false;
    }

    @Override
    public boolean isMemCacheEnabled() {
        return false;
    }

    @Override
    public void evictDisk() {
        // Nothing
    }

    @Override
    public void evictMem() {
        // Nothing
    }

    @Override
    public CacheManager<Movie> fork(CachePolicy param) {
        return this;
    }
}
