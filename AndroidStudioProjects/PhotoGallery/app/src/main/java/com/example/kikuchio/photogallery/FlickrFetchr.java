package com.example.kikuchio.photogallery;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlickrFetchr {

    public static final int ITEMS_PER_PAGE = 100;

    private static final String TAG = "FlickrFetchr";

    public static final String PREF_SEARCH_QUERY = "searchQuery";
    public static final String PREF_LAST_RESULT_ID = "lastResultId";


    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "4f63dbfdc06c6e8ff214857e6ead7d91";
    private static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    private static final String METHOD_SEARCH = "flickr.photos.search";
    private static final String PARAM_EXTRAS = "extra";
    private static final String PARAM_TEXT = "text";
    private static final String EXTRA_SMALL_URL = "url_s";

    private static final String XML_PHOTO = "photo";

    public List<GalleryItem> downladGalleryItems(String url) {
        try {
            String xml = getUrl(url);
            Log.i(TAG, "Received xml: " + xml);
            XmlPullParser parser1 = XmlPullParserFactory.newInstance().newPullParser();
            parser1.setInput(new StringReader(xml));
            XmlPullParser parser = parser1;
            return parseItems(parser);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch xml ", e);
        } catch (XmlPullParserException ppe) {
            Log.e(TAG, "Failed to parse items ", ppe);
        }
        return Collections.emptyList();
    }

    public List<GalleryItem> fetchItems(int pageNo) {
        String url = Uri.parse(ENDPOINT).buildUpon()
                .appendQueryParameter("method", METHOD_GET_RECENT)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", String.valueOf(pageNo))
                .appendQueryParameter(PARAM_EXTRAS, EXTRA_SMALL_URL)
                .build().toString();
        return downladGalleryItems(url);
    }

    public List<GalleryItem> search(String query, int pageNo) {
        String url = Uri.parse(ENDPOINT).buildUpon()
                .appendQueryParameter("method", METHOD_SEARCH)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", String.valueOf(pageNo))
                .appendQueryParameter(PARAM_EXTRAS, EXTRA_SMALL_URL)
                .appendQueryParameter(PARAM_TEXT, query)
                .build().toString();
        return downladGalleryItems(url);
    }

    private List<GalleryItem> parseItems(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        int eventType = parser.next();
        List<GalleryItem> galleryItems = new ArrayList<>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && XML_PHOTO.equals(parser.getName())) {
                String id = parser.getAttributeValue(null, "id");
                String caption = parser.getAttributeValue(null, "title");
                String farmId = parser.getAttributeValue(null, "farm");
                String serverId = parser.getAttributeValue(null, "server");
                String secret = parser.getAttributeValue(null, "secret");

                String smallUrl = String.format("http://farm%s.staticflickr.com/%s/%s_%s.jpg", farmId, serverId, id, secret);
//                String smallUrl = parser.getAttributeValue(null, EXTRA_SMALL_URL);
                galleryItems.add(toGalleryItem(id, caption, smallUrl));
            }
            eventType = parser.next();
        }
        return galleryItems;
    }

    @NonNull
    private GalleryItem toGalleryItem(String id, String caption, String smallUrl) {
        GalleryItem item = new GalleryItem();
        item.setId(id);
        item.setCaption(caption);
        item.setUrl(smallUrl);
        return item;
    }

    byte[] getUrlBytes(String srcUrl) throws IOException {
        URL url = new URL(srcUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private String getUrl(String url) throws IOException {
        return new String(getUrlBytes(url));
    }
}
