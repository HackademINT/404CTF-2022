package org.hallebarde.recrutement.network;

import org.hallebarde.recrutement.NetworkManager;
import org.hallebarde.recrutement.api.network.UserConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class PlayerSocket implements UserConnection {

    private final NetworkManager networkManager;
    private final Socket socket;
    private final InputStream inStream;
    private final OutputStream outStream;
    private final byte[] readBuffer = new byte[2048];

    public PlayerSocket(NetworkManager manager, Socket socket) throws IOException {
        this.networkManager = manager;
        this.socket = socket;
        this.inStream = socket.getInputStream();
        this.outStream = socket.getOutputStream();
    }

    @Override
    public CompletableFuture<String> readLine() {
        return this.networkManager.doNetwork(this::receiveData)
                .thenApplyAsync(b -> new String(b, StandardCharsets.UTF_8).strip().intern());
    }

    @Override
    public CompletableFuture<Void> write(String text) {
        if (text == null) return CompletableFuture.completedFuture(null);
        return this.networkManager.doNetwork(() -> {
            this.sendData(text.getBytes(StandardCharsets.UTF_8));
            return null;
        });
    }

    @Override
    public synchronized boolean isOpened() {
        return this.socket.isConnected() && !this.socket.isClosed();
    }

    @Override
    public CompletableFuture<Void> close(String message) {
        return this.writeLine(message).thenRunAsync(this::closeSocket);
    }

    @Override
    public InetAddress getAddress() {
        return this.socket.getInetAddress();
    }

    private void sendData(byte[] data) {
        try {
            synchronized (this.outStream) {
                this.outStream.write(data);
                this.outStream.flush();
            }
        } catch (IOException e) {
            NetworkManager.LOGGER.error("An error occurred when sending data to client {}, considering them as offline.", this.getAddress());
            NetworkManager.LOGGER.catching(e);
            this.closeSocket();
        }
    }

    private byte[] receiveData() {
        try {
            synchronized (this.inStream) {
                int read = this.inStream.read(this.readBuffer);
                return Arrays.copyOf(this.readBuffer, read);
            }
        } catch (IOException e) {
            NetworkManager.LOGGER.error("An error occurred when receiving data from client {}, considering them as offline.", this.getAddress());
            NetworkManager.LOGGER.catching(e);
            this.closeSocket();
        }
        return new byte[0];
    }

    private void closeSocket() {
        try {
            synchronized (this.inStream) {
                synchronized (this.outStream) {
                    this.socket.close();
                }
            }
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException f) {
                NetworkManager.LOGGER.error("Failed to close a socket!");
                NetworkManager.LOGGER.catching(e);
            }
        }
    }

}
