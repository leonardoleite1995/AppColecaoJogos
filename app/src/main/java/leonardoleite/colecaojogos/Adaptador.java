package leonardoleite.colecaojogos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by leonardoleite on 02/09/17.
 */

public class Adaptador extends ArrayAdapter<Jogo>{
    private int resourceId;
    private LayoutInflater inflater;

    public Adaptador(Context context, int resource, List<Jogo> objects){
        super(context, resource, objects);
        this.resourceId = resource;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Jogo jogo = getItem(position);
        convertView = inflater.inflate(resourceId, null);
        TextView tvNome = (TextView) convertView.findViewById(R.id.tvNome);
        TextView tvAno = (TextView) convertView.findViewById(R.id.tvAno);
        TextView tvGenero = (TextView) convertView.findViewById(R.id.tvGenero);

        if(jogo.capa != null){
            ImageView ivCapa = convertView.findViewById(R.id.ivCapa);
            ivCapa.setImageBitmap(Utils.getImage(jogo.capa));
        }
        /**/

        tvNome.setText(jogo.nome);
        tvAno.setText(Integer.toString(jogo.anoLancamento));
        tvGenero.setText(jogo.genero);
        return convertView;
    }
}
