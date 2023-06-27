package com.wish.lmbank.album.compress;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/compress/InputStreamAdapter.class */
public abstract class InputStreamAdapter implements InputStreamProvider {
    private InputStream inputStream;

    public abstract InputStream openInternal() throws IOException;

    @Override // com.wish.lmbank.album.compress.InputStreamProvider
    public InputStream open() throws IOException {
        InputStream openInternal = openInternal();
        this.inputStream = openInternal;
        return openInternal;
    }

    @Override // com.wish.lmbank.album.compress.InputStreamProvider
    public void close() {
        InputStream inputStream = this.inputStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            } catch (Throwable th) {
                this.inputStream = null;
                throw th;
            }
            this.inputStream = null;
        }
    }
}
