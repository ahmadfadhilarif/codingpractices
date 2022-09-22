package mp.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.*;

public class CalculatorInterface extends JFrame{
    int style;
    
    String[] operators = new String[]{"←","CE","C","/","X","√","-","+","^2","=","%","1/x"}; 
    String[] pastThread = new String[20];
    String[] numbers = new String[]{"1","2","3","4","5","6","7","8","9","±","0","."};
    String[] numpadTooltip = new String[]{"One","Two","Three","Four","Five","Six",
        "Seven","Eight","Nine","Plus-minus","Zero","Dot"
    };
    String[] operatorTooltip = new String[]{"Backspace","Clear entry","Clear","Divide",
        "Multiply","Square root","Minus","Plus","Square","Solve","Modulo","Reciprocal"
    };
    Color colorValues [] = { Color.black, Color.blue, Color.red, Color.green };
    JRadioButtonMenuItem colorItems [], fonts [];
    
    JLabel display = new JLabel("0");
    
    JPanel closure = new JPanel();
    JPanel currentThread = new JPanel();
    JPanel Main = new JPanel();
    JPanel input = new JPanel();
    JPanel numpad = new JPanel();
    JPanel operator = new JPanel();
    JPanel histories = new JPanel();
    
    JCheckBoxMenuItem styleItems [];
    
    ButtonGroup fontGroup, colorGroup;
    
    public CalculatorInterface(){
        closure.setLayout(new GridLayout(1,2));
        //NUMPAD
        numpad.setLayout(new GridLayout(4,3));
        for (int i = 0; i < 12; i++){
            JButton butang = (new JButton(numbers[i]));
            butang.setFont(new Font("Times New Roman",Font.BOLD,36));
            butang.setToolTipText(numpadTooltip[i]);
            numpad.add(butang);
        }
            
        TitledBorder numpadBorder = new TitledBorder(new EtchedBorder(), "Numpad");
        Border who = new LineBorder(Color.CYAN,3,true);
        numpadBorder.setBorder(who);
        numpadBorder.setTitleFont(new Font("Comic Sans",Font.BOLD,16));
        numpad.setBorder(numpadBorder);
        
        //OPERATOR
        operator.setLayout(new GridLayout(4,3));
        for (int i=0;i<operators.length;i++) {
            JButton butang = new JButton(operators[i]);
            butang.setFont(new Font("Times New Roman",Font.BOLD,36));
            butang.setToolTipText(operatorTooltip[i]);
            operator.add(butang);
        }
        
        TitledBorder operatorBorder = new TitledBorder(new EtchedBorder(), "Operator");
        Border which = new LineBorder(Color.GREEN,3,true);
        operatorBorder.setBorder(which);
        operatorBorder.setTitleFont(new Font("Comic Sans",Font.BOLD,16));
        operator.setBorder(operatorBorder);
        
        input.setLayout(new GridLayout(1,2));
        input.add(numpad).setBackground(Color.GREEN);
        input.add(operator).setBackground(Color.CYAN);
        
        //MAIN PANEL
        Main.setLayout(new BorderLayout());

        //CURRENT THREAD
        TitledBorder threadBorder = new TitledBorder(new EtchedBorder(),"Current thread");
        Border when = new LineBorder(Color.BLACK,3,true);
        threadBorder.setBorder(when);
        threadBorder.setTitleFont(new Font("Comic Sans",Font.BOLD,20));
        currentThread.setBorder(threadBorder);
	display.setFont(new Font("Times New Roman",Font.BOLD,76));
        currentThread.add(display);
        currentThread.setBackground(Color.YELLOW);
        currentThread.setToolTipText("Current operation");
        
        Main.add(currentThread, BorderLayout.NORTH);
        Main.add(input, BorderLayout.CENTER);
        
        closure.add(Main);
        
        //HISTORY PANEL
        TitledBorder borderHistory = new TitledBorder(new EtchedBorder(), "History");
        borderHistory.setTitleColor(Color.MAGENTA);
        Border what = new LineBorder(Color.BLUE,3,true);
        borderHistory.setBorder(what);
        borderHistory.setTitleFont(new Font("Comic Sans",Font.BOLD,20));
        histories.setBorder(borderHistory);
        histories.setBackground(Color.ORANGE);
        histories.setToolTipText("Past calculations");
        
        closure.add(histories);
        add(closure);
        
        //set up File menu and its menu items
        JMenu fileMenu = new JMenu ("File");
        fileMenu.setMnemonic ('F');
        
        //set up About.....menu items
        JMenuItem aboutItem = new JMenuItem ("About...");
        aboutItem.setMnemonic ('A');
        
        //anonymous inner class to handle menu item event
        aboutItem.addActionListener (new ActionListener () {
        //display message dialog when user selects about...
            String one = "Calculator Interface™ by AHMAD FADHIL ARIF BIN BAHARUDIN ";
            String two = "\nTOTALLY USELESS THOUGH. CANT CALCULATE ANYTHING";
            String three = "\n- INTERFACE ONLY - NON FUNCTIONAL CALCULATOR -";
            
            @Override
            public void actionPerformed (ActionEvent event ){
                JOptionPane. showMessageDialog (CalculatorInterface.this,
                one+two+three, "About",
                JOptionPane.WARNING_MESSAGE);
            }
        });
        fileMenu.add( aboutItem);
        
        //set up Exit menu item
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
        exitItem.addActionListener((ActionEvent event) -> {
            System.exit (0);
        });
        fileMenu.add (exitItem);
        
        //create Format menu, its submenus and menu items
        JMenu formatMenu = new JMenu("Thread");
        formatMenu.setMnemonic('r');
        
        //create color submenu
        String colors [] = { "Black", "Blue", "Red", "Green" };
        JMenu colorMenu = new JMenu ("Color");
        colorMenu.setMnemonic('C');
        colorItems = new JRadioButtonMenuItem[colors.length];
        colorGroup = new ButtonGroup ();
        ItemHandler itemHandler = new ItemHandler ();
        
        //crate color radio button menu items
        for (int count = 0; count < colors.length; count ++) {
            colorItems [count] = new JRadioButtonMenuItem( colors [count] );
            colorMenu.add(colorItems [count]);
            colorGroup.add(colorItems [count]);
            colorItems[count].addActionListener(itemHandler);
        }
        
        //select first color menu item
        colorItems [0].setSelected(true);
        
        //add format menu to menu bar
        formatMenu.add (colorMenu);
        formatMenu.addSeparator();
        
        //create Font submenu
        String fontNames [] = { "Serif", "Monospaced","SansSerif"};
        JMenu fontMenu = new JMenu ("Font");
        fontMenu.setMnemonic ('n');
        fonts = new JRadioButtonMenuItem[ fontNames.length ];
        fontGroup = new ButtonGroup ();
        
        //create font radio button menu items
        for (int count = 0; count < fonts.length; count ++) {
            fonts [count] =
            new JRadioButtonMenuItem( fontNames [count] );
            fontMenu.add(fonts [count]);
            fontGroup.add(fonts [count]);
            fonts[count].addActionListener( itemHandler );
        }
        
        //select first font menu item
        fonts[0].setSelected(true);
        fontMenu.addSeparator();
        
        //set up style menu item
        String styleNames []= { "Bold", "Italic" };
        styleItems = new JCheckBoxMenuItem [styleNames.length ];
        StyleHandler styleHandler = new StyleHandler ();
        
        //create style checkbox menu item
        for (int count = 0; count < styleNames.length; count ++)
        {
            styleItems [count] = new JCheckBoxMenuItem( styleNames [count] );
            fontMenu.add(styleItems [count]);
            styleItems[count].addItemListener( styleHandler );
        }
        
        //put font menu in Format menu
        formatMenu.add (fontMenu);
        
        //create menu bar and attach it to MenuTest window
        JMenuBar bar = new JMenuBar ();
        setJMenuBar(bar);
        fileMenu.setForeground(Color.red);
        bar.add(fileMenu);
        formatMenu.setForeground(Color.red);
        bar.add(formatMenu);
        bar.setBackground(Color.BLACK);
        
    }
    public void start() {
        CalculatorInterface frame = new CalculatorInterface();
        frame.setVisible(true);
        frame.setTitle("Calculator Interface™");
        frame.setSize(1360, 760);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
    
    private class ItemHandler implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent event ) {
            //process color selection
            for ( int count = 0; count < colorItems.length;count++)
                if( colorItems [count].isSelected() ) {
                    display.setForeground (colorValues [count] );
                    break;
                }
            
            //process font selection
            for (JRadioButtonMenuItem font : fonts) {
                if (event.getSource() == font) {
                    display.setFont(new Font(font.getText(), style, 76));
                    break;
                }
            }
            repaint ();
        }
    }
    
    private class StyleHandler implements ItemListener {
        //process font style selection
        @Override
        public void itemStateChanged(ItemEvent e) {
        style = 0;
        
        //check for bold selection
        if (styleItems [0].isSelected() )
            style += Font.BOLD;
        
        //check for italic selection
        if (styleItems [1].isSelected() )
            style += Font.ITALIC;
        
        display.setFont( new Font(display.getFont().getName(),style,76));
        repaint ();
        }
    }
}