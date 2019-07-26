package com.zhou.pattern.state;

import com.zhou.pattern.proxy.Concert;

public class Client {

    public static void main(String[] args) {
        Context ctx = new Context();
        ctx.setState(new FreeState());
        ctx.setState(new BookedState());
        ctx.setState(new CheckedState());
    }
}
