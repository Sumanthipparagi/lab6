import android.os.Bundle; import android.util.Log; import android.util.Xml; import android.view.View; import android.widget.Button;
import android.widget.TextView; import android.widget.Toast; import org.json.JSONArray; import org.json.JSONException; import org.json.JSONObject; import org.w3c.dom.Document; import org.w3c.dom.Element; import org.w3c.dom.Node; import org.w3c.dom.NodeList; import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException; import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets; import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; import javax.xml.parsers.ParserConfigurationException;
public class MainActivity extends AppCompatActivity { Button parseXmlBtn, parseJsonBtn;
TextView displayTextView;
@Override
protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
parseJsonBtn = findViewById(R.id.parseJsonBtn); parseXmlBtn = findViewById(R.id.parseXmlBtn);
displayTextView = findViewById(R.id.displayTextView);
parseXmlBtn.setOnClickListener(new View.OnClickListener() { @Override
public void onClick(View v) { try {
InputStream is = getAssets().open("city.xml");
DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
Document document = documentBuilder.parse(is);
StringBuilder stringBuilder = new StringBuilder(); stringBuilder.append("XML Data"); stringBuilder.append("\n --------- ");
NodeList nodeList = document.getElementsByTagName("place"); for (int i = 0; i < nodeList.getLength(); i++) {
Node node = nodeList.item(i);
if (node.getNodeType() == Node.ELEMENT_NODE) { Element element = (Element) node;
element));
}
stringBuilder.append("\nName: ").append(getValue("name", element)); stringBuilder.append("\nLatitude: ").append(getValue("lat", element)); stringBuilder.append("\nLongitude: ").append(getValue("long", element)); stringBuilder.append("\nTemperature: ").append(getValue("temperature",
stringBuilder.append("\nHumidity: ").append(getValue("humidity", element)); stringBuilder.append("\n --------- ");
}
displayTextView.setText(stringBuilder.toString());
} catch (Exception e) { e.printStackTrace();
Toast.makeText(MainActivity.this, "Error Parsing XML", Toast.LENGTH_SHORT).show();
}
}
});
parseJsonBtn.setOnClickListener(new View.OnClickListener() { @Override
public void onClick(View v) { String json;
StringBuilder stringBuilder = new StringBuilder(); try {
InputStream is = getAssets().open("city.json");
int size = is.available();
byte[] buffer = new byte[size]; is.read(buffer);
json = new String(buffer, StandardCharsets.UTF_8);
JSONArray jsonArray = new JSONArray(json); stringBuilder.append("JSON Data"); stringBuilder.append("\n --------- ");
for (int i = 0; i < jsonArray.length(); i++) {
JSONObject jsonObject = jsonArray.getJSONObject(i); stringBuilder.append("\nName: ").append(jsonObject.getString("name")); stringBuilder.append("\nLatitude: ").append(jsonObject.getString("lat")); stringBuilder.append("\nLongitude: ").append(jsonObject.getString("long")); stringBuilder.append("\nTemperature:
").append(jsonObject.getString("temperature"));
stringBuilder.append("\nHumidity: ").append(jsonObject.getString("humidity")); stringBuilder.append("\n --------- ");
}
displayTextView.setText(stringBuilder.toString());
is.close();
} catch (IOException | JSONException e) { e.printStackTrace();
Toast.makeText(MainActivity.this, "Error in parsing JSON data from!", Toast.LENGTH_SHORT).show();
}
}
});
}
private String getValue(String tag, Element element) { return
element.getElementsByTagName(tag).item(0).getChildNodes().item(0).getNodeValue();
}
}