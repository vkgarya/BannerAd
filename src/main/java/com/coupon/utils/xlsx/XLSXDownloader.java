package com.coupon.utils.xlsx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class XLSXDownloader {

    private XLSXDownloader() {

    }

    public static void downloadXLSXCSVFile(final List<String> headers, final List<XLSXCSVRecord> cellValues,
                                           final HttpServletResponse response, final String outputFileName) throws XLSXException {
        String fileName = outputFileName + ".xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet0");
        int index, cellIndex = 0;

        // create Row for Header
        Row headerRow = sheet.createRow(0);

        // Header style
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);
        style.setFont(font);

        // Header
        Iterator<String> value = headers.iterator();
        while (value.hasNext()) {
            Cell cell = headerRow.createCell(cellIndex);
            cell.setCellValue(value.next().toString());
            cell.setCellStyle(style);
            cellIndex += 1;
        }

        int rowIndex = 1, headerIndex;
        List<XLSXCSVRecord> records = cellValues;

        for (XLSXCSVRecord record : records) {
            index = 0;
            Row row = sheet.createRow(rowIndex);

            for (headerIndex = 0; headerIndex < headers.size(); headerIndex += 1) {
                if (record.isMapped(headers.get(headerIndex))) {
                    row.createCell(index).setCellValue(record.getValues().get(headers.get(headerIndex)));
                }
                index += 1;
            }
            rowIndex += 1;
        }

        for (headerIndex = 0; headerIndex < headers.size(); headerIndex += 1) {
            sheet.autoSizeColumn(headerIndex);
        }


        downloadXLSXCSVFile(workbook, response, fileName);
    }


    public static void downloadXLSXCSVFile(final XSSFWorkbook workbook, final HttpServletResponse response, final String fileName) throws XLSXException {
        try {
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            workbook.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            response.setContentType("application/ms-excel");
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
            workbook.close();
        } catch (IOException e) {
            throw new XLSXException(e);
        }
    }
}
