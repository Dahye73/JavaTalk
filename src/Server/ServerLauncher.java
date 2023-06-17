package Server;

public class ServerLauncher {
    private boolean isLoggedIn;
    public static int port = 9999;
    public ServerLauncher(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void startServer() {
        if (isLoggedIn) {
            Server server = new Server();
            server.start(port);
        } else {
            System.out.println("사용자가 로그인하지 않았으므로 서버가 시작되지 않습니다.");
        }
    }
}
