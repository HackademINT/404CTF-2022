package org.hallebarde.recrutement.util;

import org.hallebarde.recrutement.RecrutementGame;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class IoUtil {

    public static <E> E loadJsonFile(File file, Class<E> clazz) throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
            String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            return RecrutementGame.GSON.fromJson(json, clazz);
        }
    }

    public static void writeResourceToFile(Class<?> clazz, String resource, File file) throws IOException {
        try (InputStream in = clazz.getResourceAsStream(resource)) {
            if (in == null) throw new IOException("Missing resource: " + resource);
            InputStream bufferedIn = new BufferedInputStream(in);
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                bufferedIn.transferTo(out);
            }
        }
    }

    private IoUtil() {
        throw new IllegalStateException("Cannot instantiate " + this.getClass());
    }

}
