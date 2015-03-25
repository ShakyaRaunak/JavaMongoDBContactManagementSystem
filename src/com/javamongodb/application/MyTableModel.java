/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javamongodb.application;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Raunak Shakya
 */
public class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;

    }

}
