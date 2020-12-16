/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cleanPet.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MarcioC
 */
public class Conexao {
    private Connection conexao;
    
    public boolean conectar(){
        try {
            String url="jdbc:sqlite:db/db_cleanPet.db";
            this.conexao = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        System.out.println("Conectou!!");
        return true;
    }
    public boolean desconectar(){
        try {
            if(this.conexao.isClosed()== false){
                this.conexao.close();
            }
            
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        System.out.println("Desonectou!!");
        return true;
    }
    
}
