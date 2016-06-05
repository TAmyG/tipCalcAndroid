package com.distinct.tamyg.tipcalc.fragments;

import com.distinct.tamyg.tipcalc.models.TipRecord;

/**
 * Created by tamyg on 3/06/16.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
