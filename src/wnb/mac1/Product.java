package wnb.mac1;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//This activity allows the user to select a product and an image
//Inserts it into the database.
public class Product extends Activity {

	Button b;
	EditText pNameIn, pDescIn, pRegPrice, pSalePrice;
	SQLiteHelper sql = new SQLiteHelper(this);
	RadioGroup rImg;
	RadioButton rButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		b = (Button) findViewById(R.id.insertProduct);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	// creates the JSON and calls the method to insert it into the database
	public void insertProduct(View v) {
		JSONObject jsonObj = new JSONObject();

		pNameIn = (EditText) findViewById(R.id.pName);
		pDescIn = (EditText) findViewById(R.id.pDesc);
		pRegPrice = (EditText) findViewById(R.id.pRegPrice);
		pSalePrice = (EditText) findViewById(R.id.pSalePrice);
		String imageURI;
		rImg = (RadioGroup) findViewById(R.id.radioImage);
		int selectedId = rImg.getCheckedRadioButtonId();
		rButton = (RadioButton) findViewById(selectedId);
		if (rButton.getText().equals("Blue Ball"))
			imageURI = "android.resource://wnb.mac1/drawable/ball.jpg";
		else if (rButton.getText().equals("Gold 55"))
			imageURI = "android.resource://wnb.mac1/drawable/gold55.jpg";
		else
			imageURI = "android.resource://wnb.mac1/drawable/thunder.jpg";
		// try to create the JSON object and check to see if one already exists
		// in the database
		try {
			jsonObj.put("name", pNameIn.getText().toString());
			jsonObj.put("description", pDescIn.getText().toString());
			jsonObj.put("regular_price",
					Double.parseDouble(pRegPrice.getText().toString()));
			jsonObj.put("sale_price",
					Double.parseDouble(pSalePrice.getText().toString()));
			jsonObj.put("product_photo", imageURI);
			jsonObj.put("colors", "White");

			if (sql.getProduct(jsonObj) == true){
				Toast.makeText(this, "This product already exists!", Toast.LENGTH_SHORT).show();
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		sql.addProduct(jsonObj);

		Intent i = new Intent(getBaseContext(), ShowProduct.class);
		startActivity(i);
	}

	//Creates the Blue Ball Product when Create 1 is clicked
	public void create1(View v) {
		JSONObject jsonObj = new JSONObject();

		pNameIn = (EditText) findViewById(R.id.pName);
		pDescIn = (EditText) findViewById(R.id.pDesc);
		pRegPrice = (EditText) findViewById(R.id.pRegPrice);
		pSalePrice = (EditText) findViewById(R.id.pSalePrice);
		// String imageURI = "android.resource://wnb.mac1/drawable/ball.jpg";

		// try to create the JSON object and check to see if one already exists
		// in the database
		try {
			jsonObj.put("name", "Blue Ball");
			jsonObj.put("description",
					"Perfect for when life has you feeling blue and you need something to "
							+ "put some bounce back in your step!");
			jsonObj.put("regular_price", 10.00);
			jsonObj.put("sale_price", 5.00);
			jsonObj.put("product_photo",
					"android.resource://wnb.mac1/drawable/ball.jpg");
			jsonObj.put("colors", "Blue");

			if (sql.getProduct(jsonObj) == true) {
				Toast.makeText(this, "This product already exists!", Toast.LENGTH_SHORT).show();
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		sql.addProduct(jsonObj);
		Intent i = new Intent(getBaseContext(), ShowProduct.class);
		startActivity(i);

	}
	
	//Creates the Gold 55 product when create 2 is clicked
	public void create2(View v) {
		JSONObject jsonObj = new JSONObject();

		pNameIn = (EditText) findViewById(R.id.pName);
		pDescIn = (EditText) findViewById(R.id.pDesc);
		pRegPrice = (EditText) findViewById(R.id.pRegPrice);
		pSalePrice = (EditText) findViewById(R.id.pSalePrice);

		// try to create the JSON object and check to see if one already exists
		// in the database
		try {
			jsonObj.put("name", "Gold 55");
			jsonObj.put("description", "The gold 55 is the most Golden and 55-iest!");
			jsonObj.put("regular_price", 10.00);
			jsonObj.put("sale_price", 5.00);
			jsonObj.put("product_photo",
					"android.resource://wnb.mac1/drawable/gold55.jpg");
			jsonObj.put("colors", "Blue");

			
			if (sql.getProduct(jsonObj) == true){
				Toast.makeText(this, "This product already exists!", Toast.LENGTH_SHORT).show();
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		sql.addProduct(jsonObj);
		Intent i = new Intent(getBaseContext(), ShowProduct.class);
		startActivity(i);

	}
	
	//Creates the Thunder Cloud product when create 3 is clicked
		public void create3(View v) {
			JSONObject jsonObj = new JSONObject();

			pNameIn = (EditText) findViewById(R.id.pName);
			pDescIn = (EditText) findViewById(R.id.pDesc);
			pRegPrice = (EditText) findViewById(R.id.pRegPrice);
			pSalePrice = (EditText) findViewById(R.id.pSalePrice);
			// String imageURI = "android.resource://wnb.mac1/drawable/ball.jpg";

			// try to create the JSON object and check to see if one already exists
			// in the database
			try {
				jsonObj.put("name", "Thunder Cloud");
				jsonObj.put("description", "Feel the thunder!");
				jsonObj.put("regular_price", 10000.00);
				jsonObj.put("sale_price", 1.00);
				jsonObj.put("product_photo",
						"android.resource://wnb.mac1/drawable/thunder.jpg");
				jsonObj.put("colors", "Navy");

				
				if (sql.getProduct(jsonObj) == true){
					Toast.makeText(this, "This product already exists!", Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			sql.addProduct(jsonObj);
			Intent i = new Intent(getBaseContext(), ShowProduct.class);
			startActivity(i);
		}
}
