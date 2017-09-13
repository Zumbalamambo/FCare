package com.prasilabs.fcare.backend.security;

public class HashGenerator
{
    /**
     *
     * @param txt, text in plain format 
     * @param hashType MD5 OR SHA1 
     * @return hash in hashType  
     */
    private static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            //error action
        }
        return null;
    }

    public static String md5(String txt) {
        return HashGenerator.getHash(txt, "MD5");
    }

    public static String sha1(String txt) {
        return HashGenerator.getHash(txt, "SHA1");
    }
} 