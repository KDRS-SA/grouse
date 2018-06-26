package no.kdrs.grouse.document;

import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import static no.kdrs.grouse.utils.Constants.*;

/**
 * Created by tsodring on 10/28/17.
 */
public class Document {

    private OutputStream out;
    private XWPFDocument document;
    private XWPFStyles styles;
    // A table object that gets reinstantiated every time a new table is
    // created. Not using an array of tables as we likely do not need access
    // to the table after it has been added to the document
    private XWPFTable table;

    private String headingLevel1 = "headingLevel1";
    private String headingLevel2 = "headingLevel2";

    // The current row in the table when adding content
    // is reset every time a table is created
    private int rowNumber = 1;




    public Document(OutputStream out) {
        this.out = out;
        this.document = new XWPFDocument();
        XWPFStyles styles = document.createStyles();

        addCustomHeadingStyle(document, styles, headingLevel1, 1, 36, "4288BC");
        addCustomHeadingStyle(document, styles, headingLevel2, 2, 28, "4288BC");
    }

    public void addRow(ProjectRequirement requirement) {
        XWPFTableRow row = table.getRow(rowNumber++);
        row.getCell(COLUMN_NUMBER).setText(Integer.toString(rowNumber));
        row.getCell(COLUMN_FUNCTIONALITY_TITLE).setText(requirement.getRequirementText());
        row.getCell(COLUMN_PRIORITY).setText(requirement.getPriority());
        row.getCell(COLUMN_ANSWER).setText("");
        row.getCell(COLUMN_REFERENCE).setText("");
     }

    public void addSection(String sectionTitle) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(sectionTitle);
        run.addBreak();
    }

    public void addHeading1(String sectionTitle) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setStyle(headingLevel1);
        XWPFRun run = paragraph.createRun();
        run.setText(sectionTitle);
        run.addBreak();
    }

    public void addHeading2(String sectionTitle) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setStyle(headingLevel2);
        XWPFRun run = paragraph.createRun();
        run.setText(sectionTitle);
        run.addBreak();
    }

    public void addText(String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(11);
        run.setText(text);
        run.addBreak();
    }

    public void createTable(Integer numberOfRows, ProjectFunctionality
            functionality) {

            rowNumber = 1;
            // Add 1 to include the header
            table = document.createTable(numberOfRows + 1, REQUIRMENT_TABLE_NUMBER_COLUMNS);
            table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(REQUIRMENT_TABLE_NUMBER_WIDTH));
            table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(REQUIRMENT_TABLE_TITLE_WIDTH));
            table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(REQUIRMENT_TABLE_PRIORITY_WIDTH));
            table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(REQUIRMENT_TABLE_ANSWER_WIDTH));
            table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(REQUIRMENT_TABLE_REFERENCE_WIDTH));

            // Get the first row and add header values
            XWPFTableRow row = table.getRow(0);
            row.getCell(COLUMN_NUMBER).setText(TEXT_REQUIREMENT_NUMBER);
            row.getCell(COLUMN_FUNCTIONALITY_TITLE).setText(functionality.getTitle());
            row.getCell(COLUMN_PRIORITY).setText(TEXT_REQUIREMENT_PRIORITY);
            row.getCell(COLUMN_ANSWER).setText(TEXT_REQUIREMENT_ANSWER);
            row.getCell(COLUMN_REFERENCE).setText(TEXT_REFERENCE_REQUIREMENT);
    }

    public void close() throws IOException {
        document.write(out);
    }


    private static void addCustomHeadingStyle(XWPFDocument docxDocument, XWPFStyles styles,
                                              String strStyleId, int headingLevel, int pointSize, String hexColor) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);


        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        CTHpsMeasure size = CTHpsMeasure.Factory.newInstance();
        size.setVal(new BigInteger(String.valueOf(pointSize)));
        CTHpsMeasure size2 = CTHpsMeasure.Factory.newInstance();
        size2.setVal(new BigInteger("24"));

        CTFonts fonts = CTFonts.Factory.newInstance();
        fonts.setAscii("Loma" );

        CTRPr rpr = CTRPr.Factory.newInstance();
        rpr.setRFonts(fonts);
        rpr.setSz(size);
        rpr.setSzCs(size2);

        CTColor color=CTColor.Factory.newInstance();
        color.setVal(hexToBytes(hexColor));
        rpr.setColor(color);
        style.getCTStyle().setRPr(rpr);
        // is a null op if already defined

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }

    public static byte[] hexToBytes(String hexString) {
        HexBinaryAdapter adapter = new HexBinaryAdapter();
        byte[] bytes = adapter.unmarshal(hexString);
        return bytes;
    }
}
