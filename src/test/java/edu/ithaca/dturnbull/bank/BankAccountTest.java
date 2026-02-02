package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        assertFalse(BankAccount.isEmailValid("abc-@gmail.com"));
        assertFalse(BankAccount.isEmailValid("abc..@b.com"));
        assertFalse(BankAccount.isEmailValid("abc#@b.com"));
        assertFalse(BankAccount.isEmailValid("abc@c.c"));
        assertFalse(BankAccount.isEmailValid("abc@b.com"));
        assertFalse(BankAccount.isEmailValid("abc@b..com"));
        assertFalse(BankAccount.isEmailValid("abc@com"));
        assertFalse(BankAccount.isEmailValid("abc@b#.com"));

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
        assertTrue(BankAccount.isEmailValid("abc@.com")); 
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
    }

}