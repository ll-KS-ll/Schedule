package com.schema.bro.nova;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class NovaOnItemSelectedListener implements OnItemSelectedListener {

	private NovaPagerFragment fragment;

	public NovaOnItemSelectedListener(Fragment fragment) {
		this.fragment = (NovaPagerFragment) fragment;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int pos, long arg3) {
		String classURL;
		switch (pos) {
		case 0:
			classURL = "5ECAD1FC-388E-468D-880F-634BA6EF8C9D";
			break;
		case 1:
			classURL = "487830F6-5C23-4A08-BA2F-1D6B82BFD80E";
			break;
		case 2:
			classURL = "8C95EECD-0352-45D2-B22E-5FA4F9D79A67";
			break;
		case 3:
			classURL = "C8FF7C6D-EF3F-42DF-BBF2-31EFACC1EBF0";
			break;
		case 4:
			classURL = "397C258C-0B7F-4449-972A-C2A06785A058";
			break;
		case 5:
			classURL = "3318BC4B-8710-489C-9132-BA862616920F";
			break;
		case 6:
			classURL = "CF215BA3-03E1-4C46-B9D7-91B6AC487C2E";
			break;
		case 7:
			classURL = "AE69DCAF-91EB-46A4-8401-B1D3E2098B76";
			break;
		case 8:
			classURL = "67D76A15-13F9-4B6C-A1ED-03A2F0EDE347";
			break;
		case 9:
			classURL = "D9556FEA-2F72-4141-BE02-0693711F1150";
			break;
		case 10:
			classURL = "4D6D2AAE-7E76-4E3A-A665-5908868564FF";
			break;
		case 11:
			classURL = "2E121AE9-47DC-47CA-A644-CF06A817CD52";
			break;
		case 12:
			classURL = "C0B5671A-583C-4F7F-AB79-929C5C7714F4";
			break;
		case 13:
			classURL = "CE3896F5-C9E6-4948-B586-1D38E6EE1A67";
			break;
		case 14:
			classURL = "CC03F674-03DF-4DBD-BC41-652EF65471A7";
			break;
		case 15:
			classURL = "35B08DEF-C76D-4066-93BD-0F8237E5683F";
			break;
		case 16:
			classURL = "07179309-3669-44F4-B337-3D288C1A4B00";
			break;
		case 17:
			classURL = "D90C7F91-4109-4B10-8F3C-DE6A775A81FE";
			break;
		case 18:
			classURL = "5C887E9C-DF62-474D-BB99-B0CB9B7CADF7";
			break;
		case 19:
			classURL = "DA6962A5-6225-4987-AE95-FD0ED510AA0E";
			break;
		case 20:
			classURL = "9DB665F9-10A4-45E6-853A-7DCACF25916A";
			break;
		case 21:
			classURL = "447643AE-129C-4986-A670-ADEA9A497D09";
			break;
		case 22:
			classURL = "8FE5E0F3-B1A1-4EE6-B3F0-E86D327AAD9D";
			break;
		case 23:
			classURL = "575A2DA4-F002-4C72-86E5-D0D2201C0CCB";
			break;
		case 24:
			classURL = "BF91EB9A-0E16-4C1B-B67D-59DA064CDBD8";
			break;
		case 25:
			classURL = "1D5F28F6-F3AE-4FDE-AAA1-C6CFABC34CBC";
			break;
		case 26:
			classURL = "0112DE1E-DBFB-4C46-85FD-B2619ED08DE3";
			break;
		case 27:
			classURL = "BA8AA989-549E-471B-A852-9D15EF558DAC";
			break;
		case 28:
			classURL = "BFBE085D-A887-4137-AC7D-CBE818B2E9D3";
			break;
		case 29:
			classURL = "F3E0A9F7-68D5-4868-8A7F-25E66A224F34";
			break;
		case 30:
			classURL = "6D819667-5BE1-48E6-87EA-F1D2D1F7D3B0";
			break;
		case 31:
			classURL = "7CEC979B-60B7-47D3-85D1-8669A8AB3E16";
			break;
		case 32:
			classURL = "93A935E1-BC35-453B-AE09-47C051A96DC6";
			break;
		default:
			classURL = "not_set";
		}
		
		if(fragment.isResumed())
			fragment.setClassURL(classURL);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
