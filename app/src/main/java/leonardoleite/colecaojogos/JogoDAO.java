package leonardoleite.colecaojogos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardoleite on 02/09/17.
 */

public class JogoDAO {
    private SQLiteDatabase database;
    private MeuDBHelper dbHelper;
    private String[] todasColunas ={MeuDBHelper.COLUMN_ID, MeuDBHelper.COLUMN_NOME, MeuDBHelper.COLUMN_ANO, MeuDBHelper.COLUMN_GENERO, MeuDBHelper.COLUMN_DESCRICAO, MeuDBHelper.COLUMN_CAPA};

    public JogoDAO(Context context){
        dbHelper = new MeuDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Jogo criarJogo(String nome,int ano, String genero, String descricao, byte[] capa) {
        ContentValues values = new ContentValues();
        values.put(MeuDBHelper.COLUMN_NOME, nome);
        values.put(MeuDBHelper.COLUMN_ANO, ano);
        values.put(MeuDBHelper.COLUMN_GENERO, genero);
        values.put(MeuDBHelper.COLUMN_DESCRICAO, descricao);
        values.put(MeuDBHelper.COLUMN_CAPA, capa);

        long insertId = database.insert(MeuDBHelper.TABLE_JOGOS, null, values);

        Cursor cursor = database.query(MeuDBHelper.TABLE_JOGOS, todasColunas, MeuDBHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Jogo novoJogo = cursor2Jogo(cursor);
        cursor.close();
        return novoJogo;
    }

    public List<Jogo> pegarTodosJogos(){
        List<Jogo> jogos = new ArrayList<Jogo>();
        Cursor cursor = database.query(MeuDBHelper.TABLE_JOGOS, todasColunas, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Jogo jogo = cursor2Jogo(cursor);
            jogos.add(jogo);
            cursor.moveToNext();
        }

        cursor.close();
        return jogos;
    }

    public List<Jogo> buscaJogo(String nome){
        String filtro;
        filtro = MeuDBHelper.COLUMN_NOME + " like '%" + nome + "%'";

        List<Jogo> jogos = new ArrayList<Jogo>();

        Cursor cursor = database.query(MeuDBHelper.TABLE_JOGOS, todasColunas, filtro, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Jogo jogo = cursor2Jogo(cursor);
            jogos.add(jogo);
            cursor.moveToNext();
        }

        cursor.close();
        return jogos;
    }

    public Jogo getById(long id){
        String filtro;
        filtro = MeuDBHelper.COLUMN_ID + " = " + id;
        Cursor cursor = database.query(MeuDBHelper.TABLE_JOGOS, todasColunas, filtro, null, null, null, null);
        cursor.moveToFirst();
        Jogo jogo2 = cursor2Jogo(cursor);
        return jogo2;
    }

    public void editarJogo(long id, String nome,int ano, String genero, String descricao, byte[] capa){
        String filtro = MeuDBHelper.COLUMN_ID + " = " + id;
        ContentValues values = new ContentValues();
        values.put(MeuDBHelper.COLUMN_NOME, nome);
        values.put(MeuDBHelper.COLUMN_ANO, ano);
        values.put(MeuDBHelper.COLUMN_GENERO, genero);
        values.put(MeuDBHelper.COLUMN_DESCRICAO, descricao);
        values.put(MeuDBHelper.COLUMN_CAPA, capa);
        database.update(MeuDBHelper.TABLE_JOGOS,values,filtro,null);
    }

    public void excluirJogo(Jogo jogo){
        long id = jogo.id;
        database.delete(MeuDBHelper.TABLE_JOGOS, MeuDBHelper.COLUMN_ID + " = " + id, null);
    }

    private Jogo cursor2Jogo(Cursor cursor) {
        Jogo jogo = new Jogo(cursor.getLong(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getBlob(5));
        return jogo;
    }
}
