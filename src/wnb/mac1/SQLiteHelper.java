package wnb.mac1;

import org.json.JSONException;
import org.json.JSONObject;

//import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//This is my database helper class 
public class SQLiteHelper extends SQLiteOpenHelper {


	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "products";

	private static final String KEY_NAME = "name";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_REGULAR_PRICE = "regular_price";
	private static final String KEY_SALE_PRICE = "sale_price";


	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// creates the products table
	@Override
	public void onCreate(SQLiteDatabase db) {
		// creates our Products table

		String query = "CREATE TABLE products"
				+ "( id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
				+ "description TEXT, " + "regular_price REAL, "
				+ "sale_price REAL, product_photo TEXT, colors TEXT)";
		// "stores TEXT )";

		db.execSQL(query);
	}

	// if the table is upgraded, then this will create a fresh products table
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("DB", "Upgrading Database");
		db.execSQL("DROP TABLE IF EXISTS products");

		this.onCreate(db);
		Log.w("DB", "Database upgraded, new table created");
	}

	// adds the product (based on the JSON object created) to the product table
	public void addProduct(JSONObject productIn) {
		SQLiteDatabase db = this.getWritableDatabase();

		try {
			//insert the JSON product into the database
			String query = "INSERT INTO " + DATABASE_NAME + "( name, description, regular_price, sale_price, product_photo, colors) \n VALUES (\'" +
					productIn.get("name").toString() + "\',\'" + productIn.get("description").toString() +
					"\',\'" + productIn.get("regular_price") + "\',\'" + productIn.get("sale_price") + "\',\'" + productIn.get("product_photo") + "\',\'" +
					productIn.get("colors") + "\');";

			db.execSQL(query);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// Finds the product in the database based off the JSON Object.
	//Used for checking if the product already exists.
	public boolean getProduct(JSONObject productIn) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
		try {
			String query = "SELECT * FROM " + DATABASE_NAME + " WHERE "
					+ KEY_NAME + " = \'" + productIn.get("name").toString()
					+ "\' AND " + KEY_DESCRIPTION + " = \'"
					+ productIn.get("description").toString() + "\' AND "
					+ KEY_REGULAR_PRICE + " = \'"
					+ productIn.get("regular_price").toString() + "\' AND "
					+ KEY_SALE_PRICE + " = \'"
					+ productIn.get("sale_price").toString() + "\' AND "
					+ "product_photo = \'" + productIn.get("product_photo") + "\' AND "
					+ "colors = \'" + productIn.get("colors") + "\';";

			cursor = db.rawQuery(query, null);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (cursor.moveToFirst() == true)
			return true;
		else
			return false;
	}

}
