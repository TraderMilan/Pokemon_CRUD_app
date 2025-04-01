package org.example;

import org.example.service.CRUDManager;


public class Main {
    public static void main(String[] args) {
        CRUDManager manager = new CRUDManager();
        manager.printOptions();
    }
}