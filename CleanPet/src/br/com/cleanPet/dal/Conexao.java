/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cleanPet.dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MarcioC
 */
public class Conexao {
    /*private Connection conexao;
    
    public boolean conectar(){
        String url="jdbc:sqlite:db/db_cleanPet.db";
        String driver ="org.sqlite.JDBC";
        try {
            Class.forName(driver);
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
    }*/
    public static Connection conector() {
        java.sql.Connection conexao = null;
        //Chama o driver que foi importado para biblioteca
        String driver = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:db/db_cleanPet.db";
        //Estabelecendo a conexão com banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url);
            return conexao;
        } catch (Exception e) {
            //a linha abaixo serve de apoio para esclarecer o erro
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.out.println(e);
            return null;
        }
    }
    
}
