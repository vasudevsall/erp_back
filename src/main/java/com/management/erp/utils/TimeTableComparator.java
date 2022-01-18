package com.management.erp.utils;

import com.management.erp.models.repository.TimeTableModel;

import java.util.Comparator;

public class TimeTableComparator implements Comparator<TimeTableModel> {

    @Override
    public int compare(TimeTableModel o1, TimeTableModel o2) {
        int d1 = o1.getDay().getValue();
        int d2 = o2.getDay().getValue();

        if(d1 < d2) return -1;
        if(d1 > d2) return 1;

        int h1 = o1.getHour();
        int h2 = o2.getHour();

        if(h1 < h2) return -1;
        if(h1 > h2) return 1;

        int m1 = o1.getMinute();
        int m2 = o2.getMinute();

        return Integer.compare(m1, m2);
    }
}
