package com.example.myTestApp.service;

public class NumOfDoublesCounterImpl implements NumOfDoublesCounter {
    @Override
    public int count(String str) {
        var arr = str.toCharArray();
        var num = 0;
        var isNum = false;
        for (var i = 0; i < arr.length; i++) {
            if (Character.isDigit(arr[i])) {
                isNum = true;
            }
            if (isNum && ((!Character.isDigit(arr[i]) && arr[i] != '.') || i == arr.length - 1)) {
                num++;
                isNum = false;
            }
        }
        return num;
    }
}
