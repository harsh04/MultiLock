package com.webconsole.harshmathur.multilock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,FragmentDrawer.FragmentDrawerListener{
    private static final int RC_SIGN_IN = 0;
    FirebaseAuth auth;
    Intent sendAccInfo;

    private PackageManager manager;
    private List<AppDetail> apps;
    private ListView list;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        //auth = FirebaseAuth.getInstance();
        //sendAccInfo =  new Intent(this, AccountInfo.class);
        //
        //if(auth.getCurrentUser() != null){
        //    Log.d("AUTH",auth.getCurrentUser().getEmail());
        //    AccountInfo.nameA = auth.getCurrentUser().getDisplayName();
        //    AccountInfo.emailA = auth.getCurrentUser().getEmail();
        //}else {                                           TODO: make this work . currently in main_activity2 , bring it in either home fragment or main_activity
        //    startActivityForResult(AuthUI.getInstance()
        //            .createSignInIntentBuilder()
        //            .setProviders(AuthUI.EMAIL_PROVIDER)
        //            .build(),RC_SIGN_IN);
        //}
        //findViewById(R.id.sign_out_button).setOnClickListener(this);
        //loadApps();
        //loadListView();
        //addClickListner();
    }

    private void loadApps(){
        manager = getPackageManager();
        apps = new ArrayList<AppDetail>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> avaiableActivites = manager.queryIntentActivities(i, 0);

        for(ResolveInfo ri : avaiableActivites){
            AppDetail app = new AppDetail();
            app.label = ri.loadLabel(manager);
            app.name = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(manager);
            apps.add(app);
        }
    }

    private void loadListView(){
        list = (ListView) findViewById(R.id.apps_list);

        ArrayAdapter<AppDetail> adapter = new ArrayAdapter<AppDetail>(this, R.layout.list_item, apps){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.list_item, null);
                }
                ImageView appIcon = (ImageView) convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appLable = (TextView) convertView.findViewById(R.id.item_app_label);
                appLable.setText(apps.get(position).label);

                TextView appName = (TextView) convertView.findViewById(R.id.item_app_name);
                appName.setText(apps.get(position).name);


                return convertView;

            }
        };

        list.setAdapter(adapter);

    }

    private void addClickListner(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = manager.getLaunchIntentForPackage(apps.get(position).name.toString());
                MainActivity.this.startActivity(i);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Log.d("AUTH", "User loggedin as : " + auth.getCurrentUser().getEmail());
                AccountInfo.nameA = auth.getCurrentUser().getDisplayName();
                AccountInfo.emailA = auth.getCurrentUser().getEmail();
            }
            else {
                Log.d("AUTH", "Not Authenticated");
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                AuthUI.getInstance()
                        .signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("AUTH","User logged out");
                        finish();
                    }
                });
                return true;
            case R.id.account_detail:


                startActivity(sendAccInfo);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
       // if(v.getId() == R.id.sign_out_button){

        //}
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new Fragment_2();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new Fragment_3();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void close(MenuItem item) {
        finish();
    }
}
