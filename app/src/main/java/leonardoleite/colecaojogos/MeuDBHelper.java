package leonardoleite.colecaojogos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leonardoleite on 02/09/17.
 */

public class MeuDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_JOGOS = "jogos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_ANO = "anoLancamento";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_CAPA = "capa";

    public static final String DATABASE_NAME = "arquivodobanco.bd";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_JOGOS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NOME + " TEXT NOT NULL, "
            + COLUMN_ANO + " INTEGER NOT NULL, "
            + COLUMN_GENERO + " TEXT NOT NULL, "
            + COLUMN_DESCRICAO + " TEXT, "
            + COLUMN_CAPA + " BLOB);";

    public MeuDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOGOS);
        onCreate(db);
    }
}
