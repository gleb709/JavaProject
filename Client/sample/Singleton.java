package sample;

public final class Singleton {
    private  static Singleton singleton;
    private  static String userName;

    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        return singleton;
    }

    private Singleton(){}

    public void addName(String name){
        userName = name;
    }

    public String getUserName(){
        return userName;
    }
}