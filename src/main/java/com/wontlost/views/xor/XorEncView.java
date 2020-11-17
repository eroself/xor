package com.wontlost.views.xor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.wontlost.ckeditor.*;
import com.wontlost.ckeditor.Constants.*;
import com.wontlost.cypher.EncryptData;
import com.wontlost.cypher.xor.XORCypher;
import com.wontlost.model.data.EditorData;
import com.wontlost.utils.Cipher;
import com.wontlost.views.MainView;
import org.apache.commons.lang3.RandomStringUtils;

import static com.wontlost.utils.Constant.*;

@Route(value = PAGE_XORENC_EDITOR, layout = MainView.class)
@RouteAlias(value = PAGE_ROOT, layout = MainView.class)
@PageTitle(TITLE_XORENC_EDITOR)
public class XorEncView extends VerticalLayout {

    private String innerKey;

    XorEncView() {
        super();
        EditorData message = new EditorData();
        message.setEditorContent(null);
        Binder<EditorData> binder = new Binder<>(EditorData.class);

        Config config = new Config();
        config.setPlaceHolder("Write your data that need to be encrypted.");
        VaadinCKEditor classicEditor = new VaadinCKEditorBuilder()
                .with(builder -> builder.config = config).createVaadinCKEditor();
        classicEditor.setLabel("Encryption: ");
        classicEditor.setRequiredIndicatorVisible(false);

        Binder.Binding<EditorData, String> binding = binder.forField(classicEditor)
//                .withValidator(content->content!=null && content.length()>0, "Empty is not allowed")
//                .withValidator(content->content!=null && content.length()<20, "Too many words")
//                .withConverter(new Converter<String, String>() {
//                    @Override
//                    public Result<String> convertToModel(String s, ValueContext valueContext) {
//                        return Result.ok(s+"--convertToModel");
//                    }
//
//                    @Override
//                    public String convertToPresentation(String o, ValueContext valueContext) {
//                        return o+"--convertToPresentation";
//                    }
//                })
                .bind(EditorData::getEditorContent, EditorData::setEditorContent);
        binder.setBean(message);
        binder.readBean(message);
        classicEditor.addValueChangeListener(event -> {
                classicEditor.setErrorMessage(null);
                binding.validate();
            }
        );

        VaadinCKEditor encodeEditor = new VaadinCKEditorBuilder().with(builder -> {
            builder.editorType = EditorType.INLINE;
            builder.readOnly = true;
        }).createVaadinCKEditor();

        add(classicEditor);

        TextField key = new TextField("Set the key: ");
        key.setRequiredIndicatorVisible(true);
        add(key);

        add(new Label(""));
        Button encode = new Button("Encode");
        encode.addClickListener(event -> {
            if(key.getValue()==null || key.getValue().isEmpty()) {
                Cipher.notification("Key cannot be empty.").open();
                return;
            }
            innerKey = RandomStringUtils.randomPrint(key.getValue().length()+3);
            EncryptData.innerKey().put("innerKey", innerKey);
            String classicValue = classicEditor.getValue();
            String encryptedData = XORCypher.encode(classicValue, key.getValue(), EncryptData.innerKey().get("innerKey"));
            encodeEditor.setValue(encryptedData);
        });

        Button clear = new Button("Clear");
        clear.addClickListener(event -> {
            binder.readBean(null);
            classicEditor.getConfig();
        });

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(encode, clear);
        add(horizontal);

        add(new Label(""));
        add(encodeEditor);

        add(new Label(""));

        setAlignItems(Alignment.CENTER);
    }

}
