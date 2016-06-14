/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.otosettings.systemdetail;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import java.util.List;

import com.android.otosettings.R;
import com.android.otosettings.Utils;

import java.util.Locale;

public class LocalePicker extends com.android.internal.app.LocalePicker
        implements com.android.internal.app.LocalePicker.LocaleSelectionListener{

    private static final String TAG = "LocalePicker";

    //private SettingsDialogFragment mDialogFragment;
    private static final int DLG_SHOW_GLOBAL_WARNING = 1;
    private static final String SAVE_TARGET_LOCALE = "locale";
    public static List<LocalePicker.LocaleInfo> locales;

    private Locale mTargetLocale;

    public LocalePicker() {
        super();
        setLocaleSelectionListener(this);
        Log.i("LocalePickerInit","=========init");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVE_TARGET_LOCALE)) {
            mTargetLocale = new Locale(savedInstanceState.getString(SAVE_TARGET_LOCALE));
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        final ListView list = (ListView) view.findViewById(android.R.id.list);
        Utils.forcePrepareCustomPreferencesList(container, view, list, false);
        return view;
    }

    @Override
    public void onLocaleSelected(final Locale locale) {
        if (Utils.hasMultipleUsers(getActivity())) {
            mTargetLocale = locale;
           // showDialog(DLG_SHOW_GLOBAL_WARNING);
           Log.i("onLocaleSelected1",mTargetLocale.toString());
        } else {
            getActivity().onBackPressed();
            LocalePicker.updateLocale(locale);
            Log.i("onLocaleSelected2",locale.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mTargetLocale != null) {
            outState.putString(SAVE_TARGET_LOCALE, mTargetLocale.toString());
        }
    }


    /**
     * Constructs an Adapter object containing Locale information. Content is sorted by
     * {@link LocaleInfo#label}.
     */
    public static ArrayAdapter<LocalePicker.LocaleInfo> constructAdapter(Context context) {

        return constructAdapter(context, R.layout.item_text_info, R.id.text1);
    }
    public static ArrayAdapter<LocalePicker.LocaleInfo> constructAdapter(Context context,
                                                            final int layoutId, final int fieldId) {
        boolean isInDeveloperMode = Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        //获取系统支持语言的信息
        final List<LocalePicker.LocaleInfo> localeInfos = LocalePicker.getAllAssetLocales(context, isInDeveloperMode);
        locales = localeInfos;
        final LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ArrayAdapter<LocalePicker.LocaleInfo>(context, layoutId, fieldId, localeInfos) {
            @Override
            public int getCount() {
                return localeInfos.size();
            }

            @Override
            public LocalePicker.LocaleInfo getItem(int position) {
                return localeInfos.get(position);
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                TextView text;
                if (convertView == null) {
                    view = inflater.inflate(layoutId, parent, false);
                    text = (TextView) view.findViewById(fieldId);
                    view.setTag(text);
                } else {
                    view = convertView;
                    text = (TextView) view.getTag();
                }
                LocalePicker.LocaleInfo item = getItem(position);
                text.setText(item.toString());
                text.setTextLocale(item.getLocale());
                return view;
            }
        };
    }

}
