package wnb.mac1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;

//This handles the activity that shows the product after is has been created and pushed to the database
//failed to get images to display because I ran out of time updating my code.
public class ShowProduct extends Activity {

	SQLiteHelper sql = new SQLiteHelper(this);
	Button next, prev, delete, update;
	String extras, query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_product);
		next = (Button) findViewById(R.id.next);
		prev = (Button) findViewById(R.id.previous);
		delete = (Button) findViewById(R.id.delete);
		update = (Button) findViewById(R.id.update);
		final TextView ID;
		final EditText name, desc, reg_price, sale_price;
		final ImageView photo = (ImageView) findViewById(R.id.imageView1);
		ID = (TextView) findViewById(R.id.pID);
		name = (EditText) findViewById(R.id.pName);
		desc = (EditText) findViewById(R.id.pDescription);
		reg_price = (EditText) findViewById(R.id.pRegPrice);
		sale_price = (EditText) findViewById(R.id.pSalePrice);

		SQLiteDatabase db = sql.getReadableDatabase();
		final SQLiteDatabase dbw = sql.getWritableDatabase();

		// gets the ID from the clicked product list
		extras = getIntent().getStringExtra("Product id");

		// if Extras = null, then the data is loaded for the just-created
		// product
		if (extras == null) {
			query = "SELECT * FROM products ORDER BY id DESC";
			final Cursor cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {
				photo.setImageURI(Uri.parse(cursor.getString(5)));
				ID.setText(cursor.getString(0));
				name.setText(cursor.getString(1), TextView.BufferType.EDITABLE);
				desc.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
				reg_price.setText(cursor.getString(3),
						TextView.BufferType.EDITABLE);
				sale_price.setText(cursor.getString(4),
						TextView.BufferType.EDITABLE);

				// when next product is clicked, moves and displays the next
				// product
				next.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (cursor.moveToPrevious()) {
							photo.setImageURI(Uri.parse(cursor.getString(5)));
							ID.setText(cursor.getString(0));
							name.setText(cursor.getString(1),
									TextView.BufferType.EDITABLE);
							desc.setText(cursor.getString(2),
									TextView.BufferType.EDITABLE);
							reg_price.setText(cursor.getString(3),
									TextView.BufferType.EDITABLE);
							sale_price.setText(cursor.getString(4),
									TextView.BufferType.EDITABLE);
						}
					}
				});
				// when previous product is clicked, moves and displays the
				// previous product
				prev.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (cursor.moveToNext()) {
							photo.setImageURI(Uri.parse(cursor.getString(5)));
							ID.setText(cursor.getString(0));
							name.setText(cursor.getString(1),
									TextView.BufferType.EDITABLE);
							desc.setText(cursor.getString(2),
									TextView.BufferType.EDITABLE);
							reg_price.setText(cursor.getString(3),
									TextView.BufferType.EDITABLE);
							sale_price.setText(cursor.getString(4),
									TextView.BufferType.EDITABLE);
						}
					}
				});

			}
		} else {
			// runs when extras != null, meaning that the user came to this
			// activity from the product list
			query = "SELECT * FROM products WHERE id=" + extras.toString()
					+ ";";
			final Cursor cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {
				photo.setImageURI(Uri.parse(cursor.getString(5)));
				ID.setText(cursor.getString(0));
				name.setText(cursor.getString(1), TextView.BufferType.EDITABLE);
				desc.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
				reg_price.setText(cursor.getString(3),
						TextView.BufferType.EDITABLE);
				sale_price.setText(cursor.getString(4),
						TextView.BufferType.EDITABLE);
			}
			// when next product is clicked, moves and displays the next product
			next.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (cursor.moveToNext()) {
						photo.setImageURI(Uri.parse(cursor.getString(5)));
						ID.setText(cursor.getString(0));
						name.setText(cursor.getString(1),
								TextView.BufferType.EDITABLE);
						desc.setText(cursor.getString(2),
								TextView.BufferType.EDITABLE);
						reg_price.setText(cursor.getString(3),
								TextView.BufferType.EDITABLE);
						sale_price.setText(cursor.getString(4),
								TextView.BufferType.EDITABLE);
					}
				}
			});
			// when previous product is clicked, moves and displays the previous
			// product
			prev.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (cursor.moveToPrevious()) {
						photo.setImageURI(Uri.parse(cursor.getString(5)));
						ID.setText(cursor.getString(0));
						name.setText(cursor.getString(1),
								TextView.BufferType.EDITABLE);
						desc.setText(cursor.getString(2),
								TextView.BufferType.EDITABLE);
						reg_price.setText(cursor.getString(3),
								TextView.BufferType.EDITABLE);
						sale_price.setText(cursor.getString(4),
								TextView.BufferType.EDITABLE);
					}
				}
			});
		}

		// when the delete button is clicked, this queries the database,
		// deletes the product, and redirects to the product list.
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String queryD = "DELETE FROM products WHERE name=\'"
						+ name.getText().toString() + "\' AND description=\'"
						+ desc.getText().toString() + "\' AND regular_price=\'"
						+ reg_price.getText().toString()
						+ "\' AND sale_price=\'"
						+ sale_price.getText().toString() + "';";

				dbw.execSQL(queryD);

				Intent i = new Intent(getBaseContext(), ShowProductList.class);
				startActivity(i);

			}
		});

		// when the update button is clicked, this queries the database,
		// updates the product, and redirects to the product list.
		update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String queryU = "UPDATE products SET name=\'"
						+ name.getText().toString() + "\', description=\'"
						+ desc.getText().toString() + "\', regular_price=\'"
						+ reg_price.getText().toString() + "\', sale_price=\'"
						+ sale_price.getText().toString() + "\' "
						+ "WHERE id=\'" + ID.getText().toString() + "\';";

				dbw.execSQL(queryU);

				Intent i = new Intent(getBaseContext(), ShowProductList.class);
				startActivity(i);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_product, menu);
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

}
