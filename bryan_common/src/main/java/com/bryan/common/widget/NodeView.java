package com.bryan.common.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bryan.common.R;
import com.bryan.common.utils.DensityUtils;


public class NodeView {

    RelativeLayout view = null;
    private TextView tvNodeName;
    private ImageView ivArrow;
    private Context mContext;

    public NodeView(Context context, boolean isLast, String name, View.OnClickListener listener) {
        mContext = context;
        view = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_node, null);
        tvNodeName = (TextView) view.findViewById(R.id.tv_node_name);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        setNodeName(name);
        isLast(isLast);
        view.setOnClickListener(listener);
    }

    public NodeView(Context context, boolean isLast, String name) {
        mContext = context;
        view = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_node, null);
        tvNodeName = (TextView) view.findViewById(R.id.tv_node_name);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        setNodeName(name);
        isLast(isLast);
    }

    public NodeView(Context context) {
        this(context, false, null, null);
    }

    public void setNodeName(String nodeName) {
        if (!TextUtils.isEmpty(nodeName)) {
            tvNodeName.setText(nodeName);
        }
    }

    public void isLast(boolean isLast) {
        if (isLast) {
            view.setOnClickListener(null);
            tvNodeName.setTextColor(ContextCompat.getColor(mContext, R.color.node_gray_color));
            ivArrow.setVisibility(View.GONE);
            //设置最后一个的右边距
            tvNodeName.setPadding(0, 0, DensityUtils.dp2px(mContext, 50), 0);
        } else {
            tvNodeName.setTextColor(ContextCompat.getColor(mContext, R.color.node_theme_color));
            ivArrow.setVisibility(View.VISIBLE);
        }
    }

    public RelativeLayout getView() {
        return view;
    }

    public void setOnclickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

}
