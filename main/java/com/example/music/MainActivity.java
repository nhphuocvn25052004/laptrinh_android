package com.example.music;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Music> arrayList;
    private CustomMusicAdapter adapter;
    private ListView ListofSongs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListofSongs =(ListView) findViewById(R.id.ListofSong);
        arrayList = new ArrayList<>();
        arrayList.add(new Music("Anh sẽ đợi", "TLong x Tô Minh", R.raw.aaa));
        arrayList.add(new Music("Nơi này có anh", "Sơn Tùng MTP", R.raw.bbb));
        arrayList.add(new Music("Thiên lý ơi", "J97", R.raw.ccc));
        arrayList.add(new Music("Chạnh lòng thương cô 2", "Huy Vạc", R.raw.ddd));


        adapter = new CustomMusicAdapter( this, R.layout.custom_music_items, arrayList);
        ListofSongs.setAdapter(adapter);


    }

}