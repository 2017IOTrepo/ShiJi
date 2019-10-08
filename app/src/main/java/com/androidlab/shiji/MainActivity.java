package com.androidlab.shiji;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidlab.shiji.activity.LoginActivity;
import com.androidlab.shiji.activity.SideMenu.SlideSetting;
import com.androidlab.shiji.activity.SignupActivity;
import com.androidlab.shiji.bean.User;
import com.androidlab.shiji.ui.adapter.MainViewPagerAdapter;
import com.androidlab.shiji.ui.view.AlertDialog;
import com.androidlab.shiji.utils.LoggerUtil;
import com.androidlab.shiji.utils.StaticVariable;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;


import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

import static com.androidlab.shiji.R.id.tab;

public class MainActivity extends AppCompatActivity {


    int[] testColors = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};
//    int[] testColors = {0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688};

    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    private AlertDialog mDialog;
    private long mExitTime;
    private IProfile profile;

    //private ProgressBar progress_update;


    NavigationController mNavigationController;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageNavigationView pageBottomTabLayout = findViewById(tab);
        ViewPager viewPager = findViewById(R.id.viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //  progress_update = findViewById(R.id.progress_update);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.ser, "搜索", testColors[0])
                .addItem(R.drawable.map1, "地图", testColors[1])
//                .addItem(R.drawable.ic_book_black_24dp, "Books", testColors[2])
                .addItem(R.drawable.kepu, "科普", testColors[3])
                .setDefaultColor(0x89FFFFFF)//未选中状态的颜色
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR | MaterialMode.HIDE_TEXT)//这里可以设置样式模式，总共可以组合出4种效果
                .build();

        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), mNavigationController.getItemCount()));

        // 自动适配ViewPager页面切换
        mNavigationController.setupWithViewPager(viewPager);
        //设置消息圆点
//        mNavigationController.setMessageNumber(0,1);
        mNavigationController.setHasMessage(2, true);

        // 也可以设置Item选中事件的监听
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.i("asd", "selected: " + index + " old: " + old);
            }

            @Override
            public void onRepeat(int index) {
                Log.i("asd", "onRepeat selected: " + index);
            }
        });

        //侧滑栏
        profile = new ProfileDrawerItem()
                .withName(User.INSTANCE.Name)
                .withEmail(User.INSTANCE.Email)
                .withIcon(R.drawable.touxiang);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.side_back)
                .addProfiles(
                        profile
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //侧滑栏
        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDrawerLayout(R.layout.side_menu_list)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .withScrollToTopAfterClick(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.side_user).withDescription("查看个人信息").withIcon(MaterialDesignIconic.Icon.gmi_car).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.side_set).withIcon(MaterialDesignIconic.Icon.gmi_directions_run).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.side_update).withIcon(MaterialDesignIconic.Icon.gmi_truck).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.side_star).withDescription("查看您的收藏内容").withIcon(MaterialDesignIconic.Icon.gmi_airplane).withIdentifier(4),

                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.change_account).withIcon(FontAwesome.Icon.faw_user_md).withIdentifier(5).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.side_about).withIcon(FontAwesome.Icon.faw_user_secret).withIdentifier(6).withSelectable(false)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1) {
                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();

                            return true;
                        } else if (drawerItem.getIdentifier() == 2) {
//                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, SlideSetting.class);
                            startActivity(i);
                            return true;
                        } else if (drawerItem.getIdentifier() == 3) {
                            //Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle("升级检测")//这里设置标题
                                            .setMessage("已经是最新版本！")//这里设置提示信息
                                            .setTopImage(R.drawable.dialog_update)//这里设置顶部图标
                                            .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    mDialog.dismiss();
                                                }
                                            });
                                    mDialog = builder.create();
                                    mDialog.show();
                                }
                            }, 500);

                            return true;
                        } else if (drawerItem.getIdentifier() == 4) {
                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();

                            return true;
                        } else if (drawerItem.getIdentifier() == 5) {
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            return true;
                        } else if (drawerItem.getIdentifier() == 6) {
//                            new LibsBuilder()
//                                    .withFields(R.string.class.getFields())
//                                    .withActivityStyle(Libs.ActivityStyle.DARK)
//                                    .start(MainActivity.this);
                            Intent intent = new Intent();
                            Uri uri = Uri.parse("https://github.com/xmmmmmovo/ShiJi");
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            if (drawerItem instanceof Nameable) {
                                Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();


        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        MiniDrawer miniResult = result.getMiniDrawer();
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });


        crossfadeDrawerLayout.withCrossfadeListener(new CrossfadeDrawerLayout.CrossfadeListener() {
            @Override
            public void onCrossfade(View containerView, float currentSlidePercentage, int slideOffset) {
                //Log.e("CrossfadeDrawerLayout", "crossfade: " + currentSlidePercentage + " - " + slideOffset);
            }
        });

        init();
        read();
        if (StaticVariable.isLogin) {
            setProfile();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("注意")
                    .setMessage("检测到你没有登录 请现在登录")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Start the Signup activity
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivityForResult(intent, 1);
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        LoggerUtil.showToastLong(MainActivity.this, String.valueOf(resultCode));
        if (resultCode == RESULT_OK) {
            save();
        }
    }

    private void init() {
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = preferences.edit();
    }

    private void save() {
        editor.putInt("Id", User.INSTANCE.Id);
        editor.putString("Name", User.INSTANCE.Name);
        editor.putString("Email", User.INSTANCE.Email);
        editor.putString("Password", User.INSTANCE.Password);
        editor.putBoolean("isLogin", StaticVariable.isLogin);
        editor.apply();
    }

    private void read() {
        User.INSTANCE.Id = preferences.getInt("Id", 0);
        User.INSTANCE.Name = preferences.getString("Name", "");
        User.INSTANCE.Email = preferences.getString("Email", "");
        User.INSTANCE.Password = preferences.getString("Password", "");
        StaticVariable.isLogin = preferences.getBoolean("isLogin", false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setProfile() {
        profile.withName(User.INSTANCE.Name);
        profile.withEmail(User.INSTANCE.Email);
        headerResult.updateProfile(profile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProfile();
    }
}

