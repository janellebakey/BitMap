/*
    Southern Oregon University - CS258 Computer Science III - Lab 3

    Author: Janelle Bakey
    Date: 05/05/2017
    Class: BitMap.java
    Desc: This program is a general purpose class that performs operations
    on strings of bits.

 */
import com.sun.deploy.util.StringUtils;

import java.io.Serializable;

public class BitMap implements Comparable, Serializable
{
    public static final int  BITSIZE = 64; //maximum index value of loops
    private long bitString;

    public BitMap(){clearAll();} // Constructor with no parameters, sets all bits to off
    public BitMap(String s) throws IndexOutOfBoundsException,ArithmeticException{ //Constructor that can take a string consisting of 'f' 'F' 't' or 'T'
        if(s.length()>BITSIZE){throw new IndexOutOfBoundsException();}
        int j = BITSIZE-1; //for endianness
        for(int i=0; i<s.length(); i++){ //iterate through string
            if (s.toLowerCase().charAt(i) == 't') {setBit(i);} //set bit to 1
            else if (s.toLowerCase().charAt(i) == 'f') {clearBit(i);} //set bit to 0
            else {throw new ArithmeticException();} //input is not a t or f
        }
    }
    public  BitMap(boolean[] bits) throws IndexOutOfBoundsException{ //Constructor that takes boolean array of true or false
        if(bits.length > BITSIZE) {throw new IndexOutOfBoundsException();}
        int j = BITSIZE-1; //for endianness
        for(int i=0; i<bits.length; i++){ //iterate through array
            if(bits[i]) {setBit(i);} //set bit to 1
            else if(bits[i] == false){clearBit(i);} //set bit to 0
        }
    }
    //in the following methods, parameter b is the index ar which to set the bit
    private long bitMask(int b){return(1 << b);} //return a long with bit b set
    public void setBit(int b){bitString |= (bitMask(b));} //set bit to on
    public void clearBit(int b){bitString &= ~bitMask(b);} //set bit to off
    public boolean checkBit(int b){return((bitString & (1 << b)) != 0);} //return true if bit is on
    public int countTrue(){ //return count of bits that are on
        int count = 0;
        for(int i=0; i<BITSIZE; i++){if(checkBit(i)){count++;}}
        return count;
    }
    public void clearAll(){bitString = 0L;} //set all bits to off
    public void setAll(){bitString = ~(bitString&0);} //set all bits to on
    public int compareTo(Object bm){//For Comparable; Returns 0 when same amount of bits are on, 1 when this object has more on bits, or -1 when bm object has more on bits
        final BitMap temp = (BitMap)bm; //cast Object to BitMap object
        return Integer.compare(this.countTrue(), temp.countTrue());
    }
    public boolean equals(BitMap bm) {//returns true if current BitMap object and bm object have all on and off bits in the same positions
        for(int i=0; i<BITSIZE; i++){
            if(this.checkBit(i) != bm.checkBit(i)){return false;}
        }return true;
    }
    public String toString(){ //prints string of 'f' for 0 and 't' for 1
        String s = Long.toBinaryString(bitString);
        //System.out.println(s.length()); //test to see length before 0's are added
        if(s.length()<BITSIZE){s = String.format("%0" + (BITSIZE-s.length()) + "d%s", 0, s);}//append zeros to beginning
        //System.out.println(s); //test to see bits
        //System.out.println(bitString); //test to see long
        StringBuilder str = new StringBuilder();
        for(int i=0; i<s.length(); i++){
            if (s.charAt(i) == '1') {str.append('t');}
            else if (s.charAt(i) == '0'){ str.append('f');}
        }return str.toString();
    }
}
