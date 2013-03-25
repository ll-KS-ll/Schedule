package com.schema.bro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class NovaSoftwareListActivity extends Activity implements OnItemClickListener{
	
	List<Map<String, String>> classList = new ArrayList<Map<String,String>>();
	
	private Intent intent;
	String url;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novasoftware_activity);
		
		initList();
	     
	    // We get the ListView component from the layout
	    ListView lv = (ListView) findViewById(R.id.listView);
	     
	     
	    // This is a simple adapter that accepts as parameter
	    // Context
	    // Data list
	    // The row layout that is used during the row creation
	    // The keys used to retrieve the data
	    // The View id used to show the data. The key number and the view id must match
	    SimpleAdapter simpleAdpt = new SimpleAdapter(this, classList, android.R.layout.simple_list_item_1, new String[] {"class"}, new int[] {android.R.id.text1});
	    
	 
	    lv.setAdapter(simpleAdpt);
	    lv.setOnItemClickListener(this);
	}
	
	private void initList() {
	    // We populate the classes
	    
		classList.add(createClass("class", "BF1"));
		classList.add(createClass("class", "BF2"));
		classList.add(createClass("class", "BF3"));
		classList.add(createClass("class", "EC3"));
		classList.add(createClass("class", "EE1"));
		classList.add(createClass("class", "EE2"));
		classList.add(createClass("class", "EK1"));
		classList.add(createClass("class", "EK2"));
		classList.add(createClass("class", "ES1"));
		classList.add(createClass("class", "ES2"));
		classList.add(createClass("class", "ES3"));
		classList.add(createClass("class", "HA1"));
		classList.add(createClass("class", "HA2"));
		classList.add(createClass("class", "HP3"));
		classList.add(createClass("class", "HU2"));
		classList.add(createClass("class", "HVL�R3"));
		classList.add(createClass("class", "IM"));
		classList.add(createClass("class", "IM1"));
		classList.add(createClass("class", "NA1"));
		classList.add(createClass("class", "NA2"));
		classList.add(createClass("class", "NV3"));
		classList.add(createClass("class", "PRE"));
		classList.add(createClass("class", "SA1A"));
		classList.add(createClass("class", "SA1B"));
		classList.add(createClass("class", "SA2"));
		classList.add(createClass("class", "SMAS1"));
		classList.add(createClass("class", "SMAS3"));
		classList.add(createClass("class", "SMAS4"));
		classList.add(createClass("class", "SP3"));
		classList.add(createClass("class", "TE1"));
		classList.add(createClass("class", "TE2"));
		classList.add(createClass("class", "TE3"));
		classList.add(createClass("class", "VUX"));
	  
	     
	}
	 
	private HashMap<String, String> createClass(String key, String name) {
	    HashMap<String, String> className = new HashMap<String, String>();
	    className.put(key, name);
	     
	    return className;
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		
		setUrl(position);
        intent = new Intent(this, NovaSoftwareViewerActivity.class);
        intent.putExtra("url", url);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
            
	}
	
	public void setUrl(int pos){
        switch (pos) {
        case 0:
            url = "5ECAD1FC-388E-468D-880F-634BA6EF8C9D";
        	break;
        case 1:
        	url = "487830F6-5C23-4A08-BA2F-1D6B82BFD80E";
        	break;
        case 2:
        	url = "8C95EECD-0352-45D2-B22E-5FA4F9D79A67";
        	break;
        case 3:
        	url = "C8FF7C6D-EF3F-42DF-BBF2-31EFACC1EBF0";
        	break;
        case 4:
        	url = "397C258C-0B7F-4449-972A-C2A06785A058";
        	break;
        case 5:
        	url = "3318BC4B-8710-489C-9132-BA862616920F";
        	break;
        case 6:
        	url = "CF215BA3-03E1-4C46-B9D7-91B6AC487C2E";
        	break;
        case 7:
        	url = "AE69DCAF-91EB-46A4-8401-B1D3E2098B76";
        	break;
        case 8:
        	url = "67D76A15-13F9-4B6C-A1ED-03A2F0EDE347";
        	break;
        case 9:
        	url = "D9556FEA-2F72-4141-BE02-0693711F1150";
        	break;
        case 10:
        	url = "4D6D2AAE-7E76-4E3A-A665-5908868564FF";
        	break;
        case 11:
        	url = "2E121AE9-47DC-47CA-A644-CF06A817CD52";
            break;
        case 12:
        	url = "C0B5671A-583C-4F7F-AB79-929C5C7714F4";
           	break;
        case 13:
        	url = "CE3896F5-C9E6-4948-B586-1D38E6EE1A67";
          	break;
        case 14:
        	url = "CC03F674-03DF-4DBD-BC41-652EF65471A7";
            break;
        case 15:
        	url = "35B08DEF-C76D-4066-93BD-0F8237E5683F";
           	break;
        case 16:
        	url = "07179309-3669-44F4-B337-3D288C1A4B00";
            break;
        case 17:
        	url = "D90C7F91-4109-4B10-8F3C-DE6A775A81FE";
            break;
        case 18:
        	url = "5C887E9C-DF62-474D-BB99-B0CB9B7CADF7";
            break;
        case 19:
        	url = "DA6962A5-6225-4987-AE95-FD0ED510AA0E";
        	break;
        case 20:
        	url = "9DB665F9-10A4-45E6-853A-7DCACF25916A";
            break;
        case 21:
        	url = "447643AE-129C-4986-A670-ADEA9A497D09";
            break;
        case 22:
        	url = "8FE5E0F3-B1A1-4EE6-B3F0-E86D327AAD9D";  
            break;
        case 23:
        	url = "575A2DA4-F002-4C72-86E5-D0D2201C0CCB";   
            break;
        case 24:
        	url = "BF91EB9A-0E16-4C1B-B67D-59DA064CDBD8";   
            break;
        case 25:
        	url = "1D5F28F6-F3AE-4FDE-AAA1-C6CFABC34CBC";   
            break;
        case 26:
        	url = "0112DE1E-DBFB-4C46-85FD-B2619ED08DE3";   
            break;
        case 27:
        	url = "BA8AA989-549E-471B-A852-9D15EF558DAC";   
        	break;
        case 28:
        	url = "BFBE085D-A887-4137-AC7D-CBE818B2E9D3";   
        	break;
        case 29:
        	url = "F3E0A9F7-68D5-4868-8A7F-25E66A224F34";   
            break;
        case 30:
        	url = "6D819667-5BE1-48E6-87EA-F1D2D1F7D3B0";   
            break;
        case 31:
        	url = "7CEC979B-60B7-47D3-85D1-8669A8AB3E16";    
        	break;
        case 32:
        	url = "93A935E1-BC35-453B-AE09-47C051A96DC6";   
        	break;
        default:
	}
	}
}
