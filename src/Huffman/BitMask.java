package Huffman;

import java.util.BitSet;

public class BitMask {

    public BitMask() {}

    public byte addBitToByte(byte b, boolean bit, short byteSize) {
        return (byte) (b | ((bit ? 1 : 0) << 7 - byteSize));
    }


    public BitSet mergeBitSets(BitSet bitSet1, BitSet bitSet2) {
        //len1 =  3
        //len2 =  3
        //size3 =  6
        // 0 1 2 3 4 5
        //

        BitSet bitSet3 = new BitSet(bitSet1.length() + bitSet2.length());
        int  len  =  bitSet1.length() + bitSet2.length();
        for (int i = bitSet1.length() - 1; i >= 0; i--) {
            bitSet3.set(len - (bitSet1.length() - i), bitSet1.get(i));
        }
        for (int i = bitSet2.length() - 1; i >= 0; i--) {
            System.out.println(len - bitSet1.length() - (bitSet2.length() - i)+":"+ i);
            bitSet3.set(len - bitSet1.length() - (bitSet2.length() - i), bitSet2.get(i));
        }

        /*for (int i = bitSet3.size() - 1; i > 0; i--) {
            if (i  - bitSet1.length() - 1 > 0) {
                bitSet3.set(i, bitSet1.get(bitSet1.length() - bitSet3.length() - 1));
            } else {
                bitSet3.set(i, bitSet2.get(bitSet2.length() - 1 - (bitSet3.length() - bitSet1.length())));
            }
        }*/

        return bitSet3;
    }


    public byte[] stringToBytes(String s) {
        byte[] bytesArr = new byte[(int) Math.ceil(s.length() / 8.0)];
        for (int i = 0; i < bytesArr.length; i++) {
            for (int j = 0 ; j < Math.min(s.length() - i * 8, 8); j++) {
                if (s.charAt(i * 8 + j) == '1')
                    bytesArr[i] |= (1 << (7 - j));

            }
        }

        return bytesArr;
    }

    public boolean getByteBit(byte b, byte index) {
        // 00100000
        // 00000001
        //
        // 01234567
        // index = 3 --> 8 - 3 = 5
        // 7 - 1 = 6
        return (b & (1 << (7 - index))) != 0;
    }

    public byte[] mergeTwoBytesArr(byte[] byteArr1, int lastByteSize1, byte[] byteArr2, int lastByteSize2) {
        // 11111000
        // 11111100 ----
        // 00000111

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
            if (i < byteArr1.length)//
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
