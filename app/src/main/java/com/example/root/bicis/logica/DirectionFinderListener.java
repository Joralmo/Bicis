package com.example.root.bicis.logica;

import java.util.List;

/**
 * Created by root on 7/06/17.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}