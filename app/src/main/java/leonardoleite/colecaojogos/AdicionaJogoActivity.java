package leonardoleite.colecaojogos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static leonardoleite.colecaojogos.R.id.etNome;

public class AdicionaJogoActivity extends AppCompatActivity {
    JogoDAO fonteDados;
    private int PICK_IMAGE_REQUEST = 1;
    Uri uri;
    byte[] inputData;

    @Override
    protected void onResume(){
        try {
            fonteDados.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_jogo);

        fonteDados = new JogoDAO(this);
        try {
            fonteDados.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        Button btnCapa = (Button) findViewById(R.id.btnCapa);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etNome = (EditText) findViewById(R.id.etNome);
                EditText etAno = (EditText) findViewById(R.id.etAno);
                EditText etGenero = (EditText) findViewById(R.id.etGenero);
                EditText etDescricao = (EditText) findViewById(R.id.etDescricao);
                String nome = etNome.getText().toString();
                String ano = etAno.getText().toString();
                String genero = etGenero.getText().toString();
                String descricao = etDescricao.getText().toString();

                if (nome.trim().isEmpty() || ano.trim().isEmpty() || genero.trim().isEmpty()){
                    if (nome.trim().isEmpty())
                        etNome.setError("Digite um nome");
                    if (ano.trim().isEmpty())
                        etAno.setError("Digite um Ano");
                    if (genero.trim().isEmpty())
                        etGenero.setError("Digite um Gênero");
                } else {
                    fonteDados.criarJogo(nome, Integer.parseInt(ano), genero,descricao,inputData);
                    fonteDados.close();
                    Intent intent = new Intent(AdicionaJogoActivity.this,TelaInicialActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ImageView imageView = (ImageView) findViewById(R.id.ivImgEscolhida);
                imageView.setImageBitmap(bitmap);

                InputStream iStream = getContentResolver().openInputStream(uri);
                inputData = Utils.getBytes(iStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
