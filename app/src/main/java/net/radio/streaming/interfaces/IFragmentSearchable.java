package net.radio.streaming.interfaces;

import net.radio.streaming.station.StationsFilter;

public interface IFragmentSearchable {
    void Search(StationsFilter.SearchStyle searchStyle, String query);
}
