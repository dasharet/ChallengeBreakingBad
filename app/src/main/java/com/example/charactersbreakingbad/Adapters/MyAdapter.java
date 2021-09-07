package com.example.charactersbreakingbad.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charactersbreakingbad.Models.Character;
import com.example.charactersbreakingbad.Models.FavoriteDB;
import com.example.charactersbreakingbad.R;
import com.example.charactersbreakingbad.Views.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Character> characterItems;
    private Context context;
    private FavoriteDB favDB;

    public MyAdapter(ArrayList<Character> characterItems, Context context) {
        this.characterItems = characterItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Character characterItem = characterItems.get(position);

        readCursorData(characterItem, holder);
        Picasso.get()
                .load(characterItem.getImg())
                .fit()
                .into(holder.imageView);
        holder.textViewName.setText(characterItem.getName());
        holder.textViewNickName.setText(characterItem.getNickname());
    }

    @Override
    public int getItemCount() {
        return characterItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        TextView textViewNickName;
        Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.TextViewName);
            textViewNickName = itemView.findViewById(R.id.TextViewNickName);
            favBtn = itemView.findViewById(R.id.favBtn);


            itemView.setOnClickListener(v -> {
                Toast.makeText(context, "Character detail", Toast.LENGTH_LONG).show();

                int position = getAdapterPosition();
                Character characterItem = characterItems.get(position);

                String occupation = "";
                for (String o : characterItem.getOccupation()) {
                    if (occupation == "") {
                        occupation = o;
                    } else {
                        occupation = occupation + ", " + o;
                    }
                }
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name", characterItem.getName());
                intent.putExtra("nickname", characterItem.getNickname());
                intent.putExtra("img", characterItem.getImg());
                intent.putExtra("status", characterItem.getStatus());
                intent.putExtra("portrayed", characterItem.getPortrayed());
                intent.putExtra("favStatus", characterItem.getFavStatus());
                intent.putExtra("occupation", occupation);
                context.startActivity(intent);
            });

            //add to fav btn
            favBtn.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Character characterItem = characterItems.get(position);

                if (characterItem.getFavStatus().equals("0")) {
                    characterItem.setFavStatus("1");
                    favDB.insertIntoTheDatabase(characterItem.getName(), characterItem.getImg(),
                            characterItem.getChar_id(), characterItem.getFavStatus());
                    favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24);

                } else {
                    characterItem.setFavStatus("0");
                    favDB.remove_fav(characterItem.getChar_id());
                    favBtn.setBackgroundResource(R.drawable.ic_favorite_border_24);
                }
            });
        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Character characteritem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(characteritem.getChar_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                characteritem.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_border_24);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }

}
