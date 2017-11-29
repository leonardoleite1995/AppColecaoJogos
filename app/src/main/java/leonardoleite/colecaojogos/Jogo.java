package leonardoleite.colecaojogos;

import android.graphics.Bitmap;

/**
 * Created by leonardoleite on 02/09/17.
 */

public class Jogo {
    long id;
    String nome;
    int anoLancamento;
    String genero;
    String descricao;
    byte[] capa;

    public Jogo(long id, String nome, int anoLancamento, String genero, String descricao,byte[] capa) {
        this.id = id;
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.descricao = descricao;
        this.capa = capa;
    }
}
