package com.coupon.utils.xlsx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;

public final class XLSXReader {
    private XLSXReader() {
    }

    public static String readStringFromRow(final Row row, final FormulaEvaluator evaluator) {
        String data = "";
        for (Cell cell : row) {
            data += readStringFromCell(cell, evaluator);
        }

        return data;
    }

    public static boolean hasSingleValueInRow(final Row row, final FormulaEvaluator evaluator) {
        if (row.getPhysicalNumberOfCells() == 1) {
            return true;
        }

        int dataCount = 0;
        String data;

        for (Cell cell : row) {
            data = readStringFromCell(cell, evaluator);

            if (!data.equals("")) {
                dataCount += 1;
            }
        }

        return dataCount == 1;
    }

    public static boolean isEmpty(final Row row, final FormulaEvaluator evaluator) {
        if (row == null) {
            return true;
        }

        if (row.getPhysicalNumberOfCells() == 0) {
            return true;
        }

        String data = readStringFromRow(row, evaluator);

        if (data.equals("")) {
            return true;
        }

        return false;
    }

    public static String[] readStringsFromRow(final Row row, final FormulaEvaluator evaluator) {
        int maxCells = row.getLastCellNum();

        String[] data = new String[maxCells];

        for (int i = 0; i < maxCells; i += 1) {
            data[i] = readStringFromCell(row.getCell(i), evaluator);
        }

        return data;
    }

    public static Map<String, String> readStringsFromRow(final Row row, final List<String> headers,
            final FormulaEvaluator evaluator) {
        Map<String, String> map = new HashMap<>();

        String[] data = new String[headers.size()];

        for (int i = 0; i < headers.size(); i += 1) {
            if (row.getCell(i) != null) {
                data[i] = readStringFromCell(row.getCell(i), evaluator);
            } else {
                data[i] = "";
            }

            map.put(headers.get(i), data[i]);
        }

        return map;
    }

    public static String readStringFromCell(final Cell cell, final FormulaEvaluator evaluator) {
        String data = "";

        CellValue cellValue = evaluator.evaluate(cell);

        if (cellValue == null) {
            return data;
        }

        int cellType = cellValue.getCellType();

        if (cellType == Cell.CELL_TYPE_STRING) {
            data += cellValue.getStringValue();
        } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
            data += NumberToTextConverter.toText(cellValue.getNumberValue());
        } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
            data += String.valueOf(cellValue.getBooleanValue());
        }

        return data.trim();
    }

    public static Integer parseForInteger(final Entry<String, String> data, final boolean nullAllowed)
            throws XLSXException {
        Integer result = null;
        String resultString;

        resultString = parseForString(data, nullAllowed);

        if (resultString == null) {
            return result;
        }

        try {
            return (int) Double.parseDouble(resultString);
        } catch (NumberFormatException e) {
            throw new XLSXException(data.getKey() + " should be number.");
        }
    }

    public static String parseForString(final Entry<String, String> data, final boolean nullAllowed)
            throws XLSXException {
        String result = null;

        if (data.getValue() == null) {
            if (nullAllowed) {
                return result;
            } else {
                throw new XLSXException(data.getKey() + " does not exist.");
            }
        } else if (data.getValue().trim().equals("")) {
            if (nullAllowed) {
                return result;
            } else {
                throw new XLSXException(data.getKey() + " is empty.");
            }
        }

        return data.getValue().trim();
    }
}
