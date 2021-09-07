package com.example.charactersbreakingbad.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charactersbreakingbad.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView TextViewNickName;
    private Button favBtn;
    private TextView TextViewOccupation;
    private TextView TextViewStatus;
    private TextView TextViewPortrayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        imageView = (ImageView) findViewById(R.id.imageView);
        TextViewNickName = (TextView) findViewById(R.id.TextViewNickName);
        favBtn = (Button) findViewById(R.id.favBtn);
        TextViewOccupation = (TextView) findViewById(R.id.TextViewOccupation);
        TextViewStatus = (TextView) findViewById(R.id.TextViewStatus);
        TextViewPortrayed = (TextView) findViewById(R.id.TextViewPortrayed);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String titulo = bundle.getString("name");
            String nickname = bundle.getString("nickname");
            String img = bundle.getString("img");
            String favStatus = bundle.getString("favStatus");
            String status = bundle.getString("status");
            String portrayed = bundle.getString("portrayed");
            String occupation = bundle.getString("occupation");

            this.setTitle(titulo);
            TextViewNickName.setText(nickname);
            Picasso.get()
                    .load(img)
                    .fit()
                    .into(imageView);

            //check fav status
            if (favStatus != null && favStatus.equals("1")) {
                favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24);
            } else if (favStatus != null && favStatus.equals("0")) {
                favBtn.setBackgroundResource(R.drawable.ic_favorite_border_24);
            }
            TextViewStatus.setText(status);
            TextViewPortrayed.setText(portrayed);
            TextViewOccupation.setText(occupation);

        } else {
            Toast.makeText(this, "Reload", Toast.LENGTH_LONG).show();
        }

    }
}