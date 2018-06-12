package com.diabin.latte.app;

import java.util.WeakHashMap;

/**
* 项目名： FestEC
* 包名： com.diabin.latte.app
* 文件名： Configurator
* 创建者： HJF
* 创建时间： 2018/1/2221:06
* 描述： com.diabin.latte.app
 * 新建private static final WeakHashMap<String, Object> LATTE_CONFIGS = new WeakHashMap<>();
 * 初始化是name为false
 * 优雅的单例模式
 * 取得全局成员变量方法 return LATTE_CONFIGS
 * 让成员变量 LATTE_CONFIGS 赋值方法
 * checkConfiguration():检查是否赋值给某个值，如果为false，就返回
 * 举一反三：WeakHashMap与室内机项目区别：
 * static修饰成员变量与方法
 * final修饰成员变量与方法
 *
 * 此类作用：Configurator 新建LATTE_CONFIGS,存储key，values。
 *
*/

public class Configurator {
    private static final WeakHashMap<String, Object> LATTE_CONFIGS = new WeakHashMap<>();

    //初始化，但是为false
    private Configurator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);

    }

    /**
     * 优雅的单例模式
     * 静态内部类
     * 线程安全的懒汉模式
     */
    public static Configurator getInstance(){
        return  Holder.INSTANCE;
    }
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();

    }

    /**
     * 为什么不用static呢？
     * 还有不用public 为毛只用了？
     * @return
     */
    final  WeakHashMap<String,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }
    public final void configure(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    /**
     * 为什么要转型呢？
     * 检查是否
     */
    private void checkConfiguration(){
        final boolean isReady= (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    /**
     * 好好琢磨下这里是怎样写的？
     * @param key
     * @param <T>
     * @return
     */
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }


}