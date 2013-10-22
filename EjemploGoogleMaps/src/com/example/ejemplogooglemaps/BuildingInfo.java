package com.example.ejemplogooglemaps;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

/* This Class shows the information related to a specific building inside the University.
 * It is in charge of setting the appropriate building view and all its attributes and
 * presenting the correct information about any pressed main marker.
 */
public class BuildingInfo extends Activity {
	
	private String windowTitle = "";
	private RelativeLayout buildingView;
	private TextView buildingTitle;
	
	/*	
	* Main method of the application, the windowTitle attribute is set to the contents of the received 
	* parameter, to assign to the content view, then it initializes the visual attributes of the next view.
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle paramsBag = getIntent().getExtras();  //download the bag for this Activity
		windowTitle = paramsBag.getString("windowTitle");
		selectContentView(windowTitle); //select the view depending on the chosen marker
		initializeViewAttr(); 
	}
	
	 /*
	 * @windowTitle: title of the marker chosen.
	 * 
	 * Depending of the parameter (windowTitle), it initialize the corresponding view,
	 * and to the global attribute buildingView, it sets the corresponding layout to the view.
	 */
	 public void selectContentView(String windowTitle) {
		if(windowTitle.equals(getString(R.string.Bibl))){
			setContentView(R.layout.activity_biblioteca);
			buildingView = (RelativeLayout) findViewById(R.id.biblioteca);
		
		}else if(windowTitle.equals(getString(R.string.Fund))){
			setContentView(R.layout.activity_fundadores);
			buildingView = (RelativeLayout) findViewById(R.id.fundadores);
			
		}else if(windowTitle.equals(getString(R.string.Rect))){
			setContentView(R.layout.activity_rectoria);
			buildingView = (RelativeLayout) findViewById(R.id.rectoria);	
			
		}else if(windowTitle.equals(getString(R.string.Ing))){
			setContentView(R.layout.activity_ingenieria);
			buildingView = (RelativeLayout) findViewById(R.id.bloqueIngenieria);	
		}
	}
	
    /*
    * This method initializes the visual attributes of the view. 
	*/
	public void initializeViewAttr(){
		buildingTitle = (TextView) buildingView.getChildAt(0);
		buildingTitle.setText(windowTitle);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.building_info, menu);
		return true;
	}

}
