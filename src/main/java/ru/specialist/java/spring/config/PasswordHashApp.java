package ru.specialist.java.spring.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordHashApp {

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.println(encoder.encode(scanner.nextLine()));
        }
    }
}
