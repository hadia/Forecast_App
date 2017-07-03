package com.eu.parent.hadia.weatherapp.common;

/**
 * Created by Hadia .
 * IBM
 *
 * @author Hadia
 *         on 6/12/17.
 */

import android.os.AsyncTask;

public abstract class Interactor<R> extends AsyncTask<Void, Integer, R> {

    protected InteractorListener<R> listener;

    private InteractorSuccessListener<R> successListener;

    protected abstract R onTaskWork();

    public Interactor() {

    }

    @Deprecated
    public Interactor(InteractorListener<R> listener) {

        this.listener = listener;
    }

    public void execute() {
        super.execute();
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }

    @Override
    protected R doInBackground(Void... params) {

        R result = null;

        result = onTaskWork();

        return result;
    }

    @Override
    protected void onPostExecute(R result) {

        super.onPostExecute(result);
        success(result);
    }


    /**
     * fire onInteractorSuccess
     */
    protected void success(R result) {


        if (listener != null)
            listener.onInteractorSuccess(this, result);
        if (successListener != null)
            successListener.onSuccess(this, result);
    }

    public void updateView(int progress) {

        publishProgress(progress);
    }
    public Interactor setSuccessListener(InteractorSuccessListener successListener) {
        this.successListener = successListener;
        return this;
    }

    public Interactor setListener(InteractorListener<R> listener) {
        this.listener = listener;
        return this;
    }
}


