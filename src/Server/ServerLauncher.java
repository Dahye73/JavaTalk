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
            System.out.println("����ڰ� �α������� �ʾ����Ƿ� ������ ���۵��� �ʽ��ϴ�.");
        }
    }
}
