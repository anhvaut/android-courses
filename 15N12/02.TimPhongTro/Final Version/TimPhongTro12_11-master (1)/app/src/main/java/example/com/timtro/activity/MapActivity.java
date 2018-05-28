package example.com.timtro.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.clustering.ClusterManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import example.com.timtro.interfaces.Click;
import example.com.timtro.R;
import example.com.timtro.models.MyItem;
import example.com.timtro.models.SampleData;
import example.com.timtro.models.TimNguoiOGhep;
import example.com.timtro.views.FragmentDangTinChoThue;
import example.com.timtro.views.FragmentDangTinOGhep;
import example.com.timtro.views.FragmentListOGhep;
import example.com.timtro.views.FragmentListTro;

import static example.com.timtro.R.id.map;
import static example.com.timtro.R.id.toolbar;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, Click {
    private GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;
    private ProgressDialog myProgress;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private FloatingActionButton fab;
    private static final String MYTAG = "MYTAG";
    public static final String NAME = "name";
    public static final String PICTURE = "picture";
    public static final String CHECK = "check";
    private boolean check = false;
    TextView tvnametk;
    ImageView imgavt, imglogout;
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getdatafacebook();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProgress = new ProgressDialog(MapActivity.this);
                myProgress.setTitle(R.string.map_loading);
                myProgress.setMessage("Please wait...");
                myProgress.setCancelable(true);
                myProgress.show();
                SupportMapFragment mapFragment
                        = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {

                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        onMyMapReady(googleMap);
                    }
                });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    private void getdatafacebook() {
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView1.getHeaderView(0);
        final SharedPreferences pre = getSharedPreferences("my_data", MODE_PRIVATE);
        final SharedPreferences.Editor edit = pre.edit();
        imglogout = (ImageView) hView.findViewById(R.id.imglogout);

        if (pre.getBoolean(CHECK, false) == false) {
            imglogout.setImageResource(R.drawable.login);
        } else {
            imglogout.setImageResource(R.drawable.logout);
        }
        tvnametk = (TextView) hView.findViewById(R.id.id_name_taikhoan);
        imgavt = (ImageView) hView.findViewById(R.id.imgavt);
        tvnametk.setText(pre.getString(NAME, "Đăng nhập"));
        Picasso.with(MapActivity.this).load(pre.getString(PICTURE, "")).into(imgavt);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pre.getBoolean(CHECK, false) == false) {
                    Intent intent = new Intent(MapActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    LoginManager.getInstance().logOut();
                    imglogout.setImageResource(R.drawable.login);
                    edit.putBoolean(CHECK, false);
                    edit.putString(NAME, "Đăng nhập");
                    edit.putString(PICTURE, "https://stocknews.com/wp-content/uploads/2017/06/facebook-fb-groups.png");
                    edit.commit();
                    tvnametk.setText(pre.getString(NAME, "Đăng nhập"));
                    Picasso.with(MapActivity.this).load(pre.getString(PICTURE, "")).into(imgavt);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
        if (id == R.id.search_view) {
        }
        if (id == R.id.ic_switch_view) {
            Fragment manager = getSupportFragmentManager().findFragmentByTag("Fragment1");
            if (check == true) {
                fab.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().remove(manager).commit();
                check = false;
            } else {
                fab.setVisibility(View.GONE);
                callFragment(new FragmentListTro());
                check = true;
            }
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
         int id = item.getItemId();

        if (id == R.id.tim_phong) {
            fab.setVisibility(View.VISIBLE);
            Fragment manager = getSupportFragmentManager().findFragmentByTag("Fragment1");
            if (manager != null)
                getSupportFragmentManager().beginTransaction().remove(manager).commit();
        } else if (id == R.id.tim_ghep) {
            fab.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Tìm Người Ở Ghép");
            Fragment manager = getSupportFragmentManager().findFragmentByTag("Fragment1");
            if (manager != null)
                getSupportFragmentManager().beginTransaction().remove(manager).commit();
            callFragment(new FragmentListOGhep());
        } else if (id == R.id.dang_cho_thue) {
            fab.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Đăng Tin Cho Thuê");
            Fragment manager = getSupportFragmentManager().findFragmentByTag("Fragment1");
            if (manager != null)
                getSupportFragmentManager().beginTransaction().remove(manager).commit();
            callFragment(new FragmentDangTinChoThue());
        } else if (id == R.id.dang_tim_ghep) {
            fab.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Đăng Tin Ở Ghép");
            Fragment manager = getSupportFragmentManager().findFragmentByTag("Fragment1");
            if (manager != null)
                getSupportFragmentManager().beginTransaction().remove(manager).commit();
            callFragment(new FragmentDangTinOGhep());


        } else if (id == R.id.bao_cao) {
            Intent intent = new Intent(MapActivity.this, TabBaocaoActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bk = new LatLng(16.075971, 108.153725);
        Marker marker = mMap.addMarker(new MarkerOptions().position(bk).title("Đại Học Bách Khoa ĐN").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).alpha(0.7f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bk, 15));
        marker.setVisible(true);
        marker.showInfoWindow();

        LatLng p1 = new LatLng(16.074489, 108.149567);
        Marker marker1 = mMap.addMarker(new MarkerOptions().position(p1).title("700k").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).alpha(0.7f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p1, 15));
        marker1.showInfoWindow();
        marker.setVisible(true);

        LatLng p2 = new LatLng(16.073337, 108.14982880000002);
        Marker marker2 = mMap.addMarker(new MarkerOptions().position(p2).title("1000k").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).alpha(0.7f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p2, 15));
        marker2.showInfoWindow();
        marker.setVisible(true);

        LatLng p3 = new LatLng(16.0726617, 108.14661720000004);
        Marker marker3 = mMap.addMarker(new MarkerOptions().position(p3).title("900k").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).alpha(0.7f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p3, 15));
        marker3.showInfoWindow();
        marker.setVisible(true);

        LatLng p4 = new LatLng(16.0754515, 108.15472479999994);
        Marker marker4 = mMap.addMarker(new MarkerOptions().position(p4).title("850k").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).alpha(0.7f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p4, 15));
        marker4.showInfoWindow();
        marker.setVisible(true);

        LatLng p5 = new LatLng(16.0635478, 108.15465429999995);
        Marker marker5 = mMap.addMarker(new MarkerOptions().position(p5).title("600k").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).alpha(0.7f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p5, 15));
        marker5.showInfoWindow();
        marker.setVisible(true);
    }

    private void onMyMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {

                myProgress.dismiss();

                askPermissionsAndShowMyLocation();
            }
        });
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void askPermissionsAndShowMyLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                // Các quyền cần người dùng cho phép.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
                ActivityCompat.requestPermissions(this, permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                return;
            }
        }
        // Hiển thị vị trí hiện thời trên bản đồ.
        this.showMyLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, R.string.cho_phep, Toast.LENGTH_LONG).show();
                    this.showMyLocation();
                } else {
                    Toast.makeText(this, R.string.tu_choi, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private String getEnabledLocationProvider() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);

        boolean enabled = locationManager.isProviderEnabled(bestProvider);

        if (!enabled) {
            Toast.makeText(this, R.string.khong_bat_vi_tri, Toast.LENGTH_LONG).show();
            return null;
        }
        return bestProvider;
    }

    private void showMyLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        String locationProvider = this.getEnabledLocationProvider();

        if (locationProvider == null) {
            return;
        }
        final long MIN_TIME_BW_UPDATES = 1000;
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
        Location myLocation = null;
        try {
            locationManager.requestLocationUpdates(
                    locationProvider,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
            myLocation = locationManager
                    .getLastKnownLocation(locationProvider);
        } catch (SecurityException e) {
            Toast.makeText(this, "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }
        if (myLocation != null) {
            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(15)
                    .bearing(90)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            MarkerOptions option = new MarkerOptions();
            option.title("My Location");
            option.snippet("....");
            option.position(latLng);
            Marker currentMarker = mMap.addMarker(option);
            currentMarker.showInfoWindow();
        } else Toast.makeText(this, R.string.khong_tim_thay_vi_tri, Toast.LENGTH_LONG).show();
    }

    public void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_list_tro, fragment, "Fragment1");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setUpClusterer() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.075971, 108.153725), 4));
        mClusterManager = new ClusterManager<MyItem>(this, getMap());
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        addItems();
    }

    private void addItems() {
        double lat = 16.075971;
        double lng = 108.153725;
        for (int i = 0; i < 4; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void onClick(View view) {
    }

    public GoogleMap getMap() {
        return mMap;
    }

    @Override
    public void onClick() {
        fab.setVisibility(View.VISIBLE);
        Fragment manager = getSupportFragmentManager().findFragmentByTag("Fragment1");
        if (manager != null)
            getSupportFragmentManager().beginTransaction().remove(manager).commit();
    }
}
