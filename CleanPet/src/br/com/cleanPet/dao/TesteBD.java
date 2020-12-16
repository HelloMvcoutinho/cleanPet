package br.com.cleanPet.dao;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MarcioS2Kamilla
 */
public class TesteBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexao con = new Conexao();
        con.conectar();
        con.desconectar();
    }
    
}
