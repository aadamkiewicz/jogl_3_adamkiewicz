package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JOptionPane;

public class Adamkiewicz implements GLEventListener {
//statyczne pola okre?laj?ce rotacj? wokó? osi X i Y

    private static float xrot = 0.0f, yrot = 0.0f;
    static Koparka koparka;
    public static float ambientLight[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat?o otaczaj?ce
    public static float diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f};//?wiat?o rozproszone
    public static float specular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
    public static float lightPos[] = {0.0f, 150.0f, 150.0f, 1.0f};//pozycja ?wiat?a
    static float licznik = 0.1f;
    static BufferedImage image1 = null, image2 = null;
    static Texture t1 = null, t2 = null;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Adamkiewicz());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        //Obs?uga klawiszy strza?ek
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    xrot -= 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    xrot += 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    yrot += 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    yrot -= 1.0f;
                }

                if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                    koparka.kond += (1.5f);
                    if (koparka.kond > 60.0f) {
                        koparka.kond = 60.0f;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
                    koparka.kond -= (1.5f);

                    if (koparka.kond < -50.0f) {
                        koparka.kond = -50.0f;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
                    koparka.kond2 += (1.5f);

                    if (koparka.kond2 > 50.0f) {
                        koparka.kond2 = 50.0f;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                    koparka.kond2 -= (1.5f);

                    if (koparka.kond2 < -120.0f) {
                        koparka.kond2 = -120.0f;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
                    koparka.kond3 += (1.5f);

                    if (koparka.kond3 > 10.0f) {
                        koparka.kond3 = 10.0f;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                    koparka.kond3 -= (1.5f);

                    if (koparka.kond3 < -190.0f) {
                        koparka.kond3 = -190.0f;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
                    koparka.kond4 += (1.5f);

                    if (koparka.kond4 > 60.0f) {
                        koparka.kond4 = 60.0f;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
                    koparka.kond4 -= (1.5f);

                    if (koparka.kond4 < -60.0f) {
                        koparka.kond4 = -60.0f;
                    }
                }
                if (e.getKeyChar() == 'q') {
                    ambientLight = new float[]{ambientLight[0] - 0.1f, ambientLight[1] - 0.1f, ambientLight[2] - 0.1f, ambientLight[3] - 0.01f};
                }
                if (e.getKeyChar() == 'w') {
                    ambientLight = new float[]{ambientLight[0] + 0.1f, ambientLight[1] + 0.1f, ambientLight[2] + 0.1f, ambientLight[3] + 0.01f};
                }
                if (e.getKeyChar() == 'a') {
                    diffuseLight = new float[]{diffuseLight[0] - 0.1f, diffuseLight[1] - 0.1f, diffuseLight[2] - 0.1f, diffuseLight[3] - 0.01f};
                }
                if (e.getKeyChar() == 's') {
                    diffuseLight = new float[]{diffuseLight[0] + 0.1f, diffuseLight[1] + 0.1f, diffuseLight[2] + 0.1f, diffuseLight[3] + 0.01f};
                }
                if (e.getKeyChar() == 'z') {
                    specular = new float[]{specular[0] - 0.1f, specular[1] - 0.1f, specular[2] - 0.1f, specular[3] - 0.01f};
                }
                if (e.getKeyChar() == 'x') {
                    specular = new float[]{specular[0] + 0.1f, specular[1] + 0.1f, specular[2] + 0.1f, specular[3] + 0.01f};
                }
                if (e.getKeyChar() == 'k') {
                    lightPos = new float[]{lightPos[0] - 0.1f, lightPos[1] - 0.1f, lightPos[2] - 0.1f, lightPos[3] - 0.01f};
                }
                if (e.getKeyChar() == 'l') {
                    lightPos = new float[]{lightPos[0] + 0.1f, lightPos[1] + 0.1f, lightPos[2] + 0.1f, lightPos[3] + 0.01f};
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();

    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        //warto?ci sk?adowe o?wietlenia i koordynaty ?ród?a ?wiat?a
        //(czwarty parametr okre?la odleg?o?? ?ród?a:
        //0.0f-niesko?czona; 1.0f-okre?lona przez pozosta?e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametrów ?ród?a ?wiat?a nr. 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0); //swiat?o otaczaj?ce
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0); //pozycja ?wiat?a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?ród?a ?wiat?a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?ledzenia kolorów
        //kolory b?d? ustalane za pomoc? glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        //Ustawienie jasno?ci i odblaskowo?ci obiektów
        float specref[] = {1.0f, 1.0f, 1.0f, 1.0f}; //parametry odblaskowo?ci
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specref, 0);

        gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 128);

        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        koparka = new Koparka();

        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/android.jpg"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/pokemon.jpg"));
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.toString());
            return;
        }

        t1 = TextureIO.newTexture(image1, false);
        t2 = TextureIO.newTexture(image2, false);
        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE,
                GL.GL_BLEND | GL.GL_MODULATE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 200.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private float[] Norm(float[] punkty, int ind1, int ind2, int ind3) {
        float[] norm = new float[3];
        float[] wektor0 = new float[3];
        float[] wektor1 = new float[3];

        for (int i = 0; i < 3; i++) {
            wektor0[i] = punkty[i + ind1] - punkty[i + ind2];
            wektor1[i] = punkty[i + ind2] - punkty[i + ind3];
        }

        norm[0] = wektor0[1] * wektor1[2] - wektor0[2] * wektor1[1];
        norm[1] = wektor0[2] * wektor1[0] - wektor0[0] * wektor1[2];
        norm[2] = wektor0[0] * wektor1[1] - wektor0[1] * wektor1[0];
        float d
                = (float) Math.sqrt((norm[0] * norm[0]) + (norm[1] * norm[1]) + (norm[2] * norm[2]));
        if (d == 0.0f) {
            d = 1.0f;
        }
        norm[0] /= d;
        norm[1] /= d;
        norm[2] /= d;

        return norm;
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        koparka.kond += licznik;
        if (koparka.kond > 60.0f) {
            koparka.kond = 60.0f;
            licznik *= (-1);
        }

        if (koparka.kond < -60.0f) {
            licznik *= (-1);
        }

        koparka.kond2 += licznik;
        if (koparka.kond2 > 50.0f) {
            koparka.kond2 = 50.0f;
            licznik *= (-1);

        }
        if (koparka.kond2 < -50.0f) // Clear the drawing area
        {
            gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        }
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f); //przesuni?cie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wokó? osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wokó? osi Y

        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametrów ?ród?a ?wiat?a nr. 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0); //swiat?o otaczaj?ce
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0); //pozycja ?wiat?a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?ród?a ?wiat?a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        //koparka.Rysuj(gl);
        // drzewko(gl);
        //  for(int i=0; i<10 ;i++)
        //  {
        //      drzewko(gl);
        //      gl.glTranslatef(4.0f, 0.0f, 0.0f);
        //      drzewko(gl);
        //     gl.glTranslatef(-4.0f, 4.0f, 0.0f);
        //  }
//        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
//        gl.glBegin(GL.GL_QUADS);
////?ciana przednia
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(1.0f, -1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(1.0f, 1.0f, 1.0f);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
////sciana tylnia
//
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(1.0f, 1.0f, -1.0f);
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, -1.0f);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//        gl.glEnd();
////?ciana lewa
//
//        gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
//        gl.glBegin(GL.GL_QUADS);
//        gl.glTexCoord2f(2.0f, 2.0f);
//        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//        gl.glTexCoord2f(0.0f, 2.0f);
//        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
//        gl.glTexCoord2f(2.0f, 0.0f);
//        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//////?ciana prawa
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(1.0f, 1.0f, -1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(1.0f, 1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, 1.0f);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, -1.0f);
//
//////?ciana dolna
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, -1.0f);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, 1.0f);
////
//////?ciana górna
//
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(1.0f, 1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(1.0f, 1.0f, -1.0f);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//        gl.glEnd();

//ostroslup
        //sciana dolna
        gl.glBegin(GL.GL_QUADS);
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glEnd();
        //scianka1
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        float[] scianka1 = {-1.0f, -1.0f, 1.0f, //wpó?rz?dne pierwszego punktu
            1.0f, -1.0f, 1.0f, //wspó?rz?dne drugiego punktu
            0.0f, 1.0f, 0.0f};                  //wspó?rz?dne trzeciego punktu
        float[] normalna1 = Norm(scianka1, 0, 3, 6);
        gl.glNormal3fv(normalna1, 0);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3fv(scianka1, 0);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3fv(scianka1, 3);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3fv(scianka1, 6);
        gl.glEnd();
        //scianka2
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        float[] scianka2 = {1.0f, -1.0f, -1.0f, //wpó?rz?dne pierwszego punktu
            -1.0f, -1.0f, -1.0f, //wspó?rz?dne drugiego punktu
            0.0f, 1.0f, 0.0f};                  //wspó?rz?dne trzeciego punktu
        float[] normalna2 = Norm(scianka2, 0, 3, 6);

        gl.glNormal3fv(normalna2, 0);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3fv(scianka2, 0);            //wspó?rz?dne 1go 
        gl.glTexCoord2f(1, 1);
        gl.glVertex3fv(scianka2, 3);            //wspó?rz?dne 2go 
        gl.glTexCoord2f(0, 0);
        gl.glVertex3fv(scianka2, 6);            //wspó?rz?dne 3go 
        gl.glEnd();
        //scianka3
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        float[] scianka3 = {-1.0f, -1.0f, -1.0f,//wpó?rz?dne pierwszego punktu
            -1.0f, -1.0f, 1.0f, //wspó?rz?dne drugiego punktu
            0.0f, 1.0f, 0.0f};                  //wspó?rz?dne trzeciego punktu
        float[] normalna3 = Norm(scianka3, 0, 3, 6);

        gl.glNormal3fv(normalna3, 0);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3fv(scianka3, 0);            //wspó?rz?dne 1go 
        gl.glTexCoord2f(1, 1);
        gl.glVertex3fv(scianka3, 3);            //wspó?rz?dne 2go
        gl.glTexCoord2f(0, 0);
        gl.glVertex3fv(scianka3, 6);             //wspó?rz?dne 3go
        gl.glEnd();
        //scianka4
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        float[] scianka4 = {1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f, //wspó?rz?dne drugiego punktu
            0.0f, 1.0f, 0.0f};                  //wspó?rz?dne trzeciego punktu
        float[] normalna4 = Norm(scianka4, 0, 3, 6);
        gl.glTexCoord2f(0, 1);
        gl.glNormal3fv(normalna4, 0);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3fv(scianka4, 0);            //wspó?rz?dne 1go punktu 
        gl.glTexCoord2f(1, 1);
        gl.glVertex3fv(scianka4, 3);            //wspó?rz?dne 2go punktu 
        gl.glVertex3fv(scianka4, 6);            //wspó?rz?dne 3go punktu 
        gl.glEnd();
        gl.glFlush();
        //walec
// float x,y,kat;
//gl.glBegin(GL.GL_TRIANGLE_FAN);
//gl.glVertex3f(0.0f,1.0f, .0f); //?rodek
//for(kat = 0.0f; kat < (2.0f*Math.PI); kat+=(Math.PI/32.0f))
//{
//x = 1.5f*(float)Math.sin(kat);
//y = 1.5f*(float)Math.cos(kat);
//gl.glVertex3f(x, 1.0f,y); //kolejne punkty
//}
//gl.glEnd();
//
//float x1,y1,kat1;
//gl.glBegin(GL.GL_TRIANGLE_FAN);
//gl.glVertex3f(0.0f,-1.0f, 0.0f); //?rodek
//for(kat1 = (float) (2.0f*Math.PI); kat1 > 0.0f; kat1-=(Math.PI/32.0f))
//{
//x1 = 1.5f*(float)Math.sin(kat1);
//y1 = 1.5f*(float)Math.cos(kat1);
//gl.glVertex3f(x1,-1.0f,y1); //kolejne punkty
//}
//gl.glEnd();
//
//float x2,y2,kat2;
//gl.glBegin(GL.GL_QUAD_STRIP);
//gl.glColor3f(1.0f,0.0f,1.0f);
//for(kat2 = 0.0f; kat2 < (2.0f*Math.PI); kat2+=(Math.PI/32.0f))
//{
//x2 = 1.5f*(float)Math.sin(kat2);
//y2 = 1.5f*(float)Math.cos(kat2);
// gl.glVertex3f(x2,1.0f,y2);
// gl.glVertex3f(x2,-1.0f,y2);//kolejne punkty
//}
// gl.glEnd();       // Flush all drawing operations to the graphics card
        //  gl.glFlush();
        //drzewko      
    }

    void drzewko(GL gl
    ) {
        gl.glPushMatrix();
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        stozek(gl);

        gl.glScalef(1.2f, 1.2f, 1.0f);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        stozek(gl);

        gl.glScalef(1.4f, 1.4f, 1.0f);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        stozek(gl);

        gl.glColor3f(195 / 256f, 128 / 256f, 0.0f);
        gl.glScalef(0.7f, 0.7f, 1.0f);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        walec(gl);
        gl.glPopMatrix();
    }

    void walec(GL gl
    ) {
//wywo?ujemy automatyczne normalizowanie normalnych
//bo operacja skalowania je zniekszta?ci
        gl.glEnable(GL.GL_NORMALIZE);
        float x, y, kat;
        gl.glBegin(GL.GL_QUAD_STRIP);
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), 0.0f);
            gl.glVertex3f(x, y, -1.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -1.0f); //srodek kola
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glVertex3f(x, y, -1.0f);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }

    void stozek(GL gl
    ) {
//wywo?ujemy automatyczne normalizowanie normalnych
        gl.glEnable(GL.GL_NORMALIZE);
        float x, y, kat;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -2.0f); //wierzcholek stozka
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), -2.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();

    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
