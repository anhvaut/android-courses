package example.com.timtro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import example.com.timtro.R;
import example.com.timtro.adapters.FragmentAdapter;
import example.com.timtro.models.PhongTro;
import example.com.timtro.views.FragmentChiTietTro1;
import example.com.timtro.views.FragmentChiTietTro2;

public class ChiTietTroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public PhongTro phongTro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiettro);
        Bundle bundle = getIntent().getExtras();
        phongTro = (PhongTro) bundle.getSerializable("dulieu");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        add();
    }

    private void add(){
        viewPager = (ViewPager) findViewById(R.id.vp1);
        tabLayout = (TabLayout) findViewById(R.id.tl1);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentAdapter fragmentAdapter= new FragmentAdapter(fragmentManager);
        fragmentAdapter.insertFragment(new FragmentChiTietTro1());
        fragmentAdapter.insertFragment(new FragmentChiTietTro2());
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
