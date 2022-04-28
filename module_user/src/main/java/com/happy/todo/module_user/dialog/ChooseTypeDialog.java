package com.happy.todo.module_user.dialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.happy.todo.lib_common.base.BaseAppPopWindow;
import com.happy.todo.module_user.R;


/**
 * Author by Ouyangle, Date on 2020-07-01.
 * PS: Not easy to write code, please indicate.
 */
public class ChooseTypeDialog extends BaseAppPopWindow implements View.OnClickListener {

    private int opstion = 0;
    private String title;
    private TextView tvAll;
    private TextView tvHotel;
    private TextView tvDiscover;
    private TextView tvSale;
    private TextView tvPlay;

    public ChooseTypeDialog(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        tvAll = (TextView) findViewById(R.id.tv_all);
        tvHotel = (TextView) findViewById(R.id.tv_hotel);
        tvDiscover = (TextView) findViewById(R.id.tv_discover);
        tvSale = (TextView) findViewById(R.id.tv_sale);
        tvPlay = (TextView) findViewById(R.id.tv_play);

        tvAll.setOnClickListener(this);
        tvHotel.setOnClickListener(this);
        tvDiscover.setOnClickListener(this);
        tvSale.setOnClickListener(this);
        tvPlay.setOnClickListener(this);

        initOptions();
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        if (viewID == R.id.tv_all) {
            opstion = 0;
            title = tvAll.getText().toString();
        } else if (viewID == R.id.tv_hotel) {
            opstion = 1;
            title = tvHotel.getText().toString();
        } else if (viewID == R.id.tv_discover) {
            opstion = 2;
            title = tvDiscover.getText().toString();
        } else if (viewID == R.id.tv_sale) {
            opstion = 3;
            title = tvSale.getText().toString();
        } else if (viewID == R.id.tv_play) {
            opstion = 4;
            title = tvPlay.getText().toString();
        }

        initOptions();

        if(listener != null)
            listener.changeProductType(opstion,title);

        dismiss();
    }

    private void initOptions() {
        tvAll.setSelected(opstion == 0);
        tvHotel.setSelected(opstion == 1);
        tvDiscover.setSelected(opstion == 2);
        tvSale.setSelected(opstion == 3);
        tvPlay.setSelected(opstion == 4);
    }

    private OnChangeProductTypeListener listener;
    public interface OnChangeProductTypeListener{
        void changeProductType(int position,String title);
    }

    public void setListener(OnChangeProductTypeListener listener) {
        this.listener = listener;
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(-1f, 0, 200);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, -1f, 200);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.user_dialog_choose_type);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_content);
    }
}