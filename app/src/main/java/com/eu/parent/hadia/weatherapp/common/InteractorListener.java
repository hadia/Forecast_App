package com.eu.parent.hadia.weatherapp.common;

public interface InteractorListener<R> {
    void onInteractorSuccess(Interactor interactor, R result);
}
