package com.example.ejemplogooglemaps;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.example.ejemplogooglemaps.R;


public class MainActivity extends FragmentActivity implements OnMapClickListener, 
						OnMarkerClickListener, OnCameraChangeListener, OnInfoWindowClickListener{ 
	
	private GoogleMap sicomMap;
	private int minZoom = 17;
	
	private final LatLng UniEafit = new LatLng(6.200696,-75.578433);  
											//lat y long found in GoogleMaps
	private final LatLng Biblioteca = new LatLng(6.201203,-75.57843);  
	private final LatLng Rectoria = new LatLng(6.199531,-75.578905);  
	private final LatLng Fundadores = new LatLng(6.200448,-75.578951);
	private final LatLng BloIngenierias = new LatLng(6.198059,-75.579581);  
	 
	//eafit area                               //y          x
	private final LatLng tLCorner = new LatLng(6.203500,-75.580197); 
	private final LatLng bLCorner = new LatLng(6.196832,-75.580197);    
	private final LatLng bRCorner = new LatLng(6.196832,-75.577057);
	private final LatLng tRCorner = new LatLng(6.203500,-75.577057);
	private final LatLngBounds sicomMapBounds = new LatLngBounds(bLCorner, tRCorner);
	
	private ArrayList<Marker> fixedMarkersList = new ArrayList<Marker>(); 
	private ArrayList<Boolean> infoWindowsStatus = new ArrayList<Boolean>(); 
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sicomMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
        			.getMap();
        sicomMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        sicomMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UniEafit, minZoom)); //range: 2-21
        																   //continent-street
       /* Polygon eafitPolygon = sicomMap.addPolygon(new PolygonOptions().add(tLCorner, bLCorner,
        						bRCorner, tRCorner)
        						.strokeColor(Color.TRANSPARENT));
        */
        sicomMap.setMyLocationEnabled(true);                                
        sicomMap.getUiSettings().setZoomControlsEnabled(false);
        sicomMap.getUiSettings().setCompassEnabled(true);
        addMainMarkers();   //pre-defined markers
        sicomMap.setOnMapClickListener(this);   
        sicomMap.setOnMarkerClickListener(this);
        sicomMap.setOnCameraChangeListener(this);
        sicomMap.setOnInfoWindowClickListener(this);
    }
    
  
    public void addMainMarkers(){
    	
    	Marker mkBiblioteca = sicomMap.addMarker(new MarkerOptions()
        .position(Biblioteca)
        .title(getString(R.string.Bibl))  //getString converts that int to String
        .snippet(getString(R.string.BiblLEV)) 
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fixed_marker))
        .anchor(0.5f, 0.5f));  
    	listMainMarkers(mkBiblioteca);
    	
    	Marker mkRectoria = sicomMap.addMarker(new MarkerOptions()
        .position(Rectoria)
        .title(getString(R.string.Rect))  
        .snippet(getString(R.string.RectoBloque))
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fixed_marker))
        .anchor(0.5f, 0.5f));
    	listMainMarkers(mkRectoria);
    	
    	Marker mkFundadores = sicomMap.addMarker(new MarkerOptions()
        .position(Fundadores)
        .title(getString(R.string.Fund))  
        .snippet(getString(R.string.AudFundad))
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fixed_marker))
        .anchor(0.5f, 0.5f));
    	listMainMarkers(mkFundadores);
    	
    	Marker mkBloIngenierias = sicomMap.addMarker(new MarkerOptions()
        .position(BloIngenierias)
        .title(getString(R.string.Ing))  
        .snippet(getString(R.string.BloqueInge))
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fixed_marker))
        .anchor(0.5f, 0.5f));
    	listMainMarkers(mkBloIngenierias);
    }                           
    
    
    @Override
    public void onMapClick(LatLng pressedPoint){
    	if(sicomMapBounds.contains(new LatLng(pressedPoint.latitude, pressedPoint.longitude))){
	    	sicomMap.addMarker(new MarkerOptions().position(pressedPoint).
	           icon(BitmapDescriptorFactory
	              .fromResource(R.drawable.user_marker))
	              .anchor(0.5f, 0.5f));
    	}
    }
    
    @Override
    public boolean onMarkerClick(final Marker delMarker){
    	boolean isFixedMarker = false;
    	for(int i=0; i<fixedMarkersList.size() && !isFixedMarker; i++){
	    	if(delMarker.getId().equals(fixedMarkersList.get(i).getId())){ //a fixed marker was pressed
	    		if(infoWindowsStatus.get(i)){ //this markerInfoWindow is open 
	    			delMarker.hideInfoWindow();
	    			infoWindowsStatus.set(i, false); //closing the infoWindow and setting its status 
	    		}else{                                                                    //to false
	    			for(int j=0; j<fixedMarkersList.size(); j++){ //to hide every infoWindow
	    				fixedMarkersList.get(j).hideInfoWindow();
	    				infoWindowsStatus.set(j, false); //setting every infoWindowStatus to false
	    			}
	    			delMarker.showInfoWindow(); 
	    			infoWindowsStatus.set(i, true); //this markerInfoWindow is open now
	    		}
	    		isFixedMarker = true;
	    	}
    	}
    	if(!isFixedMarker){
    		delMarker.remove();  //removes the marker by pressing it
    	}
    	return true;
    }
    
    
    public void listMainMarkers(Marker fixedMarker){
    	fixedMarkersList.add(fixedMarker);
    	infoWindowsStatus.add(false); //no infoWindow is open
    }
    
    
    @Override
    public void onCameraChange(CameraPosition position) {
    	Vibrator outOfBoundsVb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    	if(!sicomMapBounds.contains(new LatLng(position.target.latitude, 
    			position.target.longitude)) || position.zoom < minZoom){
    			outOfBoundsVb.vibrate(500); //it vibrates for two secs when going out 
    											//of bounds
    			sicomMap.animateCamera(CameraUpdateFactory.newLatLngZoom(UniEafit, minZoom));
    			//it returns to UniEafit when going out of bounds
    	}
    	
    }
   
    @Override
	public void onInfoWindowClick(Marker marker){
    	String windowTitle = "";
    	Intent openBuildingInfo = new Intent(MainActivity.this, BuildingInfo.class); //establishes the 
    	 														//connection from this activity to the
    	   														//indicated one
    	Bundle paramsBag = new Bundle();       //creates a bag for the parameters                        
		windowTitle = marker.getTitle();                              
		paramsBag.putString("windowTitle", windowTitle);  //puts a parameter in the bag
		openBuildingInfo.putExtras(paramsBag);   //puts the bag in the opening Activity
        startActivity(openBuildingInfo); 
	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*
     * Referencias:
     * 
     * La imagen de user_marker fue extraida de: http://www.iconarchive.com/show/
     * vista-map-markers-icons-by-icons-land/Map-Marker-Push-Pin-1-Left-Azure-icon.html
     * La imagen de fixed_marker fue extraida de: http://www.clker.com/clipart-12249.html
     * 
     */
}
