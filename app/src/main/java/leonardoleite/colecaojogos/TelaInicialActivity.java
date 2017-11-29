package leonardoleite.colecaojogos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

public class TelaInicialActivity extends AppCompatActivity {

    JogoDAO fonteDados;
    ListView lvJogos;
    List<Jogo> jogos;
    Adaptador adaptador;
    EditText etBusca;

    private void atualizaLista(){
        jogos = fonteDados.pegarTodosJogos();
        adaptador = new Adaptador(this, R.layout.item_da_lista, jogos);
        lvJogos.setAdapter(adaptador);
    }

    private void atualizaComBusca(){
        jogos = fonteDados.buscaJogo(etBusca.getText().toString());
        adaptador = new Adaptador(this, R.layout.item_da_lista, jogos);
        lvJogos.setAdapter(adaptador);
    }

    @Override
    protected void onResume(){

        try {
            fonteDados.open();
            atualizaLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        lvJogos = (ListView) findViewById(R.id.lvJogos);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnExclui = (Button) findViewById(R.id.btnExclui);
        etBusca = (EditText) findViewById(R.id.etBusca);

        fonteDados = new JogoDAO(this);
        try {
            fonteDados.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        atualizaLista();

        etBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                atualizaComBusca();
            }
        });

        lvJogos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //fonteDados.excluirJogo(jogos.get(position));
                Intent intent = new Intent(TelaInicialActivity.this,DetalhesJogoActivity.class);
                Bundle extras = new Bundle();
                extras.putLong("id",jogos.get(position).id);
                intent.putExtras(extras);
                startActivity(intent);
                //atualizaLista();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicialActivity.this, AdicionaJogoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onPause(){
        fonteDados.close();
        super.onPause();
    }
}
