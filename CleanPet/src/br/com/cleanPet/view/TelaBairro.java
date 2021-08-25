/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cleanPet.view;

import java.sql.*;
import br.com.cleanPet.dal.Conexao;
import java.util.HashMap;
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
        preencherBairro();
    }

    //método para consultar bairro
    private void consultar() {
        /*"select * from tb_bairro where codigobar=?";*/
        String sql = "select tb_bairro.idbai,tb_bairro.codigobai,tb_bairro.nomebai,tb_cidade.nomecid from tb_bairro left join tb_cidade on tb_bairro.idcidade = tb_cidade.idcid where codigobai =?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBaiCod.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                //busca informação no DB
                txtBaiNom.setText(rs.getString(3));
                cboBaiCid.setSelectedItem(rs.getString(4));
            } else {
                txtBaiNom.setText(null);
                preencherBairro();
                JOptionPane.showMessageDialog(null, "Bairro não cadastrado!");
                //limpa os campos
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro:"+ e);
        } 
    }

    //método para adicionar bairro
    private void adicionar() {
        String sql = "insert into tb_bairro(codigobai,nomebai,idcidade) values(?,?,?)";
        String aux1 = cboBaiCid.getSelectedItem().toString();
        int aux2 = pegarId(aux1); 
            
        try {             
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBaiCod.getText());
            pst.setString(2, txtBaiNom.getText());
            pst.setInt(3, aux2);
            
            if (txtBaiCod.getText().isEmpty() || (txtBaiNom.getText().isEmpty()) || aux2 ==0) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da inserção no DB
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Bairro adicionado com sucesso!");
                    //limpa os campos
                    txtBaiCod.setText(null);
                    txtBaiNom.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro:"+ e);
        }
    }

    //método para alterar os dados da bairro
    private void alterar() {
        String sql = "update tb_bairro set nomebai=?,idcidade=? where codigobai=? ";
        String aux1 = cboBaiCid.getSelectedItem().toString();
        int aux2 = pegarId(aux1); 
 
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBaiNom.getText());
            pst.setInt(2,aux2);
            pst.setString(3,txtBaiCod.getText());

            if (txtBaiCod.getText().isEmpty() || (txtBaiNom.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da alteração no DB
                int adicionado = pst.executeUpdate();
                System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados da cidade alterados com sucesso!");
                    //limpa os campos
                    txtBaiCod.setText(null);
                    txtBaiNom.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro:"+ e);
        }
    }
    
    //método para remover os dados do bairro
    private void remover() {
        //faz confirmação
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o bairro?", "Atenção", JOptionPane.YES_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_bairro where codigobai=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtBaiCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cidade removida com sucesso!");
                    //limpa os campos
                    txtBaiCod.setText(null);
                    txtBaiNom.setText(null);
                    preencherBairro();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Erro:"+ e);
            }
        }
    }

    private void preencherBairro() {
        
        String sql = "select nomecid from tb_cidade order by nomecid";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cboBaiCid.removeAllItems();
                cboBaiCid.addItem("Selecionar"); 
                do {
                    cboBaiCid.addItem(rs.getString(1));
                } while (rs.next());
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro:"+ e);
        }
    }
    
    private int pegarId(String cidade){
        int id = 0;
        String aux = cidade;
        String sql ="select idcid, nomecid from tb_cidade order by nomecid";
            try{
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    HashMap <String,Integer> tbCidade = new HashMap <String,Integer>();
                    do{
                        tbCidade.put(rs.getString(2),rs.getInt(1)); 
                    }while(rs.next());
                    id = tbCidade.get(aux);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }finally{
                
            }
            return id;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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
        btnBaiDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaiDeleteActionPerformed(evt);
            }
        });

        cboBaiCid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item1" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBaiNom, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBaiCod, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cboBaiCid, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(165, 165, 165))
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBaiCreate)
                        .addGap(64, 64, 64)
                        .addComponent(btnBaiRead)
                        .addGap(54, 54, 54)
                        .addComponent(btnBaiUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBaiDelete))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBaiCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cboBaiCid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBaiNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBaiRead)
                    .addComponent(btnBaiCreate)
                    .addComponent(btnBaiUpdate)
                    .addComponent(btnBaiDelete))
                .addContainerGap(56, Short.MAX_VALUE))
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

    private void btnBaiDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaiDeleteActionPerformed
        // chamando o método remover
        remover();
    }//GEN-LAST:event_btnBaiDeleteActionPerformed


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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBaiCod;
    private javax.swing.JTextField txtBaiNom;
    // End of variables declaration//GEN-END:variables
}
