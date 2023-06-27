package com.wish.lmbank.album.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/BufferedInputStreamWrap.class */
public class BufferedInputStreamWrap extends FilterInputStream {
    public static final int DEFAULT_MARK_READ_LIMIT = 5242880;
    private volatile byte[] buf;
    private int count;
    private int markLimit;
    private int markPos;
    private int pos;

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    public BufferedInputStreamWrap(InputStream inputStream) {
        this(inputStream, 65536);
    }

    BufferedInputStreamWrap(InputStream inputStream, int i) {
        super(inputStream);
        this.markPos = -1;
        this.buf = ArrayPoolProvide.getInstance().get(i);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        synchronized (this) {
            InputStream inputStream = this.in;
            if (this.buf == null || inputStream == null) {
                return 0;
            }
            int i = this.count;
            int i2 = this.pos;
            return (i - i2) + inputStream.available();
        }
    }

    private static IOException streamClosed() throws IOException {
//         throw new IOException(bb7d7pu7.m5998("KxwPDwwbDA0gBxkcHTodGwwIBEkAGkkKBQYaDA0"));
        throw new IOException("BufferedInputStream is closed");
    }

    public void fixMarkLimit() {
        synchronized (this) {
            this.markLimit = this.buf.length;
        }
    }

    public void release() {
        if ((12956 + 19438) % 19438 > 0) {
            synchronized (this) {
                if (this.buf != null) {
                    ArrayPoolProvide.getInstance().put(this.buf);
                    this.buf = null;
                }
            }
            return;
        }
        int i = (-14539) + (-14539) + 19215;
        while (true) {
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (((-5908) + 8370) % 8370 > 0) {
            if (this.buf != null) {
                ArrayPoolProvide.getInstance().put(this.buf);
                this.buf = null;
            }
            InputStream inputStream = this.in;
            this.in = null;
            if (inputStream != null) {
                inputStream.close();
                return;
            }
            return;
        }
        int i = (-56) + (-56) + 16621;
        while (true) {
        }
    }

    private int fillbuf(InputStream inputStream, byte[] bArr) throws IOException {
        int i = this.markPos;
        if (i != -1) {
            int i2 = this.pos;
            int i3 = this.markLimit;
            if (i2 - i < i3) {
                if (i == 0 && i3 > bArr.length && this.count == bArr.length) {
                    int length = bArr.length * 2;
                    if (length <= i3) {
                        i3 = length;
                    }
                    byte[] bArr2 = ArrayPoolProvide.getInstance().get(i3);
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    this.buf = bArr2;
                    ArrayPoolProvide.getInstance().put(bArr);
                    bArr = bArr2;
                } else if (i > 0) {
                    System.arraycopy(bArr, i, bArr, 0, bArr.length - i);
                }
                int i4 = this.pos - this.markPos;
                this.pos = i4;
                this.markPos = 0;
                this.count = 0;
                int read = inputStream.read(bArr, i4, bArr.length - i4);
                int i5 = this.pos;
                if (read > 0) {
                    i5 += read;
                }
                this.count = i5;
                return read;
            }
        }
        int read2 = inputStream.read(bArr);
        if (read2 > 0) {
            this.markPos = -1;
            this.pos = 0;
            this.count = read2;
        }
        return read2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        synchronized (this) {
            this.markLimit = Math.max(this.markLimit, i);
            this.markPos = this.pos;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (((-2896) + 8116) % 8116 > 0) {
            synchronized (this) {
                byte[] bArr = this.buf;
                InputStream inputStream = this.in;
                if (bArr == null || inputStream == null) {
                    throw streamClosed();
                }
                if (this.pos < this.count || fillbuf(inputStream, bArr) != -1) {
                    byte[] bArr2 = bArr;
                    if (bArr != this.buf) {
                        bArr2 = this.buf;
                        if (bArr2 == null) {
                            throw streamClosed();
                        }
                    }
                    int i = this.count;
                    int i2 = this.pos;
                    if (i - i2 > 0) {
                        this.pos = i2 + 1;
                        return bArr2[i2] & 255;
                    }
                    return -1;
                }
                return -1;
            }
        }
        int i3 = 11020 + (11020 - 781);
        while (true) {
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        int min;
        synchronized (this) {
            byte[] bArr2 = this.buf;
            if (bArr2 == null) {
                throw streamClosed();
            }
            if (i2 == 0) {
                return 0;
            }
            InputStream inputStream = this.in;
            if (inputStream == null) {
                throw streamClosed();
            }
            int i4 = this.pos;
            int i5 = this.count;
            if (i4 < i5) {
                int min2 = Math.min(i5 - i4, i2);
                System.arraycopy(bArr2, this.pos, bArr, i, min2);
                this.pos += min2;
                if (min2 == i2 || inputStream.available() == 0) {
                    return min2;
                }
                i += min2;
                i3 = i2 - min2;
            } else {
                i3 = i2;
            }
            while (true) {
                int i6 = -1;
                if (this.markPos == -1 && i3 >= bArr2.length) {
                    int read = inputStream.read(bArr, i, i3);
                    min = read;
                    if (read == -1) {
                        if (i3 != i2) {
                            i6 = i2 - i3;
                        }
                        return i6;
                    }
                } else if (fillbuf(inputStream, bArr2) == -1) {
                    if (i3 != i2) {
                        i6 = i2 - i3;
                    }
                    return i6;
                } else {
                    byte[] bArr3 = bArr2;
                    if (bArr2 != this.buf) {
                        bArr3 = this.buf;
                        if (bArr3 == null) {
                            throw streamClosed();
                        }
                    }
                    min = Math.min(this.count - this.pos, i3);
                    System.arraycopy(bArr3, this.pos, bArr, i, min);
                    this.pos += min;
                    bArr2 = bArr3;
                }
                i3 -= min;
                if (i3 == 0) {
                    return i2;
                }
                if (inputStream.available() == 0) {
                    return i2 - i3;
                }
                i += min;
            }
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        if ((18404 + 11273) % 11273 > 0) {
            synchronized (this) {
                if (this.buf == null) {
//                     throw new IOException(bb7d7pu7.m5998("Oh0bDAgESQAaSQoFBhoMDQ"));
                    throw new IOException("Stream is closed");
                }
                int i = this.markPos;
                if (-1 == i) {
//                     throw new InvalidMarkException(bb7d7pu7.m5998("JAgbAkkBCBpJCwwMB0kABx8IBQANCB0MDUVJGQYaU0k") + this.pos + bb7d7pu7.m5998("SQQIGwIlAAQAHVNJ") + this.markLimit);
                    throw new InvalidMarkException("Mark has been invalidated, pos: " + this.pos + " markLimit: " + this.markLimit);
                }
                this.pos = i;
            }
            return;
        }
        int i2 = (-4730) + (-4730) + 14765;
        while (true) {
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        if (((-8461) - 3982) % (-3982) <= 0) {
            synchronized (this) {
                if (j < 1) {
                    return 0L;
                }
                byte[] bArr = this.buf;
                if (bArr == null) {
                    throw streamClosed();
                }
                InputStream inputStream = this.in;
                if (inputStream == null) {
                    throw streamClosed();
                }
                int i = this.count;
                int i2 = this.pos;
                if (i - i2 >= j) {
                    this.pos = (int) (i2 + j);
                    return j;
                }
                long j2 = i - i2;
                this.pos = i;
                if (this.markPos != -1 && j <= this.markLimit) {
                    if (fillbuf(inputStream, bArr) == -1) {
                        return j2;
                    }
                    int i3 = this.count;
                    int i4 = this.pos;
                    if (i3 - i4 >= j - j2) {
                        this.pos = (int) ((i4 + j) - j2);
                        return j;
                    }
                    long j3 = i3;
                    long j4 = i4;
                    this.pos = i3;
                    return (j2 + j3) - j4;
                }
                return j2 + inputStream.skip(j - j2);
            }
        }
        int i5 = (-10237) + ((-10237) - 9605);
        while (true) {
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/BufferedInputStreamWrap$InvalidMarkException.class */
    static class InvalidMarkException extends IOException {
        private static final long serialVersionUID = -4338378848813561759L;

        InvalidMarkException(String str) {
            super(str);
        }
    }
}
