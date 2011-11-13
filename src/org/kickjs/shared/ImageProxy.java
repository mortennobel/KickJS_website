package org.kickjs.shared;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

/**
 * Proxy image requests
 */
public class ImageProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(ImageProxy.class.getName());

    private static final int MAX_IMAGE_SIZE = 1048576;

    private static Cache cache;

    static {
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        if (url==null || url.length()==0){
            resp.sendRedirect("/images/invalidURL.png");
            return;
        }
        byte[] content = (byte[])cache.get(url);
        if (content == null){

            content = getURLContent(url,resp);
            if (content == null){
                return;
            }
            if (content.length>MAX_IMAGE_SIZE){
                resp.sendRedirect("/images/tooLarge.png");
                return;
            }
            cache.put(url,content);
            System.out.println("Add image to cache");
        } else {
            System.out.println("Found image in cache");
        }

        if (isPng(content)){
            resp.setContentType("image/png");
        } else if (isJPEG(content)){
            resp.setContentType("image/jpeg");
        } else if (isGIF(content)){
            resp.setContentType("image/gif");
        } else {
            resp.sendRedirect("/images/unsupportedContent.png");
        }

        resp.setContentLength(content.length);
        resp.setHeader("Cache-Control","max-age=31556926");
        resp.getOutputStream().write(content);
    }

    private byte[] getURLContent(String url, HttpServletResponse response) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection urlCon = (HttpURLConnection) urlObj.openConnection();
        int responseCode = urlCon.getResponseCode();
        if (responseCode != HttpsURLConnection.HTTP_OK){
            response.sendRedirect("/images/imageUnavailable.png");
            log.info("Server responded " + responseCode + " url " + url);
            System.out.println("Server responded "+responseCode+" url "+url);
            return null;
        }
        InputStream is = urlCon.getInputStream();
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = is.read(buffer))!=-1){
            baos.write(buffer,0,bytesRead);
            if (baos.size()>MAX_IMAGE_SIZE){
                break; // avoid reading rest of data if already too large
            }
        }
        is.close();
        return baos.toByteArray();
    }

    private boolean isPng(byte[] rawImage){
        // "PNG" on position 1
        byte[] pattern = new byte[] { (byte) 0x50, (byte) 0x4E,(byte) 0x47 };

        return compareValues(pattern, rawImage, 1);
    }

    private boolean isJPEG(byte[] rawImage){
        // "0xFFD8" on position 0
        byte[] pattern = new byte[] { (byte) 0xFF, (byte) 0xD8 };

        return compareValues(pattern, rawImage, 0);
    }

    private boolean isGIF(byte[] rawImage){
        byte[] pattern = new byte[] { (byte) 0x47, (byte) 0x49,
                (byte) 0x46, (byte) 0x38 };

        return compareValues(pattern, rawImage, 0);
    }

    private boolean compareValues(byte[] pattern, byte[] data, int offset){
        for (int i=0;i<pattern.length;i++){
            if (data[offset+i] != pattern[i]){
                return false;
            }
        }
        return true;
    }
}
