package leonardoleite.colecaojogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;

public class DetalhesJogoActivity extends AppCompatActivity {
    JogoDAO fonteDados;
    Jogo jogo;

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesJogoActivity.this);
        builder.setTitle("Confirmar exclusão");
        builder.setMessage("Deseja realmente excluir esse jogo?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fonteDados.excluirJogo(jogo);
                        finish();
                    }
                });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_jogo);

        fonteDados = new JogoDAO(this);
        try {
            fonteDados.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Intent intencao = getIntent();
        Bundle extras = intencao.getExtras();
        final long id = extras.getLong("id");

        jogo = fonteDados.getById(id);

        TextView tvNome = (TextView) findViewById(R.id.tvNomeJogo);
        tvNome.setText("Nome: " + jogo.nome);
        TextView tvAno = (TextView) findViewById(R.id.tvAnoJogo);
        tvAno.setText("Ano de Lançamento: " + Integer.toString(jogo.anoLancamento));
        TextView tvGenero = (TextView) findViewById(R.id.tvGeneroJogo);
        tvGenero.setText("Gênero: " + jogo.genero);
        TextView tvDescricao = (TextView) findViewById(R.id.tvDescricaoJogo);
        tvDescricao.setText("Sobre:\n" + jogo.descricao);
        if(jogo.capa != null) {
            ImageView ivCapa = (ImageView) findViewById(R.id.ivJogoSelecionado);
            ivCapa.setImageBitmap(Utils.getImage(jogo.capa));
        }

        Button btnExclui = (Button) findViewById(R.id.btnExclui);
        Button btnEdita = (Button) findViewById(R.id.btnEdita);

        btnEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesJogoActivity.this,EditaJogoActivity.class);
                Bundle extras =  new Bundle();
                extras.putLong("id",id);
                intent.putExtras(extras);
                startActivity(intent);
                //finish();
            }
        });

        btnExclui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }

    @Override
    protected void onPause(){
        fonteDados.close();
        super.onPause();
    }

    @Override
    protected void onResume(){

        try {
            fonteDados.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }
}
