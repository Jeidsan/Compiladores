package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import analisadores.LexicalError;
import analisadores.Lexico;
import analisadores.ScannerConstants;
import analisadores.SemanticError;
import analisadores.Semantico;
import analisadores.Sintatico;
import analisadores.SyntaticError;
import analisadores.Token;

import javax.swing.JPanel;

import componentes.NumberedBorder;
import componentes.Status;
import componentes.StatusBar;

public class Editor extends JFrame
{	
	public static void main(String[] args)
	{
		new Editor().setVisible(true);
	}
	
	private static final long serialVersionUID = 1638282136954072285L;
	
	/* VARIÁVEIS */
	private Status status = null;
	private String caminhoArquivo = null;
	private String pastaArquivo = null;
	private String nomeArquivo = null;	
	
	private KeyListener keyListener;
	
	private JButton btnAbrir;
    private JButton btnColar;
    private JButton btnCompilar;
    private JButton btnCopiar;
    private JButton btnEquipe;
    private JButton btnGerarCodigo;
    private JButton btnNovo;
    private JButton btnRecortar;
    private JButton btnSalvar;
    
    private JTextArea txtEditor;
    private JTextArea txtMensagens;
    
    private JToolBar tbBarraTarefas;
    
    private JScrollPane scrollEditor;
    private JScrollPane scrollMensagens;
    
    private NumberedBorder borderEditor;
    
    private StatusBar statusBar; 
    
    public Editor()
    {
    	super();
    	inicializarJanela();
    	inicializarComponentes();
    	inicializaLeiaute();    	
    }
    
    private void inicializarJanela()
    {
    	
    	setDefaultCloseOperation(EXIT_ON_CLOSE);    	
    	setMinimumSize(new Dimension(915, 630)); 
    	
    	Dimension dimTela = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimJanela = getSize();
        setLocation((dimTela.width - dimJanela.width) / 2, (dimTela.height - dimJanela.height) / 2);
    }
    
    private void inicializarComponentes() 
    {
        /* Barra de Ferramentas */
        tbBarraTarefas = new JToolBar();
        tbBarraTarefas.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tbBarraTarefas.setMaximumSize(new java.awt.Dimension(144, Integer.MAX_VALUE));
        tbBarraTarefas.setMinimumSize(new java.awt.Dimension(144, 544));
        tbBarraTarefas.setPreferredSize(new java.awt.Dimension(144, 544));
        tbBarraTarefas.setFloatable(false);
        
        /* Botão NOVO */
        btnNovo = new JButton();
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo_16.png")));
        btnNovo.setText("Novo [CTRL-N]");
        btnNovo.setFocusable(false);
        btnNovo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNovo.setMaximumSize(new java.awt.Dimension(144, 60));
        btnNovo.setMinimumSize(new java.awt.Dimension(144, 60));
        btnNovo.setPreferredSize(new java.awt.Dimension(144, 60));
        btnNovo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);        
        btnNovo.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                btnNovoActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnNovo);
        
        /* Botão ABRIR */
    	btnAbrir = new JButton();
        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrir_16.png")));
        btnAbrir.setText("Abrir [CTRL-O]");
        btnAbrir.setFocusable(false);
        btnAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbrir.setMaximumSize(new java.awt.Dimension(144, 60));
        btnAbrir.setMinimumSize(new java.awt.Dimension(144, 60));
        btnAbrir.setPreferredSize(new java.awt.Dimension(144, 60));
        btnAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbrir.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnAbrirActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnAbrir);
        
        /* Botão SALVAR */
        btnSalvar = new JButton();        
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/salvar_16.png")));
        btnSalvar.setText("Salvar [CTRL-S]");
        btnSalvar.setFocusable(false);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setMaximumSize(new java.awt.Dimension(144, 60));
        btnSalvar.setMinimumSize(new java.awt.Dimension(144, 60));
        btnSalvar.setPreferredSize(new java.awt.Dimension(144, 60));
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalvar.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnSalvarActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnSalvar);
        
        /* Botão COPIAR */
        btnCopiar = new JButton();
        btnCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/copiar_16.png")));
        btnCopiar.setText("Copiar [CTRL-C]");
        btnCopiar.setFocusable(false);
        btnCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiar.setMaximumSize(new java.awt.Dimension(144, 60));
        btnCopiar.setMinimumSize(new java.awt.Dimension(144, 60));
        btnCopiar.setPreferredSize(new java.awt.Dimension(144, 60));
        btnCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCopiar.setMnemonic(KeyEvent.VK_COPY);
        btnCopiar.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnCopiarActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnCopiar);
        
        /* Botão COLAR */
        btnColar = new JButton();
        btnColar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/colar_16.png")));
        btnColar.setText("Colar [CTRL-V]");
        btnColar.setFocusable(false);
        btnColar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColar.setMaximumSize(new java.awt.Dimension(144, 60));
        btnColar.setMinimumSize(new java.awt.Dimension(144, 60));
        btnColar.setPreferredSize(new java.awt.Dimension(144, 60));
        btnColar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnColar.setMnemonic(KeyEvent.VK_PASTE);
        btnColar.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnColarActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnColar);
        
        /* Botão RECORTAR */
        btnRecortar = new JButton();
        btnRecortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/recortar_16.png")));
        btnRecortar.setText("Recortar [CTRL-X]");
        btnRecortar.setFocusable(false);
        btnRecortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecortar.setMaximumSize(new java.awt.Dimension(144, 60));
        btnRecortar.setMinimumSize(new java.awt.Dimension(144, 60));
        btnRecortar.setPreferredSize(new java.awt.Dimension(144, 60));
        btnRecortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRecortar.setMnemonic(KeyEvent.VK_CUT);
        btnRecortar.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnRecortarActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnRecortar);
        
        /* Botão COMPILAR */
        btnCompilar = new JButton();
        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/compilar_16.png")));
        btnCompilar.setText("Compilar [F8]");
        btnCompilar.setFocusable(false);
        btnCompilar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompilar.setMaximumSize(new java.awt.Dimension(144, 60));
        btnCompilar.setMinimumSize(new java.awt.Dimension(144, 60));
        btnCompilar.setPreferredSize(new java.awt.Dimension(144, 60));
        btnCompilar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompilar.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnCompilarActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnCompilar);
        
        /* Botão GERAR CÓDIGO */
        btnGerarCodigo = new JButton();        
        btnGerarCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/gerarcodigo_16.png")));
        btnGerarCodigo.setText("Gerar código [F9]");
        btnGerarCodigo.setFocusable(false);
        btnGerarCodigo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGerarCodigo.setMaximumSize(new java.awt.Dimension(144, 60));
        btnGerarCodigo.setMinimumSize(new java.awt.Dimension(144, 60));
        btnGerarCodigo.setPreferredSize(new java.awt.Dimension(144, 60));
        btnGerarCodigo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);  
        btnGerarCodigo.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnGerarCodigoActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnGerarCodigo);
        
        /* Botão EQUIPE */
        btnEquipe = new JButton();
        btnEquipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/equipe_16.png")));
        btnEquipe.setText("Equipe [F1]");
        btnEquipe.setFocusable(false);
        btnEquipe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEquipe.setMaximumSize(new java.awt.Dimension(144, 60));
        btnEquipe.setMinimumSize(new java.awt.Dimension(144, 60));
        btnEquipe.setPreferredSize(new java.awt.Dimension(144, 60));
        btnEquipe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEquipe.addActionListener(new java.awt.event.ActionListener()
    	{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	btnEquipeActionPerformed(evt);
            }
        });
        tbBarraTarefas.add(btnEquipe);
        
        /* Borda do Editor */
        borderEditor = new NumberedBorder();
        
        /* Editor */
        txtEditor = new JTextArea();
        txtEditor.setMinimumSize(new Dimension(750, 480));        
        txtEditor.setBorder(borderEditor);
        txtEditor.setEnabled(false); 
        txtEditor.addKeyListener(new KeyListener()
		{
        	@Override
			public void keyTyped(KeyEvent e){ }
			
			@Override
			public void keyReleased(KeyEvent e) { }
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_N && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnNovo.doClick();					
				}
				else if (e.getKeyCode() == KeyEvent.VK_O && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnAbrir.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_S && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnSalvar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_C && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnCopiar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_V && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					//Isso foi suprimido para evitar que o texto fosse colado duas vezes.
					//btnColar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_X && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnRecortar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_F8)
				{
					btnCompilar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_F9)
				{
					btnGerarCodigo.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_F1)
				{
					btnEquipe.doClick();
				}
			}
		});
        this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e){ }
			
			@Override
			public void keyReleased(KeyEvent e) { }
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_N && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnNovo.doClick();					
				}
				else if (e.getKeyCode() == KeyEvent.VK_O && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnAbrir.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_S && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnSalvar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_C && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnCopiar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_V && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					//Isso foi suprimido para evitar que o texto fosse colado duas vezes.
					btnColar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_X && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				{
					btnRecortar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_F8)
				{
					btnCompilar.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_F9)
				{
					btnGerarCodigo.doClick();
				}
				else if (e.getKeyCode() == KeyEvent.VK_F1)
				{
					btnEquipe.doClick();
				}
			}
		});
        
        txtEditor.getDocument().addDocumentListener(new DocumentListener()
		{			
			@Override
			public void removeUpdate(DocumentEvent e) { }
			
			@Override
			public void insertUpdate(DocumentEvent e) 
			{ 
				if (status == Status.NAO_MODIFICADO)
				{
					status = Status.MODIFICADO;
					statusBar.setStatus(pastaArquivo, nomeArquivo, status);
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) { }
		});
                
        /* Scroll do Editor */
        scrollEditor = new JScrollPane();
        scrollEditor.setMinimumSize(new Dimension(750, 480));        
        scrollEditor.setViewportView(txtEditor);
        scrollEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);        
        
        /* Area de Mensagens */
        txtMensagens = new JTextArea();
        txtMensagens.setMinimumSize(new Dimension(750, 90));
        txtMensagens.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));        
        txtMensagens.setEnabled(false);
        txtMensagens.setDisabledTextColor(new Color(0,0,0));
        
        /* Scroll da Área de Mensagens*/
        scrollMensagens = new JScrollPane();
        scrollMensagens.setMinimumSize(new Dimension(750, 90));
        scrollMensagens.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));        
        scrollMensagens.setViewportView(txtMensagens);
        scrollMensagens.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollMensagens.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        /* Barra de status */
        statusBar = new StatusBar();        
        statusBar.setMinimumSize(new Dimension(900,25));
        statusBar.setPreferredSize(new Dimension(900,25));
    }
    
    private void inicializaLeiaute()
    {    	
    	GroupLayout leiaute = new GroupLayout(getContentPane());
        getContentPane().setLayout(leiaute);
        
        leiaute.setHorizontalGroup
        (
        	leiaute.createParallelGroup()
        		.addGroup
        		(
        			leiaute.createSequentialGroup()       		
        				.addComponent(tbBarraTarefas)
        				.addGroup
        				(
        					leiaute.createParallelGroup(GroupLayout.Alignment.LEADING)
        						.addComponent(scrollEditor)
        						.addComponent(scrollMensagens)
        				)
        		)
        		.addComponent(statusBar)
        );
        
        leiaute.setVerticalGroup
        (
        	leiaute.createSequentialGroup()
        		.addGroup
        		(
        			leiaute.createParallelGroup(GroupLayout.Alignment.BASELINE)
        				.addComponent(tbBarraTarefas)
        				.addGroup
        				(
        					leiaute.createSequentialGroup()
        						.addComponent(scrollEditor)
        						.addComponent(scrollMensagens)
        				)
				)
        		.addComponent(statusBar)
  		);
        
        pack();
    }

    /* AÇÕES DOS BOTÕES */
    
    /* Ação do botão NOVO */
    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) 
    {
        txtEditor.setText("");
        txtMensagens.setText("");
        status = Status.NAO_MODIFICADO;
        caminhoArquivo = null;
        pastaArquivo = null;
        nomeArquivo = null;        
        statusBar.setStatus(pastaArquivo, nomeArquivo, status);
        txtEditor.setEnabled(true);        
    }
    
    /* Ação do botão ABRIR */
    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) 
    {
        abrirArquivo();
        statusBar.setStatus(pastaArquivo, nomeArquivo, status);
        txtEditor.setEnabled(true);
    }
    
    /* Ação do botão SALVAR */
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) 
    {
        try 
        {
        	if (status != null)
        	{        	
        		salvaArquivo();
            	statusBar.setStatus(pastaArquivo, nomeArquivo, Status.NAO_MODIFICADO);
        	}
        }
        catch (IOException ex) 
        {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* Ação do botão COPIAR */
    private void btnCopiarActionPerformed(java.awt.event.ActionEvent evt) 
    {    	
        txtEditor.copy();
    }
    
    /* Ação do botão COLAR */
    private void btnColarActionPerformed(java.awt.event.ActionEvent evt) 
    {
        txtEditor.paste();
    }
    
    /* Ação do botão RECORTAR */
    private void btnRecortarActionPerformed(java.awt.event.ActionEvent evt) 
    {
        txtEditor.cut();
    }
    
    /* Ação do botão COMPILAR */
    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	limparMensagens();
    	
    	Lexico lexico = new Lexico(txtEditor.getText());
    	Sintatico sintatico = new Sintatico();
    	Semantico semantico = new Semantico();       

    	try
    	{
    	    sintatico.parse(lexico, semantico);
    	    adicionarMensagem("Programa compilado com sucesso.");
    	}
    	catch ( LexicalError e )
    	{
    		limparMensagens();
    		adicionarMensagem(e.toString());
    	}
    	catch ( SyntaticError e )
    	{
    		limparMensagens();
    		adicionarMensagem(e.toString());
    	}
    	catch ( SemanticError e )
    	{
    		limparMensagens();
    		adicionarMensagem(e.toString());
    	}
    	
    	/*
    	Lexico lexico = new Lexico(txtEditor.getText());    	
    	
    	    	   
    	try
    	{
    		Token t = null;
    		
    		adicionarMensagem("Linha \tClasse \t\tLexema");
    	    while ( (t = lexico.nextToken()) != null )
    	    {
    	        adicionarMensagem(t.getLine() + "\t" + t.getClasse() + "\t" + t.getLexeme());
    	    }
    	    adicionarMensagem("\r\nPrograma compilado com sucesso.");
    	}
    	catch(LexicalError e)
    	{
    		limparMensagens();
    		adicionarMensagem(e.toString());
    	}
    	*/
    	
        //adicionarMensagem("Compilação de programas ainda não foi implementada");
    }
    
    /* Ação do botão GERAR CÓDIGO */
    private void btnGerarCodigoActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	limparMensagens();    
    	adicionarMensagem("Geração de código ainda não foi implementada");
    }
    
    /* Ação do botão EQUIPE */    
    private void btnEquipeActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	limparMensagens();
        StringBuilder equipe = new StringBuilder();
        equipe.append("Equipe:");
        equipe.append("\n");
        equipe.append("\nJeidsan Pereira,");
        equipe.append("\nWillian de Avilla.");
        
        limparMensagens();
        adicionarMensagem(equipe.toString());
    }
    
    /* MÉTODOS AUXILIARES */
    
    /* Limpa o conteúdo da área de mensagens */
    private void limparMensagens()
    {
    	txtMensagens.setText("");
    }
    
    /* Adiciona uma nova mensagem na área de mensagens */    
    private void adicionarMensagem(String mensagem)
    {
    	txtMensagens.append(mensagem + "\r\n");
    }
    
    /* Salva o conteúdo do editor num arquivo */
    private void salvaArquivo() throws IOException 
    {
    	File salvarArquivoEscolhido = null;
    	
    	if (caminhoArquivo == null)
    	{    	    	
    		FileFilter filtro = new FileNameExtensionFilter("Arquivo de texto", "txt");
    	
        	JFileChooser salvandoArquivo = new JFileChooser();
        	salvandoArquivo.addChoosableFileFilter(filtro);
        	salvandoArquivo.setAcceptAllFileFilterUsed(false);        
        
        	int resultado = salvandoArquivo.showSaveDialog(this);
        
        	if (resultado == 0) 
        	{
        		if (!salvandoArquivo.getSelectedFile().getName().endsWith(".txt"))
        		{
        			salvarArquivoEscolhido = new File(salvandoArquivo.getSelectedFile().getAbsolutePath() + ".txt");
        		}
        		else
        		{
        			salvarArquivoEscolhido = salvandoArquivo.getSelectedFile();
        		}
        	}
    	}
    	else
    	{
    		salvarArquivoEscolhido = new File(caminhoArquivo);
    	}
    	
    	if (caminhoArquivo != null)
    	{
    		FileWriter writer = new FileWriter(salvarArquivoEscolhido);
    		writer.write(txtEditor.getText());
    		writer.flush();
    		writer.close();
        
    		caminhoArquivo = salvarArquivoEscolhido.getAbsolutePath();
    		nomeArquivo = salvarArquivoEscolhido.getName();
    		pastaArquivo = caminhoArquivo.replaceAll(nomeArquivo, "");
    		status = Status.NAO_MODIFICADO;
        
    		limparMensagens();
    		statusBar.setStatus(pastaArquivo, nomeArquivo, status);
        
    		JOptionPane.showMessageDialog(this, "O arquivo " + nomeArquivo + " foi salvo com sucesso.");
    	}
    }

    /* Abre um arquivo e carrega seu conteúdo no editor */
    private void abrirArquivo() 
    {
    	FileFilter filtro = new FileNameExtensionFilter("Arquivo de texto", "txt");
    	
        JFileChooser abrir = new JFileChooser();
        abrir.addChoosableFileFilter(filtro);
        abrir.setAcceptAllFileFilterUsed(false);
        
        int retorno = abrir.showOpenDialog(null);
        
        if (retorno == JFileChooser.APPROVE_OPTION) 
        {
            txtEditor.setText("");
            txtMensagens.setText("");                      
            
            caminhoArquivo = abrir.getSelectedFile().getAbsolutePath();
            nomeArquivo = abrir.getSelectedFile().getName();
            pastaArquivo = caminhoArquivo.replaceAll(nomeArquivo, "");
            
            FileReader fr;
            BufferedReader br;            
            
            try 
            {            	
                fr = new FileReader(caminhoArquivo);
                br = new BufferedReader(fr);
                
                while (br.ready()) 
                {
                    String linha = br.readLine() + "\n";
                    txtEditor.append(linha);
                }
                
                br.close();
                fr.close();
            }
            catch (FileNotFoundException ex) 
            {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) 
            {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            status = Status.NAO_MODIFICADO;
        }
    }
}
