package com.mylhyl.circledialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;

/**
 * Created by hupei on 2017/3/29.
 */

final class AbsCircleDialog extends BaseCircleDialog {
    private static final String SAVED_PARAMS = "circle:params";
    private CircleParams mParams;
    private Controller mController;

    public static AbsCircleDialog newAbsCircleDialog(CircleParams params) {
        AbsCircleDialog circleDialog = new AbsCircleDialog();
        circleDialog.mParams = params;
        Bundle bundle = new Bundle();
        bundle.putSerializable(SAVED_PARAMS, params);
        circleDialog.setArguments(bundle);
        return circleDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mParams = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mParams = (CircleParams) savedInstanceState.getSerializable(SAVED_PARAMS);
        }
        DialogParams dialogParams = mParams.getDialogParams();
        setGravity(dialogParams.gravity);
        setCanceledOnTouchOutside(dialogParams.canceledOnTouchOutside);
        setCancelable(dialogParams.cancelable);
        setWidth(dialogParams.width);
        int[] mPadding = dialogParams.mPadding;
        if (mPadding != null)
            setPadding(mPadding[0], mPadding[1], mPadding[2], mPadding[3]);
        setAnimations(dialogParams.animStyle);
        setDimEnabled(dialogParams.isDimEnabled);
        setBackgroundColor(dialogParams.getBackgroundColor());
        setRadius(dialogParams.radius);
        setAlpha(dialogParams.alpha);
        setX(dialogParams.x);
        setY(dialogParams.y);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_PARAMS, mParams);
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        mController = new Controller(getContext(), mParams);
        mParams.dialogFragment = this;
        return mController.createView();
    }

    public void refreshView() {
        mController.refreshView();
    }
}