package top.zibin.luban;

import android.content.res.AssetFileDescriptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public abstract class AssetInputStreamAdapter implements InputStreamProvider {

    private InputStream is;

    private byte[] assetBytes;

    public AssetInputStreamAdapter(AssetFileDescriptor assetFD) throws IOException {
        readAssets(assetFD);
    }

    private void readAssets(AssetFileDescriptor assetFD) throws IOException {
        InputStream is = assetFD.createInputStream();
        ByteBuffer bb = ByteBuffer.allocate((int) assetFD.getLength());
        ReadableByteChannel rbc = Channels.newChannel(is);
        rbc.read(bb);
        rbc.close();
        assetBytes = bb.array();
    }

    @Override
    public InputStream open() {
        return is = openInternal();
    }

    public InputStream openInternal() {
        return new ByteArrayInputStream(assetBytes);
    }

    public long reportLength() {
        return assetBytes.length;
    }

    @Override
    public void close() {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ignore) {
            } finally {
                is = null;
            }
        }
    }
}
