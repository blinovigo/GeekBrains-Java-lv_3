package Lesson2and3.chat.server.core;

import Lesson2and3.chat.library.Library;
import Lesson2and3.network.SocketThread;
import Lesson2and3.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    private String nickname;
    private boolean isAuthorized;
    private boolean isReconnecting;

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public boolean isReconnecting() {
        return isReconnecting;
    }

    void reconnect() {
        isReconnecting = true;
        close();
    }

    public void authAccept(String nickname) {
        isAuthorized = true;
        this.nickname = nickname;
        sendMessage(Library.getAuthAccept(nickname));
    }

    public void authFail() {
        sendMessage(Library.getAuthDenied());
        close();
    }

    public void msgFormatError(String msg) {
        sendMessage(Library.getMsgFormatError(msg));
        close();
    }
}
