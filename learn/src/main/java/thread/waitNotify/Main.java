package thread.waitNotify;

public class Main {
    public static void main(String[] args) {
        new ProviderThread().start();
        new CusomerThread().start();
    }
}
