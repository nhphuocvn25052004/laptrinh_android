package com.example.music;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMusicAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Music> arrayList;
    private MediaPlayer mediaPlayer;
    private  Boolean flag =true;

    public CustomMusicAdapter(Context context, int layout, ArrayList<Music> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHoler{
        TextView txtName, txtSinger;
        ImageView ivPlay, ivStop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoler viewHoler;
        if (convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(layout, null);
            viewHoler.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHoler.txtSinger = (TextView) convertView.findViewById(R.id.txtSinger);
            viewHoler.ivPlay = (ImageView) convertView.findViewById(R.id.ivPlay);
            viewHoler.ivStop = (ImageView) convertView.findViewById(R.id.ivStop);

            convertView.setTag(viewHoler);
        }

        else{
            viewHoler = (ViewHoler) convertView.getTag();
        }

        final Music music = arrayList.get(position);

        viewHoler.txtName.setText(music.getName());
        viewHoler.txtSinger.setText(music.getSinger());
//////////////////////////////////
        // Play music
        viewHoler.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra nếu mediaPlayer đã tồn tại và phát nhạc, dừng và giải phóng nó trước khi phát nhạc mới
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    viewHoler.ivPlay.setImageResource(R.drawable.nutplay);
                } else {
                    // Nếu mediaPlayer chưa được tạo, khởi tạo mediaPlayer mới
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(context, music.getSong());
                        // Đảm bảo giải phóng mediaPlayer khi hoàn thành
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                viewHoler.ivPlay.setImageResource(R.drawable.nutplay);
                                mediaPlayer.release();
                                mediaPlayer = null;
                                flag = true;
                            }
                        });
                    }
                    mediaPlayer.start();
                    viewHoler.ivPlay.setImageResource(R.drawable.nutpause);
                    flag = false;
                }
            }
        });

// Stop music
        viewHoler.ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra nếu mediaPlayer đã được tạo
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    flag = true;
                }
                // Cập nhật biểu tượng nút phát nhạc
                viewHoler.ivPlay.setImageResource(R.drawable.nutplay);
            }
        });
        return convertView;
    }
}


