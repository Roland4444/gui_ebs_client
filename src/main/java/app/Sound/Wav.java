package app.Sound;

import java.math.BigInteger;

public class Wav {
    public static String printByte(byte[] arr) {
        BigInteger out = new BigInteger(1, arr);
        return out.toString(16);
    }

    public static byte[] getSubBytes(byte[] arr, int startpos, int finishpos) {
        byte[] result = new byte[finishpos - startpos + 1];
        int counter = 0;
        for (int i = startpos; i <= finishpos; i++) {
            result[counter++] = arr[i];
        }
        return result;
    }
    public static byte[] getchunkId(byte[] wav){
        return getSubBytes(wav, 0,3);
    }

    public static byte[] getchunkSize(byte[] wav){
        return getSubBytes(wav, 4,7);
    }

    public static byte[] getformat(byte[] wav){
        return getSubBytes(wav, 8,11);
    }

    public static byte[] getsubchunk1Id(byte[] wav){
        return getSubBytes(wav, 12,15);
    }

    public static byte[] getsubchunk1Size(byte[] wav){
        return getSubBytes(wav, 16,19);
    }

    public static byte[] getaudioFormat(byte[] wav){
        return getSubBytes(wav, 20,21);
    }

    public static byte[] getnumChannels(byte[] wav){
        return getSubBytes(wav, 22,23);
    }

    public static byte[] getsampleRate(byte[] wav){
        return getSubBytes(wav, 24,27);
    }

    public static byte[] getbyteRate(byte[] wav){
        return getSubBytes(wav, 28,31);
    }

    public static byte[] getblockAlign(byte[] wav){
        return getSubBytes(wav, 32,33);
    }

    public static byte[] getbitsPerSample(byte[] wav){
        return getSubBytes(wav, 34,35);
    }

    public static byte[] getsubchunk2Id(byte[] wav){
        return getSubBytes(wav, 36,39);
    }

    public static byte[] getsubchunk2Size(byte[] wav){
        return getSubBytes(wav, 40,43);
    }

    public static byte[] getdata(byte[] wav){
        return getSubBytes(wav, 44,wav.length-1);
    }

    public static int getNumberFromBytes(byte[] arr){
        var result = new BigInteger(1, arr);//String.valueOf(printByte(arr)));
        return result.intValue();
    }

    public static String getFullInfo(byte[] arr){
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("getchunkId ==>  " +getNumberFromBytes(toBigEndian(getchunkId(arr)))+"\n");
        strBuf.append("chunkSize ==>  " +getNumberFromBytes(toBigEndian(getchunkSize(arr)))+"\n");
        strBuf.append("format ==>  " +getNumberFromBytes(toBigEndian(getformat(arr)))+"\n");
        strBuf.append("subchunk1Id ==>  " +getNumberFromBytes(toBigEndian(getsubchunk1Id(arr)))+"\n");
        strBuf.append("subchunk1Size ==>  " +getNumberFromBytes(toBigEndian(getsubchunk1Size(arr)))+"\n");
        strBuf.append("audioFormat ==>  " +getNumberFromBytes(toBigEndian(getaudioFormat(arr)))+"\n");
        strBuf.append("numChannels ==>  " +getNumberFromBytes(toBigEndian(getnumChannels(arr)))+"\n");
        strBuf.append("sampleRate ==>  " +getNumberFromBytes(toBigEndian(getsampleRate(arr)))+"\n");
        strBuf.append("byteRate ==>  " +getNumberFromBytes(toBigEndian(getbyteRate(arr)))+"\n");
        strBuf.append("blockAlign ==>  " +getNumberFromBytes(toBigEndian(getblockAlign(arr)))+"\n");
        strBuf.append("bitsPerSample ==>  " +getNumberFromBytes(toBigEndian(getbitsPerSample(arr)))+"\n");
        strBuf.append("subchunk2Id ==>  " +getNumberFromBytes(toBigEndian(getsubchunk2Id(arr)))+"\n");
        strBuf.append("subchunk2Size ==>  " +getNumberFromBytes(toBigEndian(getsubchunk2Size(arr)))+"\n");
        strBuf.append("Data length ==>  " +getdata(arr).length+"\n");

        return strBuf.toString();
    }

    public static byte[] toBigEndian(byte[] arr){
        byte[] result = new byte[arr.length];
        for (int i=0; i<=arr.length-1;i++){
            result[result.length-1-i]=arr[i];
        }
        return result;
    }

}
