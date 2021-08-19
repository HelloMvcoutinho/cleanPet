/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cleanPet.view;

import java.sql.*;
import br.com.cleanPet.dal.Conexao;
import javax.swing.JOptionPane;

/**
 *
 * @author MarcioC
 */
public class TelaCidade extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaCidade
     */
    //usando a variavel conexao do DAL
    Connection conexao = null;
    //criando variáveis especiais para conexão com o banco
    //Prepared Statement e ResultSet são frameworks do pacote java.sql
    //e server para preparar e executar as instruções SQL
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCidade() {
        initComponents();
        conexao = Conexao.conector();
    }

    //método para consultar cidade
    private void consultar() {
        //select * from tb_cidade where codigocid ='5' or nomecid LIKE '?%' 
        String sql = "select * from tb_cidade where codigocid=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCidCod.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                //busca informação no DB
                txtCidNom.setText(rs.getString(3));
                txtCidUf.setText(rs.getString(4));

            } else {
                JOptionPane.showMessageDialog(null, "Cidade não cadastrado!");
                //limpa os campos
                txtCidNom.setText(null);
                txtCidUf.setText(null);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //método para adicionar cidade
    private void adicionar() {
        String sql = "insert into tb_cidade(codigocid,nomecid,ufcid) values(?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCidCod.getText());
            pst.setString(2, txtCidNom.getText());
            pst.setString(3, txtCidUf.getText());

            if (txtCidCod.getText().isEmpty() || (txtCidNom.getText().isEmpty()) || (txtCidUf.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da inserção no DB
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cidade adicionado com sucesso!");
                    //limpa os campos
                    txtCidCod.setText(null);
                    txtCidNom.setText(null);
                    txtCidUf.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //método para alterar os dados da cidade
    private void alterar() {
        String sql = "update tb_cidade set codigocid=?,nomecid=?,ufcid=? where codigocid=? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCidCod.getText());
            pst.setString(2, txtCidNom.getText());
            pst.setString(3, txtCidUf.getText());
            pst.setString(4, txtCidCod.getText());

            if (txtCidCod.getText().isEmpty() || (txtCidNom.getText().isEmpty()) || (txtCidUf.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da alteração no DB
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados da cidade alterados com sucesso!");
                    //limpa os campos
                    txtCidCod.setText(null);
                    txtCidNom.setText(null);
                    txtCidUf.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //método para excluir cidade
    private void remover() {
        //faz confirmação a remoção
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a cidade?", "Atenção", JOptionPane.YES_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_cidade where codigocid=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCidCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cidade removida com sucesso!");
                    //limpa os campos
                    txtCidCod.setText(null);
                    txtCidNom.setText(null);
                    txtCidUf.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCidNom = new javax.swing.JTextField();
        txtCidUf = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCidCod = new javax.swing.JTextField();
        btnCidCreate = new javax.swing.JButton();
        btnCidRead = new javax.swing.JButton();
        btnCidUpdate = new javax.swing.JButton();
        btnCidDelete = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro de Cidades");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(725, 450));

        jLabel1.setText("*Cidade");

        jLabel2.setText("*UF");

        jLabel3.setText("*Campo obrigatórios");

        jLabel4.setText("*Codigo");

        btnCidCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/create.png"))); // NOI18N
        btnCidCreate.setToolTipText("Adicinoar");
        btnCidCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCidCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidCreateActionPerformed(evt);
            }
        });

        btnCidRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/read.png"))); // NOI18N
        btnCidRead.setToolTipText("Consultar");
        btnCidRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCidRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidReadActionPerformed(evt);
            }
        });

        btnCidUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/update.png"))); // NOI18N
        btnCidUpdate.setToolTipText("Alterar");
        btnCidUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCidUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidUpdateActionPerformed(evt);
            }
        });

        btnCidDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/delete.png"))); // NOI18N
        btnCidDelete.setToolTipText("Remover");
        btnCidDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCidDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(165, 165, 165))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCidCreate)
                        .addGap(57, 57, 57)
                        .addComponent(btnCidRead)
                        .addGap(57, 57, 57)
                        .addComponent(btnCidUpdate)
                        .addGap(57, 57, 57)
                        .addComponent(btnCidDelete))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCidNom, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCidUf, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCidCod, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(112, 112, 112)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCidCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCidNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtCidUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnCidCreate)
                    .addComponent(btnCidRead)
                    .addComponent(btnCidUpdate)
                    .addComponent(btnCidDelete))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        setBounds(0, 0, 725, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCidReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidReadActionPerformed
        // chamando o método consultar
        consultar();
    }//GEN-LAST:event_btnCidReadActionPerformed

    private void btnCidUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidUpdateActionPerformed
        // chamando o métoto alterar
        alterar();
    }//GEN-LAST:event_btnCidUpdateActionPerformed

    private void btnCidCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidCreateActionPerformed
        // chamando o método adicionar
        adicionar();
    }//GEN-LAST:event_btnCidCreateActionPerformed

    private void btnCidDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidDeleteActionPerformed
        // chamando o método remover
        remover();
    }//GEN-LAST:event_btnCidDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCidCreate;
    private javax.swing.JButton btnCidDelete;
    private javax.swing.JButton btnCidRead;
    private javax.swing.JButton btnCidUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtCidCod;
    private javax.swing.JTextField txtCidNom;
    private javax.swing.JTextField txtCidUf;
    // End of variables declaration//GEN-END:variables
}
