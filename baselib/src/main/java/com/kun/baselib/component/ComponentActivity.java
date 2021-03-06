package com.kun.baselib.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;

/**
 * Created by kun on 2016/11/18.
 */

public abstract class ComponentActivity extends SlideActivity {
    private SparseArray<IActivityComponent> mComponents;

    /**
     * Register components
     *
     * @return the components needs to be register
     */
    protected abstract SparseArray<IActivityComponent> registerComponents();

    public SparseArray<IActivityComponent> getComponents() {
        return mComponents;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mComponents = registerComponents();
        super.onCreate(savedInstanceState);
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onCreate(this, savedInstanceState);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onRestart();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onDestroy();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onNewIntent(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int count = mComponents == null ? 0 : mComponents.size();
        for (int i = 0; i < count; i++) {
            mComponents.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }
}
