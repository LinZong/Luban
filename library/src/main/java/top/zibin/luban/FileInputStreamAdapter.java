package top.zibin.luban;

import java.io.IOException;
import java.io.InputStream;

public abstract class FileInputStreamAdapter implements InputStreamProvider {
    private InputStream inputStream;

    @Override
    public InputStream open() throws IOException {
        close();
        inputStream = openInternal();
        return inputStream;
    }

    public abstract InputStream openInternal() throws IOException;

    public abstract long reportLength();

    @Override
    public void close() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignore) {
            }finally {
                inputStream = null;
            }
        }
    }
}
