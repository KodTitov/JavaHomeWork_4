package org.itstep;

import java.util.ArrayList;
import java.util.Arrays;

public class StringCalculator {

    private static String getRedex(String s) {
        String regex = ",|\\n";

        if (s.length() == 0) {
            return regex;
        }

        String[] userRegex;
        if (!(s.length() > 2 && s.substring(0, 2).equals("//"))) {
            System.out.println("throw SpliterFormatException");
            return null;
        }

        s = s.substring(2).trim();

        if (s.length() == 1){
            userRegex = new String[]{s};
        } else {
            userRegex = s.split("]");
            for (int i = 0; i < userRegex.length; i++) {
                userRegex[i] = userRegex[i].trim();
                if (userRegex[i].length() > 0) {
                    userRegex[i] = userRegex[i].substring(1);
                }
            }
        }

        String[] arr = new String[0];
        for (int i = 0; i < userRegex.length; i++) {
            if (userRegex[i].length() != 0){
                arr = Arrays.copyOf(arr, arr.length + 1);
                arr[arr.length - 1] = userRegex[i];
            }
        }

        userRegex = arr;

        for (int i = 0; i < userRegex.length; i++) {
            String tmp = "";
            for (int j = 0; j < userRegex[i].length(); j++) {
                if (userRegex[i].charAt(j) == '*') {
                    tmp += "\\*";
                } else if (userRegex[i].charAt(j) == '|') {
                    tmp += "\\|";
                } else if (userRegex[i].charAt(j) == '$') {
                    tmp += "\\$";
                } else {
                    tmp += userRegex[i].charAt(j);
                }
            }
            userRegex[i] = tmp;
        }

        for (int i = 0; i < userRegex.length; i++) {
            boolean check = true;
            for (int j = 0; j < userRegex[i].length(); j++) {
                int num = userRegex[i].charAt(j);
                if (!(num <= '9' && num >= '0')) {
                    check = false;
                }
            }
            if (check) {
                System.out.println("throw SpliterFormatException");
                return null;
            }
        }

        for (int i = 0; i < userRegex.length; i++) {
            regex += "|" + userRegex[i];
        }
        return regex;
    }

    private static int getStartIndexNumbers(String s) {
        if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
            return 0;
        }
        if (s.length() > 2 && s.substring(0,2).equals("//")) {
            return s.indexOf("\n") + 1;
        }
        return 0;
    }

    private static boolean checkNegativeValues(String s){
        if (s.charAt(0) == '–' && s.charAt(1) == ' '){
            String sub = s.substring(2);
            for (int i = 0; i < sub.length(); i++) {
                char c = sub.charAt(i);
                if (!(c >= '0' && c <= '9')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int add(CharSequence ... s) {
        int sum = 0;
        long time = System.nanoTime();

        //Хотел сделать по другому немного, и обнаружил, что если проверять "s", на !=, выдает error.
        //Так и не понял, почему
        if (s == null || s[0].equals("")) {
            return 0;
        }

        for (int i = 0; i < s.length; i++) {
            String str = s[i].toString();
            String number;
            String begin;
            {
                int indexBegin = getStartIndexNumbers(str);
                begin = str.substring(0, indexBegin).trim();
                number = str.substring(indexBegin).trim();
            }
            String regex = getRedex(begin);
            if (regex == null) {
                return 0;
            }
            String[] nums = number.split(regex);

            ArrayList<String> valueNeg = new ArrayList<>();
            for (int y = 0; y < nums.length; y++) {
                nums[y] = nums[y].trim();
                for (int j = 0; j < nums[y].length(); j++) {
                    char c = nums[y].charAt(j);
                    if (!(c >= '0' && c <= '9')) {
                        if (c == '–'){
                            if (checkNegativeValues(nums[y])){
                                valueNeg.add(nums[y]);
                                break;
                            }
                        }
                        if (c == '^' && j > 0){
                            if (j == nums[y].length() - 1) {
                                nums[y] = String.valueOf((int) Math.pow(Integer.parseInt(nums[y].substring(0, j)), 2));
                                break;
                            }
                            else {
                                String[] tmp = nums[y].split("\\^");
                                nums[y] = String.valueOf((int)Math.pow(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1])));
                                break;
                            }
                        }
                        System.out.println("throw SpliterFormatException");
                        return 0;
                    }
                }
            }

            if (valueNeg.size() != 0){
                for (int p = 0; p < valueNeg.size() - 1; p++) {
                    System.out.print(valueNeg.get(p) + " ");
                }
                System.out.println(valueNeg.get(valueNeg.size() - 1) + "\n" +
                        "throw NumberNegativException");
                return 0;
            }

            for (String tmp : nums) {
                if (Integer.parseInt(tmp) < 1001) {
                    sum += Integer.parseInt(tmp);
                }
            }
        }

        time = System.nanoTime() - time;

        if(time/1_000_000.0 > 30){
            System.out.println("throw TimeException");
            return 0;
        }
        return sum;
    }
}
