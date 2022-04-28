package com.happy.todo.lib_common.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;

/**
 * @功能描述：
 * @创建日期： 2018/07/04
 * @作者： dengkewu
 */

public class BottomGradeDialog extends BottomSheetDialog {
    private CheckBox cb_plane_grade_1;
    private CheckBox cb_plane_grade_2;
    private CheckBox cb_plane_grade_3;
    private CheckBox cb_plane_grade_4;
    private RelativeLayout rl_plane_grade_1;
    private RelativeLayout rl_plane_grade_2;
    private RelativeLayout rl_plane_grade_3;
    private RelativeLayout rl_plane_grade_4;
    private TextView tv_plane_grade_1;
    private TextView tv_plane_grade_2;
    private TextView tv_plane_grade_3;
    private TextView tv_plane_grade_4;
    private View view;
    private int position;
    public BottomGradeDialog(@NonNull Context context, int position) {
        super(context);
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.hotel_dialog_bottom_grade, null);
        setContentView(view);
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
        initEvent();
    }

    private void initEvent() {
        resetPlaneGrade(position);

        rl_plane_grade_1.setOnClickListener(v -> {
            resetPlaneGrade(1);
        });
        rl_plane_grade_2.setOnClickListener(v -> {
            resetPlaneGrade(2);
        });
        rl_plane_grade_3.setOnClickListener(v -> {
            resetPlaneGrade(3);
        });
        rl_plane_grade_4.setOnClickListener(v -> {
            resetPlaneGrade(4);
        });
    }

    private void initView() {
        rl_plane_grade_1 = view.findViewById(R.id.rl_plane_grade_1);
        rl_plane_grade_2 = view.findViewById(R.id.rl_plane_grade_2);
        rl_plane_grade_3 = view.findViewById(R.id.rl_plane_grade_3);
        rl_plane_grade_4 = view.findViewById(R.id.rl_plane_grade_4);

        tv_plane_grade_1 = view.findViewById(R.id.tv_plane_grade_1);
        tv_plane_grade_2 = view.findViewById(R.id.tv_plane_grade_2);
        tv_plane_grade_3 = view.findViewById(R.id.tv_plane_grade_3);
        tv_plane_grade_4 = view.findViewById(R.id.tv_plane_grade_4);

        cb_plane_grade_1 = view.findViewById(R.id.cb_plane_grade_1);
        cb_plane_grade_2 = view.findViewById(R.id.cb_plane_grade_2);
        cb_plane_grade_3 = view.findViewById(R.id.cb_plane_grade_3);
        cb_plane_grade_4 = view.findViewById(R.id.cb_plane_grade_4);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private void resetPlaneGrade(int i) {
//        tvPlaneGrade.setText(BookingManager.getInstance().getPlaneGradeStr());
        cb_plane_grade_1.setVisibility(View.GONE);
        cb_plane_grade_2.setVisibility(View.GONE);
        cb_plane_grade_3.setVisibility(View.GONE);
        cb_plane_grade_4.setVisibility(View.GONE);
        tv_plane_grade_1.setTextColor(ResourceUtils.getColor(R.color.black_484848));
        tv_plane_grade_2.setTextColor(ResourceUtils.getColor(R.color.black_484848));
        tv_plane_grade_3.setTextColor(ResourceUtils.getColor(R.color.black_484848));
        tv_plane_grade_4.setTextColor(ResourceUtils.getColor(R.color.black_484848));
        switch (i) {
            case 1:
                cb_plane_grade_1.setVisibility(View.VISIBLE);
                tv_plane_grade_1.setTextColor(ResourceUtils.getColor(R.color.theme_color));
                break;
            case 2:
                cb_plane_grade_2.setVisibility(View.VISIBLE);
                tv_plane_grade_2.setTextColor(ResourceUtils.getColor(R.color.theme_color));
                break;
            case 3:
                cb_plane_grade_3.setVisibility(View.VISIBLE);
                tv_plane_grade_3.setTextColor(ResourceUtils.getColor(R.color.theme_color));
                break;
            case 4:
                cb_plane_grade_4.setVisibility(View.VISIBLE);
                tv_plane_grade_4.setTextColor(ResourceUtils.getColor(R.color.theme_color));
                break;
            default:
                cb_plane_grade_1.setVisibility(View.VISIBLE);
                tv_plane_grade_1.setTextColor(ResourceUtils.getColor(R.color.theme_color));
                break;
        }
        if (onClickListener!=null){
            onClickListener.onClick(i);
        }
        dismiss();

    }

    public interface onClickListener{
        void onClick(int position);
    }
    private onClickListener onClickListener;

    public void setOnClickListener(BottomGradeDialog.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
