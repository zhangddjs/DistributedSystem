package com.softwareArchitecture;

/**
 * @author zdd
 * @date 2018-11-23 17:26
 */
public class JDBCTest {

    public static void main(String[] args) {
        DBTool dbTool = new DBTool();
 //       dbTool.query();
        dbTool.close();
    }


}
