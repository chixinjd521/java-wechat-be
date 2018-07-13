package com.dadiyang.wx.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

/**
 * AES对称加密和解密
 */
public class Crypt {
    private static Logger logger = Logger.getLogger(Crypt.class);

    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据cryptRule规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String aesEncode(String content) {
        try {
            SecretKey key = initSecretKeyForAES();
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //11.将字符串返回
            return Base64.getEncoder().encodeToString(byte_AES);
        } catch (Exception e) {
            logger.error("aesEncode: " + content, e);
        }
        //如果有错就返加nulll
        return null;
    }

    /**
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String aesDecode(String content) {
        try {
            SecretKey key = initSecretKeyForAES();
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = Base64.getDecoder().decode(content);
            //解密
            byte[] byte_decode = cipher.doFinal(byte_content);
            return new String(byte_decode, "utf-8");
        } catch (Exception e) {
            logger.error("aesDecode: " + content, e);
        }
        return null;
    }

    private static SecretKey initSecretKeyForAES() throws NoSuchAlgorithmException {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //2.根据cryptRule规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(Conf.getValue("cryptRule").getBytes());
        keygen.init(128, random);
        //3.产生原始对称密钥
        SecretKey originalKey = keygen.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] raw = originalKey.getEncoded();
        //5.根据字节数组生成AES密钥
        return new SecretKeySpec(raw, "AES");
    }

    public static String md5WithSalt(String s) {
        return DigestUtils.md5Hex(s + "{" + Conf.getValue("cryptRule") + "}");
    }

    public static String genToken(String username, String sign, int expire) {
        String content = username + ":" + sign + ":" + (System.currentTimeMillis() + expire * 1000);
        return Crypt.aesEncode(content);
    }

    public static String auth(String token) {
        try {
            if (StringUtils.isBlank(token)) {
                return null;
            }
            String str = Crypt.aesDecode(token);
            if (StringUtils.isBlank(str)) {
                return null;
            }
            String[] strings = str.split(":");
            String username = strings[0];
            String sign = strings[1];
            long expTime = Long.parseLong(strings[2]);
            if (System.currentTimeMillis() <= expTime) {
                String cache = JedisUtil.getInstance().get(Conf.PS_SIGN_KEY + sign);
                if (Objects.equals(cache, username)) {
                    // 签名校验
                    return username;
                }
            }
        } catch (Exception e) {
            logger.error("auth", e);
        }
        return null;
    }

    public static void main(String[] args) {
        // 加密
        System.out.println("请输入要加密的内容:");
        long start = System.currentTimeMillis();
        logger.info("now" + System.currentTimeMillis());
        logger.info("now" + (System.currentTimeMillis() + 86400L * 365000L));
        String content = "zhangsan:" + md5WithSalt("Wxpwd2018") + ":" + (System.currentTimeMillis() + 86400L * 365000L);
        System.out.println("内容：" + content);
        String md5 = md5WithSalt(content);
        System.out.println("md5WithSalt: " + md5);
        String encodedContent = Crypt.aesEncode(content);
        System.out.println("加密耗时" + (System.currentTimeMillis() - start));
        System.out.println("加密后的密文是:" + encodedContent);
        //解密
        start = System.currentTimeMillis();
        String decodedContent = Crypt.aesDecode(encodedContent);
        System.out.println("解密耗时" + (System.currentTimeMillis() - start));
        System.out.println("解密后的明文是:" + decodedContent);
        System.out.println(md5WithSalt("Wxpwd2018"));
    }

}
