package com.zdd;

/**
 * @author zdd
 * @date 2018-11-24 10:27
 */
/*class SubThread implements Runnable{
    //重写run方法

    public void run() {
        while (true) {
            if (flag) {
                flag = false;
                try {
                    rmi.getProxy(IAction.class)
                            .heartBitServer();
                    mapRes.put(index+"", "1");
                } catch (Exception e) {
                    mapRes.put(index+"", "0");
                }
                flag = true;
            }
 //           Thread.sleep(30 * 1000);
        }

    }

}*/

public class ThreadTest {



}

