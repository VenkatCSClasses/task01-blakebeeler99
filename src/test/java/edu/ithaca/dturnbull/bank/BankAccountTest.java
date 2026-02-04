package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        //EC test for integer starting balance
        assertEquals(200, bankAccount.getBalance(), 0.001);
        assertNotEquals(100, bankAccount.getBalance(), 0.001);
        //EC test for decimal starting balance
        bankAccount = new BankAccount("a@b.com", 120.34);
        assertEquals(120.34, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100); //valid withdraw EC
        bankAccount.withdraw(0);   //zero withdraw EC
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-50)); //negative withdraw EC
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //insufficient funds EC
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-0.01)); //border negative withdraw EC
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.011)); //border 3 decimal place EC

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        //Whole Email Tests:

        //Special Character Followed by Letter/Digit Equivalence Classes
        assertFalse(BankAccount.isEmailValid("abc..@gmail.com")); 
        assertFalse(BankAccount.isEmailValid("abc.@gmail..com")); 
        assertTrue(BankAccount.isEmailValid("abc.a@gmail.com")); 
        //Contains @ Equivalence Classes
        assertFalse(BankAccount.isEmailValid("abcgmail.com")); 
        assertTrue(BankAccount.isEmailValid("abc@gmail.com")); 
        
        //Prefix Tests:

        //Prefix Exists Equivalence Classes
        assertFalse(BankAccount.isEmailValid("@gmail.com"));
        assertTrue(BankAccount.isEmailValid("a@gmail.com")); 
        //Valid Character Equivalence Classes
        assertFalse(BankAccount.isEmailValid("abc#@gmail.com")); 
        assertTrue(BankAccount.isEmailValid("abc@gmail.com"));
        //Start/End Character Equivalence Classes
        assertFalse(BankAccount.isEmailValid(".abc@gmail.com")); 
        assertFalse(BankAccount.isEmailValid("abc.@gmail.com")); 
        assertTrue(BankAccount.isEmailValid("abc@gmail.com")); 

        //Domain Tests:

        //Domain Exists Equivalence Classes
        assertFalse(BankAccount.isEmailValid("abc@"));
        assertTrue(BankAccount.isEmailValid("abc@b.com")); 
        //Domain Period Existence Equivalence Classes
        assertFalse(BankAccount.isEmailValid("abc@com")); 
        assertFalse(BankAccount.isEmailValid("abc@.com")); 
        //Domain Suffix Length Equivalence Classes
        assertFalse(BankAccount.isEmailValid("abc@b.c")); 
        assertTrue(BankAccount.isEmailValid("abc@b.cc")); 
        //Domain Valid Character Equivalence Classes
        assertFalse(BankAccount.isEmailValid("abc@b#.com"));
        assertTrue(BankAccount.isEmailValid("abc@b-b.com"));
        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.999));
    }

    @Test
    void isAmountValidTest(){
        //Positive Negative Value Equivalence Classes
        assertTrue(BankAccount.isAmountValid(100.00));
        assertTrue(BankAccount.isAmountValid(0.00)); //border
        assertFalse(BankAccount.isAmountValid(-0.01)); //border
        assertFalse(BankAccount.isAmountValid(-100.00));

        //Decimal Place Equivalence Classes
        assertFalse(BankAccount.isAmountValid(100.10000909));
        assertFalse(BankAccount.isAmountValid(100.999)); //border
        assertTrue(BankAccount.isAmountValid(100.00)); //border
        assertTrue(BankAccount.isAmountValid(100.9));

    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        bankAccount.deposit(50); //valid deposit EC
        assertEquals(150, bankAccount.getBalance(), 0.001);
        bankAccount.deposit(0); //zero deposit EC
        assertEquals(150, bankAccount.getBalance(), 0.001);
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-20)); //negative deposit EC
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-0.01)); //border negative deposit EC
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.011)); //border 3 decimal place EC
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount bankAccount1 = new BankAccount("a@b.com", 100);
        BankAccount bankAccount2 = new BankAccount("a@b.com", 200);
        bankAccount1.transfer(bankAccount2, 50); //valid transfer EC
        assertEquals(50, bankAccount1.getBalance(), 0.001);
        assertEquals(250, bankAccount2.getBalance(), 0.001);
        bankAccount1.transfer(bankAccount2, 0); //zero transfer EC
        assertEquals(50, bankAccount1.getBalance(), 0.001);
        assertEquals(250, bankAccount2.getBalance(), 0.001);
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(bankAccount2, -30)); //negative transfer EC
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(bankAccount2, -0.01)); //border negative transfer EC
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(bankAccount2, 0.011)); //border 3 decimal place EC
        assertThrows(InsufficientFundsException.class, () -> bankAccount1.transfer(bankAccount2, 100)); //insufficient funds EC
    }

}