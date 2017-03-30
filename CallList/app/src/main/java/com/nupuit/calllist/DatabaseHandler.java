package com.nupuit.calllist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHandler extends SQLiteOpenHelper {

	

	public DatabaseHandler(Context context) {
		super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_BOOK_TABLE = "CREATE TABLE " + Constant.TABLE_NAME + "("
				+ Constant.id + " TEXT PRIMARY KEY,"
				+ Constant.name + " TEXT,"
				 + Constant.number  + " TEXT" +")";

		db.execSQL(CREATE_BOOK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public int addData(ModelClass model) {
		int value = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		String Query = "Select * from " + Constant.TABLE_NAME + " where "
				+ Constant.id + "='" + model.getId() + "'";
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.getCount() < 1) {
			ContentValues values = new ContentValues();
			values.put(Constant.id, model.getId());
			values.put(Constant.name,model.getName());
			values.put(Constant.number, model.getNumber() );

			db.insert(Constant.TABLE_NAME, null, values);
			db.close();
			value = 1;
		}else{
			
			ContentValues values = new ContentValues();
			values.put(Constant.name,model.getName());
			values.put(Constant.number, model.getNumber() );

			db.update(Constant.TABLE_NAME, values,Constant.id + " = ?",
	                new String[] { String.valueOf(model.getId()) });
			db.close();
			value = 2;
			
		}

		return value;

	}

	public ArrayList<ModelClass> getAllData(String query) {
		ArrayList<ModelClass> list = new ArrayList<ModelClass>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {

				ModelClass model = new ModelClass();

				model.setId(cursor.getString(cursor
						.getColumnIndex(Constant.id)));
				model.setName(cursor.getString(cursor
						.getColumnIndex(Constant.name)));
				model.setNumber(cursor.getString(cursor
						.getColumnIndex(Constant.number)));

				list.add(model);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return list;
	}
	
	
	
	public Cursor getData(String query) {

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		//db.close();
		return cursor;
	}
	
	public ModelClass getDetail(String id) {
		ModelClass model = new ModelClass();
		
		String selectQuery = "Select * from " + Constant.TABLE_NAME + " where "
				+ Constant.id + "='" + id + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		model.setName(cursor.getString(cursor
				.getColumnIndex(Constant.name)));

		model.setNumber(cursor.getString(cursor
				.getColumnIndex(Constant.number)));
	
		cursor.moveToNext();

		cursor.close();

	
		return model;
	}

	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + Constant.TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		return rowCount;
	}

	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Constant.TABLE_NAME, null, null);
		db.close();
	}

}