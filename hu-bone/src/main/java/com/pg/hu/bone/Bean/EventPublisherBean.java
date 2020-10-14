package com.pg.hu.bone.Bean;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/9/21 3:44 下午
 */

public class EventPublisherBean {

    public void initialize() {
        System.out.println("EventPublisherBean initializing");
        EventManager.getInstance().publish("event published from EventPublisherBean");
    }
}