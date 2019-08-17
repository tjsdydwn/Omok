package omok.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class OmokFrame extends JFrame implements ActionListener {

   private static final long serialVersionUID = -7202540779600149090L;

   private JButton btnClear;
   private JButton btnCancel;
   private OmokPan pan;

   public OmokFrame() {
      Container con = this.getContentPane();
      con.setLayout(null);

      pan = new OmokPan();
      pan.setBounds(15, 15, 601, 601);
      con.add(pan);

      btnClear = new JButton("모두지우기");
      btnClear.setBounds(15, 630, 180, 40);
      con.add(btnClear);

      btnCancel = new JButton("무르기");
      btnCancel.setBounds(200, 630, 180, 40);
      con.add(btnCancel);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(650, 850);
      setLocationRelativeTo(null);
      setVisible(true);

      addEvent();
   }

   public void addEvent() {
      btnClear.addActionListener(this);
      btnCancel.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnClear) {
         pan.clear();
      } else if (e.getSource() == btnCancel) {
         pan.cancel();
      }
   }
}