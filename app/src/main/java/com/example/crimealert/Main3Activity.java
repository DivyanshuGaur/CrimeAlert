package com.example.crimealert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    MapView mapView;
    private GoogleMap mMap;
    String item="";
    CSVReader reader;


    private Spinner spinner1;
    SearchView searchView;
    ArrayList<String> places=new ArrayList<>();
    ArrayList<String> crimes=new ArrayList<>();
    RadioGroup rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);




        searchView = (SearchView) findViewById(R.id.sv);
        searchView.setQueryHint("Enter location");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        supportMapFragment.getMapAsync(this);
        ReadCsv();


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(this, "Touched", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(id==R.id.rep){

            Toast.makeText(this, "Show the report", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,ReportActivity.class);
            startActivity(intent);
        }
       if(id==R.id.lout){

           Intent intent=new Intent(this,Login.class);
           startActivity(intent);
           Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show();

       }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void ReadCsv() {

        ArrayList<String> location = new ArrayList<>();

        try {
            InputStreamReader is = new InputStreamReader(getAssets()
                    .open("CDb.csv"));

            reader = new CSVReader(is);

            String[] column;
            int c = 0;
            String[] loc = new String[12];

            while ((column = reader.readNext()) != null) {
                for (int i = 0; i < column.length; i++) {
                    if (i == 4) {
                        places.add(column[i]);

                    }
                    if(i==3)
                        crimes.add(column[i]);
                }
            }
        } catch (Exception e) {
        }

    }



    public void onMapSearch(View view) {
        String s=  searchView.getQuery().toString();
        System.out.println("On button click");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        String location = s;
        List<Address> addressList = null;
        LatLng latLng=null;


        Geocoder geocoder = new Geocoder(this);
        try {

            addressList = geocoder.getFromLocationName(location,1);
            Address address = addressList.get(0);
            if(address.hasLatitude() && address.hasLongitude()) {

                if (true) {
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(latLng);
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(60);

                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);
                }
            }


        } catch (Exception e) {
            Toast.makeText(this, "Invalid location", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.json2));

        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));



        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(28.4595, 77.0266))
                .radius(8000).strokeColor(Color.RED); // In meters

        Circle circle = mMap.addCircle(circleOptions);

        List<Marker> markers = new ArrayList<Marker>();
        for(int i=1;i<places.size();i++) {
            try {
                addressList = geocoder.getFromLocationName(places.get(i), 1);
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude()))); //...


                markers.add(marker);
                if(crimes.get(i).equals("Theft"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(places.get(i)));


                if(crimes.get(i).equals("Assault"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title(places.get(i)));

                if(crimes.get(i).equals("Rape"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker()).title(places.get(i)));

                if (crimes.get(i).equals("Robbery"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).title(places.get(i)));

                if(crimes.get(i).equals("Kidnapping"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(places.get(i)));


                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        googleMap.moveCamera(cu);
        googleMap.animateCamera(cu);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    public void browse(View view){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);

    }

        public void terrane(View view ){
        int c=0;

                if(view.getId()==R.id.floatingActionButton ) {


                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


                    }

                    if (view.getId()==R.id.floatingActionButton2) {

                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


                    }

                    if(view.getId()==R.id.floatingActionButton3)
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
                }



