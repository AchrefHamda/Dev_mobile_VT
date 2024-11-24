package com.example.application_mobile

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.application_mobile.ui.theme.Application_mobileTheme

class GestionT : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = FeedReaderDbHelper(this)

        setContent {
            Application_mobileTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White) // Couleur de fond blanche
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Ajout du titre
                        Text(
                            text = "Gestion des Produits",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(bottom = 70.dp) // Espacement sous le titre
                        )

                        // Boutons
                        ButtonComposable(text = "Ajouter Produit", onClick = { insertDB(dbHelper) })
                        ButtonComposable(text = "Voir Produit") {
                            readDB(dbHelper)
                        }
                        ButtonComposable(text = "Supprimer Produit") {
                            deleteDB(dbHelper)
                        }
                        ButtonComposable(text = "Mise à jour ") {
                            updateDB(dbHelper)
                        }
                        ButtonComposable(text = "Réinitialiser") {
                            resetDB(dbHelper)
                        }
                    }
                }
            }
        }
    }

    private fun insertDB(dbHelper: FeedReaderDbHelper) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "Produit")
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "Description")
        }
        val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
        println("Row inserted with ID: $newRowId")
    }

    private fun readDB(dbHelper: FeedReaderDbHelper) {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
            FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        )
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE} DESC"
        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME, null, null, null, null, null, sortOrder
        )
        val itemIds = mutableListOf<Long>()
        val itemTitles = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val itemTitle = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE))
                itemIds.add(itemId)
                itemTitles.add(itemTitle)
            }
        }
        cursor.close()
        println("Item IDs: $itemIds")
        println("Item Titles: $itemTitles")
    }

    private fun deleteDB(dbHelper: FeedReaderDbHelper) {
        val db = dbHelper.writableDatabase
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} LIKE ?"
        val selectionArgs = arrayOf("Produit")
        val deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs)
        println("Deleted rows: $deletedRows")
    }

    private fun updateDB(dbHelper: FeedReaderDbHelper) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "NouvelleProduit")
        }
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} LIKE ?"
        val selectionArgs = arrayOf("Produit")
        val count = db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs)
        println("Updated rows: $count")
    }

    private fun resetDB(dbHelper: FeedReaderDbHelper) {
        val db = dbHelper.writableDatabase
        val deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null)
        println("All rows deleted: $deletedRows")
    }
}

@Composable
fun ButtonComposable(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .width(250.dp), // Largeur fixe pour uniformité
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFCE6F1B), // Couleur du bouton
            contentColor = Color.Black // Couleur du texte
        ),
        shape = MaterialTheme.shapes.medium, // Coins légèrement arrondis
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
