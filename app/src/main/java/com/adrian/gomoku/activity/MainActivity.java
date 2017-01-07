package com.adrian.gomoku.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian.gomoku.R;
import com.adrian.gomoku.fragment.MainFragment;
import com.adrian.gomoku.service.BgMusicService;
import com.adrian.gomoku.tools.ParamUtil;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private static final int REQ_THEME = 1;
    private static final int REQ_MODE = 2;
    private static final int REQ_OTHER = 3;
    private static final int REQ_ABOUT = 4;
    public static final int RES_THEME = 0xa0;
    public static final int RES_MODE = 0xa1;
    public static final int RES_OTHER = 0xa2;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private MainFragment mainFragment;

    private long mLastBackPress;
    private static final long mBackPressThreshold = 3500;

    private boolean keepBgMusic = false;    //是否需要保持背景音乐

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

        Log.e("SDK_VERSION", "系统版本：" + Build.VERSION.SDK_INT);

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
        mainFragment = new MainFragment();
        addFragment(mainFragment, true, R.id.container);
    }

    @Override
    protected void loadData() {
//        startBgMusic();
    }

    private void startBgMusic() {
        if (ParamUtil.getInstance().openedBgMusic()) {
            Intent intent = new Intent(this, BgMusicService.class);
            intent.setAction(BgMusicService.ACTION_START_PLAY);
            startService(intent);
        }
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.mipmap.icn_close);

        MenuObject theme = new MenuObject(getString(R.string.theme_settings));
        theme.setResource(R.mipmap.theme);

        MenuObject mode = new MenuObject(getString(R.string.mode_choose));
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.mode);
        mode.setBitmap(b);

        MenuObject other = new MenuObject(getString(R.string.other_settings));
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.mipmap.settings));
        other.setDrawable(bd);

        MenuObject about = new MenuObject(getString(R.string.about));
        about.setResource(R.mipmap.about);

        menuObjects.add(close);
        menuObjects.add(theme);
        menuObjects.add(mode);
        menuObjects.add(other);
        menuObjects.add(about);
        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.mipmap.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText(R.string.app_name);
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        keepBgMusic = false;
        startBgMusic();
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            long currentTime = System.currentTimeMillis();
            if (Math.abs(currentTime - mLastBackPress) > mBackPressThreshold) {
//                Toast.makeText(this, R.string.back_again_quit, Toast.LENGTH_SHORT).show();
                showShortToast(getString(R.string.back_again_quit));
                mLastBackPress = currentTime;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!keepBgMusic) {
            Intent intent = new Intent(this, BgMusicService.class);
            intent.setAction(BgMusicService.ACTION_PAUSE_PLAY);
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, BgMusicService.class);
        stopService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RES_THEME:
                mainFragment.setTheme();
                break;
            case RES_MODE:
                mainFragment.setSinglePlayer();
                break;
            case RES_OTHER:
                mainFragment.setPieceSound();
                break;
            default:
                break;
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
//        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                break;
            case REQ_THEME:
                keepBgMusic = true;
                startActivityForResult(ThemeActivity.class, REQ_THEME);
                break;
            case REQ_MODE:
                keepBgMusic = true;
                startActivityForResult(ModeActivity.class, REQ_MODE);
                break;
            case REQ_OTHER:
                keepBgMusic = true;
                startActivityForResult(OtherActivity.class, REQ_OTHER);
                break;
            case REQ_ABOUT:
                keepBgMusic = true;
                startActivity(AboutActivity.class);
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
//        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

}
