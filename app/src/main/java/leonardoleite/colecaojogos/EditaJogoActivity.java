package leonardoleite.colecaojogos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class EditaJogoActivity extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    byte[] inputData;
    Uri uri;

    JogoDAO fonteDados;
    EditText etNome;
    EditText etAno;
    EditText etGenero;
    EditText etDescricao;
    ImageView ivCapa;
    Button btnCapa;
    Button btnSalvaAlteracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_jogo);

        fonteDados = new JogoDAO(this);
        try {
            fonteDados.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Intent intencao = getIntent();
        Bundle extras = intencao.getExtras();
        final long id = extras.getLong("id");

        final Jogo jogo = fonteDados.getById(id);

        btnCapa = (Button) findViewById(R.id.btnAlteraImagem);
        btnSalvaAlteracao = (Button) findViewById(R.id.btnSalvaAlteracao);

        etNome = (EditText) findViewById(R.id.etNome2);
        etNome.setText(jogo.nome);

        etAno = (EditText) findViewById(R.id.etAno2);
        etAno.setText(Integer.toString(jogo.anoLancamento));

        etGenero = (EditText) findViewById(R.id.etGenero2);
        etGenero.setText(jogo.genero);

        etDescricao = (EditText) findViewById(R.id.etDescricao2);
        etDescricao.setText(jogo.descricao);

        ivCapa = (ImageView) findViewById(R.id.ivCapa2);

        if(jogo.capa!=null){
            ivCapa.setImageBitmap(Utils.getImage(jogo.capa));
        }

        btnCapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btnSalvaAlteracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = etNome.getText().toString();
                int ano = Integer.parseInt(etAno.getText().toString());
                String genero = etGenero.getText().toString();
                String descricao = etDescricao.getText().toString();

                if(inputData!=null){
                    fonteDados.editarJogo(id,nome,ano,genero,descricao,inputData);
                }else{
                    fonteDados.editarJogo(id,nome,ano,genero,descricao,jogo.capa);
                }

                Intent intent = new Intent(EditaJogoActivity.this,TelaInicialActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ivCapa.setImageBitmap(bitmap);

                InputStream iStream = getContentResolver().openInputStream(uri);
                inputData = Utils.getBytes(iStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
