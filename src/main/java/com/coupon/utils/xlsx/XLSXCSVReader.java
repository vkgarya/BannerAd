package com.coupon.utils.xlsx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public final class XLSXCSVReader {

    private XLSXCSVReader() {

    }

    public static List<XLSXCSVRecord> readFromMultipartFile(final MultipartFile file) throws XLSXException {
        try {
            return readFromStream(file.getInputStream());
        } catch (IOException e) {
            throw new XLSXException(e.getMessage());
        }
    }

    public static List<XLSXCSVRecord> readFromFile(final FileInputStream fileInputStream) throws XLSXException {
        return readFromStream(fileInputStream);
    }

    private static List<XLSXCSVRecord> readFromStream(final InputStream stream) throws XLSXException {
        List<XLSXCSVRecord> records = null;

        try {
            Workbook workbook = WorkbookFactory.create(stream);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            if (workbook.getNumberOfSheets() < 1) {
                throw new XLSXException("No sheets");
            }

            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getPhysicalNumberOfRows() < 1) {
                throw new XLSXException("No rows");
            }

            Row row = sheet.getRow(0);

            String[] headers = XLSXReader.readStringsFromRow(row, evaluator);

            int lastHeader = headers.length - 1;

            for (int i = headers.length - 1; i >= 0; i -= 1) {
                if (!headers[i].trim().equals("")) {
                    lastHeader = i;
                    break;
                }
            }

            String[] validHeaders = new String[lastHeader + 1];

            for (int i = 0; i <= lastHeader; i += 1) {
                validHeaders[i] = headers[i];
            }

            for (int i = 0; i < validHeaders.length; i += 1) {
                if (headers[i].trim().equals("")) {
                    throw new XLSXException("Header cannot be empty. Header no: " + i);
                }
            }

            Set<String> headersSet = new HashSet<>(Arrays.asList(validHeaders));

            List<String> headersList = Arrays.asList(validHeaders);

            if (validHeaders.length != headersSet.size()) {
                throw new XLSXException("Duplicate headers");
            }

            int rowId = 1;

            row = sheet.getRow(rowId);

            records = new ArrayList<>();

            while (!XLSXReader.isEmpty(row, evaluator)) {
                XLSXCSVRecord record = new XLSXCSVRecord();

                record.setNumber(rowId);

                Map<String, String> recordData = XLSXReader.readStringsFromRow(row, headersList, evaluator);

                record.setValues(recordData);

                records.add(record);

                rowId++;
                row = sheet.getRow(rowId);
            }

        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            throw new XLSXException(e.getMessage());
        }

        return records;
    }
}
