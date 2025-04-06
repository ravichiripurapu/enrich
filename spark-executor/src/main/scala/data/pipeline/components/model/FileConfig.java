package data.pipeline.components.model;

public class FileConfig {
    private String fileName;
    private String delimiter;
    private String fileType;
    private boolean header;

    // TODO : Future Schema
    private String schema;

    private boolean inferSchema;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public boolean isInferSchema() {
        return inferSchema;
    }

    public void setInferSchema(boolean inferSchema) {
        this.inferSchema = inferSchema;
    }
}
