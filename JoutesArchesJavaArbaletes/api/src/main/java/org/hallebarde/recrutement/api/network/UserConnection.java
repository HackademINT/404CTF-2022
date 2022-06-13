package org.hallebarde.recrutement.api.network;

import org.hallebarde.recrutement.api.annotations.FreeToImplement;

import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

@FreeToImplement
public interface UserConnection {

    CompletableFuture<String> readLine();

    CompletableFuture<Void> write(String text);

    default CompletableFuture<Void> writeLine(String line) {
        if (line == null) return CompletableFuture.completedFuture(null);
        return this.write(line + "\n");
    }

    boolean isOpened();

    CompletableFuture<Void> close(String message);

    InetAddress getAddress();

}
