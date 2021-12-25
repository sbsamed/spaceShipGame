
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samed
 */

class Hedef{
    private int x;
    private int y;
    private int hedefHareket;
    private int red,green,blue;

    public Hedef(int x, int y, int hedefHareket, int red, int green, int blue) {
        this.x = x;
        this.y = y;
        this.hedefHareket = hedefHareket;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getHedefHareket() {
        return hedefHareket;
    }

    public void setHedefHareket(int atesHareket) {
        this.hedefHareket = atesHareket;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
       public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

}

class Ates{
    private int x;
    private int y;
    

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
  }
public class oyun extends JPanel implements KeyListener,ActionListener{
    
    Timer timer = new Timer(10,this);
    private int adet=0,kontrol=0;
    private ArrayList<Hedef> hedefler=new ArrayList<Hedef>();
    private ArrayList<Ates> atesler=new ArrayList<Ates>();
    private int uzayGemisiX=400;
    private int uzayGemisiY= 500;
    private int dirUzayX=10;
    private int skor=0;
    private  int puan=0;
    private int atesAdet=0;
     
    public oyun() {
      
        setBackground(Color.black);
        
        timer.start();
    }
   
    public int kontrolEt(){
        for (Ates ates :atesler){
           for(Hedef hedef :hedefler){
  if(new Rectangle(ates.getX(),ates.getY(),15,25).intersects(new Rectangle(hedef.getX(), hedef.getY(),25,25))){
      skor++;
      puan+=10;
      atesler.remove(ates);
      hedefler.remove(hedef);
      return skor;
          }
            }
        }
         return skor;
    }
    
  

  
    @Override
    public void paint(Graphics g) {
        
      super.paint(g); 
      g.setColor(Color.white); 
      g.fillOval(360, 520, 80, 80);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setStroke(new BasicStroke(15));
      g.drawLine(400, 520, uzayGemisiX, uzayGemisiY);
      
      for(Hedef hedef:hedefler){
        g2d.setPaint(new Color(hedef.getRed() ,hedef.getGreen(),hedef.getBlue()));
       g2d.fillRect(hedef.getX(), hedef.getY(),15, 25);
       }
      
    g.setColor(Color.red); 
       for(Ates ates:atesler){
       g.fillOval(ates.getX(),ates.getY(), 15, 25);
       
      } 
      
      String s=Integer.toString(kontrolEt()); 
      String s2=Integer.toString(puan); 
      String s3=Integer.toString(10-atesAdet); 
      s="Vurulan Hedef Sayısı :"+s;
      s2="Puanınız:"+s2;
       s3="Kalan Ateş:"+s3;
      g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
      g.setColor(Color.white);
      g.drawString(s, 20, 40);
      g.drawString(s2, 20, 60);
      g.drawString(s3, 20,80);
       
    }
    
    @Override
    public void repaint() {
        super.repaint(); 
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c=e.getKeyCode();
        
        if(c== KeyEvent.VK_LEFT){
          
           uzayGemisiX-=dirUzayX;
         
        } else if(c== KeyEvent.VK_RIGHT){
            
          uzayGemisiX+=dirUzayX;  
        }
        else if(c== KeyEvent.VK_SPACE){
            if(atesAdet<10){
               atesler.add(new Ates(uzayGemisiX,uzayGemisiY));
               atesAdet++;    
            }else{
              timer.stop();
            String message="Ateş Hakkınız Bitti.Yeniden Başlayın";
            JOptionPane.showMessageDialog(this, message);
             atesler.clear();
             hedefler.clear();
             puan=0;
             skor=0;
             adet=0;
             atesAdet=0;
             timer.start();
            }
        
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         kontrol++;
         if(kontrol%120==0 && adet<10){
           Random random = new Random();
         int rndmX=Math.abs(random.nextInt()) % 700;
         int red=Math.abs(random.nextInt()) % 255;
         int green=  Math.abs(random.nextInt()) % 255;
         int blue= Math.abs(random.nextInt()) % 255;
           hedefler.add(new Hedef(rndmX,20,2,red,green,blue));
           adet++;
         }
        for(Hedef hedef:hedefler){
            if(hedef.getY()==0 ||hedef.getY()==540){
                hedef.setHedefHareket(-hedef.getHedefHareket()); 
           } 
            hedef.setY(hedef.getY()+hedef.getHedefHareket());
       
          }
       
         for(Ates ates:atesler){
             if(ates.getX()<400){
              ates.setX(ates.getX()-5);
              ates.setY(ates.getY()-5);
            }else if(ates.getX()>400){
                 ates.setX(ates.getX()+5);
                  ates.setY(ates.getY()-5);
             }else{
                 ates.setY(ates.getY()-5);
            }
       }
        repaint();
    }
}
