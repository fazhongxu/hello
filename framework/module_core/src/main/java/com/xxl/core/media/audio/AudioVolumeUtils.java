package com.xxl.core.media.audio;

/**
 * 声音音量工具泪
 * reference https://blog.csdn.net/mai763727999/article/details/106048905
 *
 * @author xxl.
 * @date 2022/3/3.
 */
public class AudioVolumeUtils {

    /**
     * db为0表示保持音量不变，db为负数表示较低音量，为正数表示提高音量
     */
    public static int db = 20;

    public static double factor = Math.pow(10, (double) db / 20);

    private AudioVolumeUtils() {

    }

//    //调节PCM数据音量
//    public static int amplifyPCMData(byte[] pData, int nLen, byte[] data2, int nBitsPerSample, float multiple)
//    {
//        int nCur = 0;
//        if (16 == nBitsPerSample)
//        {
//            while (nCur < nLen)
//            {
//                short volum = getShort(pData, nCur);
//
//                volum = (short)(volum * multiple);
//
//                data2[nCur]   = (byte)( volum       & 0xFF);
//                data2[nCur+1] = (byte)((volum >> 8) & 0xFF);
//                nCur += 2;
//            }
//
//        }
//        return 0;
//    }


//    /**
//     * 调节PCM数据音量
//     *
//     * @param pData          原始音频byte数组
//     * @param nLen           原始音频byte数组长度
//     * @param data2          转换后新音频byte数组
//     * @param nBitsPerSample 采样率
//     * @param multiple       表示Math.pow()返回值
//     * @return
//     */
    public static int amplifyPCMData(byte[] pData, int nLen, byte[] data2, int nBitsPerSample, float multiple) {
        int nCur = 0;
        if (16 == nBitsPerSample) {
            while (nCur < nLen) {
                short volum = getShort(pData, nCur);
                float pcmval = volum * multiple;
                //volum = (short)(volum * multiple);
                //数据溢出处理
                if (pcmval < 32767 && pcmval > -32768) {
                    volum = (short) pcmval;
                } else if (pcmval > 32767) {
                    volum = (short) 32767;
                } else if (pcmval < -32768) {
                    volum = (short) -32768;
                }
                data2[nCur] = (byte) (volum & 0xFF);
                nCur += 2;
            }
        }
        return 0;
    }

    private static short getShort(byte[] data, int start) {
        return (short) ((data[start] & 0xFF) | (data[start + 1] << 8));
    }

}