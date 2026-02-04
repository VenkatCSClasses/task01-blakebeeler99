package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is negative
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException("Cannot withdraw negative amount: " + amount);
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!#$%&'*+/=?^_`{|}~- .@\"\\";
        String specialChars = "!#$%&'*+/=?^_`{|}~- .@\"\\";
        char[] emailArray = email.toCharArray();

         //prefix
        for (int i = 0; i < emailArray.length; i++) {
            if (emailArray[i] == '@'){
                if (i == 0){
                    return false;
                } else if (i==1 && specialChars.indexOf(emailArray[i - 1]) != -1){
                    return false;
                } else if (specialChars.indexOf(emailArray[i - 1]) != -1){
                    return false;
                }
                
                break;
            }
        }

        //domain
        for (int i= emailArray.length - 1; i >= 0; i--) {
            if (emailArray[i] == '@'){
                if (emailArray[i] + 1 == '.'){
                    return false;
                }
                if (i == emailArray.length - 1){
                    return false;
                }
                boolean periodFound = false;
                for (int j = i; j < emailArray.length; j++) {
                    if (emailArray[j] == '.'){
                        periodFound = true;
                        if (j >= emailArray.length - 2){
                            return false;
                        }
                    }
                }
                if (!periodFound){
                    return false;
                }
                break;
            }
        }
        
        //whole email
        if (email.indexOf('@') == -1){
            return false;
        }
        if (emailArray[0] == '.' || emailArray[emailArray.length - 1] == '.'){
            return false;
        }
        for (int i = 0; i < emailArray.length - 1; i++) {
            if (allowedChars.indexOf(emailArray[i]) == -1){
                return false;
            }
            if (specialChars.indexOf(emailArray[i]) != -1 && specialChars.indexOf(emailArray[i + 1]) != -1){
                return false;
            }
            if (emailArray[i] == '@' && emailArray[i + 1] == '.') {
                return false;
            }
        }
        
        return true;

    }

    /**
     * @return true if amount is non-negative and has 2 or less decimal places, false otherwise
     */
    public static boolean isAmountValid(double amount){
        if (amount < 0) {
            return false;
        }
        else if (((int)(amount * 100)) != amount * 100){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * @post increases the balance by amount if amount is valid
     */
    public void deposit(double amount){
        if (isAmountValid(amount)){
            balance += amount;
        }
        else {
            throw new IllegalArgumentException("Cannot deposit invalid amount: " + amount);
        }
    }
        
}