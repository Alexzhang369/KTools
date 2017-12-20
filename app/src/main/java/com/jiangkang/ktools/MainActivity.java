package com.jiangkang.ktools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jiangkang.ktools.device.MediaContentObserver;
import com.jiangkang.ktools.device.ScreenShotMonitorService;
import com.jiangkang.ktools.web.WebActivity;
import com.jiangkang.tools.service.ScanMusicService;
import com.jiangkang.tools.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private static final String TAG = "MainActivity";
    @BindView(R.id.rc_function_list)
    RecyclerView mRcFunctionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader loader = MainActivity.class.getClassLoader();
        if (loader != null){
            Log.d(TAG, "onCreate: classloader:" + loader.toString());
            Log.d(TAG, "onCreate: classloader:" + loader.getParent().toString());
        }

        ButterKnife.bind(this);
        initViews();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0 ){
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "关于");
        menu.add(0, 1, 1, "源代码");
        menu.add(0, 2, 2, "文章解析");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AboutActivity.launch(this, null);
                break;
            case 1:
                openBrowser("https://github.com/jiangkang/KTools");
                break;
            case 2:
                openBrowser("http://www.jianshu.com/u/2c22c64b9aff");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBrowser(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("launchUrl", url);
        WebActivity.launch(this, bundle);
    }

    private void initViews() {
        mRcFunctionList.setLayoutManager(new GridLayoutManager(this, 4));
        mRcFunctionList.setAdapter(new FunctionAdapter(this));

//        ToastUtils.showShortToast(stringFromJNI());
    }

    private native String stringFromJNI();


    static {
        System.loadLibrary("native-lib");
    }




}
