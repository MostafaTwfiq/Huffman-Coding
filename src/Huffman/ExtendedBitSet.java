package Huffman;

import java.util.BitSet;

public class ExtendedBitSet extends BitSet{
    int size;

    public ExtendedBitSet(int nbits){
        super(nbits);
        size = nbits;
    }

    @Override
    public void set(int bitIndex) {
        //if(bitIndex)
        super.set(bitIndex);
    }

    @Override
    public int size(){
        return size();
    }
}