package wnb.mac1;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.SimpleAdapter;
import android.view.View;

//This class handles the product list and shows all products that have been created
public class ShowProductList extends Activity {

	//Tried to implement the Map class for the Dictionary requirement
	//Doesn't work :( Code comments left in here on purpose for review.
	//List<Map<String, String>> productsList = new ArrayList<Map<String, String>>();
	//SimpleAdapter simpleAdpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_product_list);

		//productsList.add(createProduct("1", "Blue Ball"));
		//productsList.add(createProduct("2", "Red Ball"));

		final ListView lv = (ListView) findViewById(R.id.listView1);

		// simpleAdpt = new SimpleAdapter(this, productsList,
		// android.R.layout.simple_list_item_1, new String[] {"product"}, new
		// int[] {android.R.id.text1});
		SQLiteHelper sql = new SQLiteHelper(this);
		SQLiteDatabase db = sql.getReadableDatabase();
		Cursor cursor = null;
		
		//queries for all products created to display in the listview
		String query = "SELECT * FROM products";
		cursor = db.rawQuery(query, null);

		ArrayList<String> valuesAL = new ArrayList<String>();

		if (cursor.moveToFirst()) {
			do {
				valuesAL.add(cursor.getString(0) + ": " + cursor.getString(1));
			} while (cursor.moveToNext());
		}

		String[] values = new String[valuesAL.size()];
		values = valuesAL.toArray(values);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		// lv.setAdapter(simpleAdpt);
		lv.setAdapter(adapter);
		
		//when clicked, the user will be redirected to the clicked item's product page (determined by the products unique id)
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// clicked item
				String[] itemValue = new String[2];
				itemValue =  lv.getItemAtPosition(position).toString().split(":");

				Intent i = new Intent(getBaseContext(), ShowProduct.class);
				i.putExtra("Product id", itemValue[0]);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_product_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**private HashMap<String, String> createProduct(String key, String name) {
		HashMap<String, String> product = new HashMap<String, String>();
		product.put(key, name);

		return product;
	}
	**/
}
