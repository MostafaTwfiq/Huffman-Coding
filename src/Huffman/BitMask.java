package Huffman;

public class BitMask {

    public BitMask() {}

    public byte addBitToByte(byte b, boolean bit, short byteSize) {
        return (b |= (bit ? 1 : 0) << 7 - byteSize);
    }

    public byte[] stringToBytes(String s) {
        byte[] bytesArr = new byte[(int) Math.ceil(s.length() / 8.0)];
        for (int i = 0; i < bytesArr.length; i++) {
            for (int j = 0 ; j < Math.min(s.length() - i * 8, 8); j++) {
                if (s.charAt(i * 8 + j) == '1')
                    bytesArr[i] = (byte) (bytesArr[i] + (1 << (7 - j)));

            }
        }

        return bytesArr;
    }

    public byte[] mergeTwoBytesArr(byte[] byteArr1, int lastByteSize1, byte[] byteArr2, int lastByteSize2) {
        // 11111000
        // 11111100

        // 11111111
        // 11100000
        byte[] fByteArr = new byte[byteArr1.length + byteArr2.length - 1 + ((8 - lastByteSize1) >= (lastByteSize2) ? 0 : 1)];
        byteArr1[byteArr1.length - 1] |= (byte) (Byte.toUnsignedInt(byteArr2[0]) >>> lastByteSize1);

        for (int i = 0; i < byteArr2.length - 1; i++) {
            byteArr2[i] <<= (8 - lastByteSize1);
            byteArr2[i] |= (byte) (Byte.toUnsignedInt(byteArr2[i + 1]) >>> lastByteSize1);
        }

        if ((8 - lastByteSize1) < (lastByteSize2))
            byteArr2[byteArr2.length - 1] <<= (8 - lastByteSize1);

        for (int i = 0; i < fByteArr.length; i++) {
            if (i < byteArr1.length)
                fByteArr[i] = byteArr1[i];
            else
                fByteArr[i] = byteArr2[i - byteArr1.length];
        }

        return fByteArr;
    }

    public String byteToString(byte b) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= 64; i *= 2) {
            if ((b & i) != 0)
                stringBuilder.append('1');
            else
                stringBuilder.append('0');
        }
        if ((b & -128) != 0)
            stringBuilder.append('1');
        else
            stringBuilder.append('0');

        return stringBuilder.reverse().toString();
    }

}
