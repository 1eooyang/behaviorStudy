package com.example.leo.adsafelike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by leo on 2017/6/1.
 */

public class SampleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

/*
        RecyclerView recyclerView = new RecyclerView(container.getContext());
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(inflater.inflate(R.layout.item_simple, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.text.setText("Fake Item " + (position + 1));
                vh.text2.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
            }

            @Override
            public int getItemCount() {
                return 20;
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                TextView text;
                TextView text2;

                public ViewHolder(View itemView) {
                    super(itemView);

                    text = (TextView) itemView.findViewById(R.id.text);
                    text2 = (TextView) itemView.findViewById(R.id.text2);
                }

            }
        });*/

        TextView textView = new TextView(container.getContext());
        textView.setText("leoyang");
        return textView;
    }

}
