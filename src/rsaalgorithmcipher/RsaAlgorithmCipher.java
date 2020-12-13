//Author: Lucas Coutinho de Almeida

package rsaalgorithmcipher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RsaAlgorithmCipher {

    public static void main(String[] args) {
        int primeNumber1 = 17;
        int primeNumber2 = 19;
        
        int multiplicationOfPrimeNumbers = primeNumber1 * primeNumber2;
        
        int eulerPhi = (primeNumber1 - 1) * (primeNumber2 - 1);
        
        List<Integer> numbersBetween1AndEulerPhi = new ArrayList<>();
        for (int i = 0; i < eulerPhi; i++) {
            if (i > 1) {
                numbersBetween1AndEulerPhi.add(i);
            }
        }
        
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 0; i < numbersBetween1AndEulerPhi.size(); i++) {
            if (i == 0 || i == 1 || i == 3 || i == 5 || i == 9 || i == 11 || 
                i == 15 || i == 17 || i == 21 || i == 27 || i == 29  || 
                i == 35) 
            {
                primeNumbers.add(numbersBetween1AndEulerPhi.get(i));
            }
            
            if (!(numbersBetween1AndEulerPhi.get(i) % 2 == 0) && !(numbersBetween1AndEulerPhi.get(i) % 3 == 0) &&
                !(numbersBetween1AndEulerPhi.get(i) % 5 == 0) && !(numbersBetween1AndEulerPhi.get(i) % 7 == 0) &&
                !(numbersBetween1AndEulerPhi.get(i) % 11 == 0) && !(numbersBetween1AndEulerPhi.get(i) % 13 == 0) &&
                !(numbersBetween1AndEulerPhi.get(i) % 17 == 0) && !(numbersBetween1AndEulerPhi.get(i) % 19 == 0) &&
                !(numbersBetween1AndEulerPhi.get(i) % 23 == 0) && !(numbersBetween1AndEulerPhi.get(i) % 29 == 0) &&
                !(numbersBetween1AndEulerPhi.get(i) % 31 == 0) && !(numbersBetween1AndEulerPhi.get(i) % 37 == 0)) 
            {
                primeNumbers.add(numbersBetween1AndEulerPhi.get(i));
            }
        }
        
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        
        Scanner scan = new Scanner(System.in);
        System.out.print("Type a word: ");
        String typedWord = scan.nextLine().toLowerCase();
        
        int wordSize = typedWord.length();
        char[] sliptWord = typedWord.toCharArray();
        
        List<Integer> switchLetterToNumber = new ArrayList();
        for (int i = 0; i < wordSize; i++) {
            switchLetterToNumber.add(alphabet.indexOf(sliptWord[i]));
        }
        
        int randomPrimeNumber = primeNumbers.get(
                new Random().nextInt(primeNumbers.size()));
        
        String randomPrimeNumberString = String.valueOf(randomPrimeNumber);
        String primeNumbersMultiString = String.valueOf(multiplicationOfPrimeNumbers);
        
        List<BigInteger> encryptedNumbers = new ArrayList<>();
        for (int i = 0; i < wordSize; i++) {
            String number = String.valueOf(switchLetterToNumber.get(i));
            BigInteger aux = new BigInteger(number);
            encryptedNumbers.add(aux.modPow(new BigInteger(randomPrimeNumberString), 
                    new BigInteger(primeNumbersMultiString)));
        }
        
        String encryptedWord = "";
        for (int i = 0; i < wordSize; i++) {
            encryptedWord += String.valueOf(encryptedNumbers.get(i));
        }
        
        String privateKey = String.valueOf(euclides(randomPrimeNumber, eulerPhi, 1));
        
        List<BigInteger> decryptedNumbers = new ArrayList<>();
        for (int i = 0; i < wordSize; i++) {
            String number = String.valueOf(encryptedNumbers.get(i));
            
            BigInteger aux = new BigInteger(number);
            decryptedNumbers.add(aux.modPow(new BigInteger(privateKey), 
                    new BigInteger(primeNumbersMultiString)));
        }
        
        List<Character> decryptedWordList = new ArrayList<>();
        for (int i = 0; i < wordSize; i++) {
            String number = String.valueOf(decryptedNumbers.get(i));
            BigInteger big = new BigInteger(number);
            Integer position = big.intValue();
            
            decryptedWordList.add(alphabet.charAt(position));
        }
        
        String decryptedWord = "";
        for (int i = 0; i < wordSize; i++) {
            decryptedWord += decryptedWordList.get(i);
        }
        
        System.out.println("Public key: " + randomPrimeNumber);
        System.out.println("Encrypted Word: " + encryptedWord);
        System.out.println("Decrypted Word: " + decryptedWord);
    }
    
    //Extended Euclidean Algorithm
    private static long mod(long a, long b) {
        long r = a % b;
        
        if ((r < 0) && (b > 0)) {
            return (b + r);
        }
        
        if ((r > 0) && (b < 0)) {
            return (b + r);
        }
        
        return r;
    }
    
    private static long euclides(long a, long b, long c) {
        long r;
        
        r = mod(b, a);
        
        if (r == 0) {
            return (mod((c / a), (b / a)));
        }
        
        return ((euclides(r, a, -c) * b + c) / (mod(a, b)));
    }
    
}
