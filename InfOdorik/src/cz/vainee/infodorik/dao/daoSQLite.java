package cz.vainee.infodorik.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class daoSQLite implements dao {
	
	class DatabaseOpenHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "DB_name";
	    private static final int DATABASE_VERSION = 1;
	    private static final String CREDIT_TABLE_NAME = "credit";
	    private static final String KEY_DATETIME = "time";
	    private static final String KEY_AMOUNT = "amount";
	    
	    private static final String LINES_TABLE_NAME = "lines";
	    private static final String KEY_LINE_STRING = "line_info";
	    
	    private static final String CALLS_TABLE_NAME = "calls";
	    private static final String KEY_CALL_STRING = "call_info";
	    
	    
	    

	    private static final String CREDIT_TABLE_CREATE =
	                "CREATE TABLE " + CREDIT_TABLE_NAME + " (" +
	                KEY_DATETIME + " DATETIME, " +
	                KEY_AMOUNT + " REAL);";
	    private static final String LINES_TABLE_CREATE =
                "CREATE TABLE " + LINES_TABLE_NAME + " (" +
                KEY_DATETIME + " DATETIME, " +
                KEY_LINE_STRING + " TEXT);";
	    private static final String CALLS_TABLE_CREATE =
                "CREATE TABLE " + CALLS_TABLE_NAME + " (" +
                KEY_DATETIME + " DATETIME, " +
                KEY_CALL_STRING + " TEXT);";

		
	    DatabaseOpenHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	    	db.execSQL(CREDIT_TABLE_CREATE);
	        //db.execSQL(DICTIONARY_TABLE_CREATE);
	    	db.execSQL(LINES_TABLE_CREATE);
	    	db.execSQL(CALLS_TABLE_CREATE);
	    }

/*		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}
*/
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	

	@Override
	public void initData() {
		
		
		

	}

	@Override
	public void updateCredit(Integer credit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCalls(String calls) {
		// TODO Auto-generated method stub
		
	}

}
