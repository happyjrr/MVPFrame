package com.kisen.mvpframe.list;

import com.kisen.mvplib.listhelper.AbsItem;
import com.kisen.mvplib.model.ModelResult;
import com.kisen.mvplib.model.ResultCallback;
import com.kisen.mvplib.presenter.AbsListPresenter;
import com.kisen.mvplib.util.RequestParam;
import com.kisen.mvplib.view.IView;

import java.util.ArrayList;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/3/22.
 */
public class TestListPresenter extends AbsListPresenter<ListData> {

    private ListModel model;
    private MultiLogic multiLogic;

    @Override
    public void attachView(IView view) {
        super.attachView(view);
        multiLogic = new MultiLogic();
        setItemLogic(multiLogic);
        model = new ListModel();
    }

    @Override
    protected AbsItem<ListData> setupItemTemplate() {
        return new ListItem();
    }


    public void setSelect(String ids) {
        multiLogic.setSelectIds(ids);
    }

    public void onRefresh(int resultCode, RequestParam param) {
        getView().openLoadingAnim();
        model.onRefresh(resultCode, param, new ResultCallback() {
            @Override
            public void onComplete(ModelResult result) {
                getView().closeLoadingAnim();
                getView().onModelComplete(result);
                ArrayList<ListData> source = result.getBundle().getParcelableArrayList("result");
                notifyAfterLoad(source);
            }
        });
    }

    protected void onLoad(RequestParam param) {
    }

    public void commit(int resultCode) {
        model.commit(resultCode, multiLogic, new DefResultCallback());
    }

}
