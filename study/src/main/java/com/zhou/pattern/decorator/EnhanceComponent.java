package com.zhou.pattern.decorator;

public abstract class EnhanceComponent implements IComponent {
    protected IComponent component;

    public EnhanceComponent(IComponent component) {
        this.component = component;
    }
    protected abstract void enhanceBefore();
    protected abstract void enhanceAfter();
    @Override
    public void function() {
//        enhanceBefore();
        component.function();
//        enhanceAfter();
    }
}

class EnhanceComponent1 extends EnhanceComponent{

    public EnhanceComponent1(IComponent component) {
        super(component);
    }

    public void enhanceAfter(){
        System.out.println("第一个After增强功能");
    }
    public void enhanceBefore(){
        System.out.println("第一个Before增强功能");
    }

    @Override
    public void function() {
        enhanceBefore();
        super.function();
        enhanceAfter();
    }
}

class EnhanceComponent2 extends EnhanceComponent{

    public EnhanceComponent2(IComponent component) {
        super(component);
    }

    public void enhanceAfter(){
        System.out.println("第二个After增强功能");
    }
    public void enhanceBefore(){
//        System.out.println("第二个Before增强功能");
    }

    @Override
    public void function() {
        super.function();
        enhanceAfter();
    }

}
