package com.sykean.hmhc.util;









/**
 * Created by Tian on 2016/1/7.
 */
public class ByteUtil
{
    /**
     * 将 BYTE 转换成 HEX 输出
     * @param src BYTE 数据
     * @return HEX 字符串
     */
    public static String bytesToHexString(byte[] src) {
        StringBuffer sb = new StringBuffer();
        if (src == null || src.length <= 0) {
            return null;
        }

        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    public static void main(String[] args) throws Exception{
		String path = "d:/1471940684709.txt";
		String str = "{\"user_id\":\"55\",\"name\":\"这\",\"tts_text\":\"是否\",\"duty\":\" \",\"depart\":\"\",\"numbr\":\"55\",\"schedule_id\":\"\",\"weigen\":\"\",\"permission\":\"0\",\"id_card\":\"\",\"ic\":\"\",\"phone_card\":\"\",\"id\":\"\",\"fp_count\":\"0\",\"fv_count\":\"0\",\"md5\":\"8381b4f7b221f6b8d95e6269842f4f3f\",\"data_len\":\"218540\",\"irisbmplen\":\"130678\",\"enroll_type\":\"1000000000000000\"}";

//		File file = new File(path);
//		byte [] bytes = FileUtils.readFileToByteArray(file);
//		System.out.println(new String(bytes,Value.CharSet));
		
	}

	/**
	 * 
	 * Int转换BCD码(byte)
	 * 
	 * 方法添加日期: 2013-9-4 创建者:wh
	 */
	public static byte Int2Bcd(int value) {
		return Int2Bcd(1, value)[0];
	}
	
	/**
	 * 
	 * Int转换BCD码(byte数组)
	 * 
	 * 方法添加日期: 2013-9-4 创建者:wh
	 */
	public static byte[] Int2Bcd(int n, int dec) {
		byte high, low;
		byte[] buf = new byte[n];
		for (int i = 0; i < n; i++) {
			low = (byte) (dec % 10);
			dec /= 10;
			high = (byte) (dec % 10);
			dec /= 10;
			buf[i] = (byte) ((high << 4) + (low & 0x0f));
		}
		return buf;
	}

}
