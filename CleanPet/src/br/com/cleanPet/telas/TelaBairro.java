/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cleanPet.telas;

import java.sql.*;
import br.com.cleanPet.dal.Conexao;
import javax.swing.JOptionPane;

/**
 *
 * @author MarcioC
 */
public class TelaBairro extends javax.swing.JInternalFrame {

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

    public TelaBairro() {
        initComponents();
        conexao = Conexao.conector();
        preencheBairro();
    }

    //método para consultar bairro
    private void consultar() {
        /*"select * from tb_bairro where codigobar=?";*/
        String sql = "select tb_bairro.idbar,tb_bairro.codigobar,tb_bairro.nomebar,tb_cidade.nomecid from tb_bairro left join tb_cidade on tb_bairro.idcidade = tb_cidade.idcid where codigobar =?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBaiCod.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                //busca informação no DB
                txtBaiNom.setText(rs.getString(3));
                cboBaiCid.setSelectedItem(rs.getString(4));
                //cboBaiCid.removeAllItems();
                //cboBaiCid.addItem(rs.getString(3));

            } else {
                txtBaiNom.setText(null);
                preencheBairro();
                JOptionPane.showMessageDialog(null, "Bairro não cadastrado!");
                //limpa os campos
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            //Conexao.closeConector(conexao);
        }

    }

    //método para adicionar bairro
    private void adicionar() {
        String sql = "insert into tb_bairro (codigobar,nomebar,idcidade) values(?,?,?)";
        
        try {
             
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBaiCod.getText());
            pst.setString(2, txtBaiNom.getText());
            pst.setInt(3, (int) cboBaiCid.getSelectedItem());
            //pst.setString(4, cboUsuPerfil.getSelectedItem().toString());

            //int idmaria = int.parse(cbfrabicante.SelectedValue.ToString());
            //pst.setString(3, int.parse(cboBaiCid.Sele);
            //pst.setString(3, cboBaiCid.);
            if (txtBaiCod.getText().isEmpty() || (txtBaiNom.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da inserção no DB
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cidade adicionado com sucesso!");
                    //limpa os campos
                    txtBaiCod.setText(null);
                    txtBaiNom.setText(null);

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
            pst.setString(1, txtBaiCod.getText());
            pst.setString(2, txtBaiNom.getText());
            pst.setString(4, txtBaiCod.getText());

            if (txtBaiCod.getText().isEmpty() || (txtBaiNom.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da alteração no DB
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados da cidade alterados com sucesso!");
                    //limpa os campos
                    txtBaiCod.setText(null);
                    txtBaiNom.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void preencheBairro() {
        //String sql = "select * from tb_cidade order by nomecid";
        String sql = "select * from tb_cidade order by nomecid";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cboBaiCid.removeAllItems();
                do {
                    cboBaiCid.addItem(rs.getString(3));
                } while (rs.next());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void comboId() {
        try {
            String sql = "SELECT iduser FROM tbusuarios WHERE usuario='" + cboBaiCid.getSelectedItem().toString() + "'";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                int iduser = rs.getInt("iduser");
                //String usuario = rs.getString("usuario");

                // cbxusuarios.addItem(usuario);
                //txtid.setText(rs.getString("iduser"));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        txtBaiNom = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtBaiCod = new javax.swing.JTextField();
        btnBaiCreate = new javax.swing.JButton();
        btnBaiRead = new javax.swing.JButton();
        btnBaiUpdate = new javax.swing.JButton();
        btnBaiDelete = new javax.swing.JButton();
        cboBaiCid = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro de Bairros");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(725, 450));

        jLabel1.setText("*Bairro");

        jLabel2.setText("*Cidade");

        jLabel3.setText("*Campo obrigatórios");

        jLabel4.setText("*Codigo");

        btnBaiCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/create.png"))); // NOI18N
        btnBaiCreate.setToolTipText("Adicinoar");
        btnBaiCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBaiCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaiCreateActionPerformed(evt);
            }
        });

        btnBaiRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/read.png"))); // NOI18N
        btnBaiRead.setToolTipText("Consultar");
        btnBaiRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBaiRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaiReadActionPerformed(evt);
            }
        });

        btnBaiUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/update.png"))); // NOI18N
        btnBaiUpdate.setToolTipText("Alterar");
        btnBaiUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBaiUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaiUpdateActionPerformed(evt);
            }
        });

        btnBaiDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/delete.png"))); // NOI18N
        btnBaiDelete.setToolTipText("Remover");
        btnBaiDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cboBaiCid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item1" }));
        cboBaiCid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBaiCidActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBaiCreate)
                        .addGap(57, 57, 57)
                        .addComponent(btnBaiRead)
                        .addGap(57, 57, 57)
                        .addComponent(btnBaiUpdate)
                        .addGap(57, 57, 57)
                        .addComponent(btnBaiDelete))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBaiNom, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBaiCod, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(cboBaiCid, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)))
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
                    .addComponent(txtBaiCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBaiNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cboBaiCid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnBaiCreate)
                    .addComponent(btnBaiRead)
                    .addComponent(btnBaiUpdate)
                    .addComponent(btnBaiDelete))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        setBounds(0, 0, 725, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBaiReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaiReadActionPerformed
        // chamando o método consultar
        consultar();
    }//GEN-LAST:event_btnBaiReadActionPerformed

    private void btnBaiUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaiUpdateActionPerformed
        // chamando o métoto alterar
        alterar();
    }//GEN-LAST:event_btnBaiUpdateActionPerformed

    private void btnBaiCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaiCreateActionPerformed
        // chamando o método adicionar
        adicionar();
    }//GEN-LAST:event_btnBaiCreateActionPerformed

    private void cboBaiCidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBaiCidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboBaiCidActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaiCreate;
    private javax.swing.JButton btnBaiDelete;
    private javax.swing.JButton btnBaiRead;
    private javax.swing.JButton btnBaiUpdate;
    private javax.swing.JComboBox<String> cboBaiCid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtBaiCod;
    private javax.swing.JTextField txtBaiNom;
    // End of variables declaration//GEN-END:variables
}
