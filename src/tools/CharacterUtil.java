package tools;

import java.util.Random;  
  
public class CharacterUtil {  
  
    public static String getRandomString(int length){  
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
          
        for(int i = 0 ; i < length; ++i){  
            int number = random.nextInt(62);//[0,62)  
              
            sb.append(str.charAt(number));  
        }  
        return sb.toString();  
    }  
      
    public static String getRandomString2(int length){  
        Random random = new Random();  
          
        StringBuffer sb = new StringBuffer();  
          
        for(int i = 0; i < length; ++i){  
            int number = random.nextInt(3);  
            long result = 0;  
              
            switch(number){  
            case 0:  
                result = Math.round(Math.random() * 25 + 65);  
                sb.append(String.valueOf((char)result));  
                break;  
            case 1:  
                result = Math.round(Math.random() * 25 + 97);  
                sb.append(String.valueOf((char)result));  
                break;  
            case 2:  
                sb.append(String.valueOf(new Random().nextInt(10)));  
                break;  
            }  
        }  
        return sb.toString();     
    }  
  
    public static void main(String[] args){  
        System.out.println(getRandomString(10));  
        System.out.println(getRandomString2(10));  
    }  
}