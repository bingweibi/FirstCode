package com.example.bbw.listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author bbw
 * @date 2017/6/23
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;

    /**
     * @param context
     * @param textViewResourceId 子项布局的id
     * @param objects 数据源
     */
    FruitAdapter(@NonNull Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    /**
     * @param position 位置信息
     * @param convertView 缓存view
     * @param parent 父布局
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Fruit fruit = getItem(position);
        View view ;
        ViewHolder viewHolder;
        if (convertView == null){
            //记住
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            //提高效率，不必每次获取控件的id
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = view.findViewById(R.id.fruitImage);
            viewHolder.fruitName = view.findViewById(R.id.fruitName);
            view.setTag(viewHolder);
        }else{
            //是否有缓存，convertView就是用于将之前加载好的布局进行缓存以便重用
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;
    }

    private class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
    }
}
