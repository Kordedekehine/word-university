package com.cbt.cbtapp.service.utilService;

import com.cbt.cbtapp.model.WordToLearn;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class VocabularyGenerator {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 36,
            Font.BOLD);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 20,
            Font.BOLD);
    private static final Font SMALL_BOLD_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static final String LOGO_PATH = "src/main/resources/image/lesson.png";


    public ByteArrayInputStream createVocabularyLessonPDF(String courseTitle, String lessonTitle, int
            indexOfLessonInCourse, List<WordToLearn> wordsToLearn) {

        try {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            //open the empty book and start the inputs
            document.open();
            addMetaData(document,lessonTitle);
            addContent(document,courseTitle,lessonTitle,indexOfLessonInCourse,wordsToLearn);
            document.close();

            //retrieve
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (DocumentException | IOException e) {
          //  throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    public void addMetaData(Document document,String lessonTitle){
        document.addTitle(String.format("%s's Vocabulary", lessonTitle));
        document.addSubject("vocabulary");
    }

    public void addContent(Document document, String courseTitle, String lessonTitle, int indexOfLessonInCourse,
                           List<WordToLearn> wordsToLearn) throws DocumentException, IOException {

       //the preface page first
        addPrefaceParagraph(document,courseTitle,lessonTitle,indexOfLessonInCourse);

      List<WordToLearn> completedWords = wordsToLearn.stream().
              filter(wordToLearn -> wordToLearn.getCollectedPoints() >= wordToLearn.getLesson().getCourse().getMinPointsPerWord())
              .toList();

        List<WordToLearn> inProgressWords = wordsToLearn.stream().filter(wordToLearn -> wordToLearn.getCollectedPoints() >= wordToLearn.getLesson().getCourse().getMinPointsPerWord())
                .toList();

        //1
        Paragraph tableSpace = new Paragraph("Unknown Words",
                SUBTITLE_FONT);
        tableSpace.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(tableSpace, 1);
        document.add(tableSpace);
        addVocabularyTable(document,inProgressWords);

       //spacing
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        document.add(paragraph);

        //2
        Paragraph tableSpace2 = new Paragraph("Understood Words",
                SUBTITLE_FONT);
        tableSpace2.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(tableSpace2, 1);
        document.add(tableSpace2);
        addVocabularyTable(document,completedWords);

    }

    private void addVocabularyTable(Document document, List<WordToLearn> wordToLearnList) throws DocumentException {
        //in our pdf we create a group table for the UNKNOWN WORDS ABD THE TRANSLATION FOR EASY COMPREHENSION
        PdfPTable pdfPTable = new PdfPTable(2);

        for (WordToLearn wordToLearn: wordToLearnList){
            PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(wordToLearn.getOriginalWord()));
            PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(wordToLearn.getTranslation()));
            pdfPTable.addCell(pdfPCell1);
            pdfPTable.addCell(pdfPCell2);
        }

        document.add(pdfPTable);
    }

    public void addPrefaceParagraph(Document document, String courseTitle,String lessonTitle,
                                    int indexOfLessonInCourse) throws DocumentException, IOException {

        Paragraph preface = new Paragraph();

        //logo first inside paragraph
        Image image = Image.getInstance(LOGO_PATH);
        image.setAlignment(Element.ALIGN_CENTER);
        image.setScaleToFitHeight(true);
        image.setBackgroundColor(BaseColor.BLUE);
        preface.add(image);
        addEmptyLine(preface,1);

        Paragraph subTitleSpace = new Paragraph("Vocabulary of", SMALL_BOLD_FONT);
        subTitleSpace.setAlignment(Element.ALIGN_CENTER);
        preface.add(subTitleSpace);

        //title pf the pdf
        Paragraph titleSpace = new Paragraph(lessonTitle, TITLE_FONT);
        titleSpace.setAlignment(Element.ALIGN_CENTER);
        preface.add(titleSpace);
        addEmptyLine(preface,1);


        Paragraph subtitleSpace2 = new Paragraph(String.format("Lesson number. %d of course %s",
                indexOfLessonInCourse, courseTitle),
                SMALL_BOLD_FONT);
        subtitleSpace2.setAlignment(Element.ALIGN_CENTER);
        preface.add(subtitleSpace2);
        addEmptyLine(preface, 3);

        document.add(preface);

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
