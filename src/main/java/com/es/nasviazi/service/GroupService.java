package com.es.nasviazi.service;

import com.es.nasviazi.model.GoogleData;
import com.es.nasviazi.model.YandexData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class GroupService {
    private final String synonyms = ",смартфон телефон,";

    public List<List<String>> getGroups(String keywords) {
        List<List<String>> res = new ArrayList<>();
        List<String> keywordsGroup = Arrays.stream(keywords.split("\r\n")).toList();

        for (int i = 0; i < keywordsGroup.size(); i++) {
            String currentKeywords = keywordsGroup.get(i);
            List<String> matchings = keywordsGroup.stream()
                    .filter(k -> checkIfTwoGroupsAreEqual(currentKeywords, k))
                    .toList();

            res.add(matchings);
        }

        return res.stream().distinct().toList();
    }

    public boolean checkIfTwoGroupsAreEqual(String g1, String g2) {
        Set<String> s1 = new HashSet<>(Arrays.asList(g1.split(" ")));
        Set<String> s2 = new HashSet<>(Arrays.asList(g2.split(" ")));

        if (s1.equals(s2)) {
            return true;
        }

        if (s1.size() == s2.size()) {
            List<String> l1 = new ArrayList<>(s1);
            List<String> l2 = new ArrayList<>(s2);
            label1: for (int i = 0; i < s1.size(); i++) {
                if (l1.get(i).equals(l2.get(i))) {
                    continue;
                }

                if (synonyms.contains(l1.get(i))) {
                    String s = l1.get(i);
                    int sIdx = synonyms.indexOf(s);

                    int end = synonyms.indexOf(',', sIdx);

                    int start = synonyms.lastIndexOf(',', sIdx);

                    List<String> syn = Arrays.stream(synonyms.substring(start + 1, end).split(" ")).toList();

                    for (String s3 : syn) {
                        if (l2.contains(s3)) {
                            continue label1;
                        }
                    }
                } else if (l2.contains(l1.get(i))) {
                    continue;
                }

                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public byte[] createGoogleExcel(GoogleData googleData) {
        List<List<String>> groups = getGroups(googleData.getKeywords());

        byte[] res;

        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("data");

            for (int i = 0; i < 24; i++) {
                sheet.setColumnWidth(i, 6000);
            }

            CellRangeAddress mergedRegion = new CellRangeAddress(0,0,3,22);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,0, 0);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,1, 1);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,2, 2);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,23, 23);
            sheet.addMergedRegion(mergedRegion);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);

            Row headerRow = sheet.createRow(0);

            Cell cell0 = headerRow.createCell(0);
            cell0.setCellValue("Компания");
            cell0.setCellStyle(cellStyle);

            Cell cell1 = headerRow.createCell(1);
            cell1.setCellValue("Группа");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = headerRow.createCell(2);
            cell2.setCellValue("Ключевые слова для группы Google Ads");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = headerRow.createCell(3);
            cell3.setCellValue("Объявление");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = headerRow.createCell(23);
            cell4.setCellValue("Ссылка");
            cell4.setCellStyle(cellStyle);

            Row headerRow2 = sheet.createRow(1);

            int counter = 0;

            for (int i = 0; i < 10; i += 2) {
                Cell cellH = headerRow2.createCell(i + 3);
                cellH.setCellValue("Заголовок " + (counter + 1));
                cellH.setCellStyle(cellStyle);

                Cell cellP = headerRow2.createCell(i + 4);
                cellP.setCellValue("Позиция " + (counter + 1));
                cellP.setCellStyle(cellStyle);
                counter++;
            }

            counter = 0;

            for (int i = 0; i < 8; i += 2) {
                Cell cellD = headerRow2.createCell(i + 13);
                cellD.setCellValue("Описание " + (counter + 1));
                cellD.setCellStyle(cellStyle);

                Cell cellP = headerRow2.createCell(i + 14);
                cellP.setCellValue("Позиция " + (counter + 1));
                cellP.setCellStyle(cellStyle);
                counter++;
            }

            Cell cell2_3 = headerRow2.createCell(21);
            cell2_3.setCellValue("Путь 1");
            cell2_3.setCellStyle(cellStyle);

            Cell cell2_4 = headerRow2.createCell(22);
            cell2_4.setCellValue("Путь 2");
            cell2_4.setCellStyle(cellStyle);


            int currentRow = 1;
            int firstRowIdx = 2;

            for (int i = 0; i < groups.size(); i++) {
                List<String> currentGroup = groups.get(i);

                int rowCountForGroup = currentGroup.size();

                for (int j = 0; j < rowCountForGroup; j++) {
                    Row row = sheet.createRow(++currentRow);
                    if (currentRow == firstRowIdx) {
                        Cell companyCell = row.createCell(0);
                        companyCell.setCellValue(googleData.getProject());
                    }

                    if (j == 0) {
                        Cell groupNameCell = row.createCell(1);
                        groupNameCell.setCellValue(currentGroup.iterator().next());

                        Cell cell5 = row.createCell(3);
                        cell5.setCellValue(currentGroup.get(0));

                        cell5 = row.createCell(4);
                        cell5.setCellValue(1);

                        int count = 0;
                        for (int k = 0; k < 8; k += 2) {
                            Cell cellH = row.createCell(5 + k);
                            cellH.setCellValue(googleData.getAdditionalHeaders().get(count));

                            Cell cellP = row.createCell(6 + k);
                            cellP.setCellValue(googleData.getPositions().get(count));
                            count++;
                        }
                        count = 0;

                        for (int k = 0; k < 8; k += 2) {
                            Cell cellH = row.createCell(13 + k);
                            cellH.setCellValue(googleData.getDescriptions().get(count));

                            Cell cellP = row.createCell(14 + k);
                            cellP.setCellValue(1);
                            count++;
                        }

                        Cell cell = row.createCell(21);
                        cell.setCellValue(googleData.getVisibleUrls().get(0));

                        cell = row.createCell(22);
                        cell.setCellValue(googleData.getVisibleUrls().get(1));

                        cell = row.createCell(23);
                        cell.setCellValue(googleData.getTargetUrl());
                    }

                    if (currentGroup.size() > j) {
                        String currentKeyWords = currentGroup.get(j);
                        Cell dataCell2 = row.createCell(2);
                        dataCell2.setCellValue(currentKeyWords);
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            res = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public byte[] createExcel(YandexData yandexData) {
        List<List<String>> groups = getGroups(yandexData.getKeywords());

        byte[] res;

        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("data");

            for (int i = 0; i < 8; i++) {
                sheet.setColumnWidth(i, 6000);
            }

            CellRangeAddress mergedRegion = new CellRangeAddress(0,0,3,5);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,0, 0);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,1, 1);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,2, 2);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,6, 6);
            sheet.addMergedRegion(mergedRegion);
            mergedRegion = new CellRangeAddress(0,1,7, 7);
            sheet.addMergedRegion(mergedRegion);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);

            Row headerRow = sheet.createRow(0);

            Cell cell0 = headerRow.createCell(0);
            cell0.setCellValue("Компания");
            cell0.setCellStyle(cellStyle);

            Cell cell1 = headerRow.createCell(1);
            cell1.setCellValue("Группа");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = headerRow.createCell(2);
            cell2.setCellValue("Ключевые слова для группы Яндекс Директ");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = headerRow.createCell(3);
            cell3.setCellValue("Объявление");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = headerRow.createCell(6);
            cell4.setCellValue("Ссылка");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = headerRow.createCell(7);
            cell5.setCellValue("Отображаемая ссылка");
            cell5.setCellStyle(cellStyle);

            Row headerRow2 = sheet.createRow(1);

            Cell cell2_3 = headerRow2.createCell(3);
            cell2_3.setCellValue("Заголовок 1");
            cell2_3.setCellStyle(cellStyle);

            Cell cell2_4 = headerRow2.createCell(4);
            cell2_4.setCellValue("Заголовок 2");
            cell2_4.setCellStyle(cellStyle);

            Cell cell2_5 = headerRow2.createCell(5);
            cell2_5.setCellValue("Описание");
            cell2_5.setCellStyle(cellStyle);

            int currentRow = 1;
            int firstRowIdx = 2;

            for (int i = 0; i < groups.size(); i++) {
                List<String> currentGroup = groups.get(i);

                int rowCountForGroup = Math.max(currentGroup.size(), 3) + 1;

                for (int j = 0; j < rowCountForGroup; j++) {
                    Row row = sheet.createRow(++currentRow);
                    if (currentRow == firstRowIdx) {
                        Cell companyCell = row.createCell(0);
                        companyCell.setCellValue(yandexData.getProject());
                    }

                    if (j == 0) {
                        Cell groupNameCell = row.createCell(1);
                        groupNameCell.setCellValue(currentGroup.iterator().next());
                    }

                    if (currentGroup.size() > j) {
                        String currentKeyWords = currentGroup.get(j);
                        Cell dataCell2 = row.createCell(2);
                        dataCell2.setCellValue(currentKeyWords);
                    }

                    if (j < 3) {
                        Cell dataHeader1 = row.createCell(3);
                        dataHeader1.setCellValue(currentGroup.get(0));

                        Cell dataHeader2 = row.createCell(4);
                        dataHeader2.setCellValue(yandexData.getVariants().get(j).getHeader());

                        Cell dataDesc = row.createCell(5);
                        dataDesc.setCellValue(yandexData.getVariants().get(j).getDescription());

                        Cell dataUrl = row.createCell(6);
                        dataUrl.setCellValue(yandexData.getTargetUrl());

                        Cell dataVisibleUrl = row.createCell(7);
                        dataVisibleUrl.setCellValue(yandexData.getVisibleUrl());
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            res = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
