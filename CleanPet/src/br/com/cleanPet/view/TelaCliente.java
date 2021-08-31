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

//Biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author MarcioC
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaCliente
     */
    
    //usando a variavel conexao do DAL
    //criando variáveis especiais para conexão com o banco
    //Prepared Statement e ResultSet são frameworks do pacote java.sql
    //e server para preparar e executar as instruções SQL
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public TelaCliente() {
        initComponents();
        conexao = Conexao.conector();
        preencherCboBairro();
    }
    
    //método para adicionar clientes
    private void adicionar() {
        String sql = "insert into tb_cliente(nomecli,ruacli,fonecli,cepcli,numede,emailcli,idbairro) values(?,?,?,?,?,?,?)";
        String aux1 = cboBairro.getSelectedItem().toString();
        int aux2 = pegarId(aux1);
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliRua.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliCep.getText());
            pst.setString(5, txtCliNumero.getText());
            pst.setString(6, txtCliEmail.getText());
            pst.setInt(7, aux2);
            if (txtCliNome.getText().isEmpty()||(txtCliRua.getText().isEmpty())||(txtCliFone.getText().isEmpty())||(txtCliNumero.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da inserção no DB
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
                    //limpa os campos
                    txtCliCod.setText(null);
                    txtCliNome.setText(null);
                    txtCliRua.setText(null);
                    txtCliFone.setText(null);
                    txtCliNumero.setText(null);
                    txtCliEmail.setText(null);
                    preencherCboBairro();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    //método para pesquisar o cadastro do clientes
    private void pesquisarCliente(){
        //método filtrar clientes
       //String sql ="select nomecli,fonecli,ruacli,idbairro,numede,emailcli from tb_cliente where nomecli like ?";
        String sql ="select tb_cliente.idcli,tb_cliente.nomecli,tb_cliente.fonecli,tb_cliente.ruacli,tb_cliente.numede,tb_bairro.nomebai,tb_cliente.cepcli,tb_cliente.emailcli from tb_cliente LEFT JOIN tb_bairro ON tb_cliente.idbairro = tb_bairro.idbai  where tb_cliente.D_E_L_E_T_E is not '*' and tb_cliente.nomecli like ? order by tb_cliente.nomecli";
        
        try {
                    txtCliCod.setText(null);
                    txtCliNome.setText(null);
                    txtCliEmail.setText(null);
                    txtCliFone.setText(null);
                    txtCliNumero.setText(null);
                    txtCliRua.setText(null);
                    txtCliCep.setText(null);
                   
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisar.getText()+ "%");
            rs = pst.executeQuery();
            
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro:"+ e);
        }
                
    }
    //método para alterar o cadastro do cliente 
    private void alterar() {
        String sql = "update tb_cliente set nomecli=?,emailcli=?,fonecli=?,numede=?,ruacli=?,cepcli=?,idbairro=? where idcli=? ";
        String aux1 = cboBairro.getSelectedItem().toString();
        int aux2 = pegarId(aux1);
        
        try {
            pst = conexao.prepareStatement(sql);
           
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEmail.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliNumero.getText());
            pst.setString(5, txtCliRua.getText());
            pst.setString(6, txtCliCep.getText());
            pst.setInt(7, aux2);            
            pst.setString(8, txtCliCod.getText());
            
            

            if (txtCliNome.getText().isEmpty() || (txtCliFone.getText().isEmpty()) || (txtCliNumero.getText().isEmpty()) ||(txtCliRua.getText().isEmpty()||(cboBairro.getSelectedItem().toString().equals("Selecionar")))){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //Faz a atualização na tabela com os valores do formularios
                //Faz a confirmação da alteração no DB
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso!");
                    //limpa os campos
                    pesquisarCliente();
                    preencherCboBairro();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //método para excluir usuário
    private void remover() {
        //faz confirmação a remoção
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cliente?", "Atenção", JOptionPane.YES_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "update tb_cliente set D_E_L_E_T_E='*' where idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                    //limpa os campos
                    pesquisarCliente();
                    preencherCboBairro();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    //método para preencher os campos com os registros da tabela
    public void setarCampo(){
        txtCliCep.setText(null);
        txtCliEmail.setText(null);
       int setar = tblClientes.getSelectedRow();
        
        txtCliCod.setText(tblClientes.getModel().getValueAt(setar,0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar,1).toString());        
        txtCliFone.setText(tblClientes.getModel().getValueAt(setar,2).toString());
        txtCliRua.setText(tblClientes.getModel().getValueAt(setar,3).toString());
        txtCliNumero.setText(tblClientes.getModel().getValueAt(setar,4).toString());
        cboBairro.setSelectedItem(tblClientes.getModel().getValueAt(setar,5).toString());
        txtCliCep.setText(tblClientes.getModel().getValueAt(setar,6).toString());
        txtCliEmail.setText(tblClientes.getModel().getValueAt(setar,7).toString());
        
    }
    //método para preencher o Jcombobox
    private void preencherCboBairro() {
       String sql = "select nomebai from tb_bairro order by nomebai";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cboBairro.removeAllItems();
                cboBairro.addItem("Selecionar"); 
                do {
                    cboBairro.addItem(rs.getString(1));
                } while (rs.next());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro:"+ e);
        }
        
    }
    //método para buscar o id da tabela
    private int pegarId(String cidade){
        int id = 0;
        String aux = cidade;
        String sql ="select idbai, nomebai from tb_bairro order by nomebai";
            try{
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    HashMap <String,Integer> tbClienteIn = new HashMap <String,Integer>();
                    do{
                        tbClienteIn.put(rs.getString(2),rs.getInt(1)); 
                    }while(rs.next());
                    id = tbClienteIn.get(aux);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }finally{
                
            }
            return id;
    }
    /*private String pegarNome(int id){
        String nome="";
        int aux = id;
        String sql ="select idbai, nomebai from tb_bairro order by nomebai";
            try{
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    HashMap <Integer,String> tbClienteIn = new HashMap <Integer,String>();
                    do{
                        tbClienteIn.put(rs.getInt(1),rs.getString(2)); 
                    }while(rs.next());
                   
                    nome = tbClienteIn.get(aux);
                     System.out.println(aux);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }finally{
                
            }
            return nome;
    }*/

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        txtCliNome = new javax.swing.JTextField();
        txtCliEmail = new javax.swing.JTextField();
        txtCliFone = new javax.swing.JTextField();
        txtCliNumero = new javax.swing.JTextField();
        txtCliRua = new javax.swing.JTextField();
        txtCliCep = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        cboBairro = new javax.swing.JComboBox<>();
        btnCliCreate = new javax.swing.JButton();
        btnCliUpdate = new javax.swing.JButton();
        btnCliDelete = new javax.swing.JButton();
        btnCliRead = new javax.swing.JButton();
        btnAddBairro = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCliCod = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cliente");
        setPreferredSize(new java.awt.Dimension(725, 450));

        jLabel2.setText("*Nome");

        jLabel3.setText("*Rua");

        jLabel4.setText("*Telefone");

        jLabel5.setText("*Bairro");

        jLabel6.setText("Cep");

        jLabel7.setText("*Nº");

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel8.setText("*Campos obrigatórios");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Telefone", "Rua", "Bairro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel12.setText("E-mail");

        cboBairro.setEditable(true);
        cboBairro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnCliCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/create.png"))); // NOI18N
        btnCliCreate.setToolTipText("Adicionar");
        btnCliCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliCreateActionPerformed(evt);
            }
        });

        btnCliUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/update.png"))); // NOI18N
        btnCliUpdate.setToolTipText("Alterar");
        btnCliUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliUpdateActionPerformed(evt);
            }
        });

        btnCliDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/delete.png"))); // NOI18N
        btnCliDelete.setToolTipText("Remover");
        btnCliDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliDeleteActionPerformed(evt);
            }
        });

        btnCliRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/pesquisar.png"))); // NOI18N
        btnCliRead.setToolTipText("Consultar");
        btnCliRead.setContentAreaFilled(false);
        btnCliRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliReadActionPerformed(evt);
            }
        });

        btnAddBairro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/cleanPet/icones/add+.png"))); // NOI18N
        btnAddBairro.setToolTipText("Adcionar Bairro");
        btnAddBairro.setContentAreaFilled(false);
        btnAddBairro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBairroActionPerformed(evt);
            }
        });

        jLabel1.setText("Código");

        txtCliCod.setEditable(false);
        txtCliCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliCodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnCliCreate)
                                .addGap(85, 85, 85)
                                .addComponent(btnCliUpdate)
                                .addGap(85, 85, 85)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCliDelete)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCliRua, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCliCod)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(34, 34, 34)
                                .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(58, 58, 58))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCliRead, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(202, 202, 202)
                                .addComponent(jLabel8))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAddBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel12))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btnCliRead, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCliCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCliRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(cboBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCliUpdate)
                    .addComponent(btnCliCreate)
                    .addComponent(btnCliDelete))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        setBounds(0, 0, 756, 508);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCliCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliCreateActionPerformed
        // Chama método para adicionar clientes
        adicionar();
        
    }//GEN-LAST:event_btnCliCreateActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // Chama método de pesquisa avançada
        pesquisarCliente();
        preencherCboBairro();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Chama com clique do mouse o método
        setarCampo();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnAddBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBairroActionPerformed
        // Vai abrir o form TelaCliente dentro do desktop pane
        TelaBairro bairro = new TelaBairro();
        getParent().add(bairro);
        bairro.setVisible(true);
        
    }//GEN-LAST:event_btnAddBairroActionPerformed

    private void txtCliCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliCodActionPerformed

    private void btnCliUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliUpdateActionPerformed
        //Chama com clique do mouse o método
        alterar();
    }//GEN-LAST:event_btnCliUpdateActionPerformed

    private void btnCliReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliReadActionPerformed
        //Chama com clique do mouse o método
        pesquisarCliente();
        preencherCboBairro();
    }//GEN-LAST:event_btnCliReadActionPerformed

    private void btnCliDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliDeleteActionPerformed
        //Chama com clique do mouse o método
        remover();
    }//GEN-LAST:event_btnCliDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBairro;
    private javax.swing.JButton btnCliCreate;
    private javax.swing.JButton btnCliDelete;
    private javax.swing.JButton btnCliRead;
    private javax.swing.JButton btnCliUpdate;
    private javax.swing.JComboBox<String> cboBairro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliCep;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNumero;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCliRua;
    // End of variables declaration//GEN-END:variables
}
