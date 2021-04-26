package driver;

public class DriverManagerFactory {

    private DriverManager driverManager = null;
    private static DriverManagerFactory driverManagerFactory;

    private DriverManagerFactory(){
    }

    public static DriverManagerFactory getDriverManagerFactory() {
        if(driverManagerFactory == null) {
            synchronized (DriverManagerFactory.class) {
                if(driverManagerFactory == null)
                    driverManagerFactory = new DriverManagerFactory();
            }
        }
        return driverManagerFactory;
    }

    public DriverManager getManager(DriverType type) {
        switch (type) {
            case IOS :
                driverManager = new IOSDriverManager();
                break;
        }
        return driverManager;
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

}
