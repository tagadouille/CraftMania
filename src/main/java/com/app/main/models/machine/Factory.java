package com.app.main.models.machine;

import com.app.main.models.resources.ResourceEnum;

public final class Factory extends Machine {

    public Factory(int price, ResourceEnum product, long capacity, long production_duration) {
        super(price, product, capacity, production_duration);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void process() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'process'");
    }

}
//TODO
/*
public sealed abstract class Factory extends Machine {

    public static class SimpleFactory {

    }
    
}*/
