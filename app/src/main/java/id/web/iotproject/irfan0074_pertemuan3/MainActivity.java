package id.web.iotproject.irfan0074_pertemuan3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ArrayList<HashMap<String, String>> userList = new ArrayList<>();
            ListView lv =(ListView) findViewById(R.id.userList);
            InputStream inputStream = getAssets().open("userdetail.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            NodeList nodeList = document.getElementsByTagName("user");

            for (int i = 0; 1 < nodeList.getLength();i++){
                if (nodeList.item(0).getNodeType() == Node.ELEMENT_NODE){
                    HashMap<String,String> user = new HashMap<>();
                    Element element = (Element)nodeList.item(i);
                    user.put("nama", getNodeValue("nama",element));
                    user.put("jabatan", getNodeValue("jabatan",element));
                    userList.add(user);
                }
            }
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,userList, R.layout.listview, new String[]{"nama","jabatan"}),
            lv.setAdapter(adapter);


        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

    }

    private String getNodeValue(String nama, Element element) {
        NodeList nodeList = element.getElementsByTagName(nama);
        Node node = nodeList.item(0);
        if (node != null){
            if (node.hasChildNodes()){
                Node child = node.getFirstChild();
                while (child != null){
                    if (child.getNodeType()== node.TEXT_NODE){
                        return  child.getNodeValue();

                    }
                }
            }
        }
        return "";
    }

}