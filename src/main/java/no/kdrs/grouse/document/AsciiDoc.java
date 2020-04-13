package no.kdrs.grouse.document;

import no.kdrs.grouse.model.ProjectRequirement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.max;

public class AsciiDoc {

    private BufferedWriter asciiDoc;
    private Map<String, List<String>> properties = new HashMap<>();

    public AsciiDoc(FileWriter fileWriter) {
        initialiseProperties();
        asciiDoc = new BufferedWriter(fileWriter);
    }

    public void addSectionHeader(String title, int level)
            throws IOException {
        String section = "=".repeat(max(0, level)) + " ";
        asciiDoc.write(section + title);
        asciiDoc.newLine();
        asciiDoc.newLine();
    }

    public void addText(String text) throws IOException {
        asciiDoc.newLine();
        asciiDoc.write(text);
        asciiDoc.newLine();
    }

    public void createTable(String title) throws IOException {
        asciiDoc.write("." + title);
        asciiDoc.newLine();
        asciiDoc.write("[cols=\"" + getColWidth("1") + "," +
                getColWidth("2") + "," +
                getColWidth("3") + "," +
                getColWidth("4") + "," +
                getColWidth("5") + "\"" +
                ", options = \"header\"]");
        asciiDoc.newLine();
        asciiDoc.write("|===");
        asciiDoc.newLine();
        asciiDoc.write("| " + getColText("1") +
                "| " + getColText("2") +
                "| " + getColText("3") +
                "| " + getColText("4") +
                "| " + getColText("5"));
        asciiDoc.newLine();
    }

    public void endTable() throws IOException {
        asciiDoc.write("|===");
        asciiDoc.newLine();
    }

    public void addRow(ProjectRequirement requirement,
                       String functionalityNumber,
                       Integer requirementNumber) throws IOException {
        if (!functionalityNumber.isEmpty()) {
            asciiDoc.write("| " + functionalityNumber +
                    "." + requirementNumber.toString());
        } else {
            asciiDoc.write("| " + requirementNumber.toString());
        }
        asciiDoc.write(" | " + requirement.getRequirementText().strip());
        asciiDoc.write(" | " + requirement.getPriority());
        asciiDoc.write(" | ");
        asciiDoc.write(" |");
        asciiDoc.newLine();
    }

    public void addToc() throws IOException {
        asciiDoc.newLine();
        asciiDoc.write(":toc:");
        asciiDoc.newLine();
        asciiDoc.newLine();
    }

    public void addPageBreak() throws IOException {
        asciiDoc.newLine();
        asciiDoc.write("<<<");
        asciiDoc.newLine();
        asciiDoc.newLine();
    }

    public void close() throws IOException {
        asciiDoc.newLine();
        asciiDoc.flush();
        asciiDoc.close();
    }

    private String getColText(String colNumber) {
        return properties.get("col" + colNumber).get(0);
    }

    private String getColWidth(String colNumber) {
        return properties.get("col" + colNumber).get(1);
    }

    protected void initialiseProperties() {
        properties.put("col1", Arrays.asList("Nr.", "10%"));
        properties.put("col2", Arrays.asList("Beskrivelse", "50%"));
        properties.put("col3", Arrays.asList("Pri.", "10%"));
        properties.put("col4", Arrays.asList("Svar", "20%"));
        properties.put("col5", Arrays.asList("Ref.", "10%"));
    }
}
