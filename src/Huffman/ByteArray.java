package Huffman;

import java.util.Arrays;

public class ByteArray {
    private final byte[] data;
    public ByteArray(byte[] data){
       this.data = data;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public boolean equals(Object byteArray) {
        byte[] b = ((ByteArray) byteArray).getData();
        if (b.length != data.length)
            return false;

        for (int i = 0; i < data.length; i++) {
            if (data[i] != b[i])
                return false;
        }

        return true;
    }

    public byte[] getData() {
        return data;
    }
}
