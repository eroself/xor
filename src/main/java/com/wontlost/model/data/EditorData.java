package com.wontlost.model.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "xor")
public class EditorData {

    @Id
    private String id;
    private String editorId;
    private String editorContent;

    public EditorData(){

    }

    public EditorData(String editorId, String editorContent){
        this.editorId = editorId;
        this.editorContent = editorContent;
    }

    public String getEditorId() {
        return editorId;
    }

    public void setEditorId(String editorId) {
        this.editorId = editorId;
    }

    public String getEditorContent() {
        return editorContent;
    }

    public void setEditorContent(String editorContent) {
        this.editorContent = editorContent;
    }

}
