package com.wontlost.views.xor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wontlost.ckeditor.*;
import com.wontlost.ckeditor.Constants.*;
import com.wontlost.cypher.EncryptData;
import com.wontlost.cypher.xor.XORCypher;
import com.wontlost.utils.Cipher;
import com.wontlost.views.MainView;
import org.jsoup.Jsoup;

import static com.wontlost.utils.Constant.*;

@Route(value = PAGE_XORDEC_EDITOR, layout = MainView.class)
@PageTitle(TITLE_XORDEC_EDITOR)
public class XorDecView extends VerticalLayout {

    XorDecView() {
        super();

        Config config = new Config();
        config.setPlaceHolder("Paste your data that need to be decrypted.");
        VaadinCKEditor classicEditor = new VaadinCKEditorBuilder()
                .with(builder -> builder.config = config).createVaadinCKEditor();

        classicEditor.setLabel("Decryption: ");
        classicEditor.setRequiredIndicatorVisible(false);

        VaadinCKEditor decodeEditor = new VaadinCKEditorBuilder().with(builder -> {
            builder.editorType = EditorType.INLINE;
            builder.editorData = "";
            builder.readOnly = true;
        }).createVaadinCKEditor();

        add(classicEditor);

        TextField key = new TextField("Set the key: ");
        add(key);

        add(new Label(""));
        Button decode = new Button("Decode");
        decode.addClickListener(event -> {
            if(key.getValue()==null || key.getValue().isEmpty()) {
                Cipher.notification("Key cannot be empty.").open();
                return;
            }
            String encodeValue = Jsoup.parse(classicEditor.getValue()).text();
            String decodeValue = XORCypher.decode(encodeValue, key.getValue(), EncryptData.innerKey().get("innerKey"));
            decodeEditor.setValue(decodeValue);
        });
        add(decode);
        add(new Label(""));
        add(decodeEditor);

        setAlignItems(Alignment.CENTER);
    }

}
