package com.example.tubes_02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tubes_02.model.Player;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    protected static final int database_version = 1;
    protected static final String database_name = "Player_List";
    private static final String TABLE_PLAYER = "Player_Table";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    public DBHandler(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PLAYER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_SCORE + " TEXT"
                +" )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        onCreate(db);
    }

    public void addRecord(Player player) {
        SQLiteDatabase db  = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_SCORE, player.getScore());

        db.insert(TABLE_PLAYER, null, values);
    }

    public Player getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLAYER, new String[] {KEY_ID, KEY_NAME, KEY_SCORE}, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        Player food = new Player(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return food;
    }

    public String getHighScore() {
        String countQuery = "SELECT * FROM " + TABLE_PLAYER + " ORDER BY " + KEY_SCORE + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // Return high score
        if (cursor.getCount() == 0) {
            cursor.close();
            return "0";
        }
        else {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            String score = cursor.getString(2);
            cursor.close();
            return score;
        }
    }

    public List<Player> getHighScores() {
        List<Player> playerList = new ArrayList<>();
        // Select all query
        String selectQuery = "SELECT * FROM " + TABLE_PLAYER + " ORDER BY " + KEY_SCORE + " DESC LIMIT 10";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Player player = new Player(); //THIS
                player.setId(Integer.parseInt(cursor.getString(0)));
                player.setName(cursor.getString(1));
                player.setScore(cursor.getString(2));

                playerList.add(player);
            } while (cursor.moveToNext());
        }

        // Return contact list
        return playerList;
    }

    public int getPlayerCount() {
        String countQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // Return number of food in database
        return count;
    }

    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_SCORE, player.getScore());

        // Update row
        return db.update(TABLE_PLAYER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(player.getId()) });
    }

    public void updateLastRecord(String name) {
        String updateQuery = "UPDATE " + TABLE_PLAYER + " SET " + KEY_NAME + " = '" + name + "' WHERE " + KEY_ID + " = (SELECT MAX(" + KEY_ID + ") FROM " + TABLE_PLAYER + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(updateQuery);
    }

    public void deleteAllPlayer() {
        String deleteQuery = "DELETE FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }

    public void deleteModel(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, KEY_ID + " = ?",
                new String[] { String.valueOf(player.getId()) });
        db.close();
    }
}
