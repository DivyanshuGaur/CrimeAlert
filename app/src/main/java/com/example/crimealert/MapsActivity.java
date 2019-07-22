package com.example.crimealert;



import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
import java.util.List;




public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String item="";
    CSVReader reader;

    private Spinner spinner1;
    SearchView searchView;
    MainActivity M= new MainActivity();
    ArrayList<String> places=new ArrayList<>();
    ArrayList<String> crimes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        System.out.println("places are"+places);
        System.out.println("Crimes are"+crimes);


        searchView=(SearchView)findViewById(R.id.sv);
        searchView.setQueryHint("Enter location");

        ReadCsv();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Toast.makeText(this, "MAP HAS BEEN FOUND", Toast.LENGTH_SHORT).show();

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





    public void two(View v){
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));
    }




    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


                // Pirates are the best
                    break;
            case R.id.radioButton3:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);



                // Ninjas rule
                    break;

            case R.id.radioButton2:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;

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
                           Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(this, "Inav", Toast.LENGTH_SHORT).show();
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

// Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);

   //     LatLng sydney = new LatLng(27.746974, 85.301582);
        List<Marker> markers = new ArrayList<Marker>();
        for(int i=1;i<places.size();i++) {
            try {
                addressList = geocoder.getFromLocationName(places.get(i), 1);
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude()))); //...


                markers.add(marker);


                if(crimes.get(i).equals("Assault"))
                mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title(places.get(i)));

                if(crimes.get(i).equals("Rape"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker()).title(places.get(i)));

                if (crimes.get(i).equals("Robbery"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).title(places.get(i)));

                if(crimes.get(i).equals("Kidnapping"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(places.get(i)));

                if(crimes.get(i).equals("Theft"))
                    mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(places.get(i)));


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


}
