

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.applet.Applet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Reed
 */
//CLASE NO UTILIZADA AÚN
public class CambioClass {
    public Method metodo;
    public String path;
    public String pathJar;
    private boolean found;
    private ClassLoader load;
    public CambioClass (String dir, String dirJar) throws MalformedURLException{
        path = dir;
        pathJar = dir;
        found = false;
        URL url = new URL(pathJar);
        load = new URLClassLoader(new URL[] {url});
        url = null;
    }
    public Applet createApplet() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class applet = load.loadClass("net.minecraft.client.MinecraftApplet");
        return (Applet)applet.newInstance();
    }
    public boolean cambiarClass (){
        try {
            URLClassLoader classLoader = ((URLClassLoader) ClassLoader.getSystemClassLoader());
            metodo = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class});
            metodo.setAccessible(true);
            URL url = new URL (pathJar);
            metodo.invoke(classLoader, new Object[] { url });
            found = true;
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            found = false;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            found = false;
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
            found = false;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            found = false;
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            found = false;
        } catch (SecurityException ex) {
            ex.printStackTrace();
            found = false;
        }
        return found;
    }
}
