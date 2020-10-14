package com.pg.hu.bone.Bean;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/9/21 3:45 下午
 */

public class EventListenerBean {

    private void initialize() {
        EventManager.getInstance().
                addListener(s ->
                        System.out.println("event received in EventListenerBean : " + s));
    }
}
