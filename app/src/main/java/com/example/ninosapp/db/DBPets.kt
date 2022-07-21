package com.example.ninosapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.ninosapp.model.Pet

class DBPets (context: Context) : DBHelper(context) {
    //Aqui va el codigo para el crud
    /*val context = context

    fun insertPet(name: String, age: Int, gender: String,brench:String,esteril: String,owner: String,talla: String): Long {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0
        try {
            val values = ContentValues()
            values.put("name", name)
            values.put("age", age)
            values.put("gender", gender)
            values.put("brench",brench)
            values.put("esteril",esteril)
            values.put("owner",owner)
            values.put("talla",talla)

            id = db.insert(TABLE_PETS, null, values)
        } catch (e: Exception) {
            print("algo paso")

        } finally {
            db.close()
        }

        return id
    }

    fun getPets(): ArrayList<Pet> {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var listPets = ArrayList<Pet>()
        var petTmp: Pet? = null
        var cursorPets: Cursor? = null

        cursorPets = db.rawQuery("SELECT * FROM $TABLE_PETS", null)

        if (cursorPets.moveToFirst()) {
            do {
                petTmp = Pet(
                    cursorPets.getInt(0),
                    cursorPets.getString(1),
                    cursorPets.getInt(2),
                    cursorPets.getString(3),
                    cursorPets.getString(4),
                    cursorPets.getString(5),
                    cursorPets.getString(6),
                    cursorPets.getString(7)
                )
                listPets.add(petTmp)
            } while (cursorPets.moveToNext())
        }

        cursorPets.close()

        return listPets
    }

    fun getPet(id: Int): Pet? {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var pet: Pet? = null
        var cursorPets: Cursor? = null

        cursorPets = db.rawQuery("SELECT * FROM $TABLE_PETS WHERE id = $id LIMIT 1", null)
        if (cursorPets.moveToFirst()) {
            pet = Pet(
                cursorPets.getInt(0),
                cursorPets.getString(1),
                cursorPets.getInt(2),
                cursorPets.getString(3),
                cursorPets.getString(4),
                cursorPets.getString(5),
                cursorPets.getString(6),
                cursorPets.getString(7)
            )
        }

        cursorPets.close()

        return pet

    }

    fun updatePet(id: Int,name: String, age: Int, gender: String,brench: String,esteril: String,owner: String,talla:String): Boolean {
        var banderaCorrecto = false
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        try {
            db.execSQL("UPDATE $TABLE_PETS SET name = '$name', age = '$age', gender = '$gender',brench = '$brench',esteril = '$esteril',owner = '$owner',talla = '$talla' WHERE id = $id")
            banderaCorrecto = true

        } catch (e: Exception) {

        } finally {
            db.close()
        }
        return banderaCorrecto
    }

    fun deletePet(id: Int): Boolean {

        var banderaCorrecto = true

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try {
            db.execSQL("DELETE FROM $TABLE_PETS WHERE id = $id")
            banderaCorrecto = true
        } catch (e: Exception) {

        } finally {
            db.close()
        }

        return banderaCorrecto
    }

*/
}