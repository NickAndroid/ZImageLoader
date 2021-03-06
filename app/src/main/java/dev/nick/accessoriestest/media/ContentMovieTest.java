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

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import dev.nick.accessories.injection.Injector;
import dev.nick.accessories.injection.annotation.binding.BindView;
import dev.nick.accessories.injection.annotation.permission.RequestPermissions;
import dev.nick.accessories.media.loader.LoaderConfig;
import dev.nick.accessories.media.loader.MediaLoader;
import dev.nick.accessories.media.loader.cache.CachePolicy;
import dev.nick.accessories.media.loader.ui.DisplayOption;
import dev.nick.accessories.media.loader.ui.MediaQuality;
import dev.nick.accessories.media.loader.ui.animator.FadeInViewAnimator;
import dev.nick.accessories.media.loader.worker.network.NetworkPolicy;
import dev.nick.accessoriestest.R;

@RequestPermissions(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
public class ContentMovieTest extends BaseTest {

    static String mArtworkUri = "content://media/external/audio/albumart";
    @BindView(R.id.list)
    ListView listView;
    MediaLoader mLoader;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_image_layout);
        setTitle(getClass().getSimpleName());
        Injector.shared().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final List<Track> tracks = MediaUtils.getTrackList(this);

        mLoader = MediaLoader.shared().fork(LoaderConfig.builder()
                .cachePolicy(CachePolicy.builder()
                        .enableMemCache()
                        .enableDiskCache()
                        .cachingThreads(Runtime.getRuntime().availableProcessors())
                        .cacheDirName("dis.cache.tests.content")
                        .preferredLocation(CachePolicy.Location.INTERNAL)
                        .compressFormat(Bitmap.CompressFormat.JPEG)
                        .build())
                .networkPolicy(NetworkPolicy.builder().trafficStatsEnabled(true).build())
                .build());

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return tracks.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.textView.setText(tracks.get(position).getTitle());

                String uri = mArtworkUri + File.separator + tracks.get(position).getAlbumId();

                mLoader.loadBitmap()
                        .from(uri)
                        .option(DisplayOption.bitmapBuilder()
                                .imageQuality(MediaQuality.RAW)
                                .viewMaybeReused()
                                .imageAnimator(new FadeInViewAnimator())
                                .build())
                        .into(holder.imageView)
                        .start();

                return convertView;
            }
        };

        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoader.clearMemCache();
        mLoader.terminate();
    }

    class ViewHolder implements BindView.RootViewProvider {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.textView)
        TextView textView;

        View mRoot;

        public ViewHolder(View convert) {
            mRoot = convert;
            Injector.shared().inject(this);
        }

        @NonNull
        @Override
        public View getRootView() {
            return mRoot;
        }
    }
}
